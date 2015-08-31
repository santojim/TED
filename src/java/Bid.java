
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ted.SQLConnector;

@WebServlet(urlPatterns = {"/Bid"})
public class Bid extends HttpServlet {


    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int buy_price=0,amount=0,error=0;
        SQLConnector sqlDB = new SQLConnector();
        
        int item_id = Integer.parseInt(request.getParameter("item_id"));
        if(request.getParameter("buy_price")!=null)
             buy_price = Integer.parseInt(request.getParameter("buy_price"));
        if(request.getParameter("bid")!=null)
            amount = Integer.parseInt(request.getParameter("bid"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        

        if(buy_price!=0){
            error=sqlDB.InsertBuy(item_id,user_id,buy_price);
            if(error==1)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&message=You bought the item !");            
            else if (error==2)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Something went wrong 2!");
            else if (error==3)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Something went wrong 3!");            
        }
        else if (amount!=0){
            error=sqlDB.InsertBid(item_id,user_id,amount);
            if(error==10)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Invalid Bid (your previous bid was higher) ");
            else if(error==15)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Invalid Bid (your bid is lower than allowed) ");
            else if(error==20)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Your have been outbidded!");
            else if(error==1)
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&message=Your are the highest bidder!");
            else
                response.sendRedirect("search/viewItem.jsp?id="+item_id+"&error=Something went wrong!");
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