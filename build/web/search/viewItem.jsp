<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="ted.Photo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ted.SQLConnector"%>
<%@page import="ted.Item"%>
<%@page import="ted.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Προβολή αντικειμένου | E-Buy </title>
        <%  int visitor = 0;
            if(session.getAttribute("visitor").equals("1")){
                visitor = 1;
            }else if(session.getAttribute("buyer").equals("0")){
                 response.sendRedirect("/TED/notAuthorised.jsp");
            }
            int id = Integer.parseInt(request.getParameter("id"));
            Item newItem;
            SQLConnector sqlDB = new SQLConnector();
            newItem = sqlDB.bringItemById(id);
        %>
        <link rel='stylesheet' type='text/css' href='/ted_ergasia/css/general.css'/>
        <style>
            ul{
                list-style: none;
                
            }
            li{
                list-style: none;
            }
             #map-canvas { 
                height: 300px;
                width:100%;
            }
            input,select,option{
                color:black;
            }
            #myModal{
                color:black;
            }
            .tab-content{
                display: table;
                float: left;
                width: 450px;
                height: 250px;  
            }
            .tab{
                margin: 4% 4% 4% 4% ;
            }
            input{
                text-align: center;
                border-collapse:collapse;
                border-radius: 4em;
                font-size: 17px;
            }
            textarea{
                border-collapse:collapse;
                font-size: 17px;                
            }
        </style>
        <script type="text/javascript"
             src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC3IuwqgqifgfDg3zDbET_EZ1A5s8mOYJg">
        </script>
<!-- http://stackoverflow.com/questions/9335140/how-to-countdown-to-a-date -->
<script>
    CountDownTimer('<%out.print(newItem.getEnded());%>', 'countdown');
    function CountDownTimer(dt, id)
    {
        var end = new Date(dt);

        var _second = 1000;
        var _minute = _second * 60;
        var _hour = _minute * 60;
        var _day = _hour * 24;
        var timer;

        function showRemaining() {
            var now = new Date();
            var distance = end - now;
            if (distance < 0) {
                clearInterval(timer);
                document.getElementById(id).innerHTML = 'Auction Ended!';
                return;
            }
            var days = Math.floor(distance / _day);
            var hours = Math.floor((distance % _day) / _hour);
            var minutes = Math.floor((distance % _hour) / _minute);
            var seconds = Math.floor((distance % _minute) / _second);
            document.getElementById(id).innerHTML = 'TIME LEFT : ';
            document.getElementById(id).innerHTML += days + ' days ';
            document.getElementById(id).innerHTML += hours + ' hrs ';
            document.getElementById(id).innerHTML += minutes + ' mins ';
            document.getElementById(id).innerHTML += seconds + ' secs';
        }

        timer = setInterval(showRemaining, 1000);
    }    
