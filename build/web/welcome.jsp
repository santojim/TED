<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="views/header.jsp" %>
<%@include file="views/navBar.jsp"%>

<%
    if(session.getAttribute("admin").equals("1")){//diaxeiristis
        response.sendRedirect("/TED/administration/");
    }else if(session.getAttribute("seller").equals("1")){
        response.sendRedirect("/TED/sell/");
    }else if(session.getAttribute("buyer").equals("1")){
        response.sendRedirect("/TED/search/");
    }else if(session.getAttribute("visitor").equals("1")){
        response.sendRedirect("TED/search/");/*den mporei na kanei bid se dhmoprasies*/
    }
%>
