package org.example.bean;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class MatchDataBean {

  private long dataSeq;

  private Map<String, String> dataMap;

  public MatchDataBean() {
    dataSeq = 0;
    dataMap = new HashMap<>();
  }

  @Override
  public String toString() {
    return "MatchDataBean{" +
        "dataSeq=" + dataSeq +
        ", dataMap=" + dataMap.size() +
        '}';
  }
}
