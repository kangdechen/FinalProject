

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.RecomDao;
import bean.Recommendation;

import com.google.gson.Gson;

/**
 * Servlet implementation class SearchRecServlet
 */
@WebServlet("/SearchRecServlet")
public class SearchRecServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRecServlet() {
        super();
        // TODO Auto-generated constructor stu	b
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
       
        System.out.println("received request");
      }
      
      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String search ="";
        String Filter ="";
        String name ="";
         search= request.getHeader("Search");
         Filter= request.getHeader("Filter");
         name =request.getHeader("uname");
        
        System.out.println("search ="+search +"and filter ="+Filter);
        response.setContentType("application/json;charset=utf-8");
        ArrayList<Recommendation> list = new ArrayList<>();
        try {
          list = RecomDao.Search(search, Filter,name);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        } catch (SQLException e) {
          e.printStackTrace();
        } 
        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);
        out.print(json);
      }

}
