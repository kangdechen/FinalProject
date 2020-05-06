import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.UserService;


@WebServlet({"/DownLoadServlet"})
public class DownLoadServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
    String fileName = request.getHeader("filename");
    boolean profile = Boolean.valueOf(request.getHeader("profile"));	
    System.out.println(String.valueOf(profile)+ "? profile");
    System.out.println(String.valueOf(fileName) + "=filename");
    
    if(profile)
    {
    	try {
			fileName =UserService.getPic(fileName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");
    System.out.println(String.valueOf(fileName) + "=new filename");
    String fileSaveRootPath = getServletContext().getRealPath("/WEB-INF/upload");
    
    String path = findFileSavePathByFileName(fileName, fileSaveRootPath);
    File file = new File(String.valueOf(path) + "\\" + fileName);
    
    
    if (!file.exists()) {
      request.setAttribute("message","the file request is not exist");
      fileName="404.png";
       path = fileSaveRootPath;
     
    } 
//    String realname = fileName.substring(fileName.indexOf("_") + 1);
//    System.out.println(String.valueOf(realname) + "=realname");
    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    FileInputStream in = new FileInputStream(String.valueOf(path) + "\\" + fileName);
    ServletOutputStream servletOutputStream = response.getOutputStream();
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = in.read(buffer)) > 0)
      servletOutputStream.write(buffer, 0, len); 
    in.close();
    servletOutputStream.close();
  }
  
  public String findFileSavePathByFileName(String filename, String saveRootPath) {
    int hashcode = filename.hashCode();
    int dir1 = hashcode & 0xF;
    int dir2 = (hashcode & 0xF0) >> 4;
    String dir = String.valueOf(saveRootPath) + "\\" + dir1 + "\\" + dir2;
    File file = new File(dir);
    if (!file.exists())
      file.mkdirs(); 
    return dir;
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
