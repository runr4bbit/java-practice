package org.example.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class DataConvertor {

  public static byte[] serializeMap(Map<String, String> map) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos)) {
      out.writeObject(map);
      return bos.toByteArray();
    }
  }

  public static Map<String, String> deserializeMap(byte[] data)
      throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bis)) {
      return (Map<String, String>) in.readObject();
    }
  }
}
