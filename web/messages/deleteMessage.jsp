<%@page import="ted.SQLConnector"%>
<%
    
        if(session.getAttribute("seller").equals("0") && session.getAttribute("buyer").equals("0") ){
             response.sendRedirect("/TED/notAuthorised.jsp");
        }
    String reDirect = request.getParameter("page");
    String mes_id = request.getParameter("id");
    SQLConnector sqlDB = new SQLConnector();
    int errorCode = sqlDB.DeleteMessage(mes_id);
    if(errorCode==-1){
        if(reDirect.equals("in"))
           response.sendRedirect("/TED/messages/incoming.jsp?&error=Something went wrong!");                
        else
           response.sendRedirect("/TED/messages/outgoing.jsp?&error=Something went wrong!");                
    }else{
        if(reDirect.equals("in"))
            response.sendRedirect("/TED/messages/incoming.jsp?&message=Message has been successfully deleted!");                
        else
            response.sendRedirect("/TED/messages/outgoing.jsp?&message=Message has been successfully deleted!");
    }
    
%>