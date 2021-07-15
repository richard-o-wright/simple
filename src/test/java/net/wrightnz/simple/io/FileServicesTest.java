package net.wrightnz.simple.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;

class FileServicesTest {

  @Test
  void getHumanReadableFileSize() {
    String fileSize = FileServices.getHumanReadableFileSize(new File("README.md"));
    assertEquals("44 bytes", fileSize);
  }

}
