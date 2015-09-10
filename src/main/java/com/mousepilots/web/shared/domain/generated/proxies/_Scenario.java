/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.web.shared.domain.generated.proxies;

import com.mousepilots.web.shared.domain.generated.proxies._Entities.EntityClass;
import com.mousepilots.web.shared.domain.generated.proxies._Entities.EntityProperty;
import java.util.EnumSet;


/**
 *
 * @author jgeenen
 */
public interface _Scenario{
    
    /**
     * @return the name of the {@link javax.persistence.NamedQuery} for this scenario if any, otherwise {@code null}
     */
    String getSelectQueryName();
    
    /**
     * @return the entity-classes which the caller principal may create {@code iff} he becomes an owner of the entity,
     * see {@link HasOwners}.
     */
    EnumSet<EntityClass>    getAllowedCreates();

    /**
     * @return the entity-classes which the caller principal may create {@code iff} he is an owner of the entity,
     * see {@link HasOwners}.
     */
    EnumSet<EntityProperty> getAllowedUpdates();

    /**
     * @return the entity-classes which the caller principal may delete {@code iff} he is an owner of the entity,
     * see {@link com.mousepilots.web.shared.domain.custom.HasOwners}.
     */
    EnumSet<EntityClass>    getAllowedDeletes();
}
