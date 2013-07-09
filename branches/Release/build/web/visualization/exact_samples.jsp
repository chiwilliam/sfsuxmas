<%@page import="com.sfsu.xmas.database.*"%>
<%@page import="xml.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>

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

            //Probe probe = new Probe(probeID);
            Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(new String[]{probeID}, false);
            Probe probe = probes.get(probeID);
        %>
        
        <div id="loading_container" class="loading_container" style="display: none;">
            <p>
                <b>Loading...</b>
            </p>
            <br />
            <img src="../resources/images/loading.gif" />
        </div>
        
        <div id="iframe_content">
            
            <div style="text-align: center;">
                <img src="../SGetSampleVisualization?probeID=<%= probe.getID()%>&timePeriod=<%= timePeriod%>" style="border: 0;" />
            </div>
            
        </div>
        
    </body>
    
</html>
