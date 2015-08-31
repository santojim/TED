package ted;





import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public final class SQLConnector {
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

    public ArrayList<String> BringUsernames(){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<String> u_names = new ArrayList<String>();

        try {
            st = conn.prepareStatement("SELECT username FROM Users");
            res=st.executeQuery();
            while (res.next()){
                u_names.add(res.getString("username"));
            }

        }catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return u_names;
    }
    public int insertNewUser(String username, String password, String email,String phone, String fname, 
    String lname, String address,String longitude ,String latitude,String afm, String userType) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;
        int id=0;

        try {
            st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
            st.executeQuery();
            st=conn.prepareStatement("SET NAMES 'utf8'");
            st.executeQuery();
            
            st = conn.prepareStatement("SELECT id FROM Users WHERE username=?");
            st.setString(1,username);                   
            res = st.executeQuery();
            if(res.next()){
                error=15;
            }else{
                st = conn.prepareStatement("INSERT INTO Users (username,password,role,confirmed,s_rating,b_rating) VALUES (?,?,?,0,0,0)");
                st.setString(1,username);
                st.setString(2, password);
                st.setString(3,userType);
                
                if(st.executeUpdate()>0){
                    error=1;
                }                
                st = conn.prepareStatement("SELECT id FROM Users WHERE username=?");
                st.setString(1,username);

                res=st.executeQuery();
                if(res.next())
                    id=res.getInt("id");
                System.out.print(address);
                System.out.print(address);
                System.out.print(address);                
                st = conn.prepareStatement("INSERT INTO User_Personal_Info (fname,lname,email,phone,address,afm,latitude,longitude,user_id) VALUES (?,?,?,?,?,?,?,?,?)");
                st.setString(1, fname);
                st.setString(2, lname);
                st.setString(3, email);
                st.setString(4, phone);
                st.setString(5, address);
                st.setString(6, afm);
                st.setString(7, latitude);
                st.setString(8, longitude);
                st.setInt(9, id);

                if(st.executeUpdate()>0){
                    error = 1;
                }
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }
    
    
    
        
    public int insertNewAuction(String id,String name,String categories, int buy_now_price,int starting_bid,String starts,
            String ends,String lon,String lat,String address) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;

        int itemid=0,a_author=0;
        try {
            ArrayList<Integer> cat = new ArrayList<Integer>();           
            String catlist[];
            st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
            st.executeQuery();
            st=conn.prepareStatement("SET NAMES 'utf8'");
            st.executeQuery();
            //FOR TABLE Auction
            st = conn.prepareStatement("INSERT INTO Auction (number_of_bids,auction_author,auction_name,buy_price,first_bid,currently,time_start,time_end) VALUES (0,?,?,?,?,?,?,?) ");
            st.setString(1,id);
            st.setString(2, name);
            st.setInt(3, buy_now_price);
            st.setInt(4, starting_bid);
            st.setInt(5, starting_bid);//same value for currently
            st.setString(6, starts);
            st.setString(7, ends);
            if(st.executeUpdate()>0){
                error=1;
            }            
            //GET auction author and item id for next table
            st=conn.prepareStatement("SELECT LAST_INSERT_ID()");
            res=st.executeQuery();
            if(res.next())
                itemid=res.getInt("LAST_INSERT_ID()");
            
            st = conn.prepareStatement("SELECT item_id,auction_author FROM Auction WHERE auction_author=? AND item_id=?");
            st.setString(1,id);
            st.setInt(2, itemid);
            res=st.executeQuery();
            if(res.next()){
                itemid=res.getInt("item_id");
                a_author=res.getInt("auction_author");
            }
            // FOR TABLE Item_Location_And_Description
            st = conn.prepareStatement("INSERT INTO Item_Location_And_Description (description,latitude,longitude,country,Auction_item_id,Auction_auction_author) VALUES (' ',?,?,?,?,?) ");
            st.setString(1, lat);
            st.setString(2, lon);
            st.setString(3, address);
            st.setInt(4, itemid);
            st.setInt(5, a_author);
            if(st.executeUpdate()>0){
                error=1;
            }
            //GET ID OF CATEGORIES0            
            catlist = categories.split("\\|");
            for (int i=0;i<catlist.length;i++ ) {
//                System.out.println(catlist[i]);
                st = conn.prepareStatement("SELECT id_categories FROM Categories WHERE category = ? ");
                st.setString(1,catlist[i].trim());
                res=st.executeQuery();
                if(res.next()){
                    cat.add(res.getInt("id_categories"));
                }                
            }
            // INSERT CATEGORIES
            int i;
            for (i=0;i<cat.size();i++){

                st = conn.prepareStatement("INSERT INTO Auction_has_Categories (Auction_item_id,Categories_id_categories) VALUES (?,?)");
                st.setInt(1,itemid);
                st.setInt(2,cat.get(i) );
                if(st.executeUpdate()>0){
                    error=1;
                }
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }

    
    
    
    
    public int insertPhoto(String path, int ItemId) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;
        try {
            st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
            st.executeQuery();
            st=conn.prepareStatement("SET NAMES 'utf8'");
            st.executeQuery();       
                st = conn.prepareStatement("INSERT INTO Item_Photos (auction_item_id,path) VALUES (?,?)");
                st.setInt(1, ItemId);
                st.setString(2,path);
                st.executeUpdate();        
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
    }
    
    
    
    
    public User validate_user(String username,String password){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        User user=null;
        try {
            
            st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
            st.executeQuery();
            st=conn.prepareStatement("SET NAMES 'utf8'");
            st.executeQuery();
            st = conn.prepareStatement("SELECT * FROM Users,User_Personal_Info WHERE username=? AND password = ? AND id = user_id");
            st.setString(1,username);
            st.setString(2, password);
            
            res = st.executeQuery();
            if(res.next()){
                user = new User(res.getInt("id"),res.getString("username"),res.getString("email"),res.getString("role"),res.getString("phone"),res.getString("fname"),res.getString("lname"),res.getString("address"),res.getString("afm"),res.getString("latitude"),res.getString("longitude"),res.getInt("s_rating"),res.getInt("b_rating"),res.getInt("confirmed"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return user;
    }
    
    
    
    
    
    public int UpdateInfo(String username, String password, String email,String phone, String fname, 
            String lname , String address , String afm , String latitude , String longitude) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;
        ResultSet res;
        int id=0;

        try {
            if(!password.isEmpty()){
                
                st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
                st.executeQuery();
                st=conn.prepareStatement("SET NAMES 'utf8'");
                st.executeQuery();
                
                MD5Digest temp = new MD5Digest();
                password = temp.StringToMD5(password);

                st = conn.prepareStatement("UPDATE Users SET password=? WHERE username=?");
                st.setString(1,password);
                st.setString(2, username);
            
                if(st.executeUpdate()>0){
                    error = 1;
                }
            }else{
                st = conn.prepareStatement("SELECT id FROM Users WHERE username=?");
                st.setString(1,username);

                res=st.executeQuery();
                if(res.next())
                    id=res.getInt("id");
                
                st = conn.prepareStatement("UPDATE User_Personal_Info SET fname=?,lname=?,email=?,phone=?,afm=?,address=?,latitude=?,longitude=? WHERE user_id=?");
                st.setString(1, fname);
                st.setString(2, lname);
                st.setString(3, email);
                st.setString(4, phone);
                st.setString(5,afm);
                st.setString(6,address);
                st.setString(7, latitude);
                st.setString(8, longitude);
                st.setInt(9, id);
            
                if(st.executeUpdate()>0){
                    error = 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
               
        return error;
    }
    
    
    
    
    public int UpdateItem(String id, String name, String buy_price,String first_bid,
                    String country, String started, String ended, String description, String categories) {

        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        ResultSet res;       
        Integer error=-1;
        String catlist[];
        ArrayList<Integer> cat = new ArrayList<Integer>();
        try {
                st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
                st.executeQuery();
                st=conn.prepareStatement("SET NAMES 'utf8'");
                st.executeQuery();

                st = conn.prepareStatement("UPDATE Auction SET auction_name=?,buy_price=?,first_bid=?,time_start=?,time_end=? WHERE item_id=?");
                st.setString(1,name);
                st.setString(2, buy_price);
                st.setString(3,first_bid);
                st.setString(4, started);                
                st.setString(5,ended);
                st.setString(6, id);
                
                if(st.executeUpdate()>0){
                    error = 1;
                }
                st = conn.prepareStatement("UPDATE Item_Location_And_Description SET description=?,country=? WHERE Auction_item_id = ?");
                st.setString(1, description);
                st.setString(2,country);
                st.setString(3,id);
                if(st.executeUpdate()>0){
                    error = 1;
                }
                
            //GET ID OF CATEGORIES0            
            catlist = categories.split("\\|");
            for (int i=0;i<catlist.length;i++ ) {
                //System.out.println(catlist[i]);
                st = conn.prepareStatement("SELECT id_categories FROM Categories WHERE category = ? ");
                st.setString(1,catlist[i].trim());
                res=st.executeQuery();
                if(res.next()){
                    cat.add(res.getInt("id_categories"));
                }                
            }
            //DELETE OLD ENTRIES FOR ITEM ID
            st = conn.prepareStatement("DELETE FROM Auction_has_Categories WHERE Auction_item_id = ?");
            st.setString(1,id);
            if(st.executeUpdate()>0){
                error = 1;
            }
            // INSERT CATEGORIES
            int i;
            for (i=0;i<cat.size();i++){

                st = conn.prepareStatement("INSERT INTO Auction_has_Categories (Auction_item_id,Categories_id_categories) VALUES (?,?)");
                st.setString(1,id);
                st.setInt(2,cat.get(i));
                if(st.executeUpdate()>0){
                    error=1;
                }
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
               
        return error;
    }
    
    
    
    
    public ArrayList<User> bringAllUsers(){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<User> users= new ArrayList<User>();
        try {
            st = conn.prepareStatement("SELECT confirmed,id,username,role,phone,email,fname,lname,address,afm,latitude,longitude,s_rating,b_rating FROM Users ,User_Personal_Info WHERE id=user_id;");
            res = st.executeQuery();
            while(res.next()){
                users.add(new User(res.getInt("id"),res.getString("username"),res.getString("email"),res.getString("role"),res.getString("phone"),res.getString("fname"),res.getString("lname"),res.getString("address"),res.getString("afm"),res.getString("latitude"),res.getString("longitude"),res.getInt("s_rating"),res.getInt("b_rating"),res.getInt("confirmed")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return users;
    }
    
    
    
    
    public ArrayList<Photo> bringPhotosById(String id){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Photo> Photos= new ArrayList<Photo>();
        try {
            st = conn.prepareStatement("SELECT path,auction_item_id,photo_id FROM Item_Photos WHERE auction_item_id=? ");
            st.setString(1, id);
            res = st.executeQuery();
            while(res.next()){
                Photos.add(new Photo(res.getString("path"), res.getInt("photo_id"),res.getInt("auction_item_id")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Photos;
    }
    
    
    
    
    
    public int DeleteItem(int id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        ResultSet res = null;
        Item ItemToDelete = sql.bringItemById(id);
        Integer error=-1;  
        try {
            st = conn.prepareStatement("DELETE FROM Auction_has_Categories WHERE Auction_item_id = ?");
            st.setString(1, ItemToDelete.getItemId());
            st.executeUpdate();
            
            st = conn.prepareStatement("DELETE FROM Item_Location_And_Description WHERE Auction_item_id = ?");
            st.setString(1, ItemToDelete.getItemId());
            st.executeUpdate();
            
            st = conn.prepareStatement("SELECT photo_id FROM Item_Photos WHERE auction_item_id=?");
            st.setString(1,ItemToDelete.getItemId());
            res = st.executeQuery();
            while(res.next()){
                DeletePhoto(res.getString("photo_id"));
            }            
            st = conn.prepareStatement("DELETE FROM Auction WHERE item_id = ?");
            st.setString(1, ItemToDelete.getItemId());
            if (st.executeUpdate()>0 )
                error=1;
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }
    

    public int DeletePhoto(String id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        ResultSet res = null;
        Integer error=-1;  
        
        try {
            st=conn.prepareStatement("SELECT path FROM Item_Photos WHERE photo_id=?");
            st.setString(1, id);
            res = st.executeQuery();
            res.next();
            String[] parts;
            parts = res.getString("path").split("/img/");
            
            File oldFile = new File("/home/jim/NetBeansProjects/TED/web/img/"+parts[1]);
            oldFile.delete();
           
            st = conn.prepareStatement("DELETE FROM Item_Photos  WHERE photo_id=?");
            st.setString(1,id);
        
            if(st.executeUpdate()>0){
                 error = 1;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }
  
    public int DeleteUser(String username, int id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;  
        try {
            st = conn.prepareStatement("DELETE FROM User_Personal_Info WHERE user_id=?");
            st.setInt(1,id);        
            if(st.executeUpdate()>0){
                 error = 1;
            }
            st = conn.prepareStatement("DELETE FROM Users WHERE id= ? AND username=?");
            st.setInt(1,id);
            st.setString(2,username);
            if(st.executeUpdate()>0){
                 error = 1;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }       
        return error;
    }

    public int ConfirmUser(String username, int id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        Integer error=-1;  
        try {
            
            st = conn.prepareStatement("UPDATE Users SET confirmed=1 WHERE username=? AND id=?");
            st.setString(1,username);
            st.setInt(2,id);
        
            if(st.executeUpdate()>0){
                 error = 1;
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }
    
    
    
    
    
        public ArrayList<Item> bringAllItems(int userID){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Item> Items= new ArrayList<Item>();
        try {
            st = conn.prepareStatement("SELECT item_id,currently,s_rating,username,buy_price,first_bid,number_of_bids,auction_name,time_start,time_end,latitude,longitude,country FROM Auction , Item_Location_And_Description , Users WHERE item_id = Auction_item_id AND auction_author = Auction_auction_author AND auction_author = id AND auction_author =? ");
            st.setInt(1, userID);
            res = st.executeQuery();
            while(res.next()){
                Items.add(new Item(res.getString("item_id"),res.getString("number_of_bids") ,res.getString("s_rating"),res.getString("username"),res.getString("auction_name"),res.getString("currently"),res.getString("buy_price"),res.getString("first_bid"),res.getString("country"),res.getString("time_start"),res.getString("time_end"),res.getString("latitude"),res.getString("longitude") ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Items;
    }
        
        
        public ArrayList<Item> bringItemsOfAllUsers(){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Item> Items= new ArrayList<Item>();
        try {
            st = conn.prepareStatement("SELECT item_id,currently,s_rating,username,buy_price,first_bid,number_of_bids,auction_name,time_start,time_end,latitude,longitude,country FROM Auction , Item_Location_And_Description , Users WHERE item_id = Auction_item_id AND auction_author = Auction_auction_author AND auction_author = id");
            res = st.executeQuery();
            while(res.next()){
                Items.add(new Item(res.getString("item_id"),res.getString("number_of_bids") ,res.getString("s_rating"),res.getString("username"),res.getString("auction_name"),res.getString("currently"),res.getString("buy_price"),res.getString("first_bid"),res.getString("country"),res.getString("time_start"),res.getString("time_end"),res.getString("latitude"),res.getString("longitude") ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Items;
    }        


        
        
        
        
        
        
    public Item bringItemById(int id){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null,res2=null;
        conn = sql.connect();
        Item tempItem =null;
        ArrayList<String> cat = new ArrayList<String>();
        PreparedStatement st;
        try {

            st = conn.prepareStatement("SELECT * FROM Auction , Item_Location_And_Description WHERE item_id = Auction_item_id AND item_id = ?");
            st.setInt(1, id);
            res = st.executeQuery();
            res.next();
            
            if(res.getString("item_id")!=null){
                tempItem = new Item();
                tempItem.setItemId(res.getString("item_id"));
                tempItem.setNumberOfBids(res.getString("number_of_bids"));
                tempItem.setName(res.getString("auction_name"));
                tempItem.setCurrently(res.getString("currently"));
                tempItem.setBuyPrice(res.getString("buy_price"));
                tempItem.setFirstBid(res.getString("first_bid"));
                tempItem.setCountry(res.getString("Country"));
                tempItem.setStarted(res.getString("time_start"));
                tempItem.setEnded(res.getString("time_end"));
                tempItem.setLatitude(res.getString("latitude"));
                tempItem.setLongitude(res.getString("longitude"));
                tempItem.setDescription(res.getString("description"));
                st = conn.prepareCall("SELECT username FROM Users WHERE id = ? ");
                st.setInt(1,res.getInt("auction_author"));
                res2=st.executeQuery();
                if (res2.next()){
                    tempItem.setSellerName(res2.getString("username"));
                }                
                
                st = conn.prepareStatement("SELECT category FROM Categories WHERE id_categories IN (SELECT Categories_id_categories FROM Auction_has_Categories WHERE Auction_item_id = ? )");
                st.setInt(1, id);
                res = st.executeQuery();
                while(res.next()){
                    cat.add(res.getString("category"));
                }
                tempItem.setCategory(cat);
                
                
            }
            else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return tempItem;
    }

        public User getUserById(int userId){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        User user=null;
        
        try {
            st = conn.prepareStatement("select * from user where id=?");
            st.setInt(1, userId);
            
            res = st.executeQuery();
            if (res.next()) {
                user = new User(res.getInt("id"),res.getString("username"),res.getString("email"),res.getString("role"),res.getString("phone"),res.getString("fname"),res.getString("lname"),res.getString("address"),res.getString("afm"),res.getString("latitude"),res.getString("longitude"),res.getInt("s_rating"),res.getInt("b_rating"),res.getInt("confirmed"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
        return user;
    }
        
        public int InsertBuy(int item_id,int user_id,int buy_price){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;  
        int error=0;
        try {
            //check if user has made a bid so needs to update
            st = conn.prepareStatement("SELECT * FROM Bids WHERE item_id = ? AND id_user= ? ");
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            res=st.executeQuery();
            if(res.next()){
                st = conn.prepareStatement("UPDATE Bids SET amount= ? , time = NOW() WHERE item_id = ? AND id_user = ?"); 
                st.setInt(1, buy_price);
                st.setInt(2, item_id);
                st.setInt(3, user_id);                
                if(st.executeUpdate()>0){
                error=10000;
                }                
            }else{            
            st = conn.prepareStatement("INSERT INTO Bids (item_id,id_user,time,amount) VALUES (?,?,NOW(),?)"); 
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            st.setInt(3, buy_price);
            if(st.executeUpdate()>0){
                error=1;
            }
            }
            //end auction once the item is bought by changing time_end to current time
            st = conn.prepareStatement("UPDATE Auction SET time_end = NOW() WHERE item_id=? "); 
            st.setInt(1, item_id);
            if(st.executeUpdate()>0){
                error=1;
            }
            //update Auction table , set currently = buy price
            st = conn.prepareStatement("UPDATE Auction SET currently = ? WHERE item_id = ? "); 
            st.setInt(1, buy_price);
            st.setInt(2, item_id);
            if(st.executeUpdate()>0){
                error=1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
        }
        
        
        
        
        public int InsertBid(int item_id,int user_id,int amount){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;  
        int error=999,found=0;
        try {// check if user has made higher bid than current and return error
            st = conn.prepareStatement("SELECT amount FROM Bids WHERE item_id = ? AND id_user = ?"); 
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            res = st.executeQuery();
            while(res.next()){
                if (res.getInt("amount")>=amount)
                    return error=10;
            }
            // check if user has made a lower bid than auction's lowest bid allowed
            st = conn.prepareStatement("SELECT first_bid FROM Auction WHERE item_id = ?"); 
            st.setInt(1, item_id);
            res = st.executeQuery();
            if(res.next()){
                if (res.getInt("first_bid")>amount){
                    return error=15;    
                }
            }
            //check if user has made a bid so needs to update
            st = conn.prepareStatement("SELECT * FROM Bids WHERE item_id = ? AND id_user= ? ");
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            res=st.executeQuery();
            if(res.next()){
                st = conn.prepareStatement("UPDATE Bids SET amount = ? , time = NOW() WHERE item_id = ? AND id_user = ?"); 
                st.setInt(1, amount);
                st.setInt(2, item_id);
                st.setInt(3, user_id);
                st.executeUpdate();
                //++bid_counter
                st = conn.prepareStatement("UPDATE Auction SET number_of_bids = number_of_bids + 1 WHERE item_id = ?");
                st.setInt(1,item_id);
                st.executeUpdate();
               // check if it is the highest bid
                st = conn.prepareStatement("SELECT amount FROM Bids WHERE item_id = ? AND id_user !=?");
                st.setInt(1, item_id);
                st.setInt(2, user_id);
                res=st.executeQuery();
                while(res.next()){
                    if(res.getInt("amount")>=amount)
                        found=1;
                }
                if(found==1)
                    return error=20;
                else{
                    //if highest bid update auction column currently
                    st = conn.prepareStatement("UPDATE Auction SET currently = ? WHERE item_id=?");
                    st.setInt(1, amount);
                    st.setInt(2, item_id);
                    if(st.executeUpdate()>0){
                        error=1;
                    }                
                    return error;                    
                }
            }
            // insert BID
            st = conn.prepareStatement("INSERT INTO Bids (item_id,id_user,time,amount) VALUES (?,?,NOW(),?)"); 
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            st.setInt(3, amount);
            if(st.executeUpdate()>0){
                error=1;
            }
            //++bid_counter
            st = conn.prepareStatement("UPDATE Auction SET number_of_bids = number_of_bids + 1 WHERE item_id = ?");
            st.setInt(1,item_id);
            st.executeUpdate();
            // check if he has the highest bid
            st = conn.prepareStatement("SELECT amount FROM Bids WHERE item_id = ? AND id_user != ?");
            st.setInt(1, item_id);
            st.setInt(2, user_id);
            res=st.executeQuery();
            while(res.next()){
                if(res.getInt("amount")>=amount)
                    found=1;
            }
            if(found==1)
                return error=20;
            else{
                //if highest bid update auction column currently
                st = conn.prepareStatement("UPDATE Auction SET currently = ? WHERE item_id=?");
                st.setInt(1, amount);
                st.setInt(2, item_id);
                if(st.executeUpdate()>0){
                    error=1;
                }                
                return error;                
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
        }
        
        public void CalcWonItems(int userID){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Integer> item_ids= new ArrayList<Integer>();
        ArrayList<Integer> temp_ids = new ArrayList<Integer>();
        ArrayList<Integer> final_ids = new ArrayList<Integer>();
        int i;
        double amount=0;
        try {
            //find bids our user has made 14015
            st = conn.prepareStatement("SELECT item_id FROM Bids WHERE id_user = ?");
            st.setInt(1,userID);
            res=st.executeQuery();
            while(res.next()){
                item_ids.add(res.getInt("item_id"));
            }
            System.out.println("1 :"+item_ids);
            //find ended auctions
            st = conn.prepareStatement("SELECT item_id FROM Auction WHERE time_end < NOW()");
            res=st.executeQuery();
            while(res.next()){
                temp_ids.add(res.getInt("item_id"));
            }
            item_ids.retainAll(temp_ids);
            System.out.println("h lista exei twra ws :"+item_ids);            
            //decide the winner
            for(i=0;i<item_ids.size();i++ ){
                st = conn.prepareStatement("SELECT currently FROM Auction WHERE item_id = ? ");
                st.setInt(1,item_ids.get(i));
                res=st.executeQuery();
                if(res.next()){
                    amount=res.getDouble("currently");
                }
                //descending order is for case of same amount , the first bid made wins
                st = conn.prepareStatement("SELECT item_id , id_user FROM Bids where item_id = ? and amount = ? ORDER BY time ASC limit 1 ");
                st.setInt(1, item_ids.get(i));
                
                st.setDouble(2, amount);
                System.out.println(amount);
                res=st.executeQuery();

                if(res.next()){
                    System.out.println("enai uparxei");
                    if(userID==res.getInt("id_user"))
                        final_ids.add(res.getInt("item_id"));
                }                
            }
            //System.out.println(final_ids);
            System.out.println("telos exei"+final_ids);
            //insert into tables wins
            for(i=0;i<final_ids.size();i++){
                st = conn.prepareStatement("SELECT * FROM Wins WHERE Auction_item_id = ? AND Users_id = ?");
                st.setInt(1,final_ids.get(i) );
                st.setInt(2,userID);
                res=st.executeQuery();
                if(res.next()){
                    //do nothing
                }
                else{
                    //insert into
                    st = conn.prepareStatement("INSERT INTO Wins (Auction_item_id,Users_id,feedback_for_seller,feedback_to_buyer) VALUES (?,?,0,0)");
                    st.setInt(1,final_ids.get(i));
                    st.setInt(2,userID);
                    st.executeUpdate();
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return ;        
        }
        
    public ArrayList<Item> bringWonItems(int userID){
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Item> Items= new ArrayList<Item>();
        ArrayList<Integer> item_ids=new ArrayList<Integer>();
        int i;
        try {
            st = conn.prepareStatement("SELECT Auction_item_id FROM Wins WHERE Users_id = ?");
            st.setInt(1, userID);
            res = st.executeQuery();
            while(res.next()){
                item_ids.add(res.getInt("Auction_item_id"));
            }
            for(i=0;i<item_ids.size();i++){
                st = conn.prepareStatement("SELECT item_id,currently,s_rating,username,buy_price,first_bid,number_of_bids,auction_name,time_start,time_end,latitude,longitude,country FROM Auction , Item_Location_And_Description , Users WHERE item_id = Auction_item_id AND auction_author = Auction_auction_author AND auction_author = id AND item_id = ? ");
                st.setInt(1,item_ids.get(i));
                res=st.executeQuery();
                if(res.next()){
                    Items.add(new Item(res.getString("item_id"),res.getString("number_of_bids") ,res.getString("s_rating"),res.getString("username"),res.getString("auction_name"),res.getString("currently"),res.getString("buy_price"),res.getString("first_bid"),res.getString("country"),res.getString("time_start"),res.getString("time_end"),res.getString("latitude"),res.getString("longitude") ));
                }
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Items;
    }
    public ArrayList<Integer> RemainingFeedback(int u_id){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Integer> feed= new ArrayList<Integer>();
        try{
            st = conn.prepareStatement("SELECT * FROM Wins WHERE Users_id = ? AND feedback_for_seller = 0");
            st.setInt(1, u_id);
            res = st.executeQuery();
            while(res.next()){
                feed.add(res.getInt("Auction_item_id"));
            }
        }catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return feed;
    }
    public int InsertFeedbackToSeller(int itemid , int userid , int feed){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        int a_author=0;
        int error=0;
        try{
            if(feed==1){
                st=conn.prepareStatement("UPDATE Wins SET feedback_for_seller = 1 WHERE Auction_item_id = ? AND Users_id = ?"); 
                st.setInt(1, itemid);
                st.setInt(2, userid);
                if(st.executeUpdate()>0){
                    error=1;
                }
                st=conn.prepareStatement("SELECT auction_author FROM Auction WHERE item_id = ?");
                st.setInt(1, itemid);
                res = st.executeQuery();
                if(res.next()){
                    a_author=res.getInt("auction_author");
                }
                st=conn.prepareStatement("UPDATE Users SET s_rating = s_rating + 1 WHERE id = ?");
                st.setInt(1,a_author);
                st.executeUpdate();
            }else if (feed==-1){
                st=conn.prepareStatement("UPDATE Wins SET feedback_for_seller = 1 WHERE Auction_item_id = ? AND Users_id = ?"); 
                st.setInt(1, itemid);
                st.setInt(2, userid);
                if(st.executeUpdate()>0){
                    error=1;
                }
                st=conn.prepareStatement("SELECT auction_author FROM Auction WHERE item_id = ?");
                st.setInt(1, itemid);
                res = st.executeQuery();
                if(res.next()){
                    a_author=res.getInt("auction_author");
                }
                st=conn.prepareStatement("UPDATE Users SET s_rating = s_rating - 1 WHERE id = ?");
                st.setInt(1,a_author);
                st.executeUpdate();
            }

            
        }catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
    }
    
    public ArrayList<Integer> RemainingForBuyerFeedback(int userid){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        ArrayList<Integer> feed = new ArrayList<Integer>();
        ArrayList<Integer> temp = new ArrayList<Integer>();
        int i;
        try{
            st=conn.prepareStatement("SELECT item_id FROM Auction WHERE auction_author = ?");
            st.setInt(1, userid);
            res=st.executeQuery();
            while(res.next()){
                temp.add(res.getInt("item_id"));
            }
            for(i=0;i<temp.size();i++){
                st=conn.prepareStatement("SELECT Auction_item_id FROM Wins WHERE Auction_item_id = ? AND feedback_to_buyer = 0");
                st.setInt(1, temp.get(i));
                res=st.executeQuery();
                if(res.next())
                    feed.add(res.getInt("Auction_item_id"));
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return feed;
    }
    
    public int InsertFeedbackToBuyer(int item_id,int feedback){
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res;
        conn = sql.connect();
        PreparedStatement st;
        int buyer_id=0,error=999;
        try{
            if(feedback==1){                
                st = conn.prepareStatement("SELECT Users_id FROM Wins WHERE Auction_item_id = ?");
                st.setInt(1,item_id);
                res = st.executeQuery();
                if (res.next()){
                    buyer_id=res.getInt("Users_id");
                    st = conn.prepareStatement("UPDATE Users SET b_rating = b_rating + 1 WHERE id = ?");
                    st.setInt(1, buyer_id);
                    if(st.executeUpdate()>0 )
                        error=1;
                    st=conn.prepareStatement("UPDATE Wins SET feedback_to_buyer = 1 WHERE Auction_item_id = ? AND Users_id = ?"); 
                    st.setInt(1, item_id);
                    st.setInt(2, buyer_id);
                    if(st.executeUpdate()>0){
                        error=1;
                    }
                }
            }else if (feedback==-1){
                st = conn.prepareStatement("SELECT Users_id FROM Wins WHERE Auction_item_id = ?");
                st.setInt(1,item_id);
                res = st.executeQuery();
                if (res.next()){
                    buyer_id=res.getInt("Users_id");
                    st = conn.prepareStatement("UPDATE Users SET b_rating = b_rating - 1 WHERE id = ?");
                    st.setInt(1, buyer_id);
                    if(st.executeUpdate()>0 )
                        error=1;
                    st=conn.prepareStatement("UPDATE Wins SET feedback_to_buyer = 1 WHERE Auction_item_id = ? AND Users_id = ?"); 
                    st.setInt(1, item_id);
                    st.setInt(2, buyer_id);
                    if(st.executeUpdate()>0){
                        error=1;
                    }
                }
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;
        
    }

    public int SubmitMessage(String from,String to , String message) {
        
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        int error=999;
        int s_id=0,r_id=0;
        try {

            st=conn.prepareStatement("SET CHARACTER SET 'utf8'");
            st.executeQuery();
            st=conn.prepareStatement("SET NAMES 'utf8'");
            st.executeQuery();
            //get id of sender
            st=conn.prepareStatement("SELECT id FROM Users WHERE username = ?");
            st.setString(1, from);
            res = st.executeQuery();
            if(res.next())
                s_id=res.getInt("id");

            //get id of receiver
            st=conn.prepareStatement("SELECT id FROM Users WHERE username = ?");
            st.setString(1, to);
            res = st.executeQuery();
            if(res.next())
                r_id=res.getInt("id");
            System.out.println(s_id);
            System.out.println(r_id);
            st=conn.prepareStatement("INSERT INTO Messages (send_id,receive_id,Message,time,unread) VALUES (?,?,?,NOW(),0 ) ");
            st.setInt(1, s_id);
            st.setInt(2, r_id);
            st.setString(3, message);
            if(st.executeUpdate()>0)
                error=1;
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return error;

    }
    
    public ArrayList<Message> BringUnreadMessages(int u_id) {
        ArrayList<Message> mes_list = new ArrayList<Message>();
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null;
        conn = sql.connect();
        PreparedStatement st;
        try {
            st = conn.prepareStatement("SELECT * FROM Messages WHERE receive_id = ? AND unread = 0");
            st.setInt(1,u_id);
            res=st.executeQuery();
            while(res.next()){
                mes_list.add(new Message(res.getInt("send_id"),res.getInt("receive_id"),res.getInt("message_id"),res.getString("Message"),res.getString("time")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mes_list;
    }
    public ArrayList<Message> BringIncomingMessages(int u_id) {
        ArrayList<Message> mes_list = new ArrayList<Message>();
        Message temp_message;
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null,res2=null;
        conn = sql.connect();
        PreparedStatement st;
        String send_name=null;
        try {
            st = conn.prepareStatement("SELECT * FROM Messages WHERE receive_id = ?");
            st.setInt(1,u_id);
            res=st.executeQuery();
            while(res.next()){
                temp_message = new Message(res.getInt("send_id"),res.getInt("receive_id"),res.getInt("message_id"),res.getString("Message"),res.getString("time"));
                //get username of sender
                st = conn.prepareStatement("SELECT username FROM Users WHERE id = ?");
                st.setInt(1, res.getInt("send_id"));
                res2=st.executeQuery();
                if(res2.next())
                    send_name=res2.getString("username");
                temp_message.setSendName(send_name);
                mes_list.add(temp_message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mes_list;
    }
    
    
    public int DeleteMessage(String id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        ResultSet res = null;
        Integer error=-1;  
        
        try {
            st = conn.prepareStatement("DELETE FROM Messages WHERE message_id = ? ");
            st.setString(1,id);
        
            if(st.executeUpdate()>0){
                 error = 1;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        return error;
    }
    
    public ArrayList<Message> BringOutgoingMessages(int u_id) {
        ArrayList<Message> mes_list = new ArrayList<Message>();
        Message temp_message;
        SQLConnector sql = new SQLConnector();
        Connection conn;
        ResultSet res=null,res2=null;
        conn = sql.connect();
        PreparedStatement st;
        String receive_name=null;
        try {
            st = conn.prepareStatement("SELECT * FROM Messages WHERE send_id = ?");
            st.setInt(1,u_id);
            res=st.executeQuery();
            while(res.next()){
                temp_message = new Message(res.getInt("send_id"),res.getInt("receive_id"),res.getInt("message_id"),res.getString("Message"),res.getString("time"));
                //get username of receiver
                st = conn.prepareStatement("SELECT username FROM Users WHERE id = ?");
                st.setInt(1, res.getInt("receive_id"));
                res2=st.executeQuery();
                if(res2.next())
                    receive_name=res2.getString("username");
                temp_message.setReceiveName(receive_name);
                mes_list.add(temp_message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mes_list;
    }    

    public void UpdateMessageRead(int id) {
    
        SQLConnector sql = new SQLConnector();
        Connection conn;
        conn = sql.connect();
        PreparedStatement st;
        ResultSet res = null;
        Integer error=-1;  
        
        try {
            st = conn.prepareStatement("UPDATE Messages SET unread = 1 WHERE receive_id = ? ");
            st.setInt(1,id);
        
            if(st.executeUpdate()>0){
                 error = 1;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(SQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }    
}

