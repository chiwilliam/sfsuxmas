<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.database.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.visualizations.*"%>

<%@page import="database_management.*"%>
<%@page import="xml.*"%>
<%@page import="filter.*"%>
<%@page import="filter.managers.*"%>

<div class="sidebar_header">Trajectory Shape Filter</div>

<div class="sidebar_padding">
    
    <p>
        <%
            FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));
            if (fl != null) {
                String ess = "";
                String isAre = "are";
                if (fl.size() != 1) {
                    ess = "s";
                } else {
                    isAre = "is";
                }
        %>
        There <%=isAre%> currently <b><%= fl.size()%></b> filter<%= ess%> applied to the data.
        <%
            }
        %>
    </p>
    
    <form action="../SFilterManipulator?filterType=0&subtractive" method="POST" id="trajFilterForm" TARGET="_parent">
        
        <%
            int[][] existingFilters = null;
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
            ExpressionDataSet db = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            if (db != null) {
                int maxBin = td.getMaximumSubtractiveDegree();
                int minBin = td.getMinimumSubtractiveDegree();
                int numberOfTimePeriods = db.getNumberOfTimePeriods();

                FilterList trajFl = fl.getTrajectoryShapeFilters();
                for (int i = 0; i < trajFl.size(); i++) {
                    IFilter f = (IFilter) trajFl.get(i);
                    if (f instanceof TrajectoryShapeFilter) {
                        TrajectoryShapeFilter filter = (TrajectoryShapeFilter) f;
                        int[][] shapeFromFilt = filter.getShape();
                        if (existingFilters == null) {
                            existingFilters = shapeFromFilt;
                        } else {
                            for (int tps = 0; tps < shapeFromFilt.length; tps++) {
                                int[] timePeriod = shapeFromFilt[tps];
                                for (int exps = 0; exps < timePeriod.length; exps++) {
                                    if (existingFilters[tps][exps] != 1) {
                                        existingFilters[tps][exps] = timePeriod[exps];
                                    }
                                }
                            }
                        }
                    }
                }
        %>
        <div style="overflow-x: scroll;">
            <table style="clear: both; width: 100%; border-collapse:collapse; font-size:11px;">
                <thead>
                    <tr>
                        <th style="width: 10%;" />
                        <%
            for (int i = 0; i < numberOfTimePeriods; i++) {
                int width = (int) Math.round(90.0 / numberOfTimePeriods);
                        %>
                        <th style="width: <%= width + "%" %>;"><%= i + 1%></th>
                        <%
            }
                        %>
                    </tr>
                </thead>
                <tbody>
                    <%
            for (int i = maxBin; i >= minBin; i--) {
                String rowStyle = "";
                String op = "";

                String rowColor = "#" + ColorManager.getHexColor(i, minBin, maxBin);

                rowStyle = "background-color: " + rowColor + "; text-align: right; padding-right: 5px; color: white; ";

                if (i > 0) {
                    op = "+";
                }
                    %>
                    <tr>
                        <th style="<%= rowStyle + " font-family: courier new; border: 1px solid gray;"%>"><%= op%><%= i * td.getBinUnit()%></th>
                        <%
                        for (int j = 1; j <= numberOfTimePeriods; j++) {
                            String checked = "";
                            String highlighted = "";
                            String dis = "";
                            if (existingFilters != null && existingFilters[j - 1][i - minBin] == 1) {
                                checked = "checked";
                                highlighted = "background-color: " + rowColor + ";";
                            //dis = "DISABLED";
                            }
                        %>
                        <td id="traj_filter_<%= i%>_<%= j%>" style="<%= highlighted%> border: 1px solid gray;">
                            <table style="padding: 0; margin: 0;" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td rowspan="2">
                                    <input type="checkbox" name="traj_check_<%= i%>_<%= j%>" id="traj_check_<%= i%>_<%= j%>" onclick="return updateQuantityOfTrajectories(<%= minBin%>, <%= maxBin%>, <%= numberOfTimePeriods%>);" <%= checked%> <%= dis%> />
                                           </td>
                                    <td>
                                        <span class="trajcount" id="traj_span_<%= i%>_<%= j%>">&nbsp;</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="trajcountmax" id="traj_span_<%= i%>_<%= j%>_max">&nbsp;</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <%
                        }
                        %>
                    </tr>
                    <%
            }
                    %>
                </tbody>
            </table>
        </div>
        <p style="font-style: italic; font-size: 0.8em;">
            <b style="color: black;">Black</b>:- Number of trajectories with the characteristic defined by each cell (filtered)
            <br/>
            <b style="color: grey;">Grey</b>:- Number of trajectories with the characteristic defined by each cell (unfiltered i.e. maximum possible for dataset)
        </p>
        
        <%
            }

        %>
        
        <br />
        
        <div class="corners_bar">
            <div class="corners_top_left"></div>
            <div class="corners_top_right"></div>
            <div class="corners_connector_bar"></div>
        </div>
        <div class="sidebar_tool_area">
            <div class="sidebar_tool_area_content">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                <div class="sidebar_tool_buttons" style="text-align: center;">
                    <a href="../help/dv_trajectory_filter.jsp" 
                       class="help_button" 
                       alt="Help"
                       title="Help">
                        <span>?</span>
                    </a>
                    <INPUT TYPE="SUBMIT" VALUE="Update Filter">
                    <!-- <a href="../SFilterManipulator?removeAll" class="button"><span>Remove All Filters</span></a> -->
                </div>
                <div class="corners_bar">
                    <div class="corners_inner_bottom_left"></div>
                    <div class="corners_inner_bottom_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
            </div>
        </div>
        <div class="corners_bar">
            <div class="corners_bottom_left"></div>
            <div class="corners_bottom_right"></div>
            <div class="corners_connector_bar"></div>
        </div>
        
    </form>
</div>

<!--

<div class="sidebar_header_s">Active Filters</div>

<div class="sidebar_padding">

<%@ include file="../modules/filter_renderer.jsp" %>

</div>

-->