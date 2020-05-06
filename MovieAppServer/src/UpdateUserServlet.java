

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import util.JSONToObject;
import Service.UserService;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private User user = null;
	private Boolean result =false;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 doPost(request, response);
	        response.getWriter().append("Served at: ").append(request.getContextPath());
	        System.out.println("received request");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 StringBuffer stringBuffer = new StringBuffer();
		 
		    String line = null, responseMessage = "false";
		    try {
		      BufferedReader bufferedReader = request.getReader();
		      while ((line = bufferedReader.readLine()) != null)
		        stringBuffer.append(line); 
		     
		      this.user = (new JSONToObject()).GsonToObject(stringBuffer.toString());
		      System.out.println("parse from json " + user.getUsername() +user.getPassword() + user.getPic());
		    
		    } catch (IOException e) {
		      e.printStackTrace();
		    } 
		    PrintWriter out = response.getWriter();
		    response.setContentType("application/json;charset=utf-8");
		    try {
		    	
		      this.result = UserService.update(this.user);
		      
		      System.out.println(String.valueOf(result) + "from dao");
		      if (result) {
		        responseMessage = "true";
		        System.out.println(responseMessage);
		      } else {
		        System.out.println("update user servlet responseMsg: fail");
		      } 
		    } catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } catch (SQLException e) {
		      e.printStackTrace();
		    } 
		    System.out.println("" + responseMessage);
		    out.print(result);
		  }

}
