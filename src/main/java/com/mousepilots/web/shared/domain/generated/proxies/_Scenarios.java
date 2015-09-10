/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.web.shared.domain.generated.proxies;

import com.mousepilots.web.shared.domain.generated.proxies._Entities.EntityManager;
import java.util.ArrayList;
import java.util.Collection;



/**
 *
 * @author jgeenen
 */
public abstract class _Scenarios{
    

    public static class Domain<E>{
        
        private Collection<E> domain=new ArrayList<E>();
        
        private _Entities.EntityManager entityManager;
        
        private String key;
        
        /**
         * @return the managed entities for this scenario
         */
        public Collection<E> getDomain() {
            return domain;
        }

        /**
         * @return the entitymanager for this scenario
         */
        public EntityManager getEntityManager() {
            return entityManager;
        }

        private Domain(Collection<E> domain, EntityManager entityManager, String key) {
            this.entityManager = entityManager;
            this.key = key;
        }
    }
    
    public abstract <E, SR extends _ScenarioRequest<E>> void requestDomain(SR request);
    
}
