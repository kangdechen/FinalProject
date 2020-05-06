

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
import Dao.TagDao;
import bean.Recommendation;

import com.google.gson.Gson;

/**
 * Servlet implementation class listTagServlet
 */
@WebServlet("/listTagServlet")
public class listTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listTagServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    doPost(request, response);
	    response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("received");
		  PrintWriter out = response.getWriter();
		   int recid = Integer.parseInt(request.getHeader("recID"));
		    response.setContentType("application/json;charset=utf-8");
		    ArrayList<String> list = new ArrayList<>();
		    try {
		      list =TagDao.query(recid);
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
