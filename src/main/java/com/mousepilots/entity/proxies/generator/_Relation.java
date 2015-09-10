/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.entity.proxies.generator;

/**
 *
 * @author jgeenen
 */
public class _Relation {
    
    protected _Arity arity;
    
    protected _PropertyDescriptor property;
    
    protected boolean owningSide;
    
    protected _ClassDescriptor target;
    
    protected _PropertyDescriptor targetInverse;

    public _Arity getArity() {
        return arity;
    }

    public _PropertyDescriptor getProperty() {
        return property;
    }

    public boolean isOwningSide() {
        return owningSide;
    }

    public _ClassDescriptor getTarget() {
        return target;
    }

    public _PropertyDescriptor getTargetInverse() {
        return targetInverse;
    }
    
   
    
    public boolean hasInverse(){
        return targetInverse!=null;
    }
    
    public _Relation getInverse(){
        return targetInverse.relation;
    }

    @Override
    public String toString() {
        return "_Relation{" + "arity=" + arity + ", property=" + property + ", owningSide=" + owningSide + ", target=" + target + ", targetInverse=" + targetInverse + '}';
    }
    
    
}
