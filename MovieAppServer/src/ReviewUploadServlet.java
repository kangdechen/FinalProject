import Dao.RecomDao;
import bean.Recommendation;
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

@WebServlet({"/ReviewUploadServlet"})
public class ReviewUploadServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  private static Recommendation rec;
  
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
      rec = (new JSONToObject()).GsonToRec(stringBuffer.toString());
      System.out.println("parse from json " + rec.getMovieName());
      System.out.println("SessionId= " + request.getSession().getId());
    } catch (IOException e) {
      e.printStackTrace();
    } 
    PrintWriter out = response.getWriter();
    response.setContentType("application/json;charset=utf-8");
    boolean result = false;
    try {
      result = RecomDao.addRecommend(rec);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    if (result) {
      responseMessage = "true";
      System.out.println(responseMessage);
    } else {
      System.out.println("login servlet responseMsg: fail");
    } 
    out.print(responseMessage);
  }
}
