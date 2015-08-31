<%@page import="ted.SQLConnector"%>
<%
    
    if(session.getAttribute("seller").equals("0")){
        response.sendRedirect("/TED/notAuthorised.jsp");
    }
    
    String fid = request.getParameter("fid");
    String itemid = request.getParameter("itemid");
    SQLConnector sqlDB = new SQLConnector();
    int errorCode = sqlDB.DeletePhoto(fid);
    if(errorCode==-1){
        response.sendRedirect("/TED/sell/viewItem.jsp?id="+itemid+"&error=Something went wrong!");                
    }else{
        response.sendRedirect("/TED/sell/viewItem.jsp?id="+itemid+"&message=Your photo has been successfully deleted!");                
    }
    
%>