package xml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ted.Login;

public class SQLConnector_xml { 
    String url = "jdbc:mysql://localhost:3306/"; 
    String dbName = "ted"+"?useUnicode=yes&characterEncoding=UTF-8"; 
    String driver = "com.mysql.jdbc.Driver"; 
    String userName = "root"; 
    String password = "toor";
    public Connection connect(){
         Connection conn;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url+dbName,userName,password);
            
        } catch (Exception e) {
		e.printStackTrace();
                conn=null;
        }
        
        return conn;
    }
    public int InsertUsers(String uname,String s_rating,String b_rating ,String country){
        SQLConnector_xml sql = new SQLConnector_xml();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        int error=0,uid=0;
        try {
            st= conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
            st.setString(1, uname);
            res = st.executeQuery();
            if (res.next()){
                if(res.getInt("s_rating") == 0 ){
                    st=conn.prepareStatement("UPDATE Users SET s_rating = ? WHERE id = ?");
                    st.setString(1, s_rating);
                    st.setInt(2, res.getInt("id"));
                    if(st.executeUpdate()>0)
                        return error=1;
                }else if(res.getInt("b_rating") == 0 ){
                    st=conn.prepareStatement("UPDATE Users SET b_rating = ? WHERE id = ?");
                    st.setString(1, b_rating);
                    st.setInt(2, res.getInt("id"));
                    if(st.executeUpdate()>0)
                        return error=1;
                }else
                    return error=0;
            }else{
                if (s_rating == null)
                    s_rating = "0";
                if (b_rating == null)
                    b_rating = "0";
                st = conn.prepareStatement("INSERT INTO Users (username,password,role,confirmed,s_rating,b_rating) VALUES (?,'c4ca4238a0b923820dcc509a6f75849b','sb',1,?,?) ");
                st.setString(1,uname);
                st.setString(2, s_rating);
                st.setString(3,b_rating);
                if(st.executeUpdate()>0){
                    error=1;
                }   
                //GET user id that was inserted in database
                st=conn.prepareStatement("SELECT LAST_INSERT_ID()");
                res=st.executeQuery();
                if(res.next())
                    uid=res.getInt("LAST_INSERT_ID()");

                st = conn.prepareStatement("INSERT INTO User_Personal_Info (fname,lname,email,phone,address,afm,latitude,longitude,user_id) VALUES ('default','default','d@fault','6922233',?,'9990003366','10','20',?)");
                st.setString(1, country);
                st.setInt(2, uid);
                if(st.executeUpdate()>0){
                    error=1;
                }
            }
        }catch (SQLException ex) {
            Logger.getLogger(SQLConnector_xml.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return error;
    }
    
    
    public int insertAuction(String author_name, String auct_name , String currently,String buy_price ,String first_bid , String n_o_b,
            String started,String ended, String description , String lat , String lon , String country,
            ArrayList<String> cat) {
    
        SQLConnector_xml sql = new SQLConnector_xml();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=0;
        int id_cat=0;
        int author_id=0,item_id=0;
        try {
            st = conn.prepareStatement("SELECT id FROM Users WHERE username = ?");
            st.setString(1, author_name);
            res = st.executeQuery();
            if(res.next())
                author_id=res.getInt("id");
            if(buy_price == null)
                buy_price="0";
            st = conn.prepareStatement("INSERT INTO Auction (auction_author,auction_name,currently,buy_price,first_bid,number_of_bids,time_start,time_end) VALUES (?,?,?,?,?,?,?,?)");
            st.setInt(1, author_id);
            st.setString(2, auct_name);
            st.setString(3, currently);
            st.setString(4, buy_price);
            st.setString(5, first_bid);
            st.setString(6, n_o_b);
            st.setString(7, started);
            st.setString(8, ended);
            if(st.executeUpdate()>0)
                error=1;
            //GET item id that was inserted in database
            st=conn.prepareStatement("SELECT LAST_INSERT_ID()");
            res=st.executeQuery();
            if(res.next())
                item_id=res.getInt("LAST_INSERT_ID()");
            st = conn.prepareStatement("INSERT INTO Item_Location_And_Description (description,latitude,longitude,country,Auction_item_id,Auction_auction_author ) VALUES (?,?,?,?,?,?)");
            st.setString(1, description);
            st.setString(2, lat);
            st.setString(3, lon);
            st.setString(4, country);
            st.setInt(5, item_id);
            st.setInt(6, author_id);
            if(st.executeUpdate()>0)
                error=1;            
            //insert categories of item
            for(int i=0;i<cat.size();i++){
                st = conn.prepareStatement("SELECT id_categories FROM Categories WHERE category = ? ");
                st.setString(1, cat.get(i));
                res=st.executeQuery();
                if(res.next())
                    id_cat=res.getInt("id_categories");
                st = conn.prepareStatement("INSERT INTO Auction_has_Categories (Auction_item_id,Categories_id_categories) VALUES (?,?)");
                st.setInt(1, item_id);
                st.setInt(2, id_cat);
                if(st.executeUpdate()>0 )
                    error=1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector_xml.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return item_id;
    }    
    public int insertBids(String user_id , int item_id , String time , String amount){
        SQLConnector_xml sql = new SQLConnector_xml();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=0;
        int u_id=0;
        try {
            st = conn.prepareStatement("SELECT * FROM Users WHERE username = ?");
            st.setString(1,user_id);
            res = st.executeQuery();
            if (res.next())
                u_id = res.getInt("id");
            
            st = conn.prepareStatement("SELECT * FROM Bids WHERE id_user = ? AND item_id = ?");
            st.setInt(1, u_id);
            st.setInt(2,item_id);
            res = st.executeQuery();
            if(res.next()){
                st = conn.prepareStatement("UPDATE Bids SET time = ? , amount = ? WHERE id_user = ? AND item_id = ?");
                st.setString(1,time);
                st.setString(2, amount);
                st.setInt(3, u_id);
                st.setInt(4, item_id);
                if (st.executeUpdate()>0)
                    error=1;
            }else{
                st = conn.prepareStatement("INSERT INTO Bids (item_id,id_user,time,amount) VALUES (?,?,?,?)");
                st.setInt(1, item_id);
                st.setInt(2,u_id);
                st.setString(3, time);
                st.setString(4,amount);
                if(st.executeUpdate()>0)
                    error=1;
            }
        }catch (SQLException ex) {
            Logger.getLogger(SQLConnector_xml.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
    }
}
