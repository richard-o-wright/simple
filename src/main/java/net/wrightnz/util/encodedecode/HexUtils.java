/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.util.encodedecode;

/**
 * Static Utility methods for dealing with Hex encoded data.
 *
 * @author Richard Wright
 */
public class HexUtils {

  /**
   * Decode the input hex encoded string.
   *
   * @param string hex encoded string to decode
   * @return the decoded value as a byte array
   */
  public static byte[] hexDecode(String string) {
    byte[] result = new byte[string.length() / 2];
    int c = 0;
    for (int i = 0; i < string.length() - 1; i += 2) {
      String ch = string.substring(i, i + 2);
      int decodedInt = Integer.decode("0x" + ch);
      result[c] = (byte) decodedInt;
      c++;
    }
    return result;
  }

  /**
   * Hex encode the input byte array
   *
   * @param bytes value to encode
   * @return the hex encode version of the input byte array
   */
  public static String hexEncode(byte[] bytes) {
    StringBuilder hexData = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      hexData.append(padHexString(Integer.toHexString(0xff & bytes[i])));
    }
    return hexData.toString();
  }

  /**
   * I don't comment private methods, sue me:-)
   *
   * @param str
   */
  private static String padHexString(String str) {
    if (str.length() == 1) {
      StringBuilder sb = new StringBuilder();
      sb.append("0");
      sb.append(str);
      return sb.toString();
    } else {
      return str;
    }
  }

  /**
   * Main method for unit testing only.
   *
   * @param args - not used.
   */
  public static void main(String[] args) {
    byte[] value = {Byte.MAX_VALUE, 0, Byte.MIN_VALUE, 48, 12, 33, 56, 126, -50};
    System.out.print("Original Value: ");
    for (int i = 0; i < value.length; i++) {
      System.out.print(value[i] + ",");
    }
    System.out.println();
    String encodedValue = hexEncode(value);
    System.out.println("Encoded Value: " + encodedValue);
    byte[] decodeValue = hexDecode(encodedValue);
    System.out.print("Decode Value:   ");
    for (int i = 0; i < decodeValue.length; i++) {
      System.out.print(decodeValue[i] + ",");
    }
    System.out.println();
  }

}
