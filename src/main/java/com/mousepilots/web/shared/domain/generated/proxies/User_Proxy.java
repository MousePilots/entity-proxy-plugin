/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.web.shared.domain.generated.proxies;

import com.mousepilots.web.shared.domain.generated.proxies._Entities.EntityProperty;
import com.mousepilots.web.shared.domain.generated.proxies._Entities.ProxyState;

/**
 *
 * @author jgeenen
 */
public class User_Proxy extends User implements _Entities.Proxy<User> {
    
    private static Long __ID_GENERATOR = Long.MAX_VALUE;
    private static Long generateId(){
        synchronized(User_Proxy.class){
            return --__ID_GENERATOR;
        }
    }    
    private ProxyState proxyState;
    
    @Override
    public void __generateID() throws UnsupportedOperationException, IllegalStateException {
    }

    public void __superSet(EntityProperty property, Object value) {
        //TODO
        
        //DONE
    }

    @Override
    public ProxyState __getState() {
        return this.proxyState;
    }
    
    @Override
    public void __setState(ProxyState proxyState) {
        this.proxyState = proxyState;
    }
    
}
