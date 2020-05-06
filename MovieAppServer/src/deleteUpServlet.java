

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.UpDao;
import bean.UserPreference;

import com.google.gson.Gson;

/**
 * Servlet implementation class deleteUpServlet
 */
@WebServlet("/deleteUpServlet")
public class deleteUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub、
		 doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String responseMessage =null;
		 PrintWriter out = response.getWriter();
		   
		    String uname ="";
		    int recId;
		    int rating;
//		     search= request.getHeader("Search");
//		     Filter= request.getHeader("Filter");
		     uname =request.getHeader("uname");
		     recId=Integer.parseInt(request.getHeader("recId"));
		     rating=Integer.parseInt(request.getHeader("rating"));
		    System.out.println("uname" +uname+ "  recid  +"+recId +"  rating  " +rating);
		    response.setContentType("application/json;charset=utf-8");
		 
		   boolean result=false;
		    try {
		      result = UpDao.deleteUp(uname,recId,rating);
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } 
		    
		   
		    if(result)
		    {
		    	responseMessage="true";
		        System.out.println("the up successfully deleted");
		    }
		    
		    else
		    	{
		    	responseMessage=null;
		    	}
		    
		
		    
		  
		    out.print(responseMessage);
		  }
		  
	

}
