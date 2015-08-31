import ted.SQLConnector;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ted.MD5Digest;
import ted.User;

public class Update extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String username = session.getAttribute("username").toString();
        String password = request.getParameter("u_old_pswd");
        String new_password = request.getParameter("u_new_pswd");
        String new_password_rpt = request.getParameter("u_new_pswd_rpt");
        String phone = request.getParameter("new_phone");
        String email = request.getParameter("u_new_email");
        String fname = request.getParameter("new_fname");
        String lname = request.getParameter("new_lname");
        String address = request.getParameter("new_address");
        String afm = request.getParameter("new_afm");
        String latitude = request.getParameter("new_latitude");
        String longitude = request.getParameter("new_longitude");
        SQLConnector sqlDB = new SQLConnector();
        
        
        MD5Digest temp = new MD5Digest();
        password = temp.StringToMD5(password);
        
        if(password==null){
            response.sendRedirect("error.jsp");
        }
        
        User user = sqlDB.validate_user(username, password);
        if(user==null){
            response.sendRedirect("/TED/settings.jsp?error=Wrong password!");
        }else{
            if(!new_password.isEmpty() && !new_password_rpt.isEmpty()){
                if(!new_password.equals(new_password_rpt)){
                    response.sendRedirect("/TED/settings.jsp?error=The 'New password' and 'New password repeat' fields don't match!");
                }
            }            
            if(sqlDB.UpdateInfo(username, new_password, email, phone, fname, lname , address , afm , latitude , longitude )==-1){
                    response.sendRedirect("/TED/settings.jsp?error=Something went wrong!");
            }else{
                        //fortwnoume sto session ta kainouria stoixeia
                        session.setAttribute("fname", fname);
                        session.setAttribute("lname", lname);
                        session.setAttribute("email", email);
                        session.setAttribute("phone", phone);
                        session.setAttribute("address", address);
                        session.setAttribute("afm", afm);
                        session.setAttribute("latitude", latitude);
                        session.setAttribute("longitude", longitude);
                        response.sendRedirect("/TED/settings.jsp?message=Your information have been successfully updated!"); 
            }
            
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
