package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtil;
import bean.Recommendation;
import bean.Tags;

public class TagDao {

	
	  public static ArrayList query(int recId) throws SQLException, ClassNotFoundException {
		    Connection conn = DatabaseUtil.getconn();
		    int rec=recId;
		    ArrayList<Tags> list = new ArrayList<>();
		   
		    String sql = "select contexts, count(contexts)as cnt from tag where recomId= ? group by contexts order by cnt desc limit 8 ";
		    PreparedStatement stat = conn.prepareStatement(sql);
		    stat.setInt(1,rec);
		    ResultSet rs = stat.executeQuery();
		    while (rs.next()) {
		    
		    	
		      
		      String tags = rs.getString("contexts");
		     
		      System.out.println(tags);
		      
		      Tags t =new Tags(tags);
		      list.add(t);
		    } 
		    return list;
		  }
	  
	  public static boolean addTag(List<String> tagList, int recId) throws SQLException, ClassNotFoundException {
		    Connection conn = DatabaseUtil.getconn();
		    int rec=recId;
		    boolean result =false;
		    List<String> l =tagList;
		    String sql = "insert into tag (recomId,contexts)value (?,?); ";
		    PreparedStatement stat = conn.prepareStatement(sql);
		   
		    try
		    {
		    	  for(int i = 0; i< l.size();i++){
					    
					    stat.setInt(1,rec);
					    stat.setString(2, l.get(i));
					    int r = stat.executeUpdate();
					      System.out.print(r);
					      if (r > 0) {
					        result = true;
					        System.out.println("Sucess" + result);
					      } 
					      
					    }
		    	  stat.close();
		    	
		    } finally {
		        conn.close();
		      } 
		      return result;
		    
		    
		    
	  }
	  
	  
	 
}
