/*
 * HelloImpl.java
 *
 * Created on 9 August 2004, 13:30
 */

package net.wrightnz.webservices;

public class HelloImpl implements HelloIF {

    public String message ="Hello";

    public String sayHello(String s) {
        return message + s;
    }
}
