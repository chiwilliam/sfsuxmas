<%@page import="data_structures.*"%>
<%@page import="xml.*"%>
<%@page import="java.util.Iterator"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Node Summary - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/nav_switcher.js"></script>
        <script type="text/javascript" src="../resources/script/form_actions.js"></script>
        
    </head>
    
    <body class="window_body">
        
        <div id="loading_container" class="loading_container" style="display: none;">
            <p>
                <b>Loading...</b>
            </p>
            <br />
            <img src="../resources/images/loading.gif" />
        </div>
        
        <div id="iframe_content">
            
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

            int timePeriod = 0;
            String timePeriodString = request.getParameter("timePeriod");
            if (timePeriodString != null) {
                timePeriod = Integer.parseInt(timePeriodString);
            }
            %>
            
            <jsp:include page="../modules/pathway_renderer.jsp">
                <jsp:param name="node" value="true" />
                <jsp:param name="nodeID" value="<%= nodeID %>" />
                <jsp:param name="timePeriod" value="<%= timePeriod %>" />
                <jsp:param name="divCount" value="<%= divCount %>" />
            </jsp:include>
            
        </div>
        
    </body>
    
</html>