</script>

        <script type="text/javascript" src="../js/jquery.form.js"></script>
        <script type="text/javascript">
            var map;
            function initialize() {

                var mapOptions = {
                    zoom: 12,
                    center: new google.maps.LatLng(<%out.print(newItem.getLatitude()+","+newItem.getLongitude());%>),
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    draggable: true,
                    zoomControl: true,
                    scrollwheel: true,
                    streetViewControl: false,
                    disableDoubleClickZoom: true
                };
                map = new google.maps.Map(document.getElementById("map-canvas"),mapOptions);       
                var marker = new google.maps.Marker({
                position: new google.maps.LatLng(<%out.print(newItem.getLatitude()+","+newItem.getLongitude()); %>),map: map});
                google.maps.event.trigger(map , 'resize');
            }
            google.maps.event.addDomListener(window, 'load', initialize);     
            </script>
    </head>
    <br>
    <body style="text-align:center;">
        <div class="container well well-lg" id="content_perfect" style="float: left; margin-left: 3%; max-width:50%;background-color:rgba(0,0,0,0.7);color:white;">
        <div>
        <div id="map-canvas"/>
        </div>
        <br>              
        <span style="margin-left:-15%; "id="countdown" class="timer" ></span>
        <br>       
        <%  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datenow = new Date(); 
            Date date_ends = dateFormat.parse(newItem.getEnded());
            //if item has buy_price , when buy it now date changes and button dissapears
            if( (visitor==0 && !newItem.getBuyPrice().equals("0") && date_ends.compareTo(datenow)>0  ) ){
            out.println("<form method='get' action='/TED/Bid'>");
            out.println("<input name='item_id' value="+ newItem.getItemId()+ " hidden>");
            out.println("<input name='user_id' value="+session.getAttribute("id")+" hidden>" );
            out.println("<div class='form-group'>");
            out.println("<label class='col-sm-10'>Αγορά</label>");
            out.println("<br>");
            out.println("<div class='col-sm-10'>");
            out.println("<input readonly style='width:40%;margin-left:20%;' type='number' name='buy_price' value=" + newItem.getBuyPrice() +" placeholder='buy now price'>");
            out.println("<button type='submit' style='margin-left:3%;' class='btn btn-primary ' >Submit Buy</button>");          
            out.println("</div>");                
            out.println("</div>");                
            out.println("</form>");
            }
        if(visitor==0 && date_ends.compareTo(datenow)>0){    
            out.println("<form method='get' action='/TED/Bid'>");
            out.println("<input name='item_id' value= "+ newItem.getItemId() +" hidden>");
            out.println("<input name='user_id' value="+ session.getAttribute("id") +" hidden>");
            out.println("<div class='form-group'>");
            out.println("<label class='col-sm-10'>Δώσε Προσφορά</label>");
            out.println("<br>");
            out.println("<div class='col-sm-10'>");
            out.println("<input required style='width:40%;margin-left:20%;' type='number' name='bid' placeholder='Enter your max bid'>");
            out.println("<button type='submit' style='margin-left: 3%;' class='btn btn-primary '>Submit Bid</button>");
            out.println("</div>");
            out.println("</div>");
            out.println("</form>");
        }
        %>    
        <br><br><br>
          <hr/><br>
          <form>
            <input name="id" value="<%out.print(newItem.getItemId());%>" hidden>
            <div class='form-group first_one'>
                <label class="col-sm-10">Ονομασία Δημοπρασίας:</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="name" value="<%out.print(newItem.getName());%>" placeholder='Ονομασία'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Username Πωλητή :</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="seller_name" value="<%out.print(newItem.getSellerName());%>" placeholder='buy now price'>
                </div>
            </div>
            <br>            
            <div class='form-group'>
                <label class="col-sm-10">Τιμή Άμεσης Αγοράς:</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="buy_price" value="<%out.print(newItem.getBuyPrice());%>" placeholder='buy now price'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Τιμή Ελάχιστης Προσφοράς:</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="first_bid" value="<%out.print(newItem.getFirstBid());%>" placeholder='Ελάχιστη τιμή'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Περιοχή-Χώρα</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="country" value="<%out.print(newItem.getCountry());%>" placeholder='country'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Κατηγορίες</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="categories" value="<%for(int i=0;i<newItem.getCategory().size();i++){out.print(newItem.getCategory().get(i)); out.println(" | ");} %>" placeholder='categories'>
                </div>
            </div>
            <br>            
            <div class='form-group'>
                <label class="col-sm-10">Ημερομηνία έναρξης</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" type="text" name="started" value="<%out.print(newItem.getStarted());%>" placeholder='starts'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Ημερομηνία λήξης</label>
                <br>
                <div class="col-sm-10">
                    <input readonly style="width:300px" ype="text" name="ended" value="<%out.print(newItem.getEnded());%>" placeholder='ends'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Περιγραφή</label>
                <br>
                <div class="col-sm-10">
                <textarea readonly name="description" style="width:90%; color:black; " rows="5" cols="50"><%if(newItem.getDescription()!=null){out.print(newItem.getDescription());}%></textarea>
                </div>
            </div>
            <br>
          </form>
          
        </div>
        </div>               
                                            
        <div class="container well well-lg" id="content_perfect" style="float:right; margin-right:3%; max-width: 40%; background-color:rgba(0,0,0,0.7);">          
        <center>
        <br><br>
        <div  style=" list-style:none; overflow:auto; width:90%; height:1050px;">

                <%
                    ArrayList<Photo> photos = new ArrayList<Photo>();
                    photos = sqlDB.bringPhotosById(newItem.getItemId());
                    Photo tempPhoto = new Photo();
                    int y=0;
                    for(y=0;y<photos.size();y++){
                        tempPhoto = photos.get(y);

                        if(y==0 || y%3==0){
                            out.println("<div class='row'>");
                        }
                        //out.println("<div class='col-sm-4 col-md-3'>");
                        out.println("<div class='thumbnail'>");
                        out.println("<img src='"+tempPhoto.getPath()+"' class='img-thumbnail' alt='...' width='90%' height='10%'> ");
                        out.println("<div class='caption'>");
                        out.println("</p>");
                        out.println("</div>");
                        out.println("</div><br>");
                        //out.println("</div>");

                        if(y==0 || y%3==0){
                           out.println("</div>");
                        }          
                    }
                    if(y==0){
                        out.println("<h3 style='margin-left:10%; color:white;'>No available photos </h3>");
                    }
                 %>
        </div>
        </div>
                                            
                                            
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
        function dismiss(element){
            $(element).parent().remove();
        }
        
        $("#plus_sign").click(function(){
            $("#photos").append("<li><a href='javascript:dismiss(this)' onclick='dismiss(this);' style='float:left;'><img src='../img/x_sign.png' width='20' height='20'></a><input name='file' type='file' id='file'></li>");
        });
        
        $("#upload_button").click(function(){
            
            var formData = new FormData($('#form_photos')[0]);
                $.ajax({
                   type:"POST",
                   url: "/TED/photoUpload",
                   data: formData,
                   async : false,
                   contentType: false,
                   cache: false,
                   processData: false,
                   success: function(xhr){
                           $("#finished_label").html("Η ανάρτηση των φωτογραφιών σας ολοκληρώθηκε με επιτυχία!");
                           $("#upload_button").hide();
                           $("#back_button").html("Τέλος");
                           $("#form_photos").hide();
                           $("#finished").show();
                
                   },
                   error:function(data){
                       alert("There's an error");
                   }

                });
                
            });
        
        $("#back_button").click(function(){
           if($("#back_button").text()=="Τέλος"){
               location.reload();
           }
        });
    </script>
</html>

