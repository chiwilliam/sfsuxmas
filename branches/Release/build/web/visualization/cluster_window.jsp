<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/window_styling.css" />
        
    </head>
    
    <body>
        
        <%
            int divCount = 0;
            String divCountString = request.getParameter("divID");
            if (divCountString != null) {
                divCount = Integer.parseInt(divCountString);
            }

            int nodeID = 0;
            String nodeIDString = request.getParameter("nodeID");
            if (nodeIDString != null) {
                nodeID = Integer.parseInt(nodeIDString);
            }
        %>
        
        

        <div class="window_tabs">
            <ul>
                <li id="windowTab1"><a href="javascript:void(0)" onclick="return toggleWindowContent(1, <%= divCount %>, <%= nodeID %>)">Summary</a></li>
                <li id="windowTab2"><a href="javascript:void(0)" onclick="return toggleWindowContent(2, <%= divCount %>, <%= nodeID %>)">Probes</a></li>
                <li id="windowTab3"><a href="javascript:void(0)" onclick="return toggleWindowContent(3, <%= divCount %>, <%= nodeID %>)">Pathways</a></li>
            </ul>
        </div>
        
        <iframe class="ajax_window" id="windowContent<%= divCount %>" src="../SProbeAccessor?basic&nodeID=<%= nodeID %>"></iframe>
        
    </body>
    
</html>
