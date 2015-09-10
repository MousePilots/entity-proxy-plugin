package com.mousepilots.web.shared.domain.generated.proxies;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides utilities for {@link javax.persistence.Entity}-annotated classes,
 * their persistent properties and relations.
 *
 * @author jgeenen
 */
@javax.annotation.Generated(value = {"_Entities.vsl"})
public class _Entities {
    
    private static final Logger LOG = Logger.getLogger(_Entities.class.getName());
    static {
        LOG.setLevel(Level.ALL);
    }
    /**
     * Enumerates the arity of a relationship between entities
     */
    public static enum Arity {

        OneToOne, OneToMany, ManyToOne, ManyToMany;

        /**
         * @return {@code this}' relational getInverse such that
         * {@code xToY.getInverse()==yToX}.
         */
        public Arity getInverse() {
            switch (this) {
                case OneToOne:
                    return this;
                case OneToMany:
                    return ManyToOne;
                case ManyToOne:
                    return OneToMany;
                case ManyToMany:
                    return this;
                default:
                    throw new IllegalStateException("unknown Type " + this);
            }
        }
        /**
         * contains the types corresponding from collection valued properties
         */
        private static final EnumSet<Arity> FROM_ONES = EnumSet.of(OneToOne, OneToMany);
        /**
         * contains the types corresponding from collection valued properties
         */
        private static final EnumSet<Arity> FROM_MANIES = EnumSet.of(ManyToOne, ManyToMany);
        /**
         * contains the types corresponding to collection valued properties
         */
        private static final EnumSet<Arity> TO_ONES = EnumSet.of(OneToOne, ManyToOne);
        /**
         * contains the types corresponding to collection valued properties
         */
        private static final EnumSet<Arity> TO_MANIES = EnumSet.of(OneToMany, ManyToMany);

        public boolean isFromOne() {
            return FROM_ONES.contains(this);
        }

        public boolean isFromMany() {
            return FROM_MANIES.contains(this);
        }

        public boolean isToOne() {
            return TO_ONES.contains(this);
        }

        public boolean isToMany() {
            return TO_MANIES.contains(this);
        }
    }
    
    /**
     * Customized version of {@link com.google.gwt.user.client.rpc.GwtTransient} which allows specification
     * when a field is transient, and which value to set on the field if transient
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface Transient{
        
        /** the default transient value expression, which */
        public static final String NULL = "null";
        
        /** determines when a field is transient*/
        public static enum When{
            /* the field is always transient */
            always,
            /* the field is transient only in communications to the server */
            toServer,
            /* the field is transient only in communications to a client */
            toClient,
            /** the field is never transinet*/
            never
        }
        /**
         * @return when the field is treated as transient
         */
        When transience() default When.always;

