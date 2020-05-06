package Dao;

import bean.Recommendation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtil;

public class RecomDao {
  List<Recommendation> recList = new ArrayList<>();
  
  public static ArrayList query( ) throws SQLException, ClassNotFoundException {
    Connection conn = DatabaseUtil.getconn();

    ArrayList<Recommendation> list = new ArrayList<>();
  
        {
        	String sql = "select * from recommendation order by recId desc;";
            PreparedStatement stat = conn.prepareStatement(sql);
           
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
              int Id = rs.getInt("recId");
              String username = rs.getString("userName");
              String movieName = rs.getString("movieName");
              String comment = rs.getString("comments");
              Float rating = Float.valueOf(rs.getFloat("rating"));
              String picUrl = rs.getString("picUrl");
              Recommendation recs = new Recommendation(Id, username, movieName, comment, rating, picUrl);
              System.out.println(recs);
              list.add(recs);
        }
    
    } 
    return list;
  }
  
  public static boolean addRecommend(Recommendation rec) throws SQLException, ClassNotFoundException {
    Connection conn = DatabaseUtil.getconn();
    boolean result = false;
    try {
      String sql = "insert into recommendation (userName,movieName,comments,rating,picUrl) values (?,?,?,?,?);";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, rec.getUsername());
      ps.setString(2, rec.getMovieName());
      ps.setString(3, rec.getComment());
      ps.setFloat(4, rec.getRating().floatValue());
      ps.setString(5, rec.getPic());
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
  


public static ArrayList<Recommendation> Search(String search, String filter ,String name)throws SQLException, ClassNotFoundException {
	
    Connection conn = DatabaseUtil.getconn();
    ArrayList<Recommendation> list = new ArrayList<>();
    String n =search;
    String f =filter;
    String un =name;
    System.out.println(n + f);
    String sql;
    if(filter=="")
    {
//    	 sql = "select * from recommendation where userName = '"+n+"'or movieName = '"+n+"' order by rating desc";
    	 sql = "select * from recommendation where userName = ? or movieName = ? order by rating desc";
    	 System.out.println(sql);
    	  PreparedStatement ps = conn.prepareStatement(sql);
    	  ps.setString(1, n);
    	  ps.setString(2, n);
    	    ResultSet rs = ps.executeQuery();
    
    	    while (rs.next()) {
    	      int Id = rs.getInt("recId");
    	      String username = rs.getString("userName");
    	      String movieName = rs.getString("movieName");
    	      String comment = rs.getString("comments");
    	      Float rating = Float.valueOf(rs.getFloat("rating"));
    	      String picUrl = rs.getString("picUrl");
    	      Recommendation recs = new Recommendation(Id, username, movieName, comment, rating, picUrl);
    	      System.out.println(recs);
    	      list.add(recs);
    	    } 
    }
    else if(filter.equals("rec"))
    {

        sql = "select *  from recommendation order by calculate_sum(?,recommendation.movieName,recommendation.userName) desc;";
        PreparedStatement stat = conn.prepareStatement(sql);
        stat.setString(1,un);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
          int Id = rs.getInt("recId");
          String username = rs.getString("userName");
          String movieName = rs.getString("movieName");
          String comment = rs.getString("comments");
          Float rating = Float.valueOf(rs.getFloat("rating"));
          String picUrl = rs.getString("picUrl");
          Recommendation recs = new Recommendation(Id, username, movieName, comment, rating, picUrl);
          System.out.println(recs);
          list.add(recs);
    }
   }
 
    else
    {
    	  sql = "select * from recommendation where "+f+" = ? order by rating desc";
    	  System.out.println(sql);
    	  PreparedStatement ps = conn.prepareStatement(sql);
  
    	  ps.setString(1, n);
    	    ResultSet rs = ps.executeQuery();
    	    
    	    while (rs.next()) {
    	      int Id = rs.getInt("recId");
    	      String username = rs.getString("userName");
    	      String movieName = rs.getString("movieName");
    	      String comment = rs.getString("comments");
    	      Float rating = Float.valueOf(rs.getFloat("rating"));
    	      String picUrl = rs.getString("picUrl");
    	      Recommendation recs = new Recommendation(Id, username, movieName, comment, rating, picUrl);
    	      System.out.println(recs);
    	      list.add(recs);
    	    } 
    }
  
  
    return list;
}
}
