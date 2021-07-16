/*
 * HelloIF.java
 *
 * Created on 9 August 2004, 13:30
 */

package net.wrightnz.webservices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HelloIF extends Remote {
    String sayHello(String s) throws RemoteException;
}
