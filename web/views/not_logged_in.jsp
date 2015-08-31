<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@include file="header.jsp"%>

<title>E-BUY</title>
<style>
    
    body{
        color:white;
    }
</style>
<%
    session.setAttribute("visitor", "1");   
%>
<div class="page-header" style="text-align:center;color:#ddd;border-bottom: none">
    <br> <h1 style="color:black;">Καλωσήλθατε στο E-BUY<br></h1>
        <h2 style="color:black;" >Είσοδος!</h2>
    </div>
<div class="container well well-lg" id="content" style="max-width:400px;background-color:rgba(0,0,0,0.6);">
    <form method="GET" class="form-signin"  action="/TED/Login">
        <div class="input-group">
        <span class="input-group-addon"><i class="fa fa-user"></i></span>
        <input id="login_input_username" class="login_input form-control" type="text" name="username" required autofocus placeholder="Όνομα χρήστη"/>
        </div>
        
        <br>
        <div class="input-group">
        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
        <input id="login_input_password" class="login_input form-control" type="password" name="password" autocomplete="off" required placeholder="Κωδικός"/>
        </div><br>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Είσοδος!"/>        
        <p style="text-align:center;"><br><a style="color: #AEBBDE" href="../register.jsp">Εγγραφή νέου μέλους</a></p>
        <p style="text-align:center;"><a style="color: #AEBBDE" href="../Login">Continue as guest</a></p>
    </form>
        
    </body>
</html>