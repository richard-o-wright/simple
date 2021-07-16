/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util;

/**
 * A holding class for general String utility stuff I haven't found a better
 * place for yet.
 * 
 * @author T466225
 */
public class StringUtil {

     /**
     * Test if the specified string starts with the specified prefix regardless
     * of case. For example startsWithIgnoreCase("AABBCC", "aABb") is true.
     *
     * @param string
     * @param prefix
     * @return true if string starts with prefix.
     */
    public static boolean startsWith_IgnoreCase(String string, String prefix) {
        string = string.toLowerCase();
        prefix = prefix.toLowerCase();
        return string.startsWith(prefix);
    }

    /**
     * Test if the specified string ends in the specified prefix regardless
     * of case. For example endsWithIgnoreCase("AABBCC", "bBCc") is true.
     *
     * @param string
     * @param suffix
     * @return true if string ends with suffix.
     */
    public static boolean endsWith_IgnoreCase(String string, String suffix) {
        string = string.toLowerCase();
        suffix = suffix.toLowerCase();
        return string.endsWith(suffix);
    }
    

    public static String xmlEscape(String string){
        if(string != null){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < string.length(); i++){
                sb.append(xmlEscapeChar(string.charAt(i)));
            }
            return sb.toString();
        }
        else{
            return "null";
        }
    }

    public static String xmlEscapeChar(char c){
        String s = Character.toString(c);
        if("<".equals(s)){
            return "&lt;";
        }
        else if(">".equals(s)){
            return "&gt;";
        }
        else if("&".equals(s)){
            return "&amp;";
        }
        else{
            return s;
        }
    }

    /**
    * Insert a String after a search string,
    * no chars in the original string are removed.
    *
    * @param startString string the search for.
    * @param insertString string to insert after startString.
    * @param startIndex index in line to start searching at.
    *
    * @return String with all occarance of startString converted to startString + insertString.
    */
    public static String insertAfter(String line, String startString, String insertString, int startIndex) {
        if (line.indexOf(startString, startIndex) < 0) {
            return line;
        } else {
            int insertPoint = line.indexOf(startString, startIndex) + startString.length();
            StringBuilder newLine = new StringBuilder();
            newLine.append(line.substring(0, insertPoint));
            newLine.append(insertString);
            newLine.append(line.substring(insertPoint));
            return insertAfter(newLine.toString(), startString, insertString, insertPoint + insertString.length());
        }
    }

    /**
     * Find and replace <b>first</b> occurance of searchString in a given line.
     *
     * @param startString string the search for.
     * @param searchText text to replace.
     * @param replaceText string to replace searchText with.
     */
    public static String findReplace(String line, String searchText, String replaceText) {
        StringBuilder tempContent = new StringBuilder();
        int index = indexOfIgnoreCase(line, searchText);
        if (index >= 0) {
            tempContent.append(line.substring(0, index));
            tempContent.append(replaceText);
            tempContent.append(line.substring(index + searchText.length()));
            return tempContent.toString();
        } else {
            return line;
        }
    }

    /**
     * Return the starting index of a sub string in this String
     * case insencative.
     *
     * @param line the string to search for the substring in.
     * @param search the substring to be searched for.
     *
     * @return The index of a search string in line.
     */
    public static int indexOfIgnoreCase(String line, String search) {
        for (int i = 0; i <= line.length() - search.length(); i++) {
            String lineBit = line.substring(i, i + search.length());
            if (search.equalsIgnoreCase(lineBit)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Trims white spaces from the right hand end of the
     * input line only.
     *
     * @param line: the line to trim.
     *
     * @return line with trailing white spaces removed.
     */
    public static String removeTrailingWhiteSpaces(String line) {
        int endPtr = line.length() - 1;
        while (endPtr > 0 && Character.isWhitespace(line.charAt(endPtr))) {
            endPtr--;
        }
        if (endPtr == 0) {
            return "";
        } else {
            return line.substring(0, endPtr + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(xmlEscape("This is & test <of> xml esc&pe\n is & new < line a \t\n problem&"));
    }
}
