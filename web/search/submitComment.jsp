<%@page import="ted.Photo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ted.User"%>
<%@page import="ted.Comment"%>
<%@page import="ted.SQLConnector"%>
<%@page import="ted.House"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!DOCTYPE html>
<html>

    <body>

        <%
                Integer errorCode;
                SQLConnector sqlDB = new SQLConnector();
                Comment newComment = new Comment(Integer.parseInt(request.getParameter("userId")), Integer.parseInt(request.getParameter("houseId")), request.getParameter("comment") );
                errorCode = sqlDB.submitComment(newComment);
        %>

        <br><br>
        <div class='alert alert-danger' style='text-align:center;'><h3 style="color:darkgreen;">Το μήνυμά σας καταχωρήθηκε!</h3></div>
        
    </body>
    
    
    <script type="text/javascript">
        var i=2;
            setInterval(function(){
                i--;
                if(i==-1){
                    window.location=document.referrer;
                }
            },1000);
         
    </script>
  
    
</html>