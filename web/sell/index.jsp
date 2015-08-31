<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.*" %>
<%@page import="ted.SQLConnector"%>
<%@page import="ted.Item"%>
<%@page import="ted.Photo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Ανάρτηση Δημοπρασίας | E-Buy </title>
        <%        
        if(session.getAttribute("seller").equals("0")){
             response.sendRedirect("/TED/notAuthorised.jsp");
        }
        %>
        <style>
            #content_perfect{
                text-align: center;
            }
            #map-canvas{
                width: 50%;
                height: 400px;
                background-color: #CCC;
                margin: 0 auto;
                border-radius: 1%;
            }   
            #map-canvas2{
                width: 500px;
                height: 300px;
                background-color: #CCC;
                margin: 0 auto;
                display:none;
            }
            li{
                list-style-type: none;
                font-size:17px;
                text-align: left;
                margin-right: 10px;                
            }
        </style>
        <%
            ArrayList<Item> Items = new ArrayList<Item>();
            Item tempItem=null;
            SQLConnector sqlDB = new SQLConnector();
            Integer id = Integer.parseInt(session.getAttribute("id").toString());
            
            if(session.getAttribute("admin").equals("0"))
                Items = sqlDB.bringAllItems(id);
            else
                Items = sqlDB.bringItemsOfAllUsers();
            
            ArrayList<Integer> feed= new ArrayList<Integer>();
            
            feed=sqlDB.RemainingForBuyerFeedback(id);
        %>        
        <script src="https://maps.googleapis.com/maps/api/js"></script>
        <script type="text/javascript" >
        function displayMap() {
                    document.getElementById('map-canvas2').style.display="block";
                   initialize();
                }
        var geocoder;
        var map,map2;
        var marker;
///////////////////////////////// INITIALIZE ///////////////////////////////////        
        function initialize() {
            var mapCanvas = document.getElementById('map-canvas');
            var mapCanvas2 = document.getElementById('map-canvas2');
            geocoder = new google.maps.Geocoder();
            var mapOptions = {
                zoom: 12,
                center: new google.maps.LatLng(37.989398, 23.729382),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                draggable: true,
                zoomControl: true,
                scrollwheel: true,
                streetViewControl: false,
                disableDoubleClickZoom: true
            };
            map = new google.maps.Map(mapCanvas, mapOptions);
            map2 = new google.maps.Map(mapCanvas2,mapOptions);                
            var locations =[<%
                int i;
                if(Items.size()>0){
                    for(i=0;i<Items.size()-1;i++){
                        tempItem = Items.get(i);
                        out.print("["+tempItem.getLatitude()+","+tempItem.getLongitude()+"],");
                    }
                    tempItem = Items.get(i);
                    out.print("["+tempItem.getLatitude()+","+tempItem.getLongitude()+"]");
                }
            %>];
            var contexts = [<% i=0;
                if(Items.size()>0){
                    for(i=0;i<Items.size()-1;i++){
                        tempItem = Items.get(i);
                        out.print("'"+tempItem.getName()+"',");
                    }
                tempItem = Items.get(i);
                out.print("'"+tempItem.getName()+"'");
                }
            %>];                        
            var marker2,i,latlngBounds;
            var markers = new Array();
            latlngBounds = new google.maps.LatLngBounds();
            var infowindow = new google.maps.InfoWindow({
                maxWidth: 400
            });
            for(i=0;i<locations.length;i++){
                marker2 = new google.maps.Marker({
                position: new google.maps.LatLng(locations[i][0], locations[i][1]),
                map: map
                });
                markers.push(marker2);
                google.maps.event.addListener(marker2, 'click', (function(marker2, i) {
                    return function() {
                        infowindow.setContent(contexts[i]);
                        infowindow.open(map, marker2);
                    }
                })(marker2, i));
                latlngBounds.extend(new google.maps.LatLng(locations[i][0],locations[i][1]));
            }
            
            map.fitBounds(latlngBounds);        
            google.maps.event.trigger(map , 'resize');
            google.maps.event.trigger(map2 , 'resize');                        
        }
