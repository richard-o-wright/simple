/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.test;

/**
 *
 * @author Richard Wright
 */
public class StringPlay {
    
    private static char[] INVALID_NAME_CHARACTERS = {'.', '-', '&', '/', '\'', '_'};

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        StringPlay test = new StringPlay();
        String name = "Mary-Ann & Tom& --& Criton-Ward'";
        System.out.println("[" + test.removeAmps(name) + "]");
        System.out.println("[" + test.removeInvalidCharactersFromName("Mary-Ann & Tom Criton-Ward'") + "]");
        
        String str = "&q=%5B%5B%3Ad%20%3D%20any(document.type%2C%20%5B%22plan%22%2C%20%22plan-list%22%2C%20%22plan-note%22%2C%22plan-note-list%22%5D)%5D%5D";
        System.out.println(java.net.URLDecoder.decode(str, "UTF-8"));
        
       
        String query = "[[:d = any(document.type, [\"plan\"])]]";
        System.out.println(java.net.URLEncoder.encode(query, "UTF-8"));
    }

    /**
     * This is ugly but we need to get rid of characters that occur in the first
     * and last name that will cause a validation error latter on. Better to do
     * it here then show an error message telling the customer that the first and
     * last names we have for them in ICMS are invalid.
     * 
     * @param name
     * @return 
     */
    private String removeInvalidCharactersFromName(String name){
        name = removeAmps(name);
        for(int i = 0; i < INVALID_NAME_CHARACTERS.length; i++){
            name = name.replace(INVALID_NAME_CHARACTERS[i], ' ');
        }
        return name;
    }
    
    private String removeAmps(String name) {
        int indexOfAmp = name.indexOf(" & ");
        if (indexOfAmp >= 0) {
            String beforeAmp = name.substring(0, indexOfAmp);
            String afterAmp = name.substring(indexOfAmp + 2);
            return removeAmps(beforeAmp + afterAmp);
        } else {
            return name;
        }
    }

    
    
}
