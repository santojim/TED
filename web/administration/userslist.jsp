<%@page import="ted.SQLConnector"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ted.User"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <title>User's list | E-BUY</title>
    <%
        if(session.getAttribute("admin").equals("0")){
            response.sendRedirect("/TED/notAuthorised.jsp");
        }
    %>
        <style>
            #content_perfect{
                display: block;
                margin: 0 auto;
                text-align: center;
            }
            .admin_panel{
                margin-top:50px;
            }
            tr{
                text-align:center;
            }
            th{
                text-align:center;
            }
        </style>
            <br><br>
            <script>
                function confirmation(){
                    return confirm("Are you sure you want to delete this user?");
                }
                function confirmation_user(){
                    return confirm("Are you sure you want to confirm this user?");
                }
            </script>
    <body style="text-align:center;color:white;background-color:darkslategray;">
        <div class="well well-lg" id="content_perfect" style="background-color:rgba(0,0,0,0.6); max-width: 95%;">
            <h1 style="text-align: center;color:white;">Λίστα Χρηστών</h1><br><br>
            
            <table class="table" style="color:white;">
                <thead style="display: table-header-group">
                    <tr>
                        <th>Όνομα Χρήστη</th>
                        <th>E-Μail</th>
                        <th>Όνομα</th>
                        <th>Επίθετο</th>
                        <th>Τηλέφωνο</th>
                        <th>Διεύθυνση</th>
                        <th>ΑΦΜ</th>
<!--                        <th>Γεωγραφικό πλάτος</th> -->
<!--                        <th>Γεωγραφικό μήκος</th> -->
                        <th>Rating(seller)</th>
                        <th>Rating(buyer)</th>
                        <th>Ρόλος</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    ArrayList<User> users= new ArrayList<User>();
                    User tempUser=null;
                    SQLConnector sqlDB = new SQLConnector();
                    users = sqlDB.bringAllUsers();
                    
                    for(int i=0;i<users.size();i++){
                        tempUser = users.get(i);
                        out.println("<tr>");
                        out.println("<td>"+tempUser.getUsername()+"</td>");
                        out.println("<td>"+tempUser.getEmail()+"</td>");
                        out.println("<td>"+tempUser.getFname()+"</td>");
                        out.println("<td>"+tempUser.getLname()+"</td>");
                        out.println("<td>"+tempUser.getPhone()+"</td>");
                        out.println("<td>"+tempUser.getAddress()+"</td>");
                        out.println("<td>"+tempUser.getAfm()+"</td>");
//                        out.println("<td>"+tempUser.getLatitude()+"</td>");
//                        out.println("<td>"+tempUser.getLongitude()+"</td>");
                        out.println("<td>"+tempUser.getSrating()+"</td>");
                        out.println("<td>"+tempUser.getBrating()+"</td>");
                        out.println("<td>");
                        if(tempUser.getRole().equals("s")){
                            out.println("Πωλητής ");
                        }
                        if(tempUser.getRole().equals("b")){
                            out.println("Αγοραστής ");
                        }
                        if(tempUser.getRole().equals("sb")){
                            out.println("Πωλητής/Αγοραστής");
                        }
                        if(tempUser.getRole().equals("a")){
                            out.println("Διαχειριστής ");
                        }
                        out.println("</td>");
                        if(tempUser.getConfirmed()==0){
                            out.println("<td><a href='confirmUser.jsp?username="+tempUser.getUsername()+"&id="+tempUser.getId()+"' type='button' class='btn btn-success' onclick='confirmation_user();'>Confirm</a><td>");
                        }  
                            out.println("<td><a href='deleteUser.jsp?username="+tempUser.getUsername()+"&id="+ tempUser.getId()+"' type='button' class='btn btn-danger' onclick='confirmation();'>Delete</a><td>");
                        out.println("</tr>");
                    }
                %>
                </tbody>
            </div>
    </body>
</html>
