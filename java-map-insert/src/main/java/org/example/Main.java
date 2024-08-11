package org.example;

import java.io.IOException;
import java.sql.SQLException;
import org.example.bean.MatchDataBean;
import org.example.util.DataSource;

public class Main {

  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
    MatchDataBean matchData = new MatchDataBean();
    for (long i = 0; i < 100; i++) {
      matchData.setDataSeq(i);
      matchData.getDataMap().put(String.format("%016d", i), String.format("%016d", i));
    }
    System.out.println("Origin:" + matchData);

    DataSource dataSource = new DataSource();
    dataSource.updateData("firstMap", matchData);
    MatchDataBean dbMatchData = dataSource.selectData("firstMap");
    System.out.println("DB:" + dbMatchData);
  }
}