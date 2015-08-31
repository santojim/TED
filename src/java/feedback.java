
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ted.SQLConnector;


@WebServlet(urlPatterns = {"/feedback"})
public class feedback extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
        int error=0;
        int feedback=0;
        int user_id=999,u_id=999;
        
        int item_id = Integer.parseInt(request.getParameter("item_id"));
        
        if(request.getParameter("user_id")!=null)
            user_id = Integer.parseInt(request.getParameter("user_id"));
        
        if(request.getParameter("u_id")!=null)
            u_id = Integer.parseInt(request.getParameter("u_id"));
        
        String s_feedback = request.getParameter("feedback");

        if(s_feedback.equals("pos"))
            feedback=1;
        else if(s_feedback.equals("neg"))
            feedback=-1;
        SQLConnector sqlDB = new SQLConnector();            
        if (u_id==0){
            error=sqlDB.InsertFeedbackToBuyer(item_id,feedback);
            if(error==1)
                response.sendRedirect("sell/index.jsp?&message=Thank you for leaving feedback!");
            else
                response.sendRedirect("search/index.jsp?&error=Something went wrong");
        }
        else{
            error=sqlDB.InsertFeedbackToSeller(item_id,user_id,feedback);
            if(error==1){
                response.sendRedirect("search/index.jsp?&message=Thank you for leaving feedback!");
            }else
                response.sendRedirect("search/index.jsp?&error=Something went wrong");
        }
    }  
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
