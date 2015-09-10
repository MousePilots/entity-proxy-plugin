/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

import com.thoughtworks.qdox.model.JavaClass;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author jgeenen
 */
public class _ClassDescriptor implements Comparable<_ClassDescriptor> {

    public _ClassDescriptor(String packageName) {
        this.packageName = packageName;
    }
    
    
    
    private final String packageName;
    
    protected JavaClass entityClass;
    
    protected _PropertyDescriptor idProperty;
    
    protected _PropertyDescriptor versionProperty;
    
    public final TreeSet<_PropertyDescriptor> propertyDescriptors = new TreeSet<_PropertyDescriptor>();
    
    public final TreeMap<String,_PropertyDescriptor> fieldNameToPropertyDescriptor = new TreeMap<String,_PropertyDescriptor>();
    
    public String getGeneratorTypeName(){
        if(this.idProperty.isGenerated){
            return idProperty.getField().getType().isPrimitive() ? 
            ProxyGenerator.WRAPPERS.get(idProperty.getField().getType().getJavaClass().getName()) : 
            idProperty.getField().getType().getJavaClass().getFullyQualifiedName();
        } else {
            throw new UnsupportedOperationException("cannot determine generator for property " + idProperty.property.getName() + " on " + entityClass.getFullyQualifiedName());
        }
    }
    
    public String getEntityName(){
        entityClass.getPackage().getName();
        return entityClass.getFullyQualifiedName().replace("$", ".");
    }

    public String getPackageName() {
        return packageName;
    }
    
    
    
    public String getProxyName(){
        return packageName + "." + getProxySimpleName();
    }
    
    public String getProxySimpleName(){
        return entityClass.getName().replace("$",".") + "_Proxy";
    }
    
    public String getEnumName(){
        return entityClass.getName().replace("$", "_").replace(".", "_");
    }

    public JavaClass getEntityClass() {
        return entityClass;
    }

    public _PropertyDescriptor getIdProperty() {
        return idProperty;
    }

    public _PropertyDescriptor getVersionProperty() {
        return versionProperty;
    }

    public TreeSet<_PropertyDescriptor> getPropertyDescriptors() {
        return propertyDescriptors;
    }
    
    public String getCustomFieldSerializerClassSimpleName(){
        return entityClass.getName().replace("$",".") + "_CustomFieldSerializer";
    }
    
    public String getCustomFieldSerializerClassFQN(){
        return entityClass.getFullyQualifiedName().replace("$",".") + "_CustomFieldSerializer";
    }    
    

    @Override
    public int compareTo(_ClassDescriptor o) {
        return this.entityClass.getFullyQualifiedName().compareTo(o.entityClass.getFullyQualifiedName());
    }
    
    
}
