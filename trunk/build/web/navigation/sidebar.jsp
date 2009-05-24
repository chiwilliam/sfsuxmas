<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="xml.*"%>

<style type="text/css">
    @import"../navigation/nav-h.css";
</style>

<ul id="navmenu-h">
    <li id="sidebarTab_summary" class="button">
        <a href="javascript: void(0);" onclick="make_nav_request('../modules/summary.jsp');" 
           alt="View a summary of the current analysis" 
           title="View a summary of the current analysis">Summary</a>
    </li>
    <li><a href="#" 
               alt="Inspect components of analysis" 
               title="Inspect components of analysis">Data</a>
        <ul>
            <li id="sidebarTab_probes" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/probe_list.jsp');"
                   alt="View probes in the current analysis" 
                   title="View probes in the current analysis">Probes</a>
            </li>
            <li id="sidebarTab_trajectories" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/trajectories.jsp');"
                   alt="View the most volatile trajectories from the current analysis" 
                   title="View the most volatile trajectories from the current analysis">Trajectories</a>
            </li>
            <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null) {
                if (kDB.getNumberOfPathways() > 0) {
            %>
            <li id="sidebarTab_pathways" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/pathway_list.jsp');"
                   alt="View Pathways that are represented in the current analysis" 
                   title="View Pathways that are represented in the current analysis">Pathways</a>
            </li>
            <%
            }
                if (kDB.getNumberOfLabels() > 0) {
            %>
            <li id="sidebarTab_labels" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/label_list.jsp');"
                   alt="View Labels that are represented in the current analysis" 
                   title="View Labels that are represented in the current analysis">Labels</a>
            </li>
            <% 
                }
                
                if (kDB.getNumberOfGOTerms() > 0) {
            %>
            <li id="sidebarTab_go" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/go_term_list.jsp');"
                   alt="View GO Terms that are represented in the current analysis" 
                   title="View GO Terms that are represented in the current analysis">GO Terms</a>
            </li>
            <% 
                }
            }
            %>
            <!-- <li id="sidebarTab_loaded_data" class="button"><a href="javascript: void(0);" onclick="make_nav_request('../modules/loaded_data.jsp');">Active sources</a></li> -->
        </ul>
    </li>
    <li><a href="#"
               alt="Manage and add to the filters applied to the current analysis" 
               title="Manage and add to the filters applied to the current analysis">Filters</a>
        <ul>
            <li id="sidebarTab_current_filt" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/current_filters.jsp');"
                   alt="View the filters applied to form the current analysis" 
                   title="View the filters applied to form the current analysis">Current</a>
            </li>
            <%
            TrajectoryFile sidebarTD = SessionAttributeManager.getActiveTrajectoryFile(request);
            ExpressionDataSet sidebarDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            if (sidebarDB != null && sidebarTD != null) {
                int sidebarMaxBin = sidebarTD.getMaximumSubtractiveDegree();
                int sidebarMinBin = sidebarTD.getMinimumSubtractiveDegree();
                int sidebarNumberOfTimePeriods = sidebarDB.getNumberOfTimePeriods();
            %>
            <li id="sidebarTab_trajectory" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request_traj('../modules/filter_trajectory_sub.jsp', <%= sidebarMinBin%>, <%= sidebarMaxBin%>, <%= sidebarNumberOfTimePeriods%>);"
                   alt="Create or edit the trajectory shape filer" 
                   title="Create or edit the trajectory shape filer">Traj' Shape</a>
            </li>
            <%

            }
            %>
            <li id="sidebarTab_remove_all_filt" class="button">
                <a href="../SFilterManipulator?removeAll"
                   alt="Remove all filters from the current analysis" 
                   title="Remove all filters from the current analysis">Remove All</a>
            </li>
        </ul>
    </li>
    
    <li><a href="#"
               alt="Manage the highlighted components of analysis" 
               title="Manage the highlighted components of analysis">Highlights</a>
        <ul>
            <li id="sidebarTab_current_high" class="button">
                <a href="javascript: void(0);" onclick="make_nav_request('../modules/highlights.jsp');"
                   alt="View the highlighted components of the current analysis" 
                   title="View the highlighted components of the current analysis">Current</a>
            </li>
            <li id="sidebarTab_remove_all_high" class="button">
                <a href="../SRemoveHighlights"
                   alt="Stop highlighting anything" 
                   title="Stop highlighting anything">Remove All</a>
            </li>
        </ul>
    </li>
    <li><a href="#"
               alt="Options to export the data present in the current analysis" 
               title="Options to export the data present in the current analysis">Export</a>
        <ul>
            <li id="sidebarTab_export" class="button">
                <a href="../SGetProbeSpreadsheet"
                   alt="Export a tab delimeted file containing the probes present in analysis" 
                   title="Export a tab delimeted file containing the probes present in analysis">Probes - Tab Delim'</a>
            </li>
        </ul>
    </li>
    <%
            if (sidebarDB != null && sidebarTD != null) {
    %>
    <li id="sidebarTab_capture" class="button">
        <a href="javascript: void(0);" onclick="make_nav_request('../capture/capture_analysis.jsp');"
           alt="Save the current analysis or load an existing one" 
           title="Save the current analysis or load an existing one">Capture</a>
    </li>
    <%            }
    %>
</ul>

<div id="sidebar_window">
    
</div>
