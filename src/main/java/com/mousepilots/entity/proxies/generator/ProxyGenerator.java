/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

import com.mousepilots.web.shared.domain.generated.proxies._Entities.Transient.When;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.BeanProperty;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.Type;
import com.thoughtworks.qdox.model.annotation.AnnotationValue;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.codehaus.plexus.util.DirectoryScanner;

    

/**
 * Goal which generates entity proxies and supporting classes
 *
 * @goal generateProxies
 * @phase generate-sources
 * @requiresDependencyResolution runtime
 * @author <a href="mailto:jurjenvangeenen@gmail.com">Jurjen van Geenen</a>
 * @version $Id$
 */
public class ProxyGenerator extends AbstractMojo {

    public final static Map<String, String> WRAPPERS = new HashMap<String, String>();

    static {
        WRAPPERS.put(boolean.class.getName(), Boolean.class.getName());
        WRAPPERS.put(byte.class.getName(), Byte.class.getName());
        WRAPPERS.put(char.class.getName(), Character.class.getName());
        WRAPPERS.put(short.class.getName(), Short.class.getName());
        WRAPPERS.put(int.class.getName(), Integer.class.getName());
        WRAPPERS.put(long.class.getName(), Long.class.getName());
        WRAPPERS.put(float.class.getName(), Float.class.getName());
        WRAPPERS.put(double.class.getName(), Double.class.getName());
    }
    
    static final Map<Class, Class> RUNTIME_COLLECTION_TYPE = new HashMap<Class, Class>();

    static {
        RUNTIME_COLLECTION_TYPE.put(Collection.class, ArrayList.class);
        RUNTIME_COLLECTION_TYPE.put(List.class, ArrayList.class);
        RUNTIME_COLLECTION_TYPE.put(Set.class, HashSet.class);
        RUNTIME_COLLECTION_TYPE.put(SortedSet.class, TreeSet.class);
        RUNTIME_COLLECTION_TYPE.put(SortedMap.class, TreeMap.class);
        RUNTIME_COLLECTION_TYPE.put(Map.class, HashMap.class);
    }
    
