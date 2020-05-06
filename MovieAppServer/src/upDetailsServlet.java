

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONToObject;
import Dao.RecomDao;
import Dao.UpDao;
import Service.UserService;
import bean.Recommendation;
import bean.UserPreference;

import com.google.gson.Gson;

/**
 * Servlet implementation class upDetailsServlet
 */
@WebServlet("/upDetailsServlet")
public class upDetailsServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public upDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
//		     search= request.getHeader("Search");
//		     Filter= request.getHeader("Filter");
		     uname =request.getHeader("uname");
		     recId=Integer.parseInt(request.getHeader("recId"));
		    System.out.println("uname" +uname+ "  recid  +"+recId);
		    response.setContentType("application/json;charset=utf-8");
		    UserPreference up =new UserPreference();
		    try {
		      up = UpDao.checkStatus(uname, recId);
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } 
		    
		    if(up!=null) {
		    	Gson gson =new Gson();
		      
		      responseMessage = gson.toJson(up);
		      System.out.println("get up detail responseMsg: succeed");
		    } 
		    else
		    {
		    	responseMessage =null;
		    }
		    
		    System.out.println("parse json from " + responseMessage);
		    
		  
		    out.print(responseMessage);
		  }
		  
	

}
