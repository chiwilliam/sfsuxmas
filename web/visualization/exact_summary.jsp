<%@page import="com.sfsu.xmas.database.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.highlight.*"%>
<%@page import="java.util.*" %>

<%@ include file="../template/file_header.jsp" %>

<script type="text/javascript" src="../resources/script/probes_and_pathways.js"></script>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Exact Summary - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <%
            //int divCount = 0;
            //String divCountString = request.getParameter("divID");
            //if (divCountString != null) {
            //    divCount = Integer.parseInt(divCountString);
            //}

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
            
            <div class="corners_bar">
                <div class="corners_top_left"></div>
                <div class="corners_top_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
            <div class="sidebar_tool_area">
                <div class="sidebar_tool_area_top_line">
                    <h3>Probe Time Point Summary</h3>
                </div>
            </div>
            <div class="sidebar_content_holder">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                
                <table class="window-large_quant_data" cellpadding="3px">
                    <thead>
                        <tr>
                            <th>
                                Data type
                            </th>
                            <th>
                                Value
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
            ArrayList<String> highIDs = HighlightManager.getUniqueInstance().getHighlightedProbes(SessionAttributeManager.getSessionID(request));
            String imgUrl = "unhigh.png";
            if (highIDs != null && highIDs.contains(probe.getID())) {
                imgUrl = "high.png";
            }
            int i = 0;
                        %>
                        <tr class="odd">
                            <td>Probe ID</td>
                            <td><%= probe.getID()%></td>
                            <td rowspan="1" class="button_star">
                                <img src="../resources/images/<%= imgUrl%>" name="probe_highlight_<%= probe.getID()%>" id="probe_button_<%= i%>" onclick="return highlightProbe('<%= probe.getID()%>', <%= i%>, '');" />
                            </td>
                        </tr>
                        
                        <tr class="even">
                            <td>Time Period</td>
                            <td><%= timePeriod%></td>
                        </tr>
                        <tr class="odd">
                            <td>Heatmap</td>
                            <td><img src="../SProbeHeatMap?probe_id=<%= probe.getID()%>&height=20&width=150" style="width: 150px; height:20px;" /></td>
                        </tr>
                        <tr class="even">
                            <td>Volatility</td>
                            <td><%= probe.getProfileTimePeriodBasedVolatility()%></td>
                        </tr>
                        <tr class="odd">
                            <td>Linear Trend</td>
                            <td><%= probe.getProfileTimePeriodBasedLinearTrend()%></td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
        
    </body>
</html>
