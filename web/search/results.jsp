<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ted.SQLConnector"%>
<%@page import="ted.Item"%>
<%@page import="ted.Photo"%>
<%@page import="java.util.*" %>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="../js/jquery.simplePagination.js"></script>
        <script type="text/javascript" src="../js/SimplePaginationSpec.js"></script>
        <link type="text/css" rel="stylesheet" href="../css/simplePagination.css"/>
        <title>Results | E-BUY</title>
        <%
            if(session.getAttribute("visitor").equals("1")){
                ;
            }
            else if(session.getAttribute("buyer").equals("0")){
                response.sendRedirect("/TED/notAuthorised.jsp");
            }           
            ArrayList<Item> Items = new ArrayList<Item>();
            Item tempItem=null;
            SQLConnector sqlDB = new SQLConnector();
            String[] itemIdsString = request.getParameterValues("id");


            if (itemIdsString != null)  {
                for (int i=0; i<itemIdsString.length; i++) {
                    //out.println(Integer.parseInt(itemIdsString[i]));
                    if(sqlDB.bringItemById(Integer.parseInt(itemIdsString[i]))!= null )
                        Items.add(sqlDB.bringItemById(Integer.parseInt(itemIdsString[i]) ));
                }
            }
        %>  
        <style>
            #content_perfect{
                text-align: center;
            }
            body{
                margin-left: 0%;
                margin-right: 0%;
                background-color:rgba(0,0,0,0.8);

            }
            ul{
                max-width: 60%;
                margin: 0 auto;                
                color:White;
                list-style:none;
                overflow:auto;
                
            }
            li{
                list-style-type: none;
                font-size:17px;                
            }            
        </style>
    </head>
    <body>
      
<!--################################################################################################################## -->
<!--####################################  PANEL PROBOLHS APOTELESMATWN ############################################### -->
<!--################################################################################################################## -->
        <h1 style='color: White; text-align:center;'><b>RESULTS</b></h1>
        <ul>
            <%  int i;
                ArrayList<Photo> photos = new ArrayList<Photo>();
                Photo tempPhoto = new Photo(); 
                int pages=0;
                if(Items.size()==0){
                    out.println("<h2 style='text-align : center;'> Δεν Βρέθηκαν Αποτελέσματα </h2>");
                }
                out.println("<div class='tabs'>");
                out.println("<div class='tab-links'>");
                for( i=0;i<Items.size();i++){
                    if(i==0 && Items.size()>5){
                         pages++; 
                         out.print("<a class='active' href='#tab1'>Σελίδες&nbsp;&nbsp;1η </a>");
                     }else if((i%5)==0 && i>=5){
                         pages++;
                         out.print("<a href='#tab"+pages+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+pages+"η</a>");
                     }
                }
                out.println("</div>");
                out.println("<div class='tab-content'>");
                int counter=1;
                for(i=0;i<Items.size();i++){
                    tempItem = Items.get(i);
                    photos = sqlDB.bringPhotosById(tempItem.getItemId());
                    if ( ! (photos.isEmpty()) )
                        tempPhoto = photos.get(0);
                    else {
                        tempPhoto.setPath("../img/image_not_found.gif");
                        tempPhoto.setId(-1);
                        tempPhoto.setItem_id(-1);
                    }
                   
                    if(i==0){
                        out.println("<div id='tab1' class='tab active'>");
                    }	
                    out.println("<br><br>");
                    out.println("<a  id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                    out.println("<li><a id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                    out.println("<img src='"+tempPhoto.getPath()+"' width='150' height='120' style='format:left; float:left'/></a> <h3 style='  margin-left: 30%; color:white;'></h3>");
     
                    out.println("<li><a id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                        out.println("<ul style='margin-right: 0%'>");

                        out.println("<li><text style='color:white'><b>Όνομα Δημοπρασίας : </b></text><text style='color:#9FD88D'>"+tempItem.getName()+"</text></li>");
                        if(!tempItem.getCurrently().equals("0"))
                            out.println("<li><text style='color:white'><b>Τρέχουσα Καλύτερη Προσφορά : </b></text><text style='color:#9FD88D'>"+tempItem.getCurrently()+"</text></li>");
                        else
                            out.println("<li><text style='color:white'><b>Τρέχουσα Καλύτερη Προσφορά : </b></text><text style='color:#9FD88D'>"+"  -  "+"</text></li>");
                        if(!tempItem.getBuyPrice().equals("0"))
                           out.println("<li><text style='color:white'><b>Τιμή Άμεσης Αγοράς : </b></text><text style='color:#9FD88D'>"+tempItem.getBuyPrice()+"</text></li>");
                        out.println("<li><text style='color:white'><b>Ελάχιστη Προσφορά : </b></text><text style='color:#9FD88D'>"+tempItem.getFirstBid()+"</text></li>");
                        if(!tempItem.getNumberOfBids().equals("0"))
                            out.println("<li><text style='color:white'><b>Πλήθος Προσφορών : </b></text><text style='color:#9FD88D'>"+tempItem.getNumberOfBids()+"</text></li>");
                        else
                            out.println("<li><text style='color:white'><b>Πλήθος Προσφορών : </b></text><text style='color:#9FD88D'>"+"  -  "+"</text></li>");
                        out.println("<li><text style='color:white'><b>Ημ/νία Έναρξης : </b></text><text style='color:#9FD88D'>"+tempItem.getStarted()+"</text></li>");
                        out.println("<li><text style='color:white'><b>Ημ/νία Λήξης : </b></text><text style='color:#9FD88D'>"+tempItem.getEnded()+"</text></li>");
                        out.println("<li><text style='color:white'><b>Περιοχή-Χώρα : </b></text><text style='color:#9FD88D'>"+tempItem.getCountry()+"</text></li>");
                        out.println("</ul>");
                    out.println("</a></li>");
                    out.println("<hr/>");    
                    if((i%5)+1==5){
                        counter++;
                        out.println("</div>");
                        out.println("<div id='tab"+counter+"' class='tab' style='display:none;'>");
                    }                    
                }
            out.println("</div>");
            out.println("</div>");
                pages=0;
                out.println("<div class='tabs'>");
                out.println("<div class='tab-links'>");
                for( i=0;i<Items.size();i++){
                    if(i==0 && Items.size()>5){
                         pages++; 
                         out.print("<a class='active' href='#tab1'>Σελίδες&nbsp;&nbsp;1η </a>");
                     }else if((i%5)==0 && i>=5){
                         pages++;
                         out.print("<a href='#tab"+pages+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+pages+"η</a>");
                     }
                }
            out.println("</div>");
            out.println("</div>");                
            %>    
        </ul>
        <br>
        <br>
    </body>
    <script>    
    $(document).ready(function() {
        $('.tabs .tab-links a').on('click', function(e)  {
            var currentAttrValue = $(this).attr('href');
            // Show/Hide Tabs
            $('.tabs ' + currentAttrValue).fadeIn(500).siblings().hide();

            // Change/remove current tab to active
            $(this).parent('li').addClass('active').siblings().removeClass('active');

            e.preventDefault();
        });
    }); 

    </script>
</html>
