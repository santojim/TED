<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% session.invalidate();
    response.sendRedirect("/TED/views/not_logged_in.jsp?message=You have been successfully logged out!");
%>