<!DOCTYPE html>
<html>
    <br><br>
    <% 
    if(request.getParameter("error")!=null){
        out.println("<div class='alert alert-danger' style='text-align:center;'>"+request.getParameter("error")+"</div>");
    }

    if(request.getParameter("message")!=null){
        out.println("<div class='alert alert-info' style='text-align:center;'>"+request.getParameter("message")+"</div>");
    }
    if(!request.getServletPath().equals("/views/not_logged_in.jsp")){
        if(request.getServletPath().equals("/TED/register.jsp")){
            if (session == null || session.getAttribute("logged_in") == null) {//if NOT logged in redirect to login\
                response.sendRedirect("/TED/views/not_logged_in.jsp?error=You are not logged in!");
            }
        }        
    }

    %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" type="image/x-icon" href="/TED/img/icon.png" />
    <link href="/TED/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="/TED/css/default.css" rel="stylesheet" type="text/css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
          <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.js"></script>  
    <script type="text/javascript" src="/TED/js/bootstrap.js"></script>
    <script type="text/javascript" src="/TED/js/bootstrap.min.js"></script>
</head>
<!--
<body style="background-image:url('/TED/img/background.jpg');background-repeat: no-repeat;background-position-x: center;background-position-y:center;">    
</body>
-->
</html>