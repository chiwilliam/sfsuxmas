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
                    <h3>Trajectory node operators</h3>
                </div>
            </div>
            <div class="sidebar_content_holder">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                
                <div style="padding: 30px;">
                    
                    <a href="../SFilterManipulator?filterType=1&nodeID=<%= nodeID%>&timePeriod=<%= timePeriod%>" 
                       target="_parent" 
                       class="button" 
                       id="button_preserved"
                       alt="Focus analysis on this node, and its member probes"
                       title="Focus analysis on this node, and its member probes"><span>Isolate</span></a>
                    <a href="../SFilterManipulator?filterType=1&nodeID=<%= nodeID%>&timePeriod=<%= timePeriod%>&exclude" 
                       target="_parent" 
                       class="button" 
                       id="button_collapsed"
                       alt="Remove this node, and its member probes, from analysis"
                       title="Focus analysis on this node, and its member probes"><span>Exclude</span></a>
                    <a href="../SFilterManipulator?filterType=6&nodeID=<%= nodeID%>&timePeriod=<%= timePeriod%>" 
                       target="_parent" 
                       class="button" 
                       id="button_collapsed"
                       alt="Find trajectories (and probes) which share similar characteristics to this node"
                       title="Find trajectories (and probes) which share similar characteristics to this node"><span>Find Similar</span></a>
                    
                </div>
                
            </div>
        </div>
    </body>
</html>
