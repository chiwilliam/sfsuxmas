<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="xml.*"%>
<%@page import="org.w3c.dom.*"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Node Summary - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <%
            //SessionAttributeManager sam = new SessionAttributeManager(request.getSession());
            //int divCount = 0;
            //String divCountString = request.getParameter("divID");
            //if (divCountString != null) {
            //    divCount = Integer.parseInt(divCountString);
            //}

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

            int numberOfProbes = 0;

            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);

            NodeList trajectories = td.getNodeTrajectories(SessionAttributeManager.getSessionID(request), nodeID, timePeriod);


            TrajectoryNode trajectory = null;
            if (trajectories != null && trajectories.getLength() > 0) {
                Node n = trajectories.item(0);
                for (int i = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getNumberOfTimePeriods(); i > timePeriod; i--) {
                    n = n.getParentNode();
                }
                trajectory = new TrajectoryNode(n);
            }

            LeafNodes nodes = new LeafNodes(SessionAttributeManager.getSessionID(request), trajectories, SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getNumberOfTimePeriods());

            Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(nodes.getMatchingProbes(), false);
            numberOfProbes = probes.size();

            int numberOfUnfiltProbes = nodes.getTotalNumberOfProbes();

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
                    <h3>Trajectory Node Summary</h3>
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
                        <tr class="odd">
                            <td>Trajectory Volatility:</td>
                            <td><%= trajectory.getVolatility()%></td>
                        </tr>
                        <tr class="even">
                            <td>Trajectory Linear Trend:</td>
                            <td><%= trajectory.getTrend()%></td>
                        </tr>
                        <tr class="odd">
                            <td>Time Period:</td>
                            <td><%= timePeriod%>: "<%= tp.getDescription()%>"</td>
                        </tr>
                        <tr class="even">
                            <td>Member Probes:</td>
                            <td><%= numberOfProbes%> (<%= numberOfUnfiltProbes%> unfiltered)</td>
                        </tr>
                        <tr class="odd">
                            <td>Highlighted Probes:</td>
                            <td><%= nodes.getTotalNumberOfMatchingHighlightedProbes()%> (<%= nodes.getTotalNumberOfHighlightedProbes()%> unfiltered)</td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
        </div>
    </body>
</html>
