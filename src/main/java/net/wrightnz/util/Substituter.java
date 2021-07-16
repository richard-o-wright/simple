/*
  ===============================================================================
  |
  | Confidential & Copyright (c)2006. Xtra, Telecom New Zealand Limited
  | All Rights Reserved.
  | This document is the property, confidential and proprietary information of
  | Telecom New Zealand Limited.  This document or any part thereof may not be
  | disclosed to any third party with out the prior written permission of Telecom
  | New Zealand Limited.
  | No part of this document may be  reproduced, stored in a retrieval system or
  | transmitted in any form or by any means, electronic, mechanical, magnetic,
  | optical or otherwise without the prior written permission of Telecom New
  | Zealand Limited.
  ===============================================================================
 */

package net.wrightnz.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author  Richard Wright
 */
public class Substituter {
	
    /**
     * Return a message. The message is obtained from a template string,
     * and values are interpolated into the message.
     *
     * @param template the template for the message;
     * @param tagValues   A list of TagAndValue objects, containing tags and their
     *                    corresponding values; all instances of the tag in the message
     *                    will be replaced by the value.  The tags will be replaced in the
     *                    list order -- so earlier tags can contain later tags, and the
     *                    replacement will work.
     *
     * @returns the message as String.
     * @throws TagSubstituterException  If a tag cannot be replaced.
     */
    public static String substitute(String template, List<TagAndValue> tagValues) throws TagSubstituterException {        
        String result = template;
        try {
            for (TagAndValue entry : tagValues) {
                String tag = entry.getTag();
                String value = entry.getValue();
                Pattern pattern = Pattern.compile(tag);
                Matcher matcher = pattern.matcher(result);
                result = matcher.replaceAll(value);                
            }
            return result;
        }
        catch (NullPointerException npe){
            throw new TagSubstituterException("NullPointerException: It's likely the tagValues has nulls in it: " + npe.toString());
        }    
    }
    /**
     * Main methid to unit test the class.
     * @param arguments Not used
     * @throws nz.co.telecom.idt.util.TagSubstituterException
     */
    public static void main(String[] arguments)throws TagSubstituterException{
        List<TagAndValue> tandV = new ArrayList<TagAndValue>();
        tandV.add(new TagAndValue("<insertgroup>", "#### <groupid> ####"));
        tandV.add(new TagAndValue("<groupid>", "1"));
        String template = "<groupid> blah <insertgroup> blah blah <groupid> blah fish chips <groupid> blah <groupid>";
        System.out.println(Substituter.substitute(template, tandV));
    }
    
}
