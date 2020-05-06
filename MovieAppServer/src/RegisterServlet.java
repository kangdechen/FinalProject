import Service.UserService;
import bean.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.JSONToObject;

@WebServlet({"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private User user;
  
  private boolean result = false;
  
  private String sessionId;
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    StringBuffer stringBuffer = new StringBuffer();
    String line = null, responseMessage = "false";
    try {
      BufferedReader bufferedReader = request.getReader();
      while ((line = bufferedReader.readLine()) != null)
        stringBuffer.append(line); 
      this.sessionId = request.getSession().getId().toString();
      this.user = (new JSONToObject()).GsonToObject(stringBuffer.toString());
      System.out.println("parse from json " + this.user.getUsername() + this.user.getPassword() + this.user.getPic());
      System.out.println("SessionId" + this.sessionId);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    PrintWriter out = response.getWriter();
    response.setContentType("application/json;charset=utf-8");
    try {
      this.result = UserService.register(this.user);
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
