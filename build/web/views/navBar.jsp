<%@page import="ted.Message"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ted.SQLConnector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/TED/index.jsp"><img src="/TED/img/icon.png" width="30" height="30" style="margin-bottom: -8px;padding-left:2px;"></a>
        </div>
        <div class="navbar-collapse collapse"  >
            <ul class="nav navbar-nav">
                
               <%
                int hvisitor =0;   
                ArrayList<Message> hm_list = new ArrayList<Message>();
                if(session.getAttribute("visitor").equals("1"))
                    hvisitor = 1 ;
                else{
                    if(session.getAttribute("admin").equals("1")){
                        out.print("<li> <a href='/TED/administration/'>Διαχειριστής</a></li>");
                    }
                    Integer hu_id = Integer.parseInt(session.getAttribute("id").toString());
                    SQLConnector h_sqlDB = new SQLConnector();
                    
                    hm_list=h_sqlDB.BringUnreadMessages(hu_id);
                }
               
                if(hvisitor == 0 ){                
                    out.print("<li> <a href='/TED/search/'>Αναζήτηση δημοπρασίας</a></li>");
                    out.print("<li> <a href='/TED/sell/'>Ανάρτηση δημοπρασίας</a></li>");
                }else
                    out.print("<li> <a href='/TED/search/guest.jsp'>Αναζήτηση δημοπρασίας</a></li>");
               %>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <%  if(hm_list.size()>0){
                    out.print("<li><a style='margin: -10% 0 -20% 0 ;'href='/TED/messages/incoming.jsp'><img src='/TED/img/notification.png' width='35' height='35'></a></li>");
                }
                if(hvisitor == 0){
                out.print("<li> <a href='/TED/messages/index.jsp'>Μηνύματα</a></li>");
                out.print("<li> <a href='/TED/settings.jsp'>Ρυθμίσεις</a></li>");
                out.print("<li> <a href='/TED/logout.jsp'>Αποσύνδεση</a></li>");
                }
            %>
            </ul>
        </div>
    </div>
</div>