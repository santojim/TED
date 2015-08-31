
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import ted.SQLConnector;

@WebServlet(urlPatterns = {"/photoUpload"})
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class photoUpload extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         PrintWriter out = response.getWriter();
         //System.out.print(request.getParts());
         int ItemId = Integer.parseInt(request.getParameter("ItemID"));
         String appPath = request.getServletContext().getRealPath("");     
         String savePath = appPath +"img"+ File.separator ;     
         SQLConnector sqlDB = new SQLConnector();
          File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        int i=0;
        for (Part part : request.getParts()) {
            if(i!=0){
                String fileName = extractFileName(part);
                java.util.Date date= new java.util.Date();
                String timestamp = new Timestamp(date.getTime())+"";
                String PathToSave = savePath + timestamp +fileName; 
                part.write(PathToSave);
                moveFile(PathToSave,"/home/jim/NetBeansProjects/TED/web/img/"+timestamp+fileName);
                sqlDB.insertPhoto("/TED/img/"+timestamp+fileName, ItemId);
            }
            i++;
        }
             
    }
    
    private void moveFile(String source, String destination){
        
               InputStream in = null;
	       OutputStream out = null;
 
	        try {
	 
	            File oldFile = new File(source);
	            File newFile = new File(destination);
 
	            in = new FileInputStream(oldFile);
	            out = new FileOutputStream(newFile);
	 
	            byte[] moveBuff = new byte[1024];
	 
	            int butesRead;
	 
	            while ((butesRead = in.read(moveBuff)) > 0) {
	                out.write(moveBuff, 0, butesRead);
	            }
	 
	            in.close();
	            out.close();
	 
	            oldFile.delete();
	 
	            //System.out.println("The File was successfully moved to the new folder");
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

















