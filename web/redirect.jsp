<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        .fixed-size-square {
            display: table;
            width: 500px;
            height: 250px;
            background: rgba(0,0,0,0.6);
            margin:0 auto;
        }
        .fixed-size-square span {
            display: table-cell;
            text-align: center;
            vertical-align: middle;
            color: white
        }
    </style>
    <body style="color:white;text-align:center;">
        <div class="fixed-size-square">  
            <h1 style="padding-top:15%;">Λάθος Όνομα Χρήστη ή Κωδικός!</h1>
            <p id="red"> Ανακατεύθυνση σε : 5</p>
        </div>
    </body>
    <script type="text/javascript">
        var i=5;
            setInterval(function(){
                document.getElementById("red").innerHTML="Ανακατεύθυνση σε : "+i;
                i--;
                if(i==-1){
                    window.location="/TED/views/not_logged_in.jsp?error=Wrong username or password";
                }
            },1000);
         
    </script>

</html>
