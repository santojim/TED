import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ted.MD5Digest;
import ted.SQLConnector;

@WebServlet(urlPatterns = {"/register"})
public class register extends HttpServlet {


    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("pass1");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address"); 
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String afm = request.getParameter("afm");
        
        String seller = request.getParameter("seller");
        String buyer = request.getParameter("buyer");
        String role="v";
        /*
        Admin   	a
        Seller   	s
        Buyer   	b
        */
        if ((buyer != null) && (seller != null))
            role="sb";
        else if (buyer != null)
            role="b";
        else if (seller != null)
            role="s";
        
        
        
        int errorCode;
        
        MD5Digest hashedPassword = new MD5Digest(); 
        password = hashedPassword.StringToMD5(password);
        if(password==null){
            response.sendRedirect("error.jsp");
        }
        SQLConnector sqlDB = new SQLConnector();
        errorCode = sqlDB.insertNewUser(username, password, email, phone, fname, lname, address, longitude, latitude, afm, role);
        
        if(errorCode==1){
            response.sendRedirect("views/not_logged_in.jsp?message=Your account has been created! Waiting for confirmation..");
        }else if(errorCode == 15){
            response.sendRedirect("register.jsp?error=Sorry but this username is already taken!");
        }else{
            response.sendRedirect("views/not_logged_in.jsp?error=I am sorry but an unexpected error occured!");
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
