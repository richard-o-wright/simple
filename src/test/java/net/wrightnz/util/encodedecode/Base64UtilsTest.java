package net.wrightnz.util.encodedecode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class Base64UtilsTest {

  @Test
  void test() {
    String initialValue = "Fish & Chips";
    byte[] valueBytes = initialValue.getBytes(StandardCharsets.UTF_8);
    String base64EncodedValue = Base64Utils.encodeBase64(valueBytes);
    System.out.println("Encoded Fish & Chips: " + base64EncodedValue);
    byte[] encodedBytes = base64EncodedValue.getBytes(StandardCharsets.UTF_8);
    String doubleEncodedValue = Base64Utils.encodeBase64(encodedBytes);
    System.out.println("Double decoded Fish & Chips: " + Base64Utils.decode(doubleEncodedValue));
    assertEquals("Um1semFDQW1JRU5vYVhCeg==", doubleEncodedValue);

    String encodedValue = Base64Utils.encodeBase64(valueBytes);
    String decodedValue = Base64Utils.decode(encodedValue);
    assertEquals(initialValue, decodedValue);
  }

}
