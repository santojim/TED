<%@page import="ted.SQLConnector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("admin").equals("0")){
        response.sendRedirect("/TED/notAuthorised.jsp");
    }else{
        
        int id = Integer.parseInt(request.getParameter("id").toString());
        String username = request.getParameter("username").toString();
        SQLConnector sqlDB = new SQLConnector();
        int error = sqlDB.DeleteUser(username, id);
        if(error==-1){
            response.sendRedirect("userslist.jsp?error=Unexpected error!:(");
        }else{
            response.sendRedirect("userslist.jsp?message="+username+" has been successfully deleted");
        }
    
    }
    
%>