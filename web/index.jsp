<%
    //check if user is logged in or not!
    
        if (session == null || session.getAttribute("logged_in") == null) {//if NOT logged in redirect to login
            response.sendRedirect("views/not_logged_in.jsp");
        } else { //if he is logged in redirect to main section
            response.sendRedirect("welcome.jsp");
        }
%>