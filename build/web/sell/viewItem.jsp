<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
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
        <title>Προφιλ δημοπρασίας | E-Buy </title>
        <%
            if(session.getAttribute("seller").equals("0")){
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
        </style>
        <script type="text/javascript"
             src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC3IuwqgqifgfDg3zDbET_EZ1A5s8mOYJg">
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
        </div><br>
            <%  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date datenow = new Date(); 
            Date date_starts = dateFormat.parse(newItem.getStarted());
            if(date_starts.compareTo(datenow)>0 ){
                out.println("<a class='btn btn-lg btn-danger btn-block' type='button' href='deleteItem.jsp?id="+newItem.getItemId()+" ' onclick='return confirm('Είστε σίγουρος πως θέλετε να διαγράψετε αυτήν την καταχώρηση;')'>");
                out.println("Διαγραφή καταχώρησης");
                out.println("</a>");
            }
            %>        

          <br><br>
          <hr/><br>
          <!-- STARTING MODAL -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel" style='text-align:center;'>Ανάρτηση φωτογραφίας</h4>
                </div>
                    <div class="modal-body">
                    <div id="finished" hidden>
                        <h2 id="finished_label" style="text-align:center;"></h2>
                    </div>                     
                    <form action="" enctype="multipart/form-data" name="form1" id="form_photos">
                    <center>
                        <input name="ItemID" value="<%out.print(newItem.getItemId());%>" hidden>
                        <label>Ανάρτηση φωτογραφίας</label><br>
                        <a id="plus_sign"><img src="../img/Plus_sign.png" width="35" height="35"></a>
                        <ul id="photos">
                            <li><input name="file" type="file" id="file"></li>
                        </ul>
                    </center>
                    </form>  
                    </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="back_button">Άκυρο</button>
                    <button type="button" class="btn btn-primary" id="upload_button">Ανάρτηση</button>
                </div>
               </div>
            </div>
            </div>
          
          <!-- MODAL BODY FINISHED -->
          <form method="get" action="/TED/updateItem">
            <input name="id" value="<%out.print(newItem.getItemId());%>" hidden>
            <div class='form-group first_one'>
                <label class="col-sm-10">Ονομασία Δημοπρασίας:</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="name" value="<%out.print(newItem.getName());%>" placeholder='Ονομασία'>
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
                    <input style="width:300px" type="text" name="buy_price" value="<%out.print(newItem.getBuyPrice());%>" placeholder='buy now price'>
                </div>
            </div>
            <br>            
            <div class='form-group'>
                <label class="col-sm-10">Τιμή Ελάχιστης Προσφοράς:</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="first_bid" value="<%out.print(newItem.getFirstBid());%>" placeholder='Ελάχιστη τιμή'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Περιοχή-Χώρα</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="country" value="<%out.print(newItem.getCountry());%>" placeholder='country'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Κατηγορίες</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="categories" value="<%for(int i=0;i<newItem.getCategory().size();i++){out.print(newItem.getCategory().get(i)); out.println(" | ");} %>" placeholder='categories'>
                </div>
            </div>
            <br>            
            <div class='form-group'>
                <label class="col-sm-10">Ημερομηνία έναρξης</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" type="text" name="started" value="<%out.print(newItem.getStarted());%>" placeholder='starts'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Ημερομηνία λήξης</label>
                <br>
                <div class="col-sm-10">
                    <input style="width:300px" ype="text" name="ended" value="<%out.print(newItem.getEnded());%>" placeholder='ends'>
                </div>
            </div>
            <br>
            <div class='form-group'>
                <label class="col-sm-10">Περιγραφή</label>
                <br>
                <div class="col-sm-10">
                <textarea name="description" style="width:70%; color:black; " rows="5" cols="50"><%if(newItem.getDescription()!=null){out.print(newItem.getDescription());}%></textarea>
                </div>
            </div>
            <br>
            <br><br><br>
            <% 
            if(date_starts.compareTo(datenow)>0 )
                out.println( "<button class='btn btn-lg btn-primary btn-block' name='submit_changes' type='submit'>Αποθήκευση αλλαγών!</button> " );
            %>
          </form>
          
        </div>
        </div>               
                                            
        <div class="container well well-lg" id="content_perfect" style="float:right; margin-right:3%; max-width: 40%; background-color:rgba(0,0,0,0.7);">          
        <center>
        <button class="btn btn-primary btn-primary" data-toggle="modal" data-target="#myModal" id="starting_modal">Ανάρτηση φωτογραφιών</button>
        <br><br>
        <div  style="color:White; list-style:none; overflow:auto; width:450px; height:1030px;">

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
                        out.println("<p><a type='button' href='deletePhoto.jsp?fid="+tempPhoto.getId()+"&itemid="+newItem.getItemId()+"' onclick='return confirm(\"Είστε σίγουρος οτι θελέτε να διαγράψετε αυτήν την φωτογραφία;\");' class='btn btn-danger btn-sm'>Διαγραφή!</a>");
                        out.println("</p>");
                        out.println("</div>");
                        out.println("</div><br>");
                        //out.println("</div>");

                        if(y==0 || y%3==0){
                           out.println("</div>");
                        }          
                    }
                    if(y==0){
                        out.println("<h2>Δεν έχετε αναρτήσει κάποια φωτογραφία..ακόμα!</h2>");
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