        /** 
         * Specifies a java expression generating the value for this field if transient upon deserialization. 
         * The value is used in code generation of class serializers. The owner of the {@link GwtTransientEx} field may be referred to in the expression by the variable {@code instance} if needed with care
         * because it is unpredictable whether or not other properties have allready been deserialized onto {@code instance}. If not specified, 
         * the property is skipped by deserialization, in which case it has the zero-arg constructor's default value
         * for the field this annotation occurs on.
         * @return a java expression generating the value for this field if transient upon deserialization e.g. {@code "Integer.MAX_VALUE"}
         */
        String transientValueExpression() default NULL;
    }    
    
    
    /**
     * Enumerates all {@link javax.persistence.Entity}-annotated classes in a
     * project, and provides utilities for such classes similar to those
     * provided by the reflection API.
     */
    public static enum EntityClass{
        //TODO insert VSL code here: example:
        ExampleEntity(null, null);
        //DONE
        /**
         * a map from {@link javax.persistence.Entity}-annotated {@link Class}
         * (or {@link Proxy}-{@link Class} thereof) &rarr; {@link EntityClass}
         */
        private static final Map<Class, EntityClass>                     ENITY_CLASSES      = new HashMap<Class, EntityClass>();
        private static final EnumSet<EntityProperty>                     GENERATED_IDS      = EnumSet.noneOf(EntityProperty.class);
        private static final EnumMap<EntityClass,EntityProperty>         IDS                = new EnumMap<EntityClass, EntityProperty>(EntityClass.class);
        private static final EnumMap<EntityClass,EntityProperty>         VERSIONS           = new EnumMap<EntityClass, EntityProperty>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    PROPERTIES         = new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    SIMPLE_PROPERTIES  = new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    RELATIONS          = new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    SINGULAR_PROPERTIES= new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    TO_ONE_RELATIONS   = new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        private static final EnumMap<EntityClass,Set<EntityProperty>>    TO_MANY_RELATIONS  = new EnumMap<EntityClass,Set<EntityProperty>>(EntityClass.class);
        

        /**
         * @return the {@link javax.persistence.Id}-properties of all entities
         * with a {@link javax.persistence.GeneratedValue}
         */
        public static Set<EntityProperty> getGeneratedIds() {
            return Collections.unmodifiableSet(GENERATED_IDS);
        }
        

        /**
         * Gets the {@link EntityClass} for the specified {@code entityInstance}
         *
         * @param entityInstance the owner's instance
         * @return the info for the owner
         * @throws IllegalArgumentException if {@code clazz} is no owner-class
         */
        public static EntityClass byInstance(Object entityInstance) throws IllegalArgumentException {
            return byClass(entityInstance.getClass());
        }

        /**
         * Gets the {@link EntityClass} for the specified {@code clazz}
         *
         * @param clazz the owner's class
         * @return the info for the owner
         * @throws IllegalArgumentException if {@code clazz} is no proxy-class
         */
        public static EntityClass byClass(Class clazz) throws IllegalArgumentException {
            EntityClass retval = ENITY_CLASSES.get(clazz);
            if (retval == null) {
                throw new IllegalArgumentException("bad " + clazz);
            } else {
                return retval;
            }
        }

        /**
         * @param <E>
         * @param clazz an {@link javax.persistence.Entity}-annotated
         * {@link Class} (or {@link Proxy}-{@link Class} thereof)
         * @return a new instance of {@code E}, created with its no-arg
         * constructor, none of its properties have been initialized!
         * @throws IllegalArgumentException if {@code clazz} is no proxy-class
         */
        protected static Object create(EntityClass ec, boolean proxied) {
            switch (ec) {
                //TODO vsl code
                case ExampleEntity:
                    return proxied ? new Object() : new Object();
                //DONE
            }
            throw new IllegalArgumentException("bad " + ec.entityClass);
        }

        /**
         * @param proxy
         * @return the {@code proxy}'s {@link javax.persistence.Id}
         */
        public static Object getId(Object entity) {
            return entity instanceof Proxy
                    ? ((Proxy) entity).__getState().entityClass.getIdProperty().get(entity)
                    : byInstance(entity).getIdProperty().get(entity);
        }
        /**
         * the proxy {@code this} is for
         */
        public final Class entityClass;
        /**
         * the proxy proxy {@code this} is for
         */
        public final Class<? extends Proxy> proxyClass;

        /**
         * @return the proxy's {@link javax.persistence.Id} property
         */
        public EntityProperty getIdProperty(){
            return IDS.get(this);
        }

        /**
         * @return the proxy's {@link javax.persistence.Version} property if
         * any, otherwise {@code null}
         */
        public EntityProperty getVersionProperty(){
            return VERSIONS.get(this);
        }
        
        /**
         * @return a {@link Set} of the proxy's properties,
         * ordered according to {@link EntityProperty}'s declaration order.
         */
        public Set<EntityProperty> getProperties(){
            return PROPERTIES.get(this);
        }


        /**
         * @return an unmodifiable {@link Set} of the proxy's simple properties
         * (i.e. properties which are no relation), ordered according to
         * {@link EntityProperty}'s declaration order.
         */
        public Set<EntityProperty> getSimpleProperties() {
            return SIMPLE_PROPERTIES.get(this);
        }

        /**
         * @return an unmodifiable {@link Set} of the entities's relations,
         * ordered according to {@link EntityProperty}'s declaration order.
         */
        public Set<EntityProperty> getRelations() {
            return RELATIONS.get(this);
        }
        

        /**
         * @return an unmodifiable {@link Set} of the proxy's *-to-One
         * relations, ordered according to {@link EntityProperty}'s declaration
         * order.
         */
        public Set<EntityProperty> getToOneRelations() {
            return TO_ONE_RELATIONS.get(this);
        }

        /**
         * @return an unmodifiable {@link Set} of {@code this.getSimpleProperties()} &cup; {@code this.getToOneRelations()}.
         */
        private Set<EntityProperty> getSingularProperties() {
            return SINGULAR_PROPERTIES.get(this);
        }

        /**
         * @return an unmodifiable {@link Set} of the proxy's *-to-Many relations, ordered according to {@link EntityProperty}'s declaration
         * order. Note that these are in fact all {@link java.util.Collection}-valued persistent properties of the proxy.
         */
        public Set<EntityProperty> getToManyRelations() {
            return TO_MANY_RELATIONS.get(this);
        }

        /**
         * private constructor, sets only final fields
         * @param <E>
         * @param entityClass
         * @param proxyClass
         */
        private <E> EntityClass(Class<E> entityClass, Class< ? extends Proxy<E>> proxyClass) {
            this.entityClass = entityClass;
            this.proxyClass = proxyClass;
        }
        
    }

    /**
     * Enumerates all persistent properties of
     * {@link javax.persistence.Entity}-annotated classes in a project, and
     * provides utilities for such properties, similar to those provided by the
     * reflection API.
     *
     * @author jgeenen
     */
    public static enum EntityProperty {
        //TODO vsl code
        ExampleProperty(null, false, true, false, null, null, Transient.When.never) {
            @Override
            public EntityClass getOwner() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public Object get(Object entity) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            @Override
            protected void set(Object entity, Object value, boolean managed) {
                if (!managed && entity instanceof Proxy) {
                    Proxy p = (Proxy) entity;
                    p.__superSet(this, value);
                } else {
                    //TODO vsl code here
                    //END
                }
            }
        };
        //DONE
        private static final EnumMap<EntityProperty,EntityRelation> RELATIONS = new EnumMap<EntityProperty, EntityRelation>(EntityProperty.class);
        /**
         * {@code this}' property-type
         */
        public final Class type;
        /**
         * @return this property's owner
         */
        public abstract EntityClass getOwner();
        /**
         * whether or not {@code this}' property represents the
         * {@link javax.persistence.Id} for the owner
         */
        public final boolean isId;
        /**
         * whether or not {@code this}' property represents the
         * {@link javax.persistence.Version} for the owner
         */
        public final boolean isVersion;
        /**
         * whether or not this property has a generated value
         */
        public final boolean isGenerated;
        /**
         * this property's name according to the JavaBean spec
         */
        public final String propertyName;
        /**
         * {@code this}' property-field name
         */
        public final String fieldName;
        
        public final Transient.When transientWhen;
        
        /**
         * @return info on the relation {@code this}' property is the source of
         * if any, otherwise {@code null}
         */
        public EntityRelation getRelation() {
            return RELATIONS.get(this);
        }
        
        /**
         * @return whether or not this is a relation
         */
        public boolean isRelation(){
            return RELATIONS.containsKey(this);
        }

        /**
         * @param owner an owner of {@code this} {@link #type}
         * @return
         */
        public abstract Object get(Object entity);
        
        
        /** 
         * Sets a default for this property upon deserializationif this property is transient. 
         * @param instance the entity-instance owning this property
         */
        public void setDefaultDeserializationValue(Object instance){/*no op*/}

        /**
         * Sets this property's {@code value} on the {@code proxy}.
         *
         * @param proxy an proxy or proxy thereof
         * @param value the value to set
         * @throws ClassCastException if {@code this} property is not present on the {@code proxy}
         * @throws UnsupportedOperationException if an {@link javax.persistence.Id}-annotated or {@link Collection}-valued property is set
         * @throws IllegalArgumentException if an unmanaged proxy is set on a {@link Arity#TO_ONES} relation
         */
        public final void set(Object entity, Object value) throws ClassCastException, UnsupportedOperationException, IllegalArgumentException {
            Class entityClazz = entity.getClass();
            final EntityClass owner = getOwner();
            if (entityClazz == owner.entityClass || entityClazz == owner.proxyClass) {
                //change management
                if (entity instanceof Proxy){
                    final Proxy p = (Proxy) entity;
                    final EntityManager em = p.__getState().entityManager;
                    //is this an Id?
                    if(this.isId) {
                        throw new UnsupportedOperationException(
                                new StringBuilder("setting a managed entity's Id is not supported: ")
                                .append(this.getOwner().entityClass).append('#').append(this.propertyName).toString());
                    }
                    if(this.isVersion){
                        throw new UnsupportedOperationException(
                                new StringBuilder("setting an entity's version is not supported: ")
                                .append(this.getOwner().entityClass).append('#').append(this.propertyName).toString());
                    }
                    //is this a relation?
                    if (this.isRelation()) {
                        //YES: is it collection-valued?
                        if(this.getRelation().arity.isToMany()) { 
                            //YES
                            throw new UnsupportedOperationException(
                                new StringBuilder("setting a managed entity's collection-valued property is not supported: ")
                                .append(this.getOwner().entityClass).append('#').append(this.propertyName)
                                .append("use the setter for this property to manipulate the collection").toString()
                            );
                        } else {
                            //NO: to-One relation
                            if(value!=null && em.find(owner, owner.getIdProperty().get(value))!=value){
                                throw new IllegalArgumentException( 
                                        new StringBuilder("attempt to set an unmanaged entity ").append(value).append(" on ").append(p).toString() 
                                );
                            }
                        }
                    }
                    em.new Update(owner, p, this, this.get(entity)).addToCurrentTransaction();
                }
                set(entity, value, false);
            } else {
                throw new ClassCastException("expected: " + this.getOwner().entityClass + " encountered: " + entity.getClass());
            }
        }
        
        /**
         * Sets {@code this} property to {@code value} on a <strong>managed</strong> {@code managedEntity}, without the {@code managedEntity}'s
         * {@link EntityManager} recording the change. This is useful when adding relations to a managed entity which were
         * previously unfetched.
         * @param managedEntity required
         * @param simpleValueOrManagedEntity required, if an entity: it must be managed in the same {@link EntityManager} as {@code managedEntity}
         * @param setInverse sets the inverse of a relation, if {@code this} is a bidirectional relation, otherwise ignored
         * @throws IllegalArgumentException if one of the following: <ul>
         * <li>if {@code this} represents a to-one {@link EntityRelation} but {@code entity} and {@code value} are not managed in the same entity-manager</li>
         * <li>if {@code this} represents a to-many {@link EntityRelation}</li>
         * <li>if {@code entity} is not managed or {@code this} property is allready set</li>
         * </ul>
         */
        public final void setUnmanaged(Object managedEntity, Object simpleValueOrManagedEntity, boolean setInverse){
            if(managedEntity instanceof Proxy && get(managedEntity)==null){
                final EntityRelation relation = this.getRelation();
                if(relation==null){
                    set(managedEntity,simpleValueOrManagedEntity,false);
                } else {
                    if(relation.arity.isToOne()){
                        if(simpleValueOrManagedEntity!=null && simpleValueOrManagedEntity instanceof Proxy && ((Proxy)managedEntity).__getState().entityManager==((Proxy)simpleValueOrManagedEntity).__getState().entityManager){
                            this.set(managedEntity, simpleValueOrManagedEntity, false);
                            if(setInverse && relation.isBidrectional()){
                                if(relation.arity.isFromOne()){
                                    relation.targetInverseProperty.setUnmanaged(simpleValueOrManagedEntity, managedEntity,false);
                                } else {
                                    relation.targetInverseProperty.addUnmanaged(simpleValueOrManagedEntity, Collections.singletonList(managedEntity),false);
                                }
                            }
                            
                        } else {
                            throw new IllegalArgumentException(managedEntity + " and " + simpleValueOrManagedEntity + " are not managed in the same EntityManager");
                        }
                    } else {
                        throw new IllegalArgumentException(this + " is a collection-valued property which must not be set on " + managedEntity);
                    } 
                }
            } else {
                throw new IllegalArgumentException(managedEntity + " is not managed or property " + this + " was allready set");
            }
        }
        
        /**
         * Adds the {managedRelationalEntities} to {@code this} property, representing a to-many relation, without the {@code managedEntity}'s
         * {@link EntityManager} recording the change. This is useful when adding relations to a managed entity which were previously unfetched.
         * @param managedEntity required
         * @param managedRelationalEntities required, must be managed in the same {@link EntityManager} as {@code managedEntity}
         * @param setInverse sets the inverse of a relation, if {@code this} is a bidirectional relation, otherwise ignored
         * @throws IllegalArgumentException if one of the following: <ul>
         * <li>if {@code this} represents no to-many {@link EntityRelation} or {@code managedEntity} is unmanaged</li>
         * <li>if {@code managedRelationalEntities} is {@code null}, contains unmanaged entities, contains a value of the wrong type, or, contains a value allready occuring in {@code entity}'s property</li>
         * </ul>

         */
        public final void addUnmanaged(Object managedEntity, Collection managedRelationalEntities, boolean setInverse) throws IllegalArgumentException{
            EntityRelation relation = this.getRelation();
            if(managedEntity instanceof Proxy && relation!=null && relation.arity.isToMany()){
                EntityManager entityManager = ((Proxy)managedEntity).__getState().entityManager;
                EntityManager.ProxiedList proxiedList = (EntityManager.ProxiedList) this.get(managedEntity);
                proxiedList.setReportChanges(false);
                for(Object relationEntity : managedRelationalEntities){
                    if(relationEntity instanceof Proxy && entityManager==((Proxy)relationEntity).__getState().entityManager && relation.targetClass==EntityClass.byInstance(relationEntity) && !proxiedList.contains(relationEntity)){
                        proxiedList.add(relationEntity);
                    } else {
                        proxiedList.setReportChanges(true);
                        throw new IllegalArgumentException(relationEntity + " is either null, unmanaged, of the wrong type, or, allready occuring in collection " + this.propertyName + " on " + managedEntity);
                    }
                }
                proxiedList.setReportChanges(true);
                if(setInverse && relation.isBidrectional()){
                    if(relation.arity.isFromOne()){
                        for(Object relationEntity : managedRelationalEntities){
                            relation.targetInverseProperty.setUnmanaged(relationEntity, managedEntity,false);
                        }
                    } else {
                        for(Object relationEntity : managedRelationalEntities){
                            relation.targetInverseProperty.addUnmanaged(relationEntity, Collections.singletonList(managedEntity),false);
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException(managedEntity + " is unmanaged, "  + this + " is no to-many relation, or " + managedRelationalEntities + "==null");
            }
        }
        

        /**
         * @param proxy the proxy instance
         * @param value the value to set
         * @param recordChange if {@code true} and {@code proxy} is a
         * {@link Proxy}: invokes {@link Proxy#__superSet} otherwise, the normal
         * setter is invoked.
         */
        protected abstract void set(Object entity, Object value, boolean managed);

        private EntityProperty(Class type, boolean isId, boolean isGenerated, boolean isVersion, String propertyName, String fieldName, Transient.When transientWhen) {
            this.type = type;
            this.isId = isId;
            this.isGenerated = isGenerated;
            this.isVersion = isVersion;
            this.propertyName = propertyName;
            this.fieldName = fieldName;
            this.transientWhen = transientWhen;
        }
        /** the non-object types in java*/
        public static final Set<Class> PRIMITIVE_TYPES = Collections.unmodifiableSet(
                new HashSet<Class>(Arrays.asList(boolean.class,byte.class,short.class,char.class,int.class,long.class,float.class,double.class))
        );
        /** whether or not {@code this} represents a property with a primitive java type, see {@link #PRIMITIVE_TYPES}*/
        public boolean isPrimitive(){
            return PRIMITIVE_TYPES.contains(this.type);
        }
    }

    /**
     * Represents a relation between two {@link EntityClass}es
     * @author jgeenen
     */
    public static final class EntityRelation {

        private final String toStringVal;
        
        private EntityRelation(Arity arity, EntityProperty sourceProperty, EntityClass targetClass, EntityProperty targetInverseProperty){
            this.arity = arity;
            this.sourceProperty = sourceProperty;
            this.targetClass = targetClass;
            this.targetInverseProperty = targetInverseProperty;
            toStringVal = new StringBuilder(EntityRelation.class.getSimpleName())
                    .append('[')
                        .append(sourceProperty.name())
                            .append(isBidrectional() ? (arity.isFromOne() ? "<(1)" : "<(N)") : "")
                            .append("----")
                            .append(arity.isToOne() ? "(1)>" : "(N)>")
                        .append(targetInverseProperty.name())
                    .append(']').toString();
        }
        /**
         * {@link this} relation's {@link Arity}
         */
        public final Arity arity;
        /**
         * the relation-sourceProperty's property
         */
        public final EntityProperty sourceProperty;
        /**
         * the relation-targetClass's {@link EntityClass}
         */
        public final EntityClass targetClass;
        /**
         * if the relation is bidirectional: the property on the
         * {@link #targetClass}, mapping back to
         * {@code this} {@link #sourceProperty}, otherwise: {@code null.}
         */
        public final EntityProperty targetInverseProperty;

        /**
         * @return whether the {@code targetClass} maps back to the source class
         */
        public boolean isBidrectional() {
            return targetInverseProperty != null;
        }

        /**
         * @return the inverse relation if {@code isBidirectional()}, otherwise
         * {@code null}
         */
        public EntityRelation getInverse() {
            if (isBidrectional()) {
                return targetInverseProperty.getRelation();
            } else {
                return null;
            }
        }

        @Override
        public String toString() {
            return toStringVal;
        }
    
    }

    /**
     * enumerates the typical CRUD operations on entities
     */
    public static enum CRUD {

        create, update, delete
    }

    /**
     * Stores {@link Proxy}-specific state
     */
    protected static class ProxyState {

        protected final boolean isNew;
        protected final EntityClass entityClass;
        protected final EntityManager entityManager;
        private CRUD operation;

        /**
         *
         * @param entityManager
         * @param entityClass
         * @param operation
         */
        protected ProxyState(EntityManager entityManager, EntityClass entityClass, CRUD operation, boolean isNew) {
            this.entityManager = entityManager;
            this.entityClass = entityClass;
            this.operation = operation;
            this.isNew = isNew;
        }

        /**
         * (Re-)mark this proxy as being for a {@link CRUD#delete}
         *
         * @throws IllegalStateException if
         * {@link __operation()}{@code .equals(}{@link CRUD#create}{@code )}.
         * Call {@link _EntityManager#undo()} to undo such creations
         */
        protected void delete() throws IllegalStateException {
            if (this.operation.equals(CRUD.delete)) {
                throw new IllegalStateException("this proxy is allready marked for deletion");
            } else {
                this.operation = CRUD.delete;
            }
        }

        /**
         * @return the operation this proxy is intended for
         */
        protected CRUD getOperation() {
            return operation;
        }

        /**
         * Sets the operation
         * @param operation 
         */
        public void setOperation(CRUD operation) {
            this.operation = operation;
        }
    }

    /**
     * Framework-specific interface for proxying entities
     *
     * @param EC the underlying proxy-type
     */
    protected static interface Proxy<EC> {

        /**
         * Generates an artifical {@link javax.persistence.Id}
         *
         * @throws UnsupportedOperationException if the proxy has no
         * {@link javax.persistence.GeneratedValue}
         * @throws IllegalStateException if an {@link javax.persistence.Id} was
         * allready assigned
         */
        void __generateID() throws UnsupportedOperationException, IllegalStateException;

        /**
         * Calls {@code set${Property}} on the proxy superclass instance of
         * {@code this}, bypassing all property change recording in this
         * {@link Proxy}
         *
         * @param propertyOrdinal
         * @param value
         */
        void __superSet(EntityProperty property, Object value);

        /**
         * @return this {@link Proxy}'s specific state
         */
        ProxyState __getState();

        /**
         * Sets the {@link ProxyState} for this class
         *
         * @param proxyInfo
         */
        void __setState(ProxyState proxyInfo);
    }
    
    
    
    
    /**
     * Client-side equivalent of a JPA {@link javax.persistence.EntityManager}
     * @author jgeenen
     */
    public static class EntityManager {
    
        /** superclass for recording changes to entities and providing undo/redo */
        protected abstract class EntityChange<P extends Proxy>{
            /** the the-class of the change*/
            protected final EntityClass entityClass;
            /** the proxy for undo/redo */
            protected final P proxy;
            /** the {@link javax.persistence.Id} of the {@link #proxy}*/
            protected final Object id;

            private EntityChange(EntityClass entityClass, P entity) {
                this.entityClass = entityClass;
                this.proxy = entity;
                this.id = entityClass.getIdProperty().get(entity);
            }

            public EntityClass getEntityClass() {
                return entityClass;
            }

            /** @return {@code this}' operation */
            protected abstract CRUD operation();
            
            /** undoes {@code this} change*/
            protected abstract void undo();
            
            /** redoes {@code this} change*/
            protected abstract void redo();
            
            /** reports this change to its entityManager */
            protected final void addToCurrentTransaction(){
                currentTransaction.add(this);
                while(1+currentTransactionIndex < transactions.size()){
                    transactions.removeLast();
                }
            }
        }

        /** Represents a {@link CRUD#create}. */
        protected class Creation extends EntityChange<Proxy>{
            
            protected final Object defaultId;
            
            private Creation(EntityClass entityClass, Proxy entity, Object defaultId) {
                super(entityClass, entity);
                this.defaultId=defaultId;
            }
            @Override
            protected final CRUD operation() {
                return CRUD.create;
            }
            @Override
            protected void undo() {
                HashMap<Object, Object> id2ei = EntityManager.this.ec2id2ei.get(getEntityClass());
                id2ei.remove(id);
                entityClass.getIdProperty().set(proxy, defaultId, false);
            }
            @Override
            protected void redo(){
                entityClass.getIdProperty().set(proxy, id, false);
                EntityManager.this.store(entityClass, id, proxy);
            }
        }
        
        /** Represents a {@link CRUD#delete}. */
        protected class Deletion extends EntityChange<Proxy>{
            /** the operation before {@link #proxy} got deleted */
            private final CRUD previousOperation;
            /**
             * Removes this proxy from the manager
             * @param entityManager
             * @param entityClass
             * @param proxy 
             */
            private Deletion(EntityClass entityClass, Proxy entity) {
                super(entityClass, entity);
                this.previousOperation=entity.__getState().getOperation();
            }
            @Override
            protected final CRUD operation() {
                return CRUD.delete;
            }
            @Override
            protected void undo() {
                ProxyState state = proxy.__getState();
                state.setOperation(previousOperation);
                EntityManager.this.store(entityClass, id, proxy);
            }
            @Override
            protected void redo() {
                ProxyState state = proxy.__getState();
                state.setOperation(CRUD.delete);
                EntityManager.this.ec2id2ei.get(entityClass).remove(id);
            }
        }
        
        /** Represents an {@link CRUD#update}. */
        protected class Update extends EntityChange<Proxy>{
            private final EntityProperty entityProperty;
            private Object undoValue, redoValue;
            private Collection<? extends Proxy> additions, removals;
            private Update(EntityRelation relation, Proxy entity, Collection<? extends Proxy> additions, Collection<? extends Proxy> removals) {
                super(relation.sourceProperty.getOwner(), entity);
                this.entityProperty = relation.sourceProperty;
                this.additions = additions;
                this.removals = removals;
            }
            
            private Update(EntityClass entityClass, Proxy entity, EntityProperty entityProperty, Object oldValue){
                super(entityClass, entity);
                this.entityProperty = entityProperty;
                this.undoValue =oldValue;
                this.redoValue =entityProperty.get(entity);
            }

            @Override
            protected final CRUD operation() {
                return CRUD.update;
            }
            @Override
            protected void undo() {
                EntityRelation relation = entityProperty.getRelation();
                if(relation==null || relation.arity.isToOne()){
                    proxy.__superSet(entityProperty, undoValue);
                } else {
                    EntityManager.ProxiedList relationValues = (EntityManager.ProxiedList) entityProperty.get(proxy);
                    relationValues.setReportChanges(false);
                    if(removals!=null){
                        relationValues.addAll(removals);
                    }
                    if(additions!=null){
                        relationValues.removeAll(additions);
                    }
                    relationValues.setReportChanges(true);
                }
            }
            @Override
            protected void redo(){
                EntityRelation relation = entityProperty.getRelation();
                if(relation==null || relation.arity.isToOne()){
                    proxy.__superSet(entityProperty, redoValue);
                } else {
                    EntityManager.ProxiedList relationValues = (EntityManager.ProxiedList) entityProperty.get(proxy);
                    relationValues.setReportChanges(false);
                    if(additions!=null){
                        relationValues.addAll(additions);
                    }
                    if(removals!=null){
                        relationValues.removeAll(removals);
                    }
                    relationValues.setReportChanges(true);
                }
            }
        }
        
        protected class Transaction extends ArrayList<EntityChange> {
            protected void undo(){
                for(int i=this.size()-1; 0<=i ; i--){
                    this.get(i).undo();
                }
            }
            protected void redo(){
                for(EntityChange entityChange : this){
                    entityChange.redo();
                }
            }
        }
        private final EnumMap<EntityClass, HashMap<Object, Object>> ec2id2ei = new EnumMap<EntityClass, HashMap<Object, Object>>(EntityClass.class);
        private int currentTransactionIndex=0;
        private Transaction currentTransaction = new Transaction();
        private final LinkedList<Transaction> transactions = new LinkedList<Transaction>();
        
        /**
         * @param o1 an object
         * @param o2 another object
         * @return {@code (o1==null && o2==null) || (o1!=null && o2!=null && o1.equals(o2))}.
         */
        public static boolean equals(Object o1, Object o2){
            return (o1==null && o2==null) || (o1!=null && o2!=null && o1.equals(o2));
        }        
        
        
        /**
         * Begins a new transaction if the current transaction is not empty. All previous transactions remain available for {@link #redo}. Any previously undone transactions remain available for {@link #redo}
         * until the next {@link #create}, {@link #delete}, or modification of a managed {@link EntityProperty}. 
         */
        public void beginTransaction(){
            if(!currentTransaction.isEmpty()){
                currentTransaction = new Transaction();
                transactions.add(++currentTransactionIndex, currentTransaction);
            }
        }
        
        /**
         * Undoes the first none-empty transaction, which is either the current transaction, or, the one immedeately preceding it. 
         * The undone transaction remains available for {@link #redo} until the next {@link #create}, {@link #delete}, or modification of a managed {@link EntityProperty}.
         * <p/><strong>NB:</strong> {@code This} {@link EntityManager} has a new and empty current transaction upon return.
         * @throws IllegalStateException if there is no undoable transaction, see {@link #hasUndo()}
         */
        public void undo(){
            final Transaction freshTransaction;
            //was the current transaction just created by redo()?
            if(currentTransaction.isEmpty() && 0!=currentTransactionIndex){
                //yes: remove it
                freshTransaction = transactions.remove(currentTransactionIndex);
                currentTransaction = transactions.get(--currentTransactionIndex);

            } else {
                freshTransaction = new Transaction();
            }
            if(currentTransaction.isEmpty()){
                throw new IllegalStateException("no undoable transaction available");
            } else {
                currentTransaction.undo();
                //insert fresh transaction before the one just undone
                currentTransaction = freshTransaction;
                transactions.add(currentTransactionIndex,currentTransaction);
            }

        }
        
        /**
         * Redoes the first none-empty transaction, which is either the current transaction, or, the one immedeately succeeding it. 
         * The redone transaction remains available for {@link #undo}.
         * <p/><strong>NB:</strong> {@code This} {@link EntityManager} has a new and empty current transaction upon return.
         * @throws IllegalStateException if there is no redoable transaction, see {@link #hasRedo()}
         */
        public void redo() throws IllegalStateException {
            final Transaction freshTransaction;
            //was the current transaction just created by undo()?
            if(currentTransaction.isEmpty() && 1+currentTransactionIndex!=transactions.size()){
                //yes: remove it
                freshTransaction = transactions.remove(currentTransactionIndex);
                currentTransaction = transactions.get(currentTransactionIndex);
            } else {
                freshTransaction = new Transaction();
            }
            if(currentTransaction.isEmpty()){
                throw new IllegalStateException("no redoable transactions available");
            } else {
                currentTransaction.redo();
                //insert fresh transaction after the one just redone
                currentTransaction = freshTransaction;
                transactions.add(++currentTransactionIndex,currentTransaction);
            }
        }
        
        protected EntityManager(){
            transactions.add(currentTransaction);
        }

        /**
         * @return an indiciation whether or not {@code this} contains entities which have changed. There's a very slight chance that
         * each CRUD operation performed during the lifetime of this entity-manager has an inverse which was also performed - in which case this method falsely returns {@code true}.
         * Note that the latter scenarios do not refer to transactions having been undone, as these are not counted has changes.
         */
        public boolean hasChanges(){
            return hasUndo();
        }

        /**
         * @return whether or not {@code this} has an undoable transaction
         */
        public boolean hasUndo() {
            return !currentTransaction.isEmpty() || 0<currentTransactionIndex;
        }

        /**
         * @return whether or not {@code this} has a redoable transaction
         */
        public boolean hasRedo() {
            return currentTransactionIndex+1 != transactions.size();
        }

        /**
         * Proxies a {@link Proxy}'s *-to-Many relation.
         *
         * @author jgeenen
         */
        protected class ProxiedList<P extends Proxy> extends ArrayList<P> {

            private final Proxy entityProxy;
            private final EntityRelation relation;
            private boolean reportChanges = true;

            /**
             * @param entityProxy the entityProxy for which to construct this
             * instance
             * @param relation the entityProxy's relation for which to construct
             * this instance
             * @param clazz the relation's type
             * @throws IllegalArgumentException if
             * {@code !relation.getTargetClass().equals(clazz) || ! relation.getEntityClass().equals(entityProxy.__entityClass())}
             */
            protected ProxiedList(Proxy entityProxy, EntityRelation relation) throws IllegalArgumentException {
                super();
                this.entityProxy = entityProxy;
                this.relation = relation;
            }
            /**
             * Checks if all {@code additions} are managed, and reports all {@code additions} and {@code removals} to {@link EntityManager#this}' current transaction
             * @param additions
             * @param removals
             * @throws IllegalArgumentException 
             */
            private void checkIfValidAndAddToTransaction(Collection<? extends Proxy> additions, Collection<? extends Proxy> removals) throws IllegalArgumentException {
                if (reportChanges) {
                    EntityProperty idProperty = relation.targetClass.getIdProperty();
                    EntityClass entityClass = relation.targetClass;
                    if(additions!=null && !additions.isEmpty()){
                        for(Proxy addition : additions){
                            if(addition!=EntityManager.this.find(entityClass, idProperty.get(addition))){
                                throw new IllegalArgumentException("attempt to add unmanaged entity " + addition + " to property " + relation.sourceProperty + " of " + entityProxy);
                            }
                        }
                    }
                    new Update(relation, entityProxy, additions, removals).addToCurrentTransaction();
                }
            }

            @Override
            public boolean add(P e) {
                checkIfValidAndAddToTransaction(Collections.singletonList(e),null);
                return super.add(e);
            }

            @Override
            public boolean addAll(Collection<? extends P> c) {
                checkIfValidAndAddToTransaction(new HashSet(c),null);
                return super.addAll(c);
            }

            @Override
            public boolean addAll(int index, Collection<? extends P> c) {
                throw new UnsupportedOperationException("not supported");
            }

            @Override
            public P set(int index, P element) {
                P oldValue = super.get(index);
                if(Objects.equals(oldValue, element)){
                    return oldValue;
                }
                checkIfValidAndAddToTransaction(Collections.singletonList(element), Collections.singletonList(oldValue));
                return super.set(index, element);
            }

            @Override
            public boolean remove(Object o) {
                final int index = super.indexOf(o);
                if (-1 < index) {
                    checkIfValidAndAddToTransaction(Collections.singletonList(get(index)), null);
                }
                return super.remove(o);
            }

            @Override
            public void clear() {
                if (!super.isEmpty()) {
                    checkIfValidAndAddToTransaction(null,this);
                    super.clear();
                }
            }

            @Override
            protected void removeRange(int fromIndex, int toIndex) {
                if (fromIndex < 0 || fromIndex >= size() || toIndex > size() || toIndex < fromIndex) {
                    throw new IndexOutOfBoundsException();
                }
                checkIfValidAndAddToTransaction(null, this.subList(fromIndex, toIndex));
                super.removeRange(fromIndex, toIndex);
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                HashSet removals = new HashSet();
                for(Object o : c){
                    if(this.contains(o)){
                        removals.add(o);
                    }
                }
                if(!removals.isEmpty()) {
                    checkIfValidAndAddToTransaction(null,removals);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                HashSet removals = new HashSet();
                for(Object o : this){
                    if(!c.contains(o)){
                        removals.add(o);
                    }
                }
                if (super.retainAll(c)) {
                    checkIfValidAndAddToTransaction(null, removals);
                    return true;
                } else {
                    return false;
                }
            }

            /**
             * whether or not to addToCurrentTransaction changes to the entityManager
             * @param reportChanges 
             */
            public void setReportChanges(boolean reportChanges) {
                this.reportChanges = reportChanges;
            }
        }

        /**
         * 
         * @param <E> the entity-type
         * @param ec the entity-class
         * @param id the {@link javax.persistence.Id}-value of the proxy
         * @return the proxy managed by {@code this} if found, otherwise {@code null}
         */
        public <E> E find(Class<E> ec, Object id){
            return this.<E>find(EntityClass.byClass(ec),id);
        }

        /**
         * 
         * @param <E> the entity-type
         * @param ec the entity-class
         * @return an unmodifiable {@link Map} of {@link javax.persistence.Id} &rarr; {@code E} which is {@code null} if {@code this} contains no such entities.
         */
        public <E> Map<Object,E> findAll(Class<E> ec){
            return (Map<Object,E>) findAll(EntityClass.byClass(ec));
    }
    
        /**
         * @param ec the proxy-class model
         * @return an unmodifiable {@link Map} of {@link javax.persistence.Id} &rarr; {@code proxy} of the type {@link EntityClass#entityClass} which is {@code null}
         * if {@this code} contains no such entities.
         */
        public Map findAll(EntityClass ec) {
            HashMap<Object, Object> id2ei = ec2id2ei.get(ec);
            if (id2ei == null) {
                return null;
            } else {
                return Collections.unmodifiableMap(id2ei);
            }
        }


        /**
         * @param ec the proxy-class model
         * @param id the {@link javax.persistence.Id}-value of the proxy
         * @return the proxy managed by {@code this} if found, otherwise {@code null}
         */
        public <T> T find(EntityClass ec, Object id) {
            HashMap<Object, Object> id2ei = ec2id2ei.get(ec);
            if (id2ei == null) {
                return null;
            } else {
                return (T) id2ei.get(id);
            }
        }
    
        /**
         * @param e an proxy
         * @return whether or not {@code this} contains an proxy {@code e'} such that {@code e'==e}
         */
        public boolean contains(Object e) {
            if (e == null) {
                return false;
            } else {
                final EntityClass ec = EntityClass.byInstance(e);
                return e == find(ec, ec.getIdProperty().get(e));
            }
        }

        private void store(EntityClass ec, Object id, Object entity) {
            HashMap<Object, Object> id2ei = ec2id2ei.get(ec);
            if (id2ei == null) {
                id2ei = new HashMap<Object, Object>();
                ec2id2ei.put(ec, id2ei);
            }
            id2ei.put(id, entity);
        }

        /**
         * Creates a new managed entity.
         * @param T the entity type
         * @param ec the entity class
         * @param id the {@link javax.persistence.Id}-value to set on the proxy. If the {@link javax.persistence.Id} has a {@link javax.persistence.GeneratedValue}, this argument is ignored and an artificial one is assigned instead which is unique within the caller's context
         * @return the proxy managed by {@code this}
         * @throws IllegalStateException if an entity with the specified {@link javax.persistence.Id} allready exists, can only occur if the {@link javax.persistence.Id} is no {@link javax.persistence.GeneratedValue}
         */
        public <T> T create(Class<T> ec, Object id) throws IllegalStateException {
            return create(EntityClass.byClass(ec), id);
        }
    
        /**
         * Creates a new managed entity.
         * @param entityClass the proxy-class model
         * @param the {@link javax.persistence.Id}-value to set on the proxy. If the {@link javax.persistence.Id} has a {@link javax.persistence.GeneratedValue}, this argument is ignored and an artificial one is assigned instead which is unique within the caller's context
         * @return the proxy managed by {@code this}
         * @throws IllegalStateException if an entity with the specified {@link javax.persistence.Id} allready exists, can only occur if the {@link javax.persistence.Id} is no {@link javax.persistence.GeneratedValue}
         */
        protected <T> T create(EntityClass entityClass, Object id) throws IllegalStateException {
            Object found = entityClass.getIdProperty().isGenerated ? null : find(entityClass, id);
            if (found == null) {
                Proxy proxy = (Proxy) EntityClass.create(entityClass, true);
                ProxyState proxyState = new ProxyState(this, entityClass, CRUD.create,true);
                proxy.__setState(proxyState);
                final EntityProperty idProperty = entityClass.getIdProperty();
                Object defaultId = idProperty.get(proxy);
                final Object actualId;
                if (entityClass.getIdProperty().isGenerated) {
                    proxy.__generateID();
                    actualId = entityClass.getIdProperty().get(proxy);
                } else {
                    proxy.__superSet(idProperty, id);
                    actualId = id;
                }
                store(entityClass, actualId, proxy);
                this.new Creation(entityClass, proxy, defaultId).addToCurrentTransaction();
                return (T) proxy;
            } else {
                throw new IllegalStateException("an instance of " + entityClass.entityClass + " with id=" + id + " allready exists");
            }
        }
        /**
         * Marks the {@code proxy} for deletion - any subsequent invocation of the {@code proxy}'s setters will throw {@link IllegalStateException}s. You should
         * remove all references to a deleted {@code proxy} in your code.
         * @param proxy the proxy to delete
         */
        public void delete(Object entity) {
            final Proxy proxy = (Proxy) entity;
            final EntityClass ec = EntityClass.byInstance(proxy);
            final Object id = ec.getIdProperty().get(proxy);
            if (find(ec, ec.getIdProperty().get(proxy)) == ec) {
                Deletion deletion = this.new Deletion(ec,proxy);
                deletion.redo();
                deletion.addToCurrentTransaction();
            } else {
                throw new IllegalArgumentException(proxy + " is not contained in this manager");
            }
        }

        /**
         * @param <T>
         * @param entity the entity to manage
         * @return a managed, deep clone of {@code arg} if {@code !this.contains(arg)}, otherwise {@code arg}.
         */
        public <T> T manage(T entity) {
            if (entity == null) {
                return null;
            } else {
                EntityClass entityClass = EntityClass.byInstance(entity);
                Object id = entityClass.getIdProperty().get(entity);
                if (id == null) {
                    throw new IllegalArgumentException("cannot manage entity " + entity + " with a null Id");
                }
                Proxy managedProxy = (Proxy) find(entityClass, id);
                if (managedProxy == null) {
                    //create a newly managed proxy and init its ProxyState
                    managedProxy = (Proxy) EntityClass.create(entityClass, true);
                    final ProxyState managedProxyInfo;
                    if (entity instanceof Proxy) {
                        final Proxy unmagagedProxy = (Proxy) entity;
                        ProxyState unmanagedProxyInfo = unmagagedProxy.__getState();
                        managedProxyInfo = new ProxyState( this, entityClass, unmanagedProxyInfo.getOperation(),unmagagedProxy.__getState().isNew);
                    } else {
                        managedProxyInfo = new ProxyState(this, entityClass, CRUD.update,false);
                    }
                    managedProxy.__setState(managedProxyInfo);

                    //set simple properties and store newly managed instance before recursion
                    for (EntityProperty ep : entityClass.getSimpleProperties()) {
                        managedProxy.__superSet(ep, copyIfArrayElseReturnArg(ep.get(entity)));
                    }
                    store(entityClass, id, managedProxy);

                    //set to-one relations
                    for (EntityProperty entityProperty : entityClass.getToOneRelations()) {
                        Object rv = entityProperty.get(entity);
                        managedProxy.__superSet(entityProperty, manage(rv));
                    }

                    //set to-many relations
                    for (EntityProperty ep : entityClass.getToManyRelations()) {
                        final ProxiedList proxiedList = this.new ProxiedList(managedProxy, ep.getRelation());
                        proxiedList.setReportChanges(false);
                        managedProxy.__superSet(ep, proxiedList);
                        Object rv = ep.get(entity);
                        if (rv != null) {
                            for (Object o : (Collection) rv) {
                                proxiedList.add(manage(o));
                            }
                        }
                        proxiedList.setReportChanges(true);
                    }
                }
                return (T) managedProxy;
            }
        }


        /**
         * stupid array-instance check/copy
         *
         * @param o
         * @return if {@code o} is an array: a copy thereof, otherwise {@code o}
         */
        private Object copyIfArrayElseReturnArg(Object o) {
            if (o instanceof boolean[]) {
                boolean[] src = (boolean[]) o, dest = new boolean[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof byte[]) {
                byte[] src = (byte[]) o, dest = new byte[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof short[]) {
                short[] src = (short[]) o, dest = new short[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof char[]) {
                char[] src = (char[]) o, dest = new char[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof int[]) {
                int[] src = (int[]) o, dest = new int[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof long[]) {
                long[] src = (long[]) o, dest = new long[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof float[]) {
                float[] src = (float[]) o, dest = new float[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof double[]) {
                double[] src = (double[]) o, dest = new double[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            if (o instanceof Object[]) {
                Object[] src = (Object[]) o, dest = new Object[src.length];
                System.arraycopy(src, 0, dest, 0, src.length);
                return dest;
            }
            return o;
        }
        /** represents the change of a {@link Collection} {@code c} in terms of additions to and removals from {@code c}*/
        public static class CollectionChange<T> implements Serializable{
            
            private ArrayList<T> additions = new ArrayList<T>(), removals = new ArrayList<T>();
            
            /** for de-serialization */
            protected CollectionChange(){}

            public CollectionChange(ArrayList<T> additions, ArrayList<T> removals) {
                this.additions=additions;
                this.removals=removals;
            }

            public ArrayList<T> getAdditions() {
                return additions;
            }

            public ArrayList<T> getRemovals() {
                return removals;
            }
        }//end CollectionChange            
    
    
        /**
         * Compact representation of a creation, update, or deletion to an entity, meant for efficient RPC calls.
         * @author jgeenen
         */
        public static class PackedProxy implements Serializable {
            /**
             * the represents the class for the packed {@link Proxy}
             */
            protected EntityClass entityClass;
            /**
             * the entity's {@link javax.persistence.Id}, which is artificial iff {@code co} &isin; {@link AbstractEntityManager#GENERATED_VALUE_CLASS_ORDINALS}
             */
            protected Object id;
            /**
             * the requested operation on the packed {@link Proxy}
             */
            protected CRUD operation;
            /**
             * <strong>if</strong> {@link #operation} {@code ==} {@link CRUD#update} <strong>then</strong> a map from {@link EntityProperty} &rarr to packed value, the latter of which is:
             * <ol> <li>the actual property-value, for properties which are no {@link EntityRelation}</li> 
             *      <li>an {@link javax.persistence.Id} for single-valued {@link EntityRelation}s</li> 
             *      <li>a {@link CollectionChange} of the {@link javax.persistence.Id}s for many-valued {@link EntityRelation}s</li> 
             * </ol> 
             * <strong>else</strong> {@code null}
             */
            protected EnumMap<EntityProperty, Object> propertyToValueChange = new EnumMap<EntityProperty, Object>(EntityProperty.class);

            /** for de-serialization */
            private PackedProxy() {}

            public PackedProxy(EntityClass entityClass, Object id, CRUD operation) {
                this();
                this.entityClass = entityClass;
                this.id = id;
                this.operation = operation;
            }

            public EntityClass getEntityClass() {
                return entityClass;
            }

            public Object getId() {
                return id;
            }

            public CRUD getOperation() {
                return operation;
            }
        }//end PackedProxy
        
        
        /**
         * @param ec2ids
         * @return the set of all entities {@code e} a mapping from {@link EntityClass} &rarr {@link {@link javax.persistence.Id}} is contained in {@code ec2ids}
         */
        private HashSet getEntities(EnumMap<EntityClass,HashSet<Object>> ec2ids){
            HashSet retval = new HashSet();
            for(EntityClass ec : ec2ids.keySet()){
                for(Object id : ec2ids.get(ec)){
                    retval.add(this.find(ec, id));
                }
            }
            return retval;
        }
        
        /**
         * Gets all changed entities by operation
         * @param operations
         * @return a mapping from {@code op} &rarr; {@code s}, such that {@code s} is the set of all {@code this}' entities to which {@code op} was appied
         */
        public EnumMap<CRUD, Set> getChanged(){
                EnumMap<CRUD,Set> retval = new EnumMap(CRUD.class);
                final EnumMap<EntityClass,HashSet<Object>> creations = new EnumMap(EntityClass.class);
                final EnumMap<EntityProperty,HashMap<Object,LinkedList<Update>>> updates = new EnumMap(EntityProperty.class); 
                final EnumMap<EntityClass,HashSet<Object>> p_deletions = new EnumMap(EntityClass.class);
                groupAndFilterChanges(creations, updates, p_deletions);
                retval.put(CRUD.create, getEntities(creations));
                retval.put(CRUD.delete, getEntities(p_deletions));
                HashSet us = new HashSet();
                retval.put(CRUD.update, us);
                for(EntityProperty ep : updates.keySet()){
                    for(Object id : updates.get(ep).keySet()){
                        us.add(this.find(ep.getOwner(), id));
                    }
                }
                return retval;
        }
        
        
        /**
         * Groups all {@link EntityChange}s up to and including the {@link #currentTransaction} into the categories
         * specified by its parameters. All creations and updates to entities which have subseqently been deleted are filtered out.
         * The parameters must not be {@code null}, and are filled as specified below.
         * @param creations by entity-class: the {@link javax.persistence.Id}s of newly created entities, not deleted after creation
         * @param updates by entity-property: by {@link javax.persistence.Id}: the updates to an entity's property in chronological order, the entity has not been deleted after update
         * @param p_deletions by entity-class: the {@link javax.persistence.Id}s of deleted persistent entities
         */
        protected void groupAndFilterChanges(
                final EnumMap<EntityClass,HashSet<Object>> creations, 
                final EnumMap<EntityProperty,HashMap<Object,LinkedList<Update>>> updates, 
                final EnumMap<EntityClass,HashSet<Object>> p_deletions
        ){
            final EnumMap<EntityClass,HashSet<Object>> n_deletions = new EnumMap<EntityClass, HashSet<Object>>(EntityClass.class);
            //iterate in reverse chronological order of entity changes, from the current transaction onwards
            //this allows detection of deleted entities, prior to updates to these entities
            final List<Transaction> completedTransactions = transactions.subList(0, 1+currentTransactionIndex);
            Collections.reverse(completedTransactions);
            for(Transaction transaction : completedTransactions ){
                for(int j = transaction.size()-1; 0<=j ; j--){
                    EntityChange change = transaction.get(j);
                    HashSet<Object> p_deletedIds = p_deletions.get(change.entityClass), n_deletedIds = n_deletions.get(change.entityClass);
                    switch(change.operation()){
                        case create : {
                            //was the entity deleted after its creation?
                            if( (p_deletedIds==null || !p_deletedIds.contains(change.id)) && (n_deletedIds==null || !n_deletedIds.contains(change.id)) ){
                                //NO: record the creation
                                HashSet<Object> createdIds = creations.get(change.entityClass);
                                if(createdIds==null){
                                    createdIds = new HashSet();
                                    creations.put(change.entityClass, p_deletedIds);
                                }
                                createdIds.add(change.id);
                            }
                            break;
                        }
                        case update : {
                            //was the entity deleted after this update?
                            if( (p_deletedIds==null || !p_deletedIds.contains(change.id)) && (n_deletedIds==null || !n_deletedIds.contains(change.id)) ){
                                //NO: insert this update at the corresponding list's head
                                Update u = (Update) change;
                                HashMap<Object, LinkedList<Update>> id2us = updates.get(u.entityProperty);
                                if(id2us==null){
                                    id2us = new HashMap<Object, LinkedList<Update>>();
                                    updates.put(u.entityProperty, id2us);
                                }
                                LinkedList<Update> us = id2us.get(u.id);
                                if(us==null){
                                    us = new LinkedList<Update>();
                                    id2us.put(u.id, us);
                                }
                                us.addFirst(u);
                            }
                            break;
                        }
                        case delete : {
                            if(change.proxy.__getState().isNew){
                                if(n_deletedIds==null){
                                    n_deletedIds = new HashSet();
                                    n_deletions.put(change.entityClass, n_deletedIds);
                                }
                                n_deletedIds.add(change.id);
                                
                            } else {
                                if(p_deletedIds==null){
                                    p_deletedIds = new HashSet();
                                    p_deletions.put(change.entityClass, p_deletedIds);
                                }
                                p_deletedIds.add(change.id);
                            }
                            break;
                        }
                    }//end case distinction
                }//end inner for
            }//end outer for
            
        }
        
        
        /**
         * @return a compact mapping by operation of all updates, creates and deletes in this entity-manager, up to and including the current transaction. The map may be {@code null} if no changes occurred
         */
        protected EnumMap<CRUD, ArrayList<PackedProxy>> pack(){
            final EnumMap<EntityClass,HashSet<Object>> 
                    creations = new EnumMap<EntityClass, HashSet<Object>>(EntityClass.class),// contains all creations which have not been deleted afterwards
                    deletions = new EnumMap<EntityClass, HashSet<Object>>(EntityClass.class);// deletions of persistent entities
            ;
            final EnumMap<EntityProperty,HashMap<Object,LinkedList<Update>>> updates = new EnumMap<EntityProperty, HashMap<Object, LinkedList<Update>>>(EntityProperty.class);
            groupAndFilterChanges(creations, updates, deletions);
            
            //map updates by entityClass -> Id -> EntityProperty -> packed value
            final EnumMap<EntityClass,HashMap<Object,EnumMap<EntityProperty,Object>>> ec2id2ep2pv = new EnumMap<EntityClass, HashMap<Object, EnumMap<EntityProperty, Object>>>(EntityClass.class);
            for(final EntityProperty ep : updates.keySet()){
                final HashMap<Object, LinkedList<Update>> existing_id2us = updates.get(ep);
                final EntityClass ec = ep.getOwner();
                HashMap<Object, EnumMap<EntityProperty, Object>> id2ep2pv = ec2id2ep2pv.get(ec);
                if(id2ep2pv==null){
                    id2ep2pv = new HashMap<Object, EnumMap<EntityProperty, Object>>();
                    ec2id2ep2pv.put(ec, id2ep2pv);
                }
                for(final Object id : existing_id2us.keySet()){
                    EnumMap<EntityProperty, Object> ep2pv = id2ep2pv.get(id);
                    if(ep2pv==null){
                        ep2pv = new EnumMap<EntityProperty, Object>(EntityProperty.class);
                        id2ep2pv.put(id, ep2pv);
                    }
                    final LinkedList<Update> us = existing_id2us.get(id);
                    final EntityRelation r = ep.getRelation();
                    final Object oldVal = us.getFirst().undoValue, newVal = us.getLast().redoValue;
                    if(r==null){
                        if(!equals(oldVal,newVal)){
                            ep2pv.put(ep, newVal);
                        }
                    } else {
                        EntityProperty targetIdProperty = r.targetClass.getIdProperty();
                        if(r.arity.isToOne()){
                            final Object    oldId = oldVal==null ? null : targetIdProperty.get(oldVal), 
                                            newId = newVal==null ? null : targetIdProperty.get(newVal);
                            if(!equals(oldId, newId)){
                                ep2pv.put(ep, newId);
                            }
                        } else {
                            //aggregate collection changes
                            final HashSet addedIds = new HashSet(), removedIds = new HashSet();
                            for(Update u : us){
                                if(u.additions!=null){
                                    for(Proxy p : u.additions){
                                        Object proxyId = targetIdProperty.get(p);
                                        addedIds.add(proxyId);
                                        removedIds.remove(proxyId);
                                    }
                                }
                                if(u.removals!=null){
                                    for(Proxy p : u.removals){
                                        Object proxyId = targetIdProperty.get(p);
                                        removedIds.add(proxyId);
                                        addedIds.remove(proxyId);
                                    }
                                }
                            }
                            if( !addedIds.isEmpty() || !removedIds.isEmpty() ){
                                ep2pv.put(ep, new CollectionChange(new ArrayList(addedIds), new ArrayList(removedIds)));
                            }
                        }
                    }
                }
            }
            
            //pack results by operation
            final EnumMap<CRUD,ArrayList<PackedProxy>> retval = new EnumMap<CRUD, ArrayList<PackedProxy>>(CRUD.class);
            for(CRUD crud : CRUD.values()){
                retval.put(crud, new ArrayList<PackedProxy>());
            }
            for(EntityClass ec :  creations.keySet()){
                HashSet<Object> ids = creations.get(ec);
                for(Object id : ids){
                    PackedProxy pp = new PackedProxy(ec, id, CRUD.create);
                    pp.propertyToValueChange=null;
                    retval.get(CRUD.create).add(pp);
                }
            }
            for(EntityClass ec : ec2id2ep2pv.keySet()){
                HashMap<Object, EnumMap<EntityProperty, Object>> id2ep2pv = ec2id2ep2pv.get(ec);
                for(Object id : id2ep2pv.keySet()){
                    final EnumMap<EntityProperty, Object> ep2pv = id2ep2pv.get(id);
                    if(!ep2pv.isEmpty()){
                        final PackedProxy pp = new PackedProxy(ec, id, CRUD.update);
                        retval.get(CRUD.update).add(pp);
                        for(EntityProperty ep : ep2pv.keySet()){
                            pp.propertyToValueChange.put(ep, ep2pv.get(ep));
                        }
                    }
                }
            }
            for(EntityClass ec :  deletions.keySet()){
                HashSet<Object> ids = deletions.get(ec);
                for(Object id : ids){
                    PackedProxy pp = new PackedProxy(ec, id, CRUD.delete);
                    pp.propertyToValueChange=null;
                    retval.get(CRUD.delete).add(pp);
                }
            }
            for(CRUD crud : CRUD.values()){
                if(retval.get(crud).isEmpty()){
                    retval.remove(crud);
                }
            }
            
            return retval.isEmpty() ? null : retval;
        }//end pack()
    }//end EntityManager
    
    private static synchronized void init(){
        for(EntityClass ec : EntityClass.values()){
            EntityClass.ENITY_CLASSES.put(ec.entityClass, ec);
            EntityClass.ENITY_CLASSES.put(ec.proxyClass,  ec);
            EntityClass.PROPERTIES.put(ec, EnumSet.noneOf(EntityProperty.class));
            EntityClass.RELATIONS.put(ec, EnumSet.noneOf(EntityProperty.class));
            EntityClass.SIMPLE_PROPERTIES.put(ec, EnumSet.noneOf(EntityProperty.class));
            EntityClass.SINGULAR_PROPERTIES.put(ec, EnumSet.noneOf(EntityProperty.class));
            EntityClass.TO_MANY_RELATIONS.put(ec, EnumSet.noneOf(EntityProperty.class));
            EntityClass.TO_ONE_RELATIONS.put(ec, EnumSet.noneOf(EntityProperty.class));
        }
        
        //TODO
        EntityProperty.RELATIONS.put(EntityProperty.ExampleProperty, new EntityRelation(Arity.OneToOne, EntityProperty.ExampleProperty, EntityClass.ExampleEntity, EntityProperty.ExampleProperty));
        //DONE

        //set all properties and relations
        for(EntityProperty ep : EntityProperty.values()){
            EntityClass ec = ep.getOwner();
            EntityClass.PROPERTIES.get(ec).add(ep);
            if(ep.isId){
                EntityClass.IDS.put(ec, ep);
                if(ep.isGenerated){
                    EntityClass.GENERATED_IDS.add(ep);
                }
            }
            if(ep.isVersion){
                EntityClass.VERSIONS.put(ec, ep);
            }
            EntityRelation er = ep.getRelation();
            if(er==null){
                EntityClass.SIMPLE_PROPERTIES.get(ec).add(ep);
                EntityClass.SINGULAR_PROPERTIES.get(ec).add(ep);
            } else {
                EntityClass.RELATIONS.get(ec).add(ep);
                if(er.arity.isToOne()){
                    EntityClass.SINGULAR_PROPERTIES.get(ec).add(ep);
                    EntityClass.TO_ONE_RELATIONS.get(ec).add(ep);
                } else {
                    EntityClass.TO_MANY_RELATIONS.get(ec).add(ep);
                }
            }
        }
        
        //make all exposed maps unmodifiable
        for(EnumMap<EntityClass,Set<EntityProperty>> map : Arrays.asList(
                EntityClass.PROPERTIES,
                EntityClass.RELATIONS,
                EntityClass.SIMPLE_PROPERTIES,
                EntityClass.SINGULAR_PROPERTIES,
                EntityClass.TO_MANY_RELATIONS,
                EntityClass.TO_ONE_RELATIONS)){
            EnumSet<EntityClass> keySet = EnumSet.copyOf(map.keySet());
            for(EntityClass ec : keySet){
                map.put(ec, Collections.unmodifiableSet(map.get(ec)));
            }
        }
    }
    
    static void logResult(){
        StringBuilder sb = new StringBuilder();
        for(EntityClass ec: EntityClass.values()){
            sb.append(ec).append(":")
            .append("\tentity-class: ").append(ec.entityClass)
            .append("\tproxy-class:  ").append(ec.proxyClass)
            .append("\tId-property:  ").append(ec.getIdProperty().propertyName)
            .append("\tproperties:");
            for(EntityProperty ep : ec.getProperties()){
                sb.append("\t\t").append(ep.propertyName).append(": ").append(ep.isRelation() ? ep.getRelation().arity : "simple").append(", type: ").append(ep.type.getSimpleName());
            }
            LOG.fine(sb.toString());
        }
    }
    
    static {
        init();
    }
}
