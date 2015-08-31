package ted;

public class Photo {

    String path;
    int id,item_id;
    
    public Photo(){
        
    }

    public Photo(String path, int id, int item_id) {
        this.path = path;
        this.id = id;
        this.item_id = item_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
