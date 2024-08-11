package org.example.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.bean.MatchDataBean;

public class DataSource {

  private static Connection connection;

  public DataSource() throws SQLException {
    String URL = System.getenv("URL");
    String USER = System.getenv("USER");
    String PASSWORD = System.getenv("PASSWORD");
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
  }

  public void updateData(String keyName, MatchDataBean matchData)
      throws SQLException, IOException {
    String mergeSQL = "MERGE INTO MATCH_DATA md " +
        "USING (SELECT ? AS key_name, ? AS seq_name, ? AS data_blob FROM dual) incoming " +
        "ON (md.key_name = incoming.key_name) " +
        "WHEN MATCHED THEN " +
        "    UPDATE SET md.seq_name = incoming.seq_name, md.data_blob = incoming.data_blob " +
        "WHEN NOT MATCHED THEN " +
        "    INSERT (key_name, seq_name, data_blob) " +
        "    VALUES (incoming.key_name, incoming.seq_name, incoming.data_blob)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(mergeSQL)) {
      preparedStatement.setString(1, keyName);
      preparedStatement.setString(2, Long.toString(matchData.getDataSeq()));
      preparedStatement.setBytes(3, DataConvertor.serializeMap(matchData.getDataMap()));
      preparedStatement.executeUpdate();
    }
  }

  public MatchDataBean selectData(String keyName)
      throws SQLException, IOException, ClassNotFoundException {
    String querySQL = "SELECT seq_name, data_blob FROM MATCH_DATA WHERE key_name=?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
      preparedStatement.setString(1, keyName);
      ResultSet resultSet = preparedStatement.executeQuery();

      MatchDataBean matchData = new MatchDataBean();
      if (resultSet.next()) {
        matchData.setDataSeq(Long.parseLong(resultSet.getString("seq_name")));
        matchData.setDataMap(DataConvertor.deserializeMap(resultSet.getBytes("data_blob")));
      }
      return matchData;
    }
  }
}
