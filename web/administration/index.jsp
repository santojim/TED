<%@page import="ted.SQLConnector"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ted.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!DOCTYPE html>
<html>
        <title>Διαχείριση | E-BUY </title>
        <%
        if(session.getAttribute("admin").equals("0")){
             response.sendRedirect("/TED/notAuthorised.jsp");
        }
        %>
        <style>
            #content_perfect{
                display: block;
                margin: 0 auto;
                text-align: center;
            }
            .admin_panel{
                margin-top:50px;
            }
            </style>
            <br><br>
    <body style="text-align:center;">
        <div class="container well well-lg" id="content_perfect" style="max-width:50%;background-color:rgba(0,0,0,0.6);">

            <h1 style="text-align: center;color:white;">Σελίδα διάχείρισης</h1>
            <a href="userslist.jsp" type="button" id="users" class="btn btn-primary btn-lg admin_panel">Λίστα χρηστών</a><br>

            
        </div>
    </body>
</html>
