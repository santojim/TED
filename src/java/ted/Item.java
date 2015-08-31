package ted;

import java.util.ArrayList;

public class Item {
    String item_id,name,currently,buy_price,first_bid,location,country,started,ended,
            latitude,longitude,number_of_bids,seller_rating,seller_name,Description;
    ArrayList<String> Category;
    ArrayList<Bid> Bids;
    public Item(){};
    // Constructor without bid-list , description , categories ,itemid,location
    public Item(String i_id,String nob,String s_r,String s_n,String nm,String currently,String buy_price ,
            String f_b,String country,String start,String end,String latitude,String longitude){
        this.item_id=i_id;
        this.number_of_bids=nob;
        this.seller_rating=s_r;
        this.seller_name=s_n;
        this.currently=currently;
        this.buy_price=buy_price;
        this.name=nm;
        this.first_bid=f_b;
        this.country=country;
        this.started=start;
        this.ended=end;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public class Bid {
        String UserId,Location,Country,Time,Amount,Bidder_Rating;
/*
        public Bid(String userid,String location,String country,String time,String amount,String rating){
            this.UserId=userid;
            this.Location=location;
            this.Country=country;
            this.Time=time;
            this.Amount=amount;
            this.Bidder_Rating=rating;
        }*/
        public void setUserId(String userid){
            this.UserId=userid;
        }
        public String getUserId(){
            return UserId;
        }
        public void setLocation(String location){
            this.Location=location;
        }
        public String getLocation(){
            return Location;
        }
        public void setCountry(String country){
            this.Country=country;
        }
        public String getCountry(){
            return Country;
        }
        public void setTime(String time){
            this.Time=time;
        }
        public String getTime(){
            return Time;
        }    
        public void setAmount(String amount){
            this.Amount=amount;
        }
        public String getAmount(){
            return Amount;
        }
        public void setRating(String rating){
            this.Bidder_Rating=rating;
        }
        public String getRating(){
            return Bidder_Rating;
        }
    }
    public void setNumberOfBids(String nob){
        this.number_of_bids=nob;
    }
    public String getNumberOfBids(){
        return number_of_bids;
    }
    public void setSellerRating(String rat){
        this.seller_rating=rat;
    }
    public String getSellerRating(){
        return seller_rating;
    }
    public void setSellerName(String nm){
        this.seller_name=nm;
    }
    public String getSellerName(){
        return seller_name;
    }    
    public void setItemId(String i_id){
        this.item_id=i_id;
    }
    public String getItemId(){
        return item_id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setCurrently(String cur){
        this.currently=cur;
    }
    public String getCurrently(){
        return currently;
    }    
    public void setBuyPrice(String bpr){
        this.buy_price =bpr;
    }
    public String getBuyPrice(){
        return buy_price;
    }
    public void setFirstBid(String fbid){
        this.first_bid =fbid;
    }
    public String getFirstBid(){
        return first_bid;
    }        
    public void setLocation(String loc){
        this.location =loc;
    }
    public String getLocation(){
        return location;
    }
    public void setCountry(String country){
        this.country =country;
    }
    public String getCountry(){
        return country;
    }            
    public void setStarted(String started){
        this.started =started;
    }
    public String getStarted(){
        return started;
    }  
    public void setEnded(String ended){
        this.ended =ended;
    }
    public String getEnded(){
        return ended;
    }
    public void setCategory(ArrayList<String> category){
        this.Category=category;
    }
    public ArrayList<String> getCategory(){
        return Category;
    }
    public void setDescription(String description){
        this.Description=description;
    }
    public String getDescription(){
        return Description;
    }
    public void setBids(ArrayList<Bid> bid){
        this.Bids=bid;
    }
    public ArrayList<Bid> getBids(){
        return Bids;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLongitude() {
        return longitude;
    }
}