    public static final Comparator<JavaClass> JAVA_CLASS_COMPARATOR = new Comparator<JavaClass>() {
        @Override
        public int compare(JavaClass o1, JavaClass o2) {
            return o1.getFullyQualifiedName().compareTo(o2.getFullyQualifiedName());
        }
    };
    public static final Comparator<BeanProperty> BEAN_PROPERTY_COMPARATOR = new Comparator<BeanProperty>() {
        @Override
        public int compare(BeanProperty o1, BeanProperty o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    /**
     * @parameter default-value="${project}"
     */
    private org.apache.maven.project.MavenProject project;
    
    
    private final TreeMap<String, _ClassDescriptor> classNameToClassDescriptor = new TreeMap<String, _ClassDescriptor>();
    private final TreeSet<_PropertyDescriptor> propertyDescriptors = new TreeSet<_PropertyDescriptor>();
    private final ArrayList<_Relation> relations = new ArrayList<_Relation>();

    /**
     * @parameter property="${project.build.sourceEncoding}"
     */
    private String encoding;
    /**
     * Pattern for {@link javax.persistence.Entity} source files
     *
     * @parameter
     * default-value="**\/*.java"
     */
    private String entityPattern;
    
    /**
     * Fully qualified package name to generate source files in - this plugin MUST have this package and all its usb-packages to itself
     * @parameter
     * @required
     */
    private String packageName;

    /**
     * Fully qualified package name of the interface which every entity implements
     * @parameter
     * @required
     */
    private String entityInterface;
    
    
    /**
     * The fully qualified class-names of validation groups to be included in the GWT Validation factory.
     * @parameter
     * @required
     */
    protected HashSet<String> clientValidationGroups;
    
    
    
    /**
     * FQNs of classes to add to the GWT Validation factory, besides the entity-classes found by this Mojo
     * @parameter
     */
    protected HashSet<String> additionalValidatedClasses;

    /**
     * Folder where generated sources will be created (automatically added to
     * compile classpath).
     *
     * @parameter
     * default-value="${project.build.directory}/generated-sources/entity-proxies"
     */
    private File generateDirectory;
    /**
     * Pattern for GWT service interface
     *
     * @parameter default-value="false" property="${generateAsync.force}"
     */
    private boolean force;
    
    
    /**
     * the velocity engine
     */
    private final VelocityEngine velocityEngine;
    
    
    public ProxyGenerator(){
        super();
        velocityEngine = new VelocityEngine();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init(props);
    }

  
    
    
    private boolean containsAnnotation(Annotation[] annotations, Class annotationClass) {
        return containsAnnotation(annotations, annotationClass.getName());
    }
    
    private boolean containsAnnotation(Annotation[] annotations, String annotationFQN) {
        StringBuilder message = new StringBuilder("ProxyGenerator#containsAnnotation. \n\tsearching for annotation class ").append(annotationFQN).append(" among the following annotations:");
        for (Annotation annotation : annotations) {
            message.append("\n\t\t* ").append(annotation.getType().getFullyQualifiedName());
            if (annotation.getType().getFullyQualifiedName().equals(annotationFQN)) {
                message.append("\n\t return true");
                getLog().debug(message.toString());
                return true;
            }
        }
        message.append("\n\t return false");
        getLog().debug(message.toString());
        return false;
    }

    private static Annotation getAnnotation(Annotation[] annotations, String annotationFQN) {
        for (Annotation a : annotations) {
            if (a.getType().getFullyQualifiedName().equals(annotationFQN)) {
                return a;
            }
        }
        return null;
    }

    private static Annotation getAnnotation(Annotation[] annotations, Class annotationClass) {
        return getAnnotation(annotations, annotationClass.getName());
    }
    
    
    private boolean isEligibleForGeneration(JavaClass javaClass) {
        return containsAnnotation(javaClass.getAnnotations(), Entity.class);
    }

    /**
     * @return the project classloader
     * @throws DependencyResolutionRequiredException failed to resolve project
     * dependencies
     * @throws MalformedURLException configuration issue ?
     */
    protected ClassLoader getProjectClassLoader() throws MalformedURLException, DependencyResolutionRequiredException {
        getLog().debug("AbstractMojo#getProjectClassLoader()");

        List<?> compile = project.getCompileClasspathElements();
        URL[] urls = new URL[compile.size()];
        int i = 0;
        for (Object object : compile) {
            if (object instanceof Artifact) {
                Artifact artifact = (Artifact) object;
                urls[i] = artifact.getFile().toURI().toURL();
                
            } else {
                String string = (String) object;
                urls[i] = new File(string).toURI().toURL();
            }
            getLog().debug("\t " + urls[i]);
            i++;
        }
        return new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
    }

    @SuppressWarnings("unchecked")
    private JavaDocBuilder createJavaDocBuilder() throws MojoExecutionException {
        try {
            JavaDocBuilder builder = new JavaDocBuilder();
            //builder.setEncoding(encoding);
            builder.getClassLibrary().addClassLoader(getProjectClassLoader());
            for (String sourceRoot : (List< String>) project.getCompileSourceRoots()) {
                builder.getClassLibrary().addSourceFolder(new File(sourceRoot));
            }
            return builder;
        } catch (Throwable e) {
            throw new MojoExecutionException("Failed to resolve project classpath", e);
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(!generateDirectory.exists()){
            generateDirectory.mkdir();
        }
        getLog().debug("GenerateEntityProxies#execute()");
        if (encoding == null) {
            getLog().warn("Encoding is not set, your build will be platform dependent");
            encoding = Charset.defaultCharset().name();
        }
        JavaDocBuilder builder = createJavaDocBuilder();

        List<String> sourceRoots = project.getCompileSourceRoots();
        boolean entitiesFound = false;
        for (String sourceRoot : sourceRoots) {
            try {
                entitiesFound |= scanForEntities(new File(sourceRoot), builder);
            } catch (Throwable e) {
                getLog().error("Failed to generate Proxy class", e);
                throw new MojoExecutionException("Failed to generate Proxy class", e);
            }
        }
        
        
        if (entitiesFound) {
            for(_ClassDescriptor cd : this.classNameToClassDescriptor.values()){
                for(_PropertyDescriptor pd : cd.propertyDescriptors){
                    setRelation(cd.entityClass, pd);
                }
            }
            if(!generateDirectory.getAbsolutePath().endsWith("gwt")){
                getLog().debug("add compile source root " + generateDirectory);
                project.addCompileSourceRoot(generateDirectory.getAbsolutePath());
            }
            if(this.additionalValidatedClasses!=null){
                for(String additonalClass : additionalValidatedClasses){
                    this.validatedClasses.add(additonalClass + ".class");
                }
            }
            generate();
        }
    }

    private String getTopLevelClassName(String sourceFile) {
        String className = sourceFile.substring(0, sourceFile.length() - 5); // strip ".java"
        return className.replace(File.separatorChar, '.');
    }

    /**
     * @param sourceRoot the base directory to scan for RPC services
     * @return true if an {@link Entity}-annotated class was found
     * @throws Exception generation failure
     */
    private boolean scanForEntities(File sourceRoot, JavaDocBuilder builder) throws Exception {
        getLog().info("scanning " + sourceRoot + " for @Entity annotated classes in source-files within pattern " + this.entityPattern);
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setBasedir(sourceRoot);
        scanner.setIncludes(new String[]{entityPattern});
        scanner.scan();
        String[] sources = scanner.getIncludedFiles();
        getLog().info("found " + sources.length + " candidates");
        if (sources.length == 0) {
            getLog().debug("\tno @Entity-annotated classes found in " + sourceRoot + " matching " + entityPattern);
            return false;
        }
        boolean fileGenerated = false;
        for (String source : sources) {
            final String className = getTopLevelClassName(source);
            final JavaClass clazz = builder.getClassByName(className);
            if (isEligibleForGeneration(clazz)) {
                getLog().debug("\tAdded entity " + className + " for proxy generation");
                addEntityClass(clazz);
                fileGenerated = true;
            } else {
                getLog().debug("\t" + className + " seems no @Entity");
            }
        }
        return fileGenerated;
    }

    private boolean containsTypeArgument(Type[] typeArguments, Type type) {
        if (typeArguments != null) {
            for (Type ta : typeArguments) {
                if (ta.isA(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param sourceAndOwner the source of the relation
     * @param r {@code r.target} and {@code r.property} must be set
     * @param expectedInverseArity the expected arity of the inverse mapping - a
     * {@link javax.persistence} annotation
     * @return the target inverse property
     */
    private _PropertyDescriptor getTargetInverseProperty(_ClassDescriptor sourceAndOwner, _Relation r, Class expectedInverseArity) {
        for (_PropertyDescriptor pd : r.target.propertyDescriptors) {
            boolean isCandidate = false;
            if (r.arity.isFromOne()) {
                //maps back to source class
                if (pd.field.getType().isA(sourceAndOwner.entityClass.asType())) {
                    isCandidate = true;
                }
            } else {
                Type[] actualTypeArguments = pd.field.getType().getActualTypeArguments();
                if (actualTypeArguments != null && containsTypeArgument(actualTypeArguments, sourceAndOwner.entityClass.asType())) {
                    isCandidate = true;
                }
            }
            if (isCandidate) {
                final Annotation annotation = getAnnotation(pd.field.getAnnotations(), expectedInverseArity);
                if (annotation != null) {
                    AnnotationValue property = annotation.getProperty("mappedBy");
                    if (property != null && property.getParameterValue().equals(r.property.field.getName())) {
                        return pd;
                    }
                }
            }
        }
        return null;
    }

    private String getTransientAnnotationFQN(){
        return packageName + "._Entities$Transient";
    }
    
    /** all entries end with {@code ".class"} */
    private final TreeSet<String> validatedClasses = new TreeSet<String>();
    
    /** contains the class descriptors in need of custom field serializers*/
    private final TreeSet<_ClassDescriptor> needCustomSerializer = new TreeSet<_ClassDescriptor>();
    
    
    
    /**
     * Scans the provided class and its properties, adds the result to {@link #classNameToClassDescriptor}.
     *
     * @param entityClass
     * @throws MojoExecutionException
     */
    private void addEntityClass(JavaClass entityClass) throws MojoExecutionException {
        _ClassDescriptor cd = new _ClassDescriptor(packageName);
        cd.entityClass = entityClass;
        getLog().debug("matching fields to bean properties for " + entityClass + ":");
        String transientAnnotationFQN = getTransientAnnotationFQN();
        boolean needsClientSideValidation = false;
        for (JavaField field : entityClass.getFields()) {
            if (field.isStatic() || field.isFinal() || field.isNative() || field.isTransient() || field.isVolatile()) {
                continue;
            }
            _PropertyDescriptor pd = new _PropertyDescriptor();
            pd.field = field;
            pd.owner = cd;
            //determine transience
            if(containsAnnotation(field.getAnnotations(), "com.google.gwt.user.client.rpc.GwtTransient") ){
                pd.when = When.always;
                getLog().debug("\tmarking field " + field + " on " + entityClass + " as " + pd.when + " transient");
            } else {
              if(containsAnnotation(field.getAnnotations(), transientAnnotationFQN)){
                    Annotation annotation = getAnnotation(field.getAnnotations(), transientAnnotationFQN);
                    logAnnotation(pd, annotation);
                    pd.when = When.valueOf(getParameterValue(annotation, "transience").trim().replaceAll(".?When\\.", ""));
                    pd.transientValueExpression = getStringParameterValue(annotation, "transientValueExpression");
                    if(!pd.when.equals(When.never)){
                        this.needCustomSerializer.add(cd);
                    }
              }  
       }
            
            //scan for (Embedded-)Id
            if (containsAnnotation(field.getAnnotations(), Id.class) || containsAnnotation(field.getAnnotations(), EmbeddedId.class)) {
                if (cd.idProperty != null) {
                    throw new MojoExecutionException(entityClass + " has multiple @Id annotated fields: " + cd.idProperty.field.getName() + " and " + field.getName());
                } else {
                    getLog().debug("\tfound Id field " + field + " on " + entityClass);
                    pd.isId = true;
                    cd.idProperty = pd;
                }
            }
            pd.isGenerated = containsAnnotation(field.getAnnotations(), GeneratedValue.class);
            
            //scan for @version
            if (containsAnnotation(field.getAnnotations(), Version.class)){
                pd.isVersion=true;
                cd.versionProperty=pd;
            }
            //match bean-property to field
            for (BeanProperty bp : entityClass.getBeanProperties()) {
                if (bp.getName().equalsIgnoreCase(field.getName())) {
                    pd.property = bp;
                    getLog().debug("\tmatched bean-property " + bp + " to field " + field);
                    break;
                }
            }
            if (pd.property == null) {
                throw new MojoExecutionException("unable to find bean-property for field " + field + " on " + entityClass.getFullyQualifiedName());
            }
            
            cd.propertyDescriptors.add(pd);
            cd.fieldNameToPropertyDescriptor.put(field.getName(), pd);
            this.propertyDescriptors.add(pd);
            
            
        }
        if (cd.idProperty == null) {
            throw new MojoExecutionException(" unable to find @Id or @EmbbededId-annotated field on " + entityClass.getFullyQualifiedName());
        }
        this.classNameToClassDescriptor.put(entityClass.getFullyQualifiedName(), cd);
        this.validatedClasses.add(cd.getEntityName() + ".class");
        this.validatedClasses.add(cd.getProxyName()  + ".class");
    }
    
    private void logAnnotation(_PropertyDescriptor pd, Annotation annotation){
        StringBuilder message = new StringBuilder(annotation.toString()).append(" on field ").append( pd.field.getName() ).append(" of ").append(pd.owner.entityClass).append(" has: ")
                .append("\n\tparameterValue=").append(annotation.getParameterValue()).
                append("\n\tparameterMap:");
        for(Object k : annotation.getNamedParameterMap().keySet()){
            message.append("\n\t\t").append(k).append(" --> ").append(annotation.getNamedParameterMap().get(k));
            
        }
        message.append("\n\tpropertyMap:");
        for(Object k : annotation.getPropertyMap().keySet()){
            message.append("\n\t\t").append(k).append(" --> ").append(annotation.getPropertyMap().get(k));
        } 

        Object mappedBy=annotation.getNamedParameter("mappedBy");
        if(mappedBy!=null){
            String mappedByString = ((String)mappedBy).trim();
            mappedByString = mappedByString.substring(1,mappedByString.length()-1);
            message.append("\nfound named-parameter \"mappedBy\" ").append(" of ").append(mappedBy.getClass()).append(" with String value ").append(mappedByString);
        }
        getLog().debug(message.toString());
    }

    private String getParameterValue(Annotation annotation, String parameterName){
        Object parameterValue=annotation.getNamedParameter(parameterName);
        if(parameterValue!=null){
            return ((String)parameterValue).trim();
        }
        return null;
    }
    
    
    private String getStringParameterValue(Annotation annotation, String parameterName){
        Object parameterValue=annotation.getNamedParameter(parameterName);
        if(parameterValue!=null){
            String parameterValueString = ((String)parameterValue).trim();
            parameterValueString = parameterValueString.substring(1,parameterValueString.length()-1);
            return parameterValueString;
        }
        return null;
    }
    
    
    private ArrayList<String> getObjectArrayParameterValues(Annotation annotation, String parameterName){
        ArrayList<String> retval = new ArrayList<String>();
        Object parameterValue=annotation.getNamedParameter(parameterName);
        if(parameterValue!=null){
            if(parameterValue instanceof Collection){
                StringBuilder message = new StringBuilder(annotation.toString()).append(" has a collection-valued parameter ").append(parameterName).append(" with the following toString() values:");
                for(Object o : (Collection) parameterValue){
                    String value = o.toString().trim();
                    message.append("\n\t* ").append(value);
                    retval.add(value);
                }
                getLog().debug(message.toString());
            } else {
                String parameterValueString = ((String)parameterValue).trim();
                if(0<parameterValueString.length() && parameterValueString.startsWith("{") && parameterValueString.endsWith("}")){
                    //remove { and }
                    parameterValueString = parameterValueString.substring(1,parameterValueString.length()-1);
                    String[] values = parameterValueString.split(",");
                    for(String value : values){
                        retval.add(value.trim());
                    }
                } else {
                    retval.add(((String)parameterValue).trim());
                }
            }
        }
        return retval;
    }
    

    /**
     * Sets the relation (if any) on {@code pd} AND its traget inverse (if any)
     *
     * @param entityClass owner of the property
     * @param property the property to set the relation on
     */
    private void setRelation(final JavaClass entityClass, _PropertyDescriptor pd) {
        if (pd.setRelation) {
            return;
        }
        //determine the relation type, and check if relationClass owns the relation
        Annotation[] annotations = pd.field.getAnnotations();
        _Relation r = null;
        {   //try OneToOne
            Annotation a = getAnnotation(annotations, OneToOne.class);
            if (a != null) {
                logAnnotation(pd, a);
                r = new _Relation();
                r.arity = _Arity.OneToOne;
                r.property = pd;
                r.target = this.classNameToClassDescriptor.get(pd.field.getType().getFullyQualifiedName());
                String mappedBy = getStringParameterValue(a, "mappedBy");
                //is entityClass the owning class?
                if (mappedBy == null) {
                    //yes: scan targetClass for inverse mapping
                    r.owningSide = true;
                    r.targetInverse = getTargetInverseProperty(pd.owner, r, OneToOne.class);
                } else {
                    //no: get info from annotation
                    r.owningSide = false;
                    r.targetInverse = r.target.fieldNameToPropertyDescriptor.get(mappedBy);
                }
            }
        }
        {   //try OneToMany
            Annotation a = getAnnotation(annotations, OneToMany.class);
            if (a != null) {
                logAnnotation(pd, a);
                r = new _Relation();
                r.arity = _Arity.OneToMany;
                r.property = pd;
                r.target = this.classNameToClassDescriptor.get(pd.field.getType().getActualTypeArguments()[0].getFullyQualifiedName());
                String mappedBy = getStringParameterValue(a, "mappedBy");
                //is pd.owner the owning class?
                if (mappedBy == null) {
                    //YES: implies unidirectional OneToMany relationship
                    r.owningSide = true;
                } else {
                    //NO: get info from annotation
                    r.owningSide = false;
                    r.targetInverse = r.target.fieldNameToPropertyDescriptor.get(mappedBy);
                }
            }
        }
        {   //try ManyToOne
            Annotation a = getAnnotation(annotations, ManyToOne.class);
            if (a != null) {
                logAnnotation(pd, a);
                // @ManyToOne implies owenership
                r = new _Relation();
                r.arity = _Arity.ManyToOne;
                r.property = pd;
                r.target = this.classNameToClassDescriptor.get(pd.field.getType().getFullyQualifiedName());
                r.owningSide = true;
                r.targetInverse = getTargetInverseProperty(pd.owner, r, OneToMany.class);
            }
        }
        {
            //Try ManyToMany
            Annotation a = getAnnotation(annotations, ManyToMany.class);
            if (a != null) {
                logAnnotation(pd, a);
                r = new _Relation();
                r.arity = _Arity.ManyToMany;
                r.property = pd;
                r.target = this.classNameToClassDescriptor.get(pd.field.getType().getActualTypeArguments()[0].getFullyQualifiedName());
                String mappedBy = getStringParameterValue(a, "mappedBy");
                //is pd.owner the owning class?
                if (mappedBy == null) {
                    //yes: scan targetClass for inverse mapping
                    r.owningSide = true;
                    r.targetInverse = getTargetInverseProperty(pd.owner, r, ManyToMany.class);
                } else {
                    //no: get info from annotation
                    r.owningSide = false;
                    r.targetInverse = r.target.fieldNameToPropertyDescriptor.get(mappedBy);
                }
            }
        }

        if (r != null) {
            
            pd.setRelation = true;
            pd.relation = r;
            this.relations.add(r);
            if (r.hasInverse()) {
                _Relation i = new _Relation();
                i.arity = r.arity.getInverse();
                i.owningSide = !r.owningSide;
                i.target = pd.owner;
                i.property = r.targetInverse;
                i.targetInverse = r.property;
                r.targetInverse.relation = i;
                r.targetInverse.setRelation = true;
                this.relations.add(i);
                getLog().debug("set relation " + r + " with inverse " + i);
            } else {
                getLog().debug("set relation " + r);
            }
        }
    }

    private boolean isUpToDate(File sourceFile, File targetFile) {
        return !force && targetFile.exists() && targetFile.lastModified() > sourceFile.lastModified();
    }

    private void generateProxy(JavaClass clazz, File targetFile) {

        JavaField[] fields = clazz.getFields();
        BeanProperty[] beanProperties = clazz.getBeanProperties();

    }
    
    private String toPath(String className) {
        return new StringBuilder(generateDirectory.getAbsolutePath())
                .append(File.separatorChar)
                .append( className.replace('.', File.separatorChar) )
                .append(".java")
                .toString();
    }    
    
    protected void classToDisk(StringWriter writer, String className) {
        String path = toPath(className);
        File classFile = new File(path);
        if (!classFile.getParentFile().exists()) {
            classFile.getParentFile().mkdirs();
        }
        if (classFile.exists()) {
            classFile.delete();
        }
        //write file
        try {
            PrintWriter printWriter;
            printWriter = new PrintWriter(classFile);
            printWriter.append(writer.toString());
            printWriter.flush();
            printWriter.close();
        } catch (Exception ex) {
            getLog().error("error writing class " + className + " to disk", ex);
        }
    }    
    private void generate(){
        getLog().info("generating sources in\n\tdirectory:\t" + generateDirectory.getAbsolutePath() + "\n\tpackage:\t" + packageName);
        int i=0;
        VelocityContext context = new VelocityContext();
        String className = packageName + "._Entities";
        final String entitiesFQN = className;
        context.put("package", packageName);
        context.put("cds", this.classNameToClassDescriptor.values());
        context.put("pds", this.propertyDescriptors);
        context.put("rs", relations);
        context.put("entityInterface", entityInterface);
        Template template = velocityEngine.getTemplate("templates/_Entities.vsl");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        classToDisk(writer, className);
        getLog().info("\t" + i++ + ") " + "enumerated " + this.classNameToClassDescriptor.size() + " entities  and " + this.propertyDescriptors.size() + " persistent properties in:\n\t\t" + className);
        
        template = velocityEngine.getTemplate("templates/_GwtClientValidatorFactory.vsl");
        context.put("cns", this.validatedClasses);
        TreeSet<String> validationGroups = new TreeSet();
        for(String group : clientValidationGroups){
            validationGroups.add(group + ".class");
        }
        context.put("gns", validationGroups);
        className = packageName + "._GwtClientValidatorFactory";
        writer = new StringWriter();
        template.merge(context, writer);
        classToDisk(writer, className);
        getLog().info("\t" + i++ + ") " + "created a validation factory for " + validatedClasses.size() + " classes and " + clientValidationGroups.size() + " groups in:\n\t\t" + className );
        
        template = velocityEngine.getTemplate("templates/_Proxy.vsl");
        context = new VelocityContext();
        context.put("package", packageName);
        for(_ClassDescriptor cd : this.classNameToClassDescriptor.values()){
            context.put("cd", cd);
            writer = new StringWriter();
            template.merge(context, writer);
            className = packageName + "." +  cd.getProxySimpleName();
            classToDisk(writer, className);
            getLog().debug("\t* " + className + " OK");
        }
        
        template = velocityEngine.getTemplate("templates/_CustomFieldSerializer.vsl");
        context = new VelocityContext();
        context.put("entitiesFQN", entitiesFQN);
        for(_ClassDescriptor cd : this.classNameToClassDescriptor.values()){
            context.put("package", cd.entityClass.getPackageName());
            context.put("cd", cd);
            writer = new StringWriter();
            template.merge(context, writer);
            className = cd.entityClass.getPackageName() + "." +  cd.getCustomFieldSerializerClassSimpleName();
            classToDisk(writer, className);
            getLog().debug("\t* " + className + " OK");
        }
        getLog().info("\t" + i++ + ") " + "created " + this.classNameToClassDescriptor.size() + " custom field serializers");
        
        getLog().info("\t" + i++ + ") " + "created " + this.classNameToClassDescriptor.size() + " entity-proxies in :\n\t\t" + packageName + ".*_Proxy" );
    }
    
    private String getFQN(Set<String> FQNs, Set<String> imports, String className){
        //check fqn
        for(String fqn : FQNs){
            if(fqn.equals(className)) {
                return fqn;
            }
        }
        //check import.className
        StringBuilder m = new StringBuilder("searching for presence of declared class ").append(className).append(" using the following imports:");
        for(String i : imports){
            String concat = i.concat(".").concat(className);
            m.append("\n\t trying ").append(concat).append(" using ").append(i).append(": ");
            if(FQNs.contains(concat)){
                getLog().debug( m.append("MATCH!").toString() );
                return concat;
            } else {
                m.append(" no match");
            }
        }
        return null;
    }
}
