/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

import com.mousepilots.web.shared.domain.generated.proxies._Entities.Transient;
import com.thoughtworks.qdox.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



/**
 * Offers global comparison of properties amongst entity classes
 * @author jgeenen
 */
public class _PropertyDescriptor implements Comparable<_PropertyDescriptor> {
    
    private static Class[] SERIALIZATION_TYPES = new Class[]{boolean.class,byte.class,char.class,double.class,float.class};
    
    protected boolean setRelation = false;
    
    protected _ClassDescriptor owner;
    
    protected _Relation relation;
    
    protected BeanProperty property;
    
    protected JavaField field;
    
    protected boolean isId;
    
    protected boolean isVersion;
    
    protected boolean isGenerated;
    
    protected String transientValueExpression;
    
    protected Transient.When when = Transient.When.never;
    
    public Transient.When getTransientWhen(){
        return when;
    }

    public String getTransientValueExpression() {
        return transientValueExpression;
    }
    
    protected String unSerializedValue = null;
    
    public String getEnumName(){
        return owner.getEnumName() + "__" + property.getName();
    }
    
    public boolean isCollection(){
        return isRelation() && relation.arity.isToMany();
    }
    
    public boolean isArray(){
        return property.getType().isArray();
    }
    
    
    public String getGenericTypeString(){
        return field.getType().toGenericString();
    }
    
    public String wrappedGenericType(){
        if(field.getType().isPrimitive()){
            if(field.getType().isArray()){
                return getGenericTypeString().replace("[][]", "[]");
            } else {
                return ProxyGenerator.WRAPPERS.get(field.getType().getJavaClass().getName());
            }
        } else {
            return getGenericTypeString();
        }
    }
    
    public String getClassString(){
        return isArray() ? 
                "new " + field.getType().getValue() + "[0].getClass()"  :
                field.getType().getValue() + ".class";
    }
    
    
    public String getTypeString(){
        return false ? 
               "new " + field.getType().getValue() + "[0]" : 
               field.getType().getValue();
    }

    public _ClassDescriptor getOwner() {
        return owner;
    }

    public _Relation getRelation() {
        return relation;
    }
    
    
    
    
    public boolean isId(){
        return isId;
    }
    
    public boolean isGenerated(){
        return isGenerated;
    }
    
    public boolean isVersion(){
        return isVersion;
    }
    
    public boolean isRelation(){
        return relation!=null;
    }

    public BeanProperty getProperty() {
        return property;
    }
    
    public String getSetterParameterName(){
        JavaMethod mutator = property.getMutator();
        if(mutator==null){
            return null;
        } else {
            JavaParameter param = mutator.getParameters()[0];
            return param.getName();
        }
    }

    public String getGetterDeclarationSignature(){
        JavaMethod accessor = property.getAccessor();
        return accessor==null ? null : accessor.getDeclarationSignature(true);
    }
    
    
    public String getSetterDeclarationSignature(){
        JavaMethod mutator = property.getMutator();
        return mutator==null ? null : mutator.getDeclarationSignature(true);
    }

    public JavaField getField() {
        return field;
    }
    
    private String serializationMethodPostFix(){
        if(field.getType().isPrimitive() && !field.getType().isArray()){
            String name = field.getType().getValue();
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        } else {
            if(field.getType().getFullyQualifiedName().equals(String.class.getName())){
                return "String";
            } else {
                return "Object";
            }
        }
    }
    
    public String getSerializationStreamReaderMethodName(){
        return "read" + serializationMethodPostFix();
    }
    
    public String getSerializationStreamWriterMethodName(){
        return "write" + serializationMethodPostFix();
    }
    
    private String nTabs(int n){
        StringBuilder retval = new StringBuilder();
        for(int i=0 ; i<n; n++){
            retval.append('\n');
        }
        return retval.toString();
    }
    
    @Override
    public int compareTo(_PropertyDescriptor other) {
        int classComparison = this.owner.compareTo(other.owner);
        if(classComparison==0){
            return this.property.getName().compareTo(other.property.getName());
        } else {
            return classComparison;
        }
    }
    
}
