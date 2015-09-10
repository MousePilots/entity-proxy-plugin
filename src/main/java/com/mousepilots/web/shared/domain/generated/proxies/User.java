/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mousepilots.web.shared.domain.generated.proxies;

/**
 *
 * @author jgeenen
 */
public class User {
    String userName;
    
//    private static Long __ID_GENERATOR = Long.MAX_VALUE;
//    private static Long generateId(){
//        synchronized(User_Proxy.class){
//            return --__ID_GENERATOR;
//        }
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
}
