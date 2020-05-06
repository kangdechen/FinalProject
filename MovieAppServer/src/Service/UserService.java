package Service;

import Service.UserService;
import bean.User;
import bean.UserPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DatabaseUtil;

public class UserService {
  static PreparedStatement ps = null;
  
  static ResultSet rs = null;
  
  static User user;
  
  public static User login(String username, String password) throws SQLException, ClassNotFoundException {
    Connection conn = DatabaseUtil.getconn();
    
    User user = null;
    try {
      ps = conn.prepareStatement("select * from Users where userName = ? && passwd=?");
      ps.setString(1, username);
      ps.setString(2, password);
      rs = ps.executeQuery();

       while(rs.next()) {
    	   user =new User(String.valueOf(rs.getInt("userId")),rs.getString("userName"),rs.getString("passwd"),rs.getString("picUrl"));
        System.out.println("id:" + rs.getString("userName") + " --login");
        System.out.println("id:" + rs.getInt("userId") + " --login");
        
       
      } 
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    } 
    return user;
  }
  
  public static boolean register(User user) throws SQLException, ClassNotFoundException {
    Connection conn = DatabaseUtil.getconn();
    boolean result = false;
    try {
      String sql = "insert into users (userName,passwd,picUrl)VALUES(?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getPic());
      int r = ps.executeUpdate();
      System.out.print(r);
      if (r > 0) {
        result = true;
        System.out.println("Sucess" + result);
      } 
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    } 
    return result;
  }
 

public static boolean addPreference(UserPreference up) 
		   throws SQLException, ClassNotFoundException {
	
	 Connection conn = DatabaseUtil.getconn();
	    boolean result = false;
	  
	    try {
	      String sql = "insert into userPreference (userName,uploader,movieName,rating,recId)VALUES(?,?,?,?,?)";
	      PreparedStatement ps = conn.prepareStatement(sql);
	      ps.setString(1, up.getUserName());
	      ps.setString(2, up.getUploader());
	      ps.setString(3, up.getMovieName());
	      ps.setInt(4,up.getRate());
	      ps.setInt(5, up.getRecId());
	      
	      int r = ps.executeUpdate();
	      System.out.print(r);
	      if (r > 0) {
	        result = true;
	        System.out.println("Sucess" + result);
	      } 
	      ps.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      conn.close();
	    } 
	    return result;
}
//public static Boolean updateUser(User user2) throws ClassNotFoundException, SQLException  {
//			user=user2;
//			System.out.println("NAME" +user.getUsername());
//			System.out.println("password" +user.getPassword());
//	 Connection conn = DatabaseUtil.getconn();
//	    boolean result = false;
//	    try {
//	      String sql = "update table Users set userName =? ,password=?,picUrl=?";
//	      PreparedStatement ps = conn.prepareStatement(sql);
//	      ps.setString(1, user.getUsername());
//	      ps.setString(2, user.getPassword());
//	      ps.setString(3, user.getPic());
//	      int r = ps.executeUpdate();
//	      System.out.print(r);
//	      if (r > 0) {
//	        result = true;
//	        System.out.println("Sucess update " + result);
//	      } 
//	      ps.close();
//	    } catch (SQLException e) {
//	      e.printStackTrace();
//	    } finally {
//	      conn.close();
//	    } 
//	    return result;
//}

public static Boolean update(User user2)throws SQLException, ClassNotFoundException {
	user=user2;
	System.out.println("NAME" +user.getUsername());
	System.out.println("password" +user.getPassword());
	System.out.println("pic" +user.getPic());
	System.out.println("id" +user.getNo());
	 Connection conn = DatabaseUtil.getconn();
	    boolean result = false;
	    try {
	      String sql = "update users set userName =? ,passwd=?,picUrl=? where userId=?";
	      PreparedStatement ps = conn.prepareStatement(sql);
	      ps.setString(1, user.getUsername());
	      ps.setString(2, user.getPassword());
	      ps.setString(3, user.getPic());
	      ps.setInt(4,Integer.parseInt(user.getNo()));
	      System.out.println(sql);
	      int r = ps.executeUpdate();
	      System.out.print(r);
	      if (r > 0) {
	        result = true;
	        System.out.println("Sucess update user" + result);
	      } 
	      ps.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      conn.close();
	    } 
	    return result;
}




public static String getPic(String username) throws SQLException, ClassNotFoundException {
    Connection conn = DatabaseUtil.getconn();
    String pic ="";
    try {
      ps = conn.prepareStatement("select picUrl from Users where userName = ? ");
      ps.setString(1, username);

      rs = ps.executeQuery();
      rs = ps.executeQuery();
      if (rs.next()) {
        System.out.println("id:" + rs.getString(1) + " --login");
       pic=rs.getString(1);
      } 
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      conn.close();
    } 
    return pic;
  }



  
}
