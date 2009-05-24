<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="xml.*"%>
<%@page import="org.w3c.dom.*"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Probe Operators - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <%
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

            Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(new String[]{probeID}, false);
            Probe probe = probes.get(probeID);
        %>
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
            TimePeriod tp = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getTimePeriods().get(timePeriod - 1);
            %>
            <div class="corners_bar">
                <div class="corners_top_left"></div>
                <div class="corners_top_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
            <div class="sidebar_tool_area">
                <div class="sidebar_tool_area_top_line">
                    <h3>Probe operators</h3>
                </div>
            </div>
            <div class="sidebar_content_holder">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                
                <div style="height: 60px; padding: 30px;">
                    
                    <a href="../SFilterManipulator?filterType=2&geneID=<%= probeID%>&timePeriod=<%= timePeriod%>" 
                       target="_parent" 
                       class="button" 
                       id="button_preserved"
                       alt="Focus analysis on just this probe"
                       title="Focus analysis on just this probe"><span>Isolate</span></a>
                    <a href="../SFilterManipulator?filterType=2&geneID=<%= probeID%>&timePeriod=<%= timePeriod%>&exclude" 
                       target="_parent" 
                       class="button" 
                       id="button_preserved"
                       alt="Exclude this probe from analysis"
                       title="Exclude this probe from analysis"><span>Exclude</span></a>
                    
                </div>
                
            </div>
        </div>
    </body>
</html>
