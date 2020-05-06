package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DatabaseUtil;
import bean.User;
import bean.UserPreference;

public class UpDao {
	
	  static PreparedStatement ps = null;
	  
	  static ResultSet rs = null;
	  
	  public static UserPreference checkStatus(String username, int recId) throws SQLException, ClassNotFoundException {
		    Connection conn = DatabaseUtil.getconn();
		    String un =username;
		    UserPreference up =null;
		    int r =recId;
		    System.out.println(username +"  recid= "+recId);
		    try {
		      ps = conn.prepareStatement("select * from userPreference where recId =? and userName =? ;");
		      ps.setInt(1, r);
		      ps.setString(2,un);
		      rs = ps.executeQuery();
		      
		       while(rs.next()) {
		    	  
		    	   System.out.println(rs.getInt("upId")+rs.getString("userName")+rs.getString("uploader")+
		    			   rs.getString("movieName")+rs.getInt("rating")+rs.getInt("recId") );
			        
		    	  
		    	   up =new UserPreference(rs.getInt("upId"),rs.getString("userName"),rs.getString("uploader"),
		    			   rs.getString("movieName"),rs.getInt("rating"),rs.getInt("recId"));
		       
		       
		      } 
		      ps.close();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } finally {
		      conn.close();
		    } 
		    return up;
		  }
	public static boolean deleteUp(String uname, int recId, int rating) throws SQLException, ClassNotFoundException {
		
		Connection conn = DatabaseUtil.getconn();
	    String un =uname;
	    int r =recId;
	    int ra=rating;
	    boolean result =false;
	
	    try {
	      ps = conn.prepareStatement("delete from userpreference where recId= ? and userName =? and rating =? ;");
	      ps.setInt(1, r);
	      ps.setString(2,un);
	      ps.setInt(3,ra);
	      int su = ps.executeUpdate();
	      System.out.print(r);
	      if (su > 0) {
	        result = true;
	        System.out.println(" successly delete  ? " + result);
	      } 
	    
	      ps.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } finally {
	      conn.close();
	    } 
	    return result;
	}

}
