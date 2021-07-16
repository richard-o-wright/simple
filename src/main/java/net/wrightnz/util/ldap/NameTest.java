/*
 * NameTest.java
 *
 * Created on 10 August 2004, 09:37
 */

package net.wrightnz.util.ldap;

import java.util.*;
import javax.naming.*;

/**
 *
 * @author  richardw
 */
public class NameTest {
    
    Properties syntax = new Properties();
    
    /** Creates a new instance of NameTest */
    public NameTest() throws InvalidNameException{
        /* Direction for parsing ("right_to_left", "left_to_right", "flat"). If 
         unspecified, defaults to "flat", which means the namespace is flat with
         no hierarchical structure.*/ 
        syntax.put("jndi.syntax.direction","right_to_left");
        /* Separator between atomic name components. Required unless direction 
         is "flat". */ 
        syntax.put("jndi.syntax.separator",",");
        /* If present, "true" means ignore the case when comparing name
         components. If its value is not "true", or if the property is not
         present, case is considered when comparing name components.*/
        syntax.put("jndi.syntax.ignorecase","true");
        /* If present, specifies the escape string for overriding separator, 
          escapes and quotes. */
        syntax.put("jndi.syntax.escape","\\");
        /* If present, specifies the string delimiting start of a quoted string.*/
        syntax.put("jndi.syntax.beginquote","\"");
        /* String delimiting end of quoted string. If present, specifies the
         string delimiting the end of a quoted string. If not present, use 
         syntax.beginquote as end quote. */ 
        syntax.put("jndi.syntax.endquote","\"");
        /* Alternative set of begin/end quotes. */
        syntax.put("jndi.syntax.beginquote2","'");
        /* Alternative set of begin/end quotes. */
        syntax.put("jndi.syntax.endquote2","'");
        /* If present, "true" means trim any leading and trailing whitespaces in
         a name component for comparison purposes. If its value is not "true", 
         or if the property is not present, blanks are significant.*/
        syntax.put("jndi.syntax.trimblanks","true");
                   /*"jndi.syntax.trimblanks"*/
        /* If present, specifies the string that separates attribute-value-assertions 
         when specifying multiple attribute/value pairs. (e.g. "," in age=65,gender=male).*/
        syntax.put("jndi.syntax.separator.ava",",");
        /* If present, specifies the string that separators attribute from value (e.g. "=" in "age=65") */
        syntax.put("jndi.syntax.separator.typeval","=");
        Name name1 = new CompoundName("uid=owner2000,ou=people,dc=xtra,dc=co,dc=nz,o=internet", syntax);
        Name name2 = new CompoundName("uid=owner2000, ou=people, dc=xtra, dc=co, dc=nz, o=internet", syntax);
        System.out.println("Name 1 equals Name 2? " + name1.equals(name2));
        System.out.println("Name Ones hash code is " + name1.hashCode());
        System.out.println("Name Twos hash code is " + name2.hashCode());
        System.out.println("Name 1's labels are: ");
        printLabels(name1.getAll());
        System.out.println("Name 2's labels are: ");
        printLabels(name2.getAll());
        
        Map<Name, String> names = new HashMap<Name, String>();
        names.put(name1, "1");
        names.put(name2, "2");
        System.out.println("Hashtable size is: " + names.size());
        Name name3 = new CompoundName("UID=owner2000, OU=people, DC=xtra, DC=co,DC=nz,O=internet", syntax);
        System.out.println("Name 3 is: " + name3.toString());
        System.out.println("Value: " + names.get(name3).toString());
    }
    
    private void printLabels(Enumeration en){
        while(en.hasMoreElements()){
            String label = en.nextElement().toString();
            System.out.println(label);
        }
    }
    
    public static void main(String[] args )throws InvalidNameException{
        NameTest test = new NameTest();
    }
    
    
    public void addEntry(){
    }
    
}
