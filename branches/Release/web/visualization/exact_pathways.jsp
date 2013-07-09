<%@page import="data_structures.*"%>
<%@page import="xml.*"%>
<%@page import="java.util.Iterator"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Exact Summary - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
    </head>
    
    <body class="window_body">
        
        <%
            int divCount = 0;
            String divCountString = request.getParameter("divID");
            if (divCountString != null) {
                divCount = Integer.parseInt(divCountString);
            }

            String probeID = "";
            String probeIDString = request.getParameter("probeID");
            if (probeIDString != null) {
                probeID = probeIDString;
            }

            int timePeriod = 0;
            String timePeriodString = request.getParameter("timePeriod");
            if (timePeriodString != null) {
                timePeriod = Integer.parseInt(timePeriodString);
            }
            //TimePeriod tp = new TimePeriod(timePeriod);
        %>
        
        <div id="loading_container" class="loading_container" style="display: none;">
            <p>
                <b>Loading...</b>
            </p>
            <br />
            <img src="../resources/images/loading.gif" />
        </div>
        
        <div id="iframe_content">
            
            <jsp:include page="../modules/pathway_renderer.jsp">
                <jsp:param name="exact" value="true" />
                <jsp:param name="timePeriod" value="<%= timePeriod %>" />
                <jsp:param name="divCount" value="<%= divCount %>" />
                <jsp:param name="probeID" value="<%= probeID %>" />
            </jsp:include>
            
        </div>
        
    </body>
    
</html>
