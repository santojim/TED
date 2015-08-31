
<%@page import="ted.SQLConnector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 

        if(session.getAttribute("seller").equals("0")){
            response.sendRedirect("/TED/notAuthorised.jsp");
        }
        int id = Integer.parseInt(request.getParameter("id"));
        
        SQLConnector sqlDB = new SQLConnector();
        int errorCode = sqlDB.DeleteItem(id);
        if(errorCode==-1){
            response.sendRedirect("/TED/sell/index.jsp?error=An error occured!");
        }else{
            response.sendRedirect("/TED/sell/index.jsp?message=Your entry has been successfully deleted!");
        }
%>