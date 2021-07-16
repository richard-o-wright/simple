/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.web;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author t466225
 */
public class SessionNotInitialisedException extends Exception{
    
    private List<String> sessionKeys;
    
    public SessionNotInitialisedException(){
        super();
    }
    
    public SessionNotInitialisedException(String msg){
        super(msg);
    }
    
    public SessionNotInitialisedException(String msg, String sessionKey){
        super(msg);
        sessionKeys = new ArrayList<String>();
        sessionKeys.add(sessionKey);
    }
    
    public SessionNotInitialisedException(String msg, List<String> sessionKeys){
        super(msg);
        this.sessionKeys = sessionKeys;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("SessionNotInitialisedException: ");
        sb.append(getMessage());
        sb.append("\n");
        sb.append("Session Keys Not Found\n");
        for(String sessionKey : sessionKeys){
            sb.append(sessionKey);
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
