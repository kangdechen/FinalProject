package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.DatabaseUtil;

public class DatabaseUtil {
  private static final String driver = "com.mysql.cj.jdbc.Driver";
  
  private static final String url = "jdbc:mysql://localhost:3306/movies?useUnicode=true&characterEncoding=UTF-8";
  
  private static final String user = "root";
  
  private static final String password = "root";
  
  public Connection con = null;
  
  public static Connection getconn() throws ClassNotFoundException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movies?serverTimezone=GMT%2B8", "root", "root");
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return conn;
  }
  
  public void close() {
    try {
      this.con.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
