/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.util.encodedecode;

import java.util.Base64;

/**
 * @author Richard Wright
 */
public class Base64Utils {

  /**
   * This method attempts to base64 decode the input value and return the
   * result as a String.
   * It also attempts to handle input values that are the result of base64
   * encoding a string followed by a re-base64 encoding the resulting
   * encoding string. A strange thing to do but it occurs often
   * in MDS attribute values.<p/>
   * It's method for detecting a double base64 encode is not completely
   * reliable (checking if the first base64 decode result ending  with '=')
   * so use with caution please.
   * <br/>
   * <b>This method is not suitable for decoding base64 encoded binary data.</b>
   *
   * @param value base64 (or double base64) encoded input string.
   * @return the decoded value of the base64 encoded input string.
   */
  public static String decode(String value) {
    String decodedValue = base64Decode(value);
    if (decodedValue.endsWith("=")) {
      if (decodedValue.startsWith(": ")) {
        decodedValue = decodedValue.substring(2, decodedValue.length());
      }
      decodedValue = base64Decode(decodedValue);
    }
    return decodedValue;
  }


  /**
   * This method base64 decode the input value and return the result as a
   * String using the default character encoding.
   * <br/>
   * <b>This method is not suitable for decoding base64 encoded binary data.</b>
   *
   * @param value base64 encoded input string.
   * @return the decoded value of the base64 encoded input string as a string.
   */
  public static String base64Decode(String value) {
    return new String(decodeBase64(value));
  }

  /**
   * This method base64 decodes the input value and return the
   * result as byte array. This method is suitable for decoding base64
   * encoded binary data.
   *
   * @param value base64 encoded input string.
   * @return the decoded value as a byte array.
   */
  public static byte[] decodeBase64(String value) {
    return Base64.getDecoder().decode(value);
  }

  /**
   * Base64 encode the specified byte array (value)
   *
   * @param value the data to base64 encode.
   * @return the base64 encoding of the input value.
   */
  public static String encodeBase64(byte[] value) {
    return Base64.getEncoder().encodeToString(value);
  }

}
