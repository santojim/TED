package ted;

import ted.SQLConnector;
import ted.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet( urlPatterns = {"/Login"} )
public class Login extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String url_of_redirection="";
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SQLConnector sqlDB = new SQLConnector();
        if (username!=null && password!=null){
            //conversion to MD5 for secure storage of password in database
            MD5Digest temp = new MD5Digest();
            password = temp.StringToMD5(password);
            if(password==null){
                response.sendRedirect("error.jsp");
            }

            User user = sqlDB.validate_user(username, password);

            if(user==null){ //user not found , return level=-1
                response.sendRedirect("redirect.jsp");
            }else{            
                if(user.getConfirmed()==1){
                    session.setAttribute("logged_in", 1);
                    session.setAttribute("username",user.getUsername());
                    session.setAttribute("fname",user.getFname());
                    session.setAttribute("lname",user.getLname());
                    session.setAttribute("address", user.getAddress());
                    session.setAttribute("afm", user.getAfm());
                    session.setAttribute("email",user.getEmail());
                    session.setAttribute("phone",user.getPhone());
                    session.setAttribute("latitude",user.getLatitude());
                    session.setAttribute("longitude",user.getLongitude());
                    session.setAttribute("s_rating",user.getSrating());
                    session.setAttribute("b_rating",user.getBrating());
                    session.setAttribute("role",user.getRole());
                    session.setAttribute("id", user.getId());

                    String users_role=user.getRole();

                    /*
                    Admin   	a
                    Seller   	s
                    Buyer   	b
                    */
                    session.setAttribute("admin", "0");
                    session.setAttribute("seller", "0");
                    session.setAttribute("buyer", "0");
                    session.setAttribute("visitor","0");

                    if(users_role.equals("b")){
                        session.setAttribute("buyer", "1");
                    }else if(users_role.equals("s")){
                        session.setAttribute("seller", "1");
                    }else if(users_role.equals("bs")){
                        session.setAttribute("buyer", "1");
                        session.setAttribute("seller", "1");
                    }else if(users_role.equals("sb")){
                        session.setAttribute("buyer", "1");
                        session.setAttribute("seller", "1");
                    }else if(users_role.equals("a")){
                        session.setAttribute("admin", "1");
                        session.setAttribute("seller", "1");
                        session.setAttribute("buyer", "1");
                    }               

                    response.sendRedirect("welcome.jsp");
                }else{
                    response.sendRedirect("views/not_logged_in.jsp?error=Your account has not been confirmed yet!");
                }
            } 
        }else{
            session.setAttribute("admin", "0");
            session.setAttribute("seller", "0");
            session.setAttribute("buyer", "0");
            session.setAttribute("visitor","1");
            response.sendRedirect("search/guest.jsp");
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
