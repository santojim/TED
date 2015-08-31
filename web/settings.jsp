<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ted.User"%>
<%@include file="views/header.jsp"%>
<%@include file="views/navBar.jsp"%>
<% 
    if(session==null || session.getAttribute("logged_in")==null){
        response.sendRedirect("/TED/views/not_logged_in.jsp?error=You are not logged in!");
    }
     if(request.getParameter("error")!=null){
        out.println("<div class='alert alert-danger' style='text-align:center;'>"+request.getParameter("error")+"</div>");
    }

    if(request.getParameter("message")!=null){
        out.println("<div class='alert alert-info' style='text-align:center;'>"+request.getParameter("message")+"</div>");
    }
%>
<title>Settings</title>
<style>
    label{
        color:red;
    }
    form{
        margin-left:15%;
    }
</style>
<div class="container" id="content" style="background-color: darkslategrey; ">
    <br>
        <div class="page-header">
<!--		<h2 style="text-align:center;">Ρυθμίσεις Λογαριασμού</h2> -->
            <center>
            <input disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 26px; background-color: rgba(5,5,5,0.6);" value="Ρυθμίσεις Λογαριασμού">
            </center>
        </div>
	
	<form class="form-horizontal col-md-8" role="form" id="u_settings" method="post" action="/TED/Update">
		<div class="page-header">
			<div class="form-group">
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Username">
				<div class="col-md-8">
                                    <input disabled type="text" class="form-control"  value="<%= session.getAttribute("username")%>">
				</div>
			</div>
                        <div class="form-group">
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="E-Mail">
				<div class="col-md-8">
					<input type="e-mail" class="form-control" name="u_new_email" id="u_new_email" value="<%= session.getAttribute("email")%>">
				</div>
			</div>
                        <%if(session.getAttribute("seller").equals("1")){
                        out.println("<div class='form-group'>");
                        out.println("<input class='col-md-4 control-label' disabled style='text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);' value='Rating-seller'>");
                        out.println("<div class='col-md-8'>");
                        out.println("<input disabled type='text' class='form-control' value=' "+ session.getAttribute("s_rating") +"'>");
                        out.println("</div>");
                        out.println("</div>");
                        }
                        if(session.getAttribute("buyer").equals("1")){
                        out.println("<div class='form-group'>");
                        out.println("<input class='col-md-4 control-label' disabled style='text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);' value='Rating-buyer'>");
                        out.println("<div class='col-md-8'>");
                        out.println("<input disabled type='text' class='form-control' value=' "+ session.getAttribute("b_rating") +"'>");
                        out.println("</div>");
                        out.println("</div>");
                        }
                        %>         
                        <div class="form-group">
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Όνομα">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_fname" id="fname" value="<%= session.getAttribute("fname")%>">
				</div>
			</div>
			<div class="form-group">
<!--				<label for="lname" class="col-md-4 control-label">Επίθετο</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Επίθετο">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_lname" id="lname" value="<%= session.getAttribute("lname")%>">
				</div>
			</div> 
			<div class="form-group">
<!--				<label for="address" class="col-md-4 control-label">Διεύθυνση</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Διεύθυνση">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_address" id="lname" value="<%= session.getAttribute("address")%>">
				</div>
			</div>                                
			<div class="form-group">
<!--				<label for="afm" class="col-md-4 control-label">ΑΦΜ</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="ΑΦΜ">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_afm" id="lname" value="<%= session.getAttribute("afm")%>">
				</div>
			</div>
			<div class="form-group">
<!--				<label for="latitude" class="col-md-4 control-label">Γεωγραφικό Πλάτος</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Γεωγραφικό Πλάτος">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_latitude" id="lname" value="<%= session.getAttribute("latitude")%>">
				</div>
			</div>
			<div class="form-group">
<!--				<label for="longitude" class="col-md-4 control-label">Γεωγραφικό Μήκος</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Γεωγραφικό Μήκος">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_longitude" id="lname" value="<%= session.getAttribute("longitude")%>">
				</div>
			</div>
			<div class="form-group">
<!--				<label for="phone" class="col-md-4 control-label">Τηλέφωνο</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Τηλέφωνο">
				<div class="col-md-8">
					<input type="text" class="form-control" name="new_phone" id="phone" value="<%= session.getAttribute("phone")%>">
				</div>
			</div>
			
			<div class="form-group">
<!--				<label for="u_new_pswd" class="col-md-4 control-label">Νέος κωδικός</label>-->
                                <input class="col-md-4 control-label" disabled style="text-align: center; color: #FFFFFF; font-family: sans-serif;  font-size: 16px; background-color: rgba(5,5,5,0.6);" value="Νέος Κωδικός">
				<div class="col-md-8">
					<input type="password" class="form-control" name="u_new_pswd" id="u_new_pswd" placeholder="Νέος κωδικός"/>
					<br>
					<input type="password" class="form-control" name="u_new_pswd_rpt" id="u_new_pswd_rpt" placeholder="Επανάληψη νέου κωδικού" size="40" onkeyup="checkPass(); return false;"/>
				</div>
                        </div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<input type="password" class="form-control input-lg" name="u_old_pswd" id="u_old_pswd" required placeholder="Τρέχον κωδικός">
			</div>
			<div class="col-md-8">
				<button class="btn btn-lg btn-primary btn-block" name="save_changes" type="submit">
					Αποθήκευση αλλαγών!
				</button>
                            <br>
			</div>
		</div>
	</form>
                           
</div><!--/.container-->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>


    
    <script>
        function checkPass()
        {
            //Store the password field objects into variables ...
            var u_new_pswd = document.getElementById('u_new_pswd');
            var u_new_pswd_rpt = document.getElementById('u_new_pswd_rpt');
            //Store the Confimation Message Object ...
            var message = document.getElementById('confirmMessage');
            //Set the colors we will be using ...
            var goodColor = "#66cc66";
            var badColor = "#ff6666";
            //Compare the values in the password field 
            //and the confirmation field
            if(u_new_pswd.value === u_new_pswd_rpt.value){
                //The passwords match. 
                //Set the color to the good color and inform
                //the user that they have entered the correct password 
                u_new_pswd_rpt.style.backgroundColor = goodColor;
                message.style.color = goodColor;
                message.innerHTML = "Passwords Match!";
            }else{
                //The passwords do not match.
                //Set the color to the bad color and
                //notify the user.
                u_new_pswd_rpt.style.backgroundColor = badColor;
                message.style.color = badColor;
                message.innerHTML = "Passwords Do Not Match!";
            }
        }
    </script>
 
     <br>
     <br>
</body>
</html>
