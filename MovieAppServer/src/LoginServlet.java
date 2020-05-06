import Service.UserService;
import bean.User;

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

import com.google.gson.Gson;

import util.JSONToObject;

@WebServlet({"/LoginServlet"})
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  public User user;
  
  Cookie ckUsername;
  
  Cookie ckSessionid;
  
  private String sessionId;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    StringBuffer stringBuffer = new StringBuffer();
    String line = null, responseMessage = null;
    
    try {
      BufferedReader bufferedReader = request.getReader();
      while ((line = bufferedReader.readLine()) != null)
        stringBuffer.append(line); 
      this.user = (new JSONToObject()).GsonToObject(stringBuffer.toString());
      System.out.println("parse from json " + this.user.getUsername());
      System.out.println("SessionId= " + request.getSession().getId());
    } catch (IOException e) {
      e.printStackTrace();
    } 
    PrintWriter out = response.getWriter();
    response.setContentType("application/json;charset=utf-8");
    try {
      user= UserService.login(this.user.getUsername(), this.user.getPassword());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    if(user!=null) {
    	Gson gson =new Gson();
      
      responseMessage = gson.toJson(user);
      System.out.println("login servlet responseMsg: succeed");
    } 
    
    System.out.println("parse json from " + responseMessage);
    out.print(responseMessage);
  }
}
