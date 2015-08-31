import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ted.SQLConnector;
import java.sql.Timestamp;
import javax.servlet.http.HttpSession;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


@WebServlet(urlPatterns = {"/newItem"})
public class newItem extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        String id = session.getAttribute("id").toString(); // to id to dhmiourgou ths dhmoprasias
        String name = request.getParameter("Name");
        String categories = request.getParameter("Categories");
        Integer buy_now_price = Integer.parseInt(request.getParameter("BuyNowPrice"));
        Integer starting_bid = Integer.parseInt(request.getParameter("StartingBid"));
        String starts = request.getParameter("Starts");
        String ends = request.getParameter("Ends");
        String lon = request.getParameter("c_lon").toString();
        String lat = request.getParameter("c_lat").toString();
        String address = request.getParameter("c_addr");
        
        Timestamp Starts = Timestamp.valueOf(starts.replace("T"," "));
        Timestamp Ends = Timestamp.valueOf(ends.replace("T"," ")); 
        starts= Starts.toString();
        ends= Ends.toString();

        //Uncomment this and only the County gets stored to database
        //Pattern p = Pattern.compile(".*,\\s*(.*)");
        //Matcher m = p.matcher(address);

        //if (m.find())
        //    address=m.group(1);


      int errorCode = 0;
        
        SQLConnector sqlDB = new SQLConnector();
        errorCode = sqlDB.insertNewAuction(id,name,categories,buy_now_price,starting_bid,starts,ends,lon,lat,address);
        if(errorCode==1){
            response.sendError(200);
        }else{
            response.sendError(400);
        }
        
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
