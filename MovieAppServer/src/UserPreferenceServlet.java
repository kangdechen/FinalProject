

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bean.UserPreference;
import util.JSONToObject;
import Service.UserService;

/**
 * Servlet implementation class UserPreferenceServlet
 */
@WebServlet("/UserPreferenceServlet")
public class UserPreferenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	  private UserPreference up;
	  
	  private boolean result = false;
	  
	  private String sessionId;
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPreferenceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		StringBuffer stringBuffer = new StringBuffer();
	    String line = null, responseMessage = "false";
	    try {
	      BufferedReader bufferedReader = request.getReader();
	      while ((line = bufferedReader.readLine()) != null)
	        stringBuffer.append(line); 
	      
	      this.up = (new JSONToObject()).GsonToUp(stringBuffer.toString());
	      System.out.println("parse from json " + this.up.getUserName() + this.up.getUploader() + this.up.getRate());
	      System.out.println("SessionId" + this.sessionId);
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	    PrintWriter out = response.getWriter();
	    response.setContentType("application/json;charset=utf-8");
	    try {
	      this.result = UserService.addPreference(this.up);
	      System.out.println(String.valueOf(this.result) + "from dao");
	      if (this.result) {
	        responseMessage = "true";
	        System.out.println(responseMessage);
	      } else {
	        System.out.println("login servlet responseMsg: fail");
	      } 
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	    System.out.println("" + responseMessage);
	    out.print(this.result);
	}

}
