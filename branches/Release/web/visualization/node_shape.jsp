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
            
            <div class="corners_bar">
                <div class="corners_top_left"></div>
                <div class="corners_top_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
            <div class="sidebar_tool_area">
                <div class="sidebar_tool_area_top_line">
                    <h3>Trajectory Node Shape</h3>
                </div>
            </div>
            <div class="sidebar_content_holder">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                <div style="text-align: center;">
                    <img src="../SGetTrajectoryShape?nodeID=<%= nodeID%>&timePeriod=<%= timePeriod%>&width=280&height=100" />
                </div>
                <div style="padding: 5px;">
                    <p style="font-style: italic; font-size: 0.8em;">
                        <b style="color: black;">Black</b>:- mean expression profile of trajectory (or single probe if # probes = 1)<br />
                        <b style="color: red;">Red</b>:- individual probe expression profiles
                    </p>
                </div>
            </div>
        </div>
        
    </body>
    
</html>
