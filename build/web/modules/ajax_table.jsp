<% String _title = "Ajax Window Table Data";%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>
        
        <title><%= _title %> :: ExperiGen &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../styles/modules.css" />
        
        <script type="text/javascript" src="../script/filter_page.js"></script>
        
    </head>
    
    <body onload="return window_onload()">
        
        <div class="ajax_table_container">
            <div class="dataTable">
                
                <%= session.getAttribute("table_data").toString() %>

            </div>
            
        </div>
        
    </body>
    
</html>