/////////////////////////// SEARCH ADDRESS FUNCTION ////////////////////////////
        function codeAddress() {
            var address = document.getElementById('i_addr').value;
            geocoder.geocode({'address': address}, function(results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (marker != null) {
                        marker.setMap(null);
                        marker = null;
                    }
                    document.getElementById('c_ser').className = "btn btn-md btn-primary btn-success btn-block";
                    document.getElementById('i_faddr').className = "form-group has-success";
                    document.getElementById('i_lon').value = results[0].geometry.location.lng();
                    document.getElementById('i_lat').value = results[0].geometry.location.lat();
                    document.getElementById('i_addr').value = results[0].formatted_address;
                    map2.setCenter(results[0].geometry.location);
                    marker = new google.maps.Marker({
                        map: map2,
                        position: results[0].geometry.location
                    });
                    }
                    else{
                        if (marker != null) {
                            marker.setMap(null);
                            marker = null;
                        }
                        document.getElementById('c_ser').className = "btn btn-md btn-primary btn-danger btn-block";
                        document.getElementById('i_faddr').className = "form-group has-error";
                        document.getElementById('i_lon').value = "";
                        document.getElementById('i_lat').value = "";
                    }
                });
        }        
        google.maps.event.addDomListener(window, 'load', initialize);
        </script>        
    </head>
    <body>
        <br>
        <div id="map-canvas"></div>
        <br>
        <div class="container well well-lg" id="content_perfect" style="max-width:50%;background-color:rgba(0,0,0,0.7);">
           <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal" id="starting_modal">
                Ανέβασμα Δημοπρασίας
           </button>        
