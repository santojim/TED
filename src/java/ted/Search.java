package ted;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ted.SQLConnector;

@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class Search extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int price_from , price_to;
        String tempParameter;
        tempParameter = new String();
        String freetext_cat = request.getParameter("freetext_cat");
        String freetext_desc =request.getParameter("freetext_desc");
        
        String category = request.getParameter("select_cat");
        
        tempParameter = request.getParameter("price_from");
        if (tempParameter.equalsIgnoreCase("")) price_from = 0;
        else price_from = Integer.parseInt(request.getParameter("price_from")); 
        
        tempParameter = request.getParameter("price_to");
        if (tempParameter.equalsIgnoreCase("")) price_to = 0;
        else price_to = Integer.parseInt(request.getParameter("price_to"));     


            SQLConnector sql = new SQLConnector();
            Connection conn;
            ResultSet res=null;
            ResultSet res4counting=null;
            conn = sql.connect();
            PreparedStatement st;
            StringBuilder responseMessage= new StringBuilder();

            
            try{ 
                
                int id_cat=0,i;
                ArrayList<Integer> templist = new ArrayList<Integer>();                
                ArrayList<Integer> id_cats = new ArrayList<Integer>();
                ArrayList<Integer> free_cats = new ArrayList<Integer>();
                ArrayList<Integer> item_id_price_from = new ArrayList<Integer>();
                ArrayList<Integer> item_id_price_to = new ArrayList<Integer>();
                ArrayList<Integer> item_id_description = new ArrayList<Integer>();
                
                if( !category.isEmpty() ){
                    st = conn.prepareStatement("SELECT id_categories FROM Categories WHERE category = ?");
                    st.setString(1,category);
                    res=st.executeQuery();
                    if(res.next())
                        id_cat=res.getInt("id_categories");
                    st = conn.prepareStatement("SELECT Auction_item_id FROM Auction_has_Categories WHERE Categories_id_categories = ?");
                    st.setInt(1, id_cat);
                    res=st.executeQuery();
                    while(res.next()){
                        id_cats.add(res.getInt("Auction_item_id"));
                    }
                }
                if(!freetext_cat.isEmpty()){
                    //split input to single words
                    String[] arr = freetext_cat.split(" ");    
                    for ( String ss : arr) {
                        //get id of categories according to each word given in input
                        st = conn.prepareStatement("SELECT id_categories FROM Categories WHERE INSTR (category,?) > 0");
                        st.setString(1, ss);
                        res=st.executeQuery();
                        while(res.next()){
                            templist.add(res.getInt("id_categories"));
                        }
                    }
                    //for every category found above search if any item belongs to it
                    for(i=0;i<templist.size();i++){
                        st = conn.prepareStatement("SELECT Auction_item_id FROM Auction_has_Categories WHERE Categories_id_categories = ?");
                        st.setInt(1, templist.get(i));
                        res=st.executeQuery();
                        //if found add item id to freecats list
                        while(res.next()){
                            // for items with multiple categories
                            if(!free_cats.contains(res.getInt("Auction_item_id")) )
                               free_cats.add(res.getInt("Auction_item_id"));
                        }
                    }
                 
                }
                if(price_from!=0 && price_to!=0){
                    //price FROM & TO
                    st = conn.prepareStatement("SELECT item_id FROM Auction WHERE currently >= ? AND currently <= ?");
                    st.setInt(1,price_from);
                    st.setInt(2,price_to);
                    res=st.executeQuery();
                    while(res.next()){
                        if(!item_id_price_from.contains(res.getInt("item_id")) )
                            item_id_price_from.add(res.getInt("item_id"));
                    }                    
                }
                else if(price_from!=0){
                    //price from
                    st = conn.prepareStatement("SELECT item_id FROM Auction WHERE currently >= ? ");
                    st.setInt(1,price_from);
                    res=st.executeQuery();
                    while(res.next()){
                        if(!item_id_price_from.contains(res.getInt("item_id")) )
                            item_id_price_from.add(res.getInt("item_id"));
                    }
                }
                else if(price_to!=0){
                    //price to
                    st = conn.prepareStatement("SELECT item_id FROM Auction WHERE currently <= ? ");
                    st.setInt(1,price_to);
                    res=st.executeQuery();
                    while(res.next()){
                        if(!item_id_price_from.contains(res.getInt("item_id")) )
                            item_id_price_from.add(res.getInt("item_id"));
                    }
                  
                }
                if (!freetext_desc.isEmpty() ){
                //SEARCH BY DESCRIPTION
                    String[] arr = freetext_desc.split(" ");    
                    for ( String ss : arr) {
                        st = conn.prepareStatement("SELECT Auction_item_id FROM Item_Location_And_Description WHERE INSTR (description,?) > 0 ");
                        st.setString(1, ss);
                        res=st.executeQuery();
                        while(res.next()){
                        if(!item_id_description.contains(res.getInt("Auction_item_id")) )
                            item_id_description.add(res.getInt("Auction_item_id"));
                        }                        
                    }                  
                }

                
                responseMessage.append("/TED/search/results.jsp?");
                //final_idlist will be empty if input doesn't match database
                ArrayList<Integer> final_idlist = new ArrayList<Integer>();
                if(!category.isEmpty())
                    final_idlist=id_cats;
                else if(!freetext_cat.isEmpty())
                    final_idlist=free_cats;
                else if(!freetext_desc.isEmpty())
                    final_idlist=item_id_description;                
                else if(item_id_price_from.size()>0)
                    final_idlist=item_id_price_from;
                else if(item_id_price_to.size()>0)
                    final_idlist=item_id_price_to;                
                
                //using retainall we keep the duplicates between lists
                //if item exists in every given input we send it as result
                if(id_cats.size()>0)
                    final_idlist.retainAll(id_cats);
                if(free_cats.size()>0)
                    final_idlist.retainAll(free_cats);
                if(item_id_price_from.size()>0)
                    final_idlist.retainAll(item_id_price_from);
                if(item_id_price_to.size()>0)
                    final_idlist.retainAll(item_id_price_to);
                if(item_id_description.size()>0)
                    final_idlist.retainAll(item_id_description);
                //remove expired items
                st = conn.prepareStatement("SELECT item_id from Auction WHERE time_end < NOW() ");
                res=st.executeQuery();
                    while(res.next()){
                        if(final_idlist.contains(res.getInt("item_id")) )
                            final_idlist.remove(final_idlist.indexOf(res.getInt("item_id")) ) ;
                    }
                //also remove no-started auctions
                st = conn.prepareStatement("SELECT item_id from Auction WHERE time_start > NOW() ");
                res=st.executeQuery();
                    while(res.next()){
                        if(final_idlist.contains(res.getInt("item_id")) )
                            final_idlist.remove(final_idlist.indexOf(res.getInt("item_id")) ) ;
                    }                  
                for(i=0;i<final_idlist.size();i++){
                        responseMessage.append("id="+final_idlist.get(i)+"&");
                    }
                String finalresponse=new String(responseMessage);
                response.sendRedirect(finalresponse);
            } catch (SQLException ex) {
                Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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