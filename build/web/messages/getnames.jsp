<%@page import="java.util.ArrayList"%>
<%@page import="ted.SQLConnector"%>
<%
    SQLConnector sqlDB = new SQLConnector();
    ArrayList<String> users = new ArrayList<String>();
    users = sqlDB.BringUsernames();
    for(int i=0;i<users.size();i++){
        out.println(users.get(i));
    }
%>