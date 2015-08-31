import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ted.SQLConnector;

@WebServlet(urlPatterns = {"/updateItem"})
public class updateItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String buy_price = request.getParameter("buy_price");
        String first_bid = request.getParameter("first_bid");
        String country = request.getParameter("country");
        String started = request.getParameter("started");
        String ended = request.getParameter("ended");
        String description = request.getParameter("description");
        String categories = request.getParameter("categories");
        String id = request.getParameter("id");

       
        SQLConnector sqlDB = new SQLConnector();
        int errorCode;
        errorCode = sqlDB.UpdateItem(id,name,buy_price,first_bid,country,started,ended,description,categories);
        if(errorCode==-1){
            response.sendRedirect("/TED/sell/index.jsp?error=An error occured.");
        }else{
            response.sendRedirect("/TED/sell/viewItem.jsp?id="+id+"&message=Your information has been successfully updated!!");
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
