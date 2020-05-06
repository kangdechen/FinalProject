	

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Tags;

import com.google.gson.Gson;

import util.JSONToObject;
import Dao.TagDao;
import Service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class addTagServlet
 */
@WebServlet("/addTagServlet")
public class addTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addTagServlet() {
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
		// TODO Auto-generated method stub
		int recId = Integer.parseInt(request.getHeader("recID"));
		int uId = Integer.parseInt(request.getHeader("userId"));
		
		System.out.println("recid = "+recId);
		System.out.println("userid = "+uId);
		StringBuffer stringBuffer = new StringBuffer();
	    String line = null, responseMessage ="false";
	    Gson gson =new Gson();
	    List <String>tagList = null;
	    boolean result =false;
	    try {
	      BufferedReader bufferedReader = request.getReader();
	      while ((line = bufferedReader.readLine()) != null)
	        stringBuffer.append(line); 
	    
	    } catch (IOException e) {
	      e.printStackTrace();
	    } 
	    

      
        	JSONArray jsonArray = JSONArray.fromObject(stringBuffer.toString());

            tagList=jsonArray;
            	
            for(int i = 0; i< jsonArray.size();i++){
//                JSONObject obj = jsonArray.getJSONObject(i);
            	


                String tag= jsonArray.getString(i);
                System.out.println(tag);
               
//               tagList.add(tag);

            }

     

	    PrintWriter out = response.getWriter();
	    response.setContentType("application/json;charset=utf-8");
	    
	    try {
	      result = TagDao.addTag(tagList,recId);
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } 
	    if (result) {
	      
	      responseMessage = "true";
	      System.out.println(" responseMsg: succeed");
	    } 
	    
	    else
	    {
	    	System.out.println("add tag  responseMsg: fail");
	    }
	    System.out.println("parse json from " + responseMessage);
	    out.print(responseMessage);
		
		 
	}



}
