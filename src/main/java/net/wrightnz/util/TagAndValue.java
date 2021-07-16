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

import net.wrightnz.data.ValueObjectBase;

/**
 *
 * @author  Richard Wright
 */
public class TagAndValue extends ValueObjectBase implements java.io.Serializable{
    
    private String tag;
    private String value;
    
    /**
     * Create a new TagAndValue
     * @param tag
     * @param value
     */
    public TagAndValue(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }
    
    public String getTag() {
        return tag;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
        
}