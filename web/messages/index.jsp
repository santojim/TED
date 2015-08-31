<%@page import="ted.SQLConnector"%>
<%@page import="ted.Item"%>
<%@page import="ted.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../views/header.jsp" %>
<%@include file="../views/navBar.jsp" %>
<!-- FOR AUTOCOMPLETE  -->
<!--http://viralpatel.net/blogs/tutorial-create-autocomplete-feature-with-java-jsp-jquery/ -->
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Μηνύματα | E-Buy </title>
        <%        
        if(session.getAttribute("seller").equals("0") && session.getAttribute("buyer").equals("0") ){
             response.sendRedirect("/TED/notAuthorised.jsp");
        }
            String u_name = session.getAttribute("username").toString();
            //Integer u_id = Integer.parseInt(session.getAttribute("id").toString());
            //SQLConnector sqlDB = new SQLConnector();
            //ArrayList<Message> m_list = new ArrayList<Message>();
            //m_list=sqlDB.BringUnreadMessages(u_id);
        
        %>
        <style>
            #content_perfect{
                text-align: center;
            }
            li{
                color:black;
                list-style-type: none;
                font-size:17px;
                text-align: left;
                margin-right: 10px;                
            }
            .modal-dialog{
                display:none;
                margin-top:-10%;
            }
            button{
                background-color: black;
                color:lightslategrey;
                border-radius: 1em;
                width:150px;
            }
        </style>
    <link rel="stylesheet" type="text/css" href="../css/jquery.autocomplete.css" />
    <script type="text/javascript"
            src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <script src="../js/jquery.autocomplete.js"></script>  
       
    </head>
    <body>
        <br>
        <br>
        <br>    

<!--################################################################################################################## -->
<!--######################################## PANEL MHNYMATOS ME PATHMA KOUMPIOU ###################################### -->
<!--################################################################################################################## -->
<ul>
    <li><button id="hideshow">Νέο Μήνυμα</button></li>
    <br>
    <li><button type="button" onclick="location.href='incoming.jsp';" >Εισερχόμενα</button></li>
    <br>
    <li><button type="button" onclick="location.href='outgoing.jsp';" >Απεσταλμένα</button></li>   
</ul>        
        <div id='content' class="modal-dialog">
        <div class="modal-content">
        <div class="modal-header">
            <button type="button" id="hideshow" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Άκυρο</span></button>
            <h4 class="modal-title" id="myModalLabel" style='text-align:center;'>Νέο Μήνυμα</h4>
        </div>
        <div class="modal-body">
            <div id="finished" hidden>
                <h2 id="finished_label" style="text-align:center;">Το μήνυμα σας εστάλη</h2>
            </div>
            <form action="../sendMessage" method="GET" id="form_auction" name="Form" >
                <div hidden>
                <label><b>Από</b></label>
                <div class="input-group">
                    <input style="width:300px" class="form-control" type="text" name="From" autocomplete="off" required value="<%out.print(u_name);%>"/>
                </div>
                </div>
                <label><b>Προς</b></label>
                <div class="input-group">
                    <input style="width:300px" id="uname" class="form-control" type="text" name="To" autocomplete="off" required />
                </div>     
                
                <script>
                    $("#uname").autocomplete("getnames.jsp");
                </script>
                
                <div class='input-group'>
                    <label>Μήνυμα</label>
                    <br>
                    <div>
                    <textarea required name="mes" style="width:100%; color:black; " rows="10" cols="50"></textarea>
                    </div>
                </div>                
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="upload_button">Αποστολή Μηνύματος </button>
                </div>
            </form>  
        </div>

        </div>
        </div>
    </body>
    <script>
    jQuery(document).ready(function(){
        jQuery('#hideshow').live('click', function(event) {        
             jQuery('#content').toggle('show');
        });
    });
    </script>
</html>


