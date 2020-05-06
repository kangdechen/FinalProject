import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@WebServlet({"/UploadHandleServlet"})
public class UploadHandleServlet extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String savePath = getServletContext().getRealPath("/WEB-INF/upload");
    String tempPath = getServletContext().getRealPath("/WEB-INF/temp");
    
    File tmpFile = new File(tempPath);
    if (!tmpFile.exists())
      tmpFile.mkdir(); 
    String message = "";
    try {
      DiskFileItemFactory factory = new DiskFileItemFactory();
      factory.setSizeThreshold(102400);
      factory.setRepository(tmpFile);
      ServletFileUpload upload = new ServletFileUpload(factory);
      
      upload.setProgressListener(new ProgressListener(){
          public void update(long pBytesRead, long pContentLength, int arg2) {
              System.out.println("file size = ：" + pContentLength + ",processed：" + pBytesRead);
            
              double percent= (double)pBytesRead*100/(double)pContentLength;  
              System.out.println(percent);  
              
          }
      });
      upload.setHeaderEncoding("UTF-8");
      
      
      if (!ServletFileUpload.isMultipartContent(request))
        return; 
      
      upload.setFileSizeMax(1073741824L);
      upload.setSizeMax(-2147483648L);
      
      List<FileItem> list = upload.parseRequest(request);
      for (FileItem item : list) {
        if (item.isFormField()) {
          String name = item.getFieldName();
          String value = item.getString("UTF-8");
          System.out.println(String.valueOf(name) + "=" + value + "t =");
          continue;
        } 
        String filename = item.getName();
        System.out.println(filename);
        if (filename == null || filename.trim().equals(""))
          continue; 
        
        filename = filename.substring(filename.lastIndexOf("\\") + 1);
        String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
        System.out.println(""+ fileExtName);
        InputStream in = item.getInputStream();
        String realSavePath = makePath(filename, savePath);
        
        System.out.println("upload to "+realSavePath);
        FileOutputStream out = new FileOutputStream(realSavePath + "\\" + filename);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0)
          out.write(buffer, 0, len); 
        in.close();
        out.close();
        message = "File succes uploa";
      } 
    } catch (org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException e) {
      e.printStackTrace();
      request.setAttribute("message", message);
      request.getRequestDispatcher("/message.jsp").forward((ServletRequest)request, (ServletResponse)response);
      return;
    } catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
      e.printStackTrace();
//      request.setAttribute("message", message);
//      request.getRequestDispatcher("/message.jsp").forward((ServletRequest)request, (ServletResponse)response);
      return;
    } catch (Exception e) {
      message = "File upload fail";
      e.printStackTrace();
    } 
    request.setAttribute("message", message);
    request.getRequestDispatcher("/message.jsp").forward((ServletRequest)request, (ServletResponse)response);
  }
  
  private String makeFileName(String filename) {
    return String.valueOf(UUID.randomUUID().toString()) + "_" + filename;
  }
  
  private String makePath(String filename, String savePath) {
    int hashcode = filename.hashCode();
    int dir1 = hashcode & 0xF;
    int dir2 = (hashcode & 0xF0) >> 4;
    String dir = String.valueOf(savePath) + "\\" + dir1 + "\\" + dir2;
    System.out.println("savepath =" + dir);
    File file = new File(dir);
    if (!file.exists())
      file.mkdirs(); 
    return dir;
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