<!--################################################################################################################## -->
<!--####################################  PANEL PROBOLHS DHMOPRASIWN  ################################################ -->
<!--################################################################################################################## -->
        
        <ul style="color:White;list-style:none;overflow:auto;height:300px;" >
            <%  int y;
                
                ArrayList<Photo> photos = new ArrayList<Photo>();
                Photo tempPhoto = new Photo();                
                
                for(i=0;i<Items.size();i++){
                    tempItem = Items.get(i);
                    photos = sqlDB.bringPhotosById(tempItem.getItemId());
                    int i_id = Integer.parseInt(tempItem.getItemId());
                    
                    
                    if ( ! (photos.isEmpty()) )
                        tempPhoto = photos.get(0);
                    else {
                        tempPhoto.setPath("../img/image_not_found.gif");
                        tempPhoto.setId(-1);
                        tempPhoto.setItem_id(-1);
                    }
                   
                            
                    out.println("<br><br>");
                    //FEEDBACK BUTTONS
                    for(y=0;y<feed.size();y++){
                        if(feed.get(y)== i_id){   
                            out.print("<a style='margin-left:10%; background-color:darkgreen;'class='btn btn-primary' type='button' href='/TED/feedback?item_id="+tempItem.getItemId()+"&feedback=pos&u_id=0'>");
                            out.print("Leave Positive Feedback");
                            out.print("</a>");
                            out.print("<a style='margin-left:20%;'class='btn btn-primary btn-danger' type='button' href='/TED/feedback?item_id="+tempItem.getItemId()+"&feedback=neg&u_id=0'>");
                            out.print("Leave Negative Feedback");
                            out.print("</a>");
                        }
                        
                    }                    
                    
                    
                    out.println("<a  id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                    out.println("<li><a id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                    out.println("<img src='"+tempPhoto.getPath()+"' width='150' height='120' style='format:left; float:right'/></a> <h3 style='  margin-left: 30%; color:white;'></h3>");
     
                    out.println("<li><a id='link' href='viewItem.jsp?id="+tempItem.getItemId()+"'>");
                    out.println("<ul style='margin-right: 0%'>");
                    
                    out.println("<li><text style='color:white'><b>Όνομα Δημοπρασίας : </b></text><text style='color:#9FD88D'>"+tempItem.getName()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Τρέχουσα Καλύτερη Προσφορά : </b></text><text style='color:#9FD88D'>"+tempItem.getCurrently()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Τιμή Άμεσης Αγοράς : </b></text><text style='color:#9FD88D'>"+tempItem.getBuyPrice()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Ελάχιστη Προσφορά : </b></text><text style='color:#9FD88D'>"+tempItem.getFirstBid()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Πλήθος Προσφορών : </b></text><text style='color:#9FD88D'>"+tempItem.getNumberOfBids()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Ημ/νία Έναρξης : </b></text><text style='color:#9FD88D'>"+tempItem.getStarted()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Ημ/νία Λήξης : </b></text><text style='color:#9FD88D'>"+tempItem.getEnded()+"</text></li>");
                    out.println("<li><text style='color:white'><b>Περιοχή-Χώρα : </b></text><text style='color:#9FD88D'>"+tempItem.getCountry()+"</text></li>");
                    out.println("</ul>");
                    out.println("</a></li>");
                    out.println("<hr/>");                    
                }
                if(i==0)
                    out.println("<h3 style='margin-left:10%;'>ΔΕΝ ΕΧΕΤΕ ΑΝΕΒΑΣΕΙ ΤΙΠΟΤΑ ΑΚΟΜΗ</h3>");
            %>    
        </ul>
        </div>
<!--################################################################################################################## -->
<!--###################################### PANEL DHMOPRASIAS ME PATHMA KOUMPIOU ###################################### -->
<!--################################################################################################################## -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Άκυρο</span></button>
            <h4 class="modal-title" id="myModalLabel" style='text-align:center;'>Ανάρτηση νέας δημοπρασίας</h4>
        </div>
        <div class="modal-body">
            <div id="finished" hidden>
                <h2 id="finished_label" style="text-align:center;">Η ανάρτηση της δημοπρασίας ολοκληρώθηκε</h2>
            </div>
            <form action="" method="GET" id="form_auction" name="Form" onsubmit="return validateForm()" >
                <div class="form-group">
                    <label style="margin-left: 5%;" for="i_lon" class="col-sm-1 control-label">Lon</label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control" name="c_lon" id="i_lon" required readonly>
                    </div>
                    <label for="i_lat" class="col-sm-1 control-label">Lat</label>
                    <div class="col-sm-4">
                        <input type="number" class="form-control" name="c_lat" id="i_lat" required readonly>
                    </div>
                </div><br><br>
                
                <div>
                    <button style="max-width: 50%; margin-left:25%; "class=" btn btn-primary btn-block" type="button" onclick="displayMap()">Show Map</button>
                </div>
                
                <div id="map-canvas2" ></div>                
                <br><br>
                
                <label for="i_addr" class="control-label"><b>Διεύθυνση</b></label>
                
                    <button class="btn btn-md btn-primary btn-block" id="c_ser" onclick="codeAddress()" type="button" style="max-width:20%; float:right; margin-top: 4.7%; margin-right:13%;">
                    <span class="glyphicon glyphicon-search"></span>
                    </button>
                                
                <div class="input-group" id="i_faddr">
                    <input style="width:300px" type="text" class="form-control" name="c_addr" id="i_addr" required placeholder="Διευθυνση..">
                </div>
                
                <label><b>Ονομασία Δημοπρασίας</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" type="text" name="Name" autocomplete="off" required placeholder="Σύντομη"/>
                </div>
                <label><b>Κατηγορίες</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" type="text" name="Categories" autocomplete="off" required placeholder="use character | for multiple categories"/>
                </div>
                <label><b>Τιμή Άμεσης Αγοράς</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" type="number" name="BuyNowPrice" autocomplete="off" required placeholder="Βάλτε 0 αν δεν επιθυμείτε κάποια τιμή"/>
                </div>
                <label><b>Τιμή Ελάχιστης Προσφοράς</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" type="number" name="StartingBid" autocomplete="off" required placeholder="starting bid"/>
                </div>
                <label><b>Ημερομηνία Έναξης Δημοπρασίας</b></label>
                <div class="input-group">
                    <%
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date date = new Date();                        
                    %>
                    <input style="width:300px" class="form-control" id="current_date" step="1" type="datetime-local" name="Starts" autocomplete="off"  required value=<%out.println(dateFormat.format(date));%> />
                </div>
                <label><b>Ημερομηνίας Λήξης Δημοπρασίας</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" id="current_date" step="1" type="datetime-local" name="Ends" autocomplete="off" required value=<%out.println(dateFormat.format(date));%> />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="back_button">Άκυρο</button>
                    <button type="button" class="btn btn-primary" id="upload_button">Ανέβασμα Δημοπρασίας</button>
                </div>
            </form>  
        </div>

        </div>
        </div>        
        
<!--################################################################################################################## -->
<!--################################################################################################################## -->
<!--################################################################################################################## -->

    </body>
    <script type="text/javascript">
        //https://jsfiddle.net/wvbdnb8o/        
       /* function detectBrowser(){
            var isOpera = !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
                // Opera 8.0+ (UA detection to detect Blink/v8-powered Opera)
            var isFirefox = typeof InstallTrigger !== 'undefined';   // Firefox 1.0+
            var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
                // At least Safari 3+: "[object HTMLElementConstructor]"
            var isChrome = !!window.chrome && !isOpera;              // Chrome 1+
            var isIE = false || !!document.documentMode;   // At least IE6
            
            var now = new Date();
            if (isOpera==true)
                document.getElementById("current_date").value= now.format("dd/M/yy h:mm tt");
            else if (isFirefox)
                document.getElementById("current_date").value= now.format("dd/M/yy h:mm tt");
            else if (isSafari)
                document.getElementById("current_date").value= now.format("dd/M/yy h:mm tt");
            else if(isChrome)
                document.getElementById("current_date").value= now.format("dd/M/yy h:mm tt");

        }      
        */
        function validateForm(){
            var a=document.forms["Form"]["c_lon"].value;
            var b=document.forms["Form"]["c_lat"].value;
            var c=document.forms["Form"]["c_addr"].value;
            var d=document.forms["Form"]["Starts"].value;
            var e=document.forms["Form"]["Ends"].value;
            var f=document.forms["Form"]["BuyNowPrice"].value;
            var g=document.forms["Form"]["StartingBid"].value;
            var h=document.forms["Form"]["Name"].value;
            var i=document.forms["Form"]["Categories"].value;
            if (a==null || a=="" || b==null || b=="" || c==null || c=="" || d==null 
            || d=="" || e==null || e=="" || f==null || f=="" || f==null || f=="" || 
            g==null || g=="" || h==null || h=="" || i==null || i=="")
            {
                alert("Please Fill All Required Field");
                return false;
            }
            else
                return true;
        }
        $("#back_button").click(function(){
           if($("#back_button").text()=="Τέλος"){
               location.reload();
           }
        });        
        $("#upload_button").click(function(){            
            if(validateForm()==true){
                $.ajax({
                   type:"POST",
                   url: "/TED/newItem",
                   data: $("#form_auction").serialize(),
                   statusCode: {
                       200 : function(){
                           $("#finished_label").html("Η ανάρτηση της δημοπρασίας ολοκληρώθηκε");
                           $("#upload_button").hide();
                           $("#back_button").html("Τέλος");
                           $("#form_auction").hide();
                           $("#finished").show();
                       },
                       400: function(){
                           $("#finished_label").html("Υπήρξε σφάλμα κατά την ανάρτηση της δημοπρασίας σας!");
                           $("#upload_button").hide();
                           $("#back_button").html("Τέλος");
                           $("form_auction").hide();
                           $("#finished").show();
                       }
                   },
                   success: function(xhr){

                   },
                   error:function(data){
                      alert(data.toSource()); 
                   }

                });
            }
        });    
    </script>>
</html>


