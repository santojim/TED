//http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
package xml;

import xml.SQLConnector_xml;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ted.Item;


public class FileStuff {

    
public static void main(String argv[]){
    int i,j,y;
    ArrayList<ArrayList<Item>> main_item_list= new ArrayList<ArrayList<Item>>();
    SQLConnector_xml sqlDB = new SQLConnector_xml();
    String country_location=null,uname=null,b_rating,s_rating;
    String bidtime,amount=null;
    String seller_name , auct_name,currently,b_price ,first_bid , number_of_bids,started,ended,description,lat,lon,country;
    ArrayList<String> cat;
    int item_id;
    for (i=0;i<2;i++){
       main_item_list.add( XMLREADER("/home/jim/NetBeansProjects/TED/ebay-data/items-"+i+".xml") );
    }
    ArrayList<Item.Bid> bidlist=new ArrayList<Item.Bid>();
    
    
    for(i=0;i<main_item_list.size();i++){
        System.out.println("XML "+ i +" starts");
        for(j=0;j<main_item_list.get(i).size();j++){      
            bidlist = main_item_list.get(i).get(j).getBids();
            for(y=0;y<bidlist.size();y++){
                //bidder name
                country_location=null;
                //System.out.println("name :"+bidlist.get(y).getUserId() );
                //System.out.println("rating :"+bidlist.get(y).getRating() );
                //System.out.println("loc :"+bidlist.get(y).getLocation() );
                //System.out.println("country :"+bidlist.get(y).getCountry() );
                country_location=bidlist.get(y).getLocation()+","+bidlist.get(y).getCountry();
                uname=bidlist.get(y).getUserId();
                b_rating=bidlist.get(y).getRating();
               if(sqlDB.InsertUsers(uname, null, b_rating, country_location)==0){
                    System.out.println("2 MAYBE DUPLICATE " +uname );
                }

                //System.out.println(bidlist.get(y).getAmount().substring(1));//remove dollar symbol
            }

            uname =main_item_list.get(i).get(j).getSellerName();
            s_rating=main_item_list.get(i).get(j).getSellerRating();
            if(sqlDB.InsertUsers(uname, s_rating, null, null)==0){
                System.out.println("1 MAYBE DUPLICATE " +uname );
            }
            

            b_price=null;
            //seller name
            //System.out.println("sellername "+main_item_list.get(i).get(j).getSellerName() );
            seller_name =main_item_list.get(i).get(j).getSellerName();
            //System.out.println("name "+main_item_list.get(i).get(j).getName() );
            auct_name = main_item_list.get(i).get(j).getName();
            //System.out.println("curre "+main_item_list.get(i).get(j).getCurrently() );
            currently = main_item_list.get(i).get(j).getCurrently().substring(1);
            currently=currently.replace(",", "");
            if(main_item_list.get(i).get(j).getBuyPrice()!=null){
                b_price=main_item_list.get(i).get(j).getBuyPrice().substring(1);
                b_price=b_price.replace(",", "");
            }
                
                //System.out.println("b_price "+main_item_list.get(i).get(j).getBuyPrice().substring(1) );
            //System.out.println("f_bid "+main_item_list.get(i).get(j).getFirstBid().substring(1) );
            first_bid = main_item_list.get(i).get(j).getFirstBid().substring(1);
            first_bid = first_bid.replace(",", "");
            //System.out.println("#bids "+main_item_list.get(i).get(j).getNumberOfBids() );
            number_of_bids = main_item_list.get(i).get(j).getNumberOfBids();
            started = main_item_list.get(i).get(j).getStarted();
            
            started=DateTimeConvert(started);
            
            //System.out.println("starts "+ started);
            
            ended=main_item_list.get(i).get(j).getEnded();
            
            ended = DateTimeConvert(ended);
            
            //System.out.println("ends "+ended );
            
            //System.out.println("desc "+main_item_list.get(i).get(j).getDescription() );
            description = main_item_list.get(i).get(j).getDescription();
            //System.out.println("lat "+main_item_list.get(i).get(j).getLatitude() );
            lat=main_item_list.get(i).get(j).getLatitude();
            //System.out.println("lon "+main_item_list.get(i).get(j).getLongitude() );
            lon=main_item_list.get(i).get(j).getLongitude();
//            System.out.println("country "+main_item_list.get(i).get(j).getCountry() );
            country= main_item_list.get(i).get(j).getCountry();
//            System.out.println("categ "+main_item_list.get(i).get(j).getCategory() );  
            cat=main_item_list.get(i).get(j).getCategory();
           // System.out.println("To currently einai : "+ currently);
            
            item_id=sqlDB.insertAuction(seller_name, auct_name, currently, b_price, first_bid, number_of_bids, started, ended, description, lat, lon, country, cat);            

            for(y=0;y<bidlist.size();y++){
                bidtime=bidlist.get(y).getTime();
                if(bidtime!=null)
                    bidtime=DateTimeConvert(bidtime);
                else
                    bidtime=null;
                if(bidlist.get(y).getAmount()!=null){
                    amount = bidlist.get(y).getAmount().substring(1);
                    amount = amount.replace(",", "");                    
                }
                if(sqlDB.insertBids(bidlist.get(y).getUserId() , item_id , bidtime , amount)==0){
                    System.out.println("3 error for item "+main_item_list.get(i).get(j).getItemId());
                }
            }
            
        }
    }
}




public static ArrayList<Item> XMLREADER(String path) {        
        ArrayList<Item> itemlist= new ArrayList<Item>();
    try {
        
          //ArrayList<Item> itemlist= new ArrayList<Item>();
//        ArrayList<String> temp_cat = new ArrayList<String>();
//        temp_cat=null;
//        ArrayList<Item.Bid> bidlist=new ArrayList<Item.Bid>();
//        Item.Bid tempBid = null;

        //"/home/jim/NetBeansProjects/MY_TED/ebay-data/items-20.xml"
	File fXmlFile = new File(path);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);
	int cur=0,i=0;
//        String lat,lon;
	//optional, but recommended
	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	doc.getDocumentElement().normalize();

//	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
	NodeList nList = doc.getElementsByTagName("Item");
			
//	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {
//mhdenismos tempItem
            //Item tempItem = new Item("1","2","3","4","5","6","7","8","9","10","11","12",temp_cat,"14",bidlist);
            Item tempItem = new Item();
//            tempItem=null; 
		Node nNode = nList.item(temp);
				
//		System.out.println("\nCurrent Element :" + nNode.getNodeName() +"###############################################");
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			Element eElement = (Element) nNode;

//			System.out.println("Item id : " + eElement.getAttribute("ItemID"));
                        tempItem.setItemId(eElement.getAttribute("ItemID"));
//			System.out.println("Auction Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());
                        tempItem.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                        cur=eElement.getElementsByTagName("Category").getLength();
//mhdenismos cat
                        ArrayList<String> temp_cat = new ArrayList<String>();
                        for(i=0; i<cur ;i++ ){
//                            System.out.println("Category : " + eElement.getElementsByTagName("Category").item(i).getTextContent());
                            temp_cat.add(eElement.getElementsByTagName("Category").item(i).getTextContent());
                        }
                        tempItem.setCategory(temp_cat);
//                        System.out.println("Currently : " + eElement.getElementsByTagName("Currently").item(0).getTextContent());
                        tempItem.setCurrently(eElement.getElementsByTagName("Currently").item(0).getTextContent());
                        if(eElement.getElementsByTagName("Buy_Price").getLength()>0 ){
//                        System.out.println("BuyPrice : " + eElement.getElementsByTagName("Buy_Price").item(0).getTextContent());
                            tempItem.setBuyPrice(eElement.getElementsByTagName("Buy_Price").item(0).getTextContent());
                        }
//                        System.out.println("First_Bid : " + eElement.getElementsByTagName("First_Bid").item(0).getTextContent());
                        tempItem.setFirstBid(eElement.getElementsByTagName("First_Bid").item(0).getTextContent());
//                        System.out.println("Number_of_Bids : " + eElement.getElementsByTagName("Number_of_Bids").item(0).getTextContent());
                        tempItem.setNumberOfBids(eElement.getElementsByTagName("Number_of_Bids").item(0).getTextContent());
//                        System.out.println("------------||| B I D |||-------------------------------------------");                        
//mhdenismos bidlist
                        ArrayList<Item.Bid> bidlist=new ArrayList<Item.Bid>();
                        for(i=0; i < eElement.getElementsByTagName("Bid").getLength() ;i++ ){
                            Element bids = (Element) eElement.getElementsByTagName("Bids").item(0);
                            Item.Bid tempBid = tempItem.new Bid();
                            
                            Element bid =(Element) bids.getElementsByTagName("Bid").item(i);
                            Element bidder =(Element) bid.getElementsByTagName("Bidder").item(0);
//                            System.out.println("-------------------------------------------------------");                        
//                            System.out.println("Bidder_rating : " + bidder.getAttribute("Rating"));
                            tempBid.setRating(bidder.getAttribute("Rating"));
//                            System.out.println("Bidder_id : " + bidder.getAttribute("UserID"));
                            tempBid.setUserId(bidder.getAttribute("UserID"));
                            if(bid.getElementsByTagName("Location").getLength()>0 ){
//                                System.out.println("Location : " + bid.getElementsByTagName("Location").item(0).getTextContent());    
                                tempBid.setLocation(bid.getElementsByTagName("Location").item(0).getTextContent());
                            }
                            if(bid.getElementsByTagName("Country").getLength()>0 ){
//                                System.out.println("Country : " + bid.getElementsByTagName("Country").item(0).getTextContent());
                                tempBid.setCountry(bid.getElementsByTagName("Country").item(0).getTextContent());
                            }                              
//                            System.out.println("Time : " + bid.getElementsByTagName("Time").item(0).getTextContent());
                            tempBid.setTime(bid.getElementsByTagName("Time").item(0).getTextContent());
//                            System.out.println("Amount : " + bid.getElementsByTagName("Amount").item(0).getTextContent());
                            tempBid.setAmount(bid.getElementsByTagName("Amount").item(0).getTextContent());
                        bidlist.add(tempBid);
                        }
                        tempItem.setBids(bidlist);
//                        System.out.println("---------------||| E N D |||----------------------------------------");
                        Element location =(Element) eElement.getElementsByTagName("Location").item(0);
                        if(eElement.getElementsByTagName("Location").getLength()>1 )
                            location =(Element) eElement.getElementsByTagName("Location").item(i-1);
                        
                        Element country =(Element) eElement.getElementsByTagName("Country").item(0);
                        if(eElement.getElementsByTagName("Country").getLength()>1 ){
                            country =(Element) eElement.getElementsByTagName("Country").item(i-1);                        
                        }
                        
//                        System.out.println("Location : " + location.getTextContent());
                        tempItem.setLocation(location.getTextContent());
//                        System.out.println("Latitude : " +location.getAttribute("Latitude"));
                        tempItem.setLatitude(location.getAttribute("Latitude"));
//                        System.out.println("Longitude : " + location.getAttribute("Longitude"));
                        tempItem.setLongitude(location.getAttribute("Longitude"));
//                        System.out.println("Country : " + country.getTextContent());                            
                        
                        
                        if( eElement.getElementsByTagName("Country").item(0) !=null)
                            tempItem.setCountry(eElement.getElementsByTagName("Country").item(0).getTextContent());
                        else
                            tempItem.setCountry(country.getTextContent());
//                        System.out.println("Started : " + eElement.getElementsByTagName("Started").item(0).getTextContent());
                        tempItem.setStarted(eElement.getElementsByTagName("Started").item(0).getTextContent());
//                        System.out.println("Ends : " + eElement.getElementsByTagName("Ends").item(0).getTextContent());
                        tempItem.setEnded(eElement.getElementsByTagName("Ends").item(0).getTextContent());
                        Element seller = (Element) eElement.getElementsByTagName("Seller").item(0);

//                        System.out.println("Seller_id : " + seller.getAttribute("UserID"));
                        tempItem.setSellerName(seller.getAttribute("UserID"));
//                        System.out.println("Seller_rating : " + seller.getAttribute("Rating"));
                        tempItem.setSellerRating(seller.getAttribute("Rating"));
//                        System.out.println("Description : " + eElement.getElementsByTagName("Description").item(0).getTextContent());
                        tempItem.setDescription(eElement.getElementsByTagName("Description").item(0).getTextContent());
		itemlist.add(tempItem);

                }
        }               /*
                        Item temp;
                        String cat;
                        int y;
                        for(i=0;i<itemlist.size();i++){
                            temp = itemlist.get(i);
                            for(y=0;y<temp.getCategory().size();y++){
                                cat=temp.getCategory().get(y) ;
                                System.out.println(cat);
                            }
                                
                        }*/
                    

    } catch (Exception e) {
	e.printStackTrace();
    }
    return itemlist;        
    }

    public static String DateTimeConvert(String datetime){
        String final_date=datetime;
            if(final_date.contains("Dec"))
               final_date=final_date.replace("Dec","12");
            else if (final_date.contains("Nov"))
               final_date=final_date.replace("Nov", "11");
            else if (final_date.contains("Oct"))
               final_date=final_date.replace("Oct", "10");
            else if (final_date.contains("Sep"))
               final_date=final_date.replace("Sep", "09");
            else if (final_date.contains("Aug"))
               final_date=final_date.replace("Aug", "08");
            else if (final_date.contains("Jul"))
               final_date=final_date.replace("Jul", "07");
            else if (final_date.contains("Jun"))
               final_date=final_date.replace("Jun", "06");
            else if (final_date.contains("May"))
               final_date=final_date.replace("May", "05");
            else if (final_date.contains("Apr"))
               final_date=final_date.replace("Apr", "04");
            else if (final_date.contains("Mar"))
               final_date=final_date.replace("Mar", "03");
            else if (final_date.contains("Feb"))
               final_date=final_date.replace("Feb", "02");
            else if (final_date.contains("Jan"))
               final_date=final_date.replace("Jan", "01");
            
        StringBuilder buf = new StringBuilder(final_date);
        int start = 6;
        int end = 6;
        buf.replace(start,end,"20"); 
        final_date=buf.toString();
    try {
        
        String oldFormat = "MM-dd-yyyy HH:mm:ss";
        String newFormat = "yyyy-MM-dd HH:mm:ss";
        
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
        
        
        final_date= sdf2.format(sdf1.parse(final_date));
    } catch (ParseException ex) {
        Logger.getLogger(FileStuff.class.getName()).log(Level.SEVERE, null, ex);
    }
    return final_date;
    }

}