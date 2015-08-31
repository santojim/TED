package ted;


public class Message {

    int send_id, receive_id, message_id;
    String message,time,send_name,receive_name;
    
    public Message(int s_id, int r_id, int m_id, String message,String time) {
        this.send_id = s_id;
        this.receive_id = r_id;
        this.message_id = m_id;
        this.message = message;
        this.time = time;
    }

    public void setSendId(int s_id) {
        this.send_id = s_id;
    }
    public int getSendId() {
        return this.send_id;
    }
    
    public void setUserId(int r_id) {
        this.receive_id = r_id;
    }
    public int getUserId() {
        return this.receive_id;
    }
    
    public void setMessageId(int m_id) {
        this.message_id = m_id;
    }
    public int getMessageId() {
        return this.message_id;
    }
    public void setTime(String tm) {
        this.time = tm;
    }
    public String getTime() {
        return this.time;
    }
    
    
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
    public void setSendName(String sname){
        this.send_name = sname;
    }
    public String getSendName(){
        return send_name;
    }
    public void setReceiveName(String rname){
        this.receive_name = rname;
    }
    public String getReceiveName(){
        return receive_name;
    }
    
}
