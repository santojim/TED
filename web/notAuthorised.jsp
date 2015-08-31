<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!DOCTYPE html>
<html>

    <body>

       
        <br><br>
        <div class='alert alert-danger' style='text-align:center;'><h3 style="color:darkgreen;">Ο τύπος χρήστη σας δεν σας επιτρέπει να δείτε αυτήν την σελίδα!</h3></div>
        
    </body>
    
    
    <script type="text/javascript">
        var i=3;
            setInterval(function(){
                i--;
                if(i==-1){
                    window.location="/TED/welcome.jsp";
                }
            },1000);
         
    </script>
  
    
</html>
