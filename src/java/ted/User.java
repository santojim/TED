package ted;

public class User  {
    
    private String username,email,role,phone,fname,lname,address,afm,latitude,longitude;

    private int s_rating,b_rating,id,confirmed;

 
    public User(){}
    public User(int idI,String uname, String emailE ,String roleR, String phoneP , String fnameF , String lnameL,String addressA ,
            String afmA ,String latitudeL,String longitudeL ,int ratingS,int ratingB,int confirmedC){
        this.username=uname;
        this.email=emailE;
        this.role=roleR;
        this.phone=phoneP;
        this.fname=fnameF;
        this.lname=lnameL;
        this.address=addressA;
        this.afm=afmA;
        this.latitude=latitudeL;
        this.longitude=longitudeL;
        this.s_rating=ratingS;
        this.b_rating=ratingB;
        this.id=idI;
        this.confirmed = confirmedC;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }
    public void setAfm(String afm) {
        this.afm = afm;
    }
    public String getAfm() {
        return afm;
    }    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public int getSrating() {
        return s_rating;
    }
    public void setSrating(int rating) {
        this.s_rating = rating;
    }        
    public int getBrating() {
        return b_rating;
    }
    public void setBrating(int brating) {
        this.b_rating = brating;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getConfirmed() {
        return confirmed;
    }
    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
