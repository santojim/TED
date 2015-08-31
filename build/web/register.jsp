<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@include file="views/header.jsp"%>

<style>
    
    body{
        color:white;
    }
    #map-canvas{        
        height: 200px;
        max-width: 400px;
        background-color: #CCC;
        margin: 0 auto;
        border-radius: 1%;
    }     
    .input_box {
        padding-left: 3%;  
        padding-bottom: 1%;
    }

    .input_category {
        color: #8C9BA7;
        max-width: 400px;
        border-bottom: 1px solid;
        border-bottom-width:1px;
    }

    
</style>
<title>Registration | E-Buy</title>
        <script src="https://maps.googleapis.com/maps/api/js"></script>
        <script type="text/javascript" >
        var geocoder;
        var map;
        var marker;
///////////////////////////////// INITIALIZE ///////////////////////////////////        
        function initialize() {
            var mapCanvas = document.getElementById('map-canvas');
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
            google.maps.event.trigger(map , 'resize');                       
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
                    map.setCenter(results[0].geometry.location);
                    marker = new google.maps.Marker({
                        map: map,
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
<body>
        <h2 style='color: #2F3840; text-align:center;'>Φόρμα Εγγραφής</h2>
<div class="container well well-lg" id="content" style="max-width:850px;background-color:rgba(0,0,0,0.7);">
    <form method="GET" class="form-signin"  action="/TED/register">
        <div style="width: 100%; overflow: hidden;">
            <div class="input_category" style="width: 300px; float: left;"> <h3>Πληροφορίες Λογαριασμού</h3> </div>
            <div class="input_category" style="margin-left: 340px;"> <h3>Προσωπικές Πληροφορίες</h3> </div>
        </div>
        <br>
        <div class="Account_details" style="width: 300px; float: left;">
           <div class="input-group">
                <label>Όνομα Χρήστη</label>
                <input style="width:300px" id="username" class="login_input form-control" type="text" name="username" required autofocus placeholder="Επιλέξτε ένα Όνομα Χρήστη" onkeyup="checkUsername(); return false;"/>
           </div>
                <br>
                <label><b>E-Mail</b></label>
                <div class="input-group">
                    <input style="width:180px" class="login_input form-control" type="email" name="email" id="email" autocomplete="off" required placeholder="E-Mail"/>
                    <div style="float: left; padding-top: 2%;">                        
                        <input style="width:180px" class="login_input form-control" type="email" name="email2" id="email2" autocomplete="off" required placeholder="Επιβεβαίωση E-Mail" size="40" onkeyup="checkEmail(); return false;"/>
                    </div>
                    <span id="confirmMessageEmail" ></span>
                </div>
                <br>
                <label><b>Κωδικός</b></label>
                <div class="input-group">
                    <input style="width:180px" class="login_input form-control" type="password" name="pass1" id="pass1" required placeholder="Κωδικός"/>
                    <div style="float: left; padding-top: 2%">
                        <input style="width:180px;" class="login_input form-control" type="password" name="pass2" id="pass2" required placeholder="Επιβεβαίωση Κωδικού" size="40" onkeyup="checkPass(); return false;"/>
                    </div> 
                    <span id="confirmMessage" ></span>
                </div>
                <br>
                <div id="map-canvas"></div>
        </div>
        <div class="Personal_details" style="margin-left: 340px;">
            <label><b>Όνομα</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" type="text" name="fname" autocomplete="off" required placeholder="Όνομα"/>
            </div>

            <label><b>Επίθετο</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" type="text" name="lname" autocomplete="off" required placeholder="Επίθετο"/>
            </div>
            <label><b>Τηλέφωνο</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" type="text" name="phone" autocomplete="off" required placeholder="Τηλέφωνο"/>
            </div>
            <label><b>Αριθμός Φορολογικού Μητρώου</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" type="text" name="afm" autocomplete="off" required placeholder="ΑΦΜ"/>
            </div>              
            <label><b>Διεύθυνση</b></label>
            <button class="btn btn-md btn-primary btn-block" id="c_ser" onclick="codeAddress()" type="button" style="max-width:20%; float:right; margin-top: 5.5%; margin-right:13%;">
            <span class="glyphicon glyphicon-search"></span>
            </button>            
            <div class="input-group " id="i_faddr">
                <input style="width:300px" class="login_input form-control" id="i_addr" type="text" name="address" autocomplete="off" required placeholder="Διεύθυνση"/>
            </div>          
            <label><b>Latitude</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" id="i_lat" type="number" name="latitude" autocomplete="off" required readonly placeholder="Γεωγραφικό Μήκος"/>
            </div>
            <label><b>Longitude</b></label>
            <div class="input-group">
                <input style="width:300px" class="login_input form-control" id="i_lon" type="number" name="longitude" autocomplete="off" required readonly placeholder="Γεωγραφικό πλάτος"/>
            </div>            
            
            
            
            <div class="Role">
                <br>
                <label>Επιθυμείτε την περιήγηση στην ιστοσελίδα ως:</label> <br>
                <input style="margin: 0px 20px 0px 20px;" type="checkbox" name="seller" id="Politis" value="Seller">
                <label style="margin: 0px 30px 0px 0px;" for="Seller">Πωλητής</label><br>
                <input style="margin: 0px 20px 0px 20px;" type="checkbox" name="buyer" id="Agorastis" value="Buyer">
                <label style="margin: 0px 30px 0px 0px;" for="Buyer">Αγοραστής</label><br>
            </div>
        </div>        
        <br><br>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Εγγραφή!"/>
    </form>
    <%@ page import="java.sql.Connection  ,java.sql.DriverManager  ,java.sql.PreparedStatement  ,java.sql.ResultSet  ,java.sql.SQLException  ,java.util.ArrayList   ,java.util.logging.Level  ,java.util.logging.Logger" %>
    <script>
        function checkPass()
        {
            //Store the password field objects into variables ...
            var pass1 = document.getElementById('pass1');
            var pass2 = document.getElementById('pass2');
            //Store the Confimation Message Object ...
            var message = document.getElementById('confirmMessage');
            //Set the colors we will be using ...
            var goodColor = "#66cc66";
            var badColor = "#ff6666";
            //Compare the values in the password field 
            //and the confirmation field
            if(pass1.value === pass2.value){
                //The passwords match. 
                //Set the color to the good color and inform
                //the user that they have entered the correct password 
                pass2.style.backgroundColor = goodColor;
                message.style.color = goodColor;
                message.innerHTML = "Passwords Match!";
            }else{
                //The passwords do not match.
                //Set the color to the bad color and
                //notify the user.
                pass2.style.backgroundColor = badColor;
                message.style.color = badColor;
                message.innerHTML = "Passwords Do Not Match!";
            }
        }
    </script>
    
   
    <script>
        function checkEmail()
        {
            var email = document.getElementById('email');
            var email2 = document.getElementById('email2');
            var message = document.getElementById('confirmMessageEmail');
            var goodColor = "#66cc66";
            var badColor = "#ff6666";
            if(email.value === email2.value){
                email2.style.backgroundColor = goodColor;
                message.style.color = goodColor;
                message.innerHTML = "E-Mail Match!";
            }else{
                email2.style.backgroundColor = badColor;
                message.style.color = badColor;
                message.innerHTML = "E-Mail Do Not Match!";
            }
        }
    </script>
    
        
        
</body>
</html>