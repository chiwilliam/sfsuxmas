<%@page import="visualization.*"%>
<%@page import="database_management.*"%>
<%@page import="xml.*"%>
<%@page import="org.w3c.dom.Document"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Cluster Visualization - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        <script type="text/javascript" src="../resources/script/probe_list.js"></script>
        <script type="text/javascript" src="../resources/script/pathway_list.js"></script>
        <script type="text/javascript" src="../resources/script/form_actions.js"></script>
        
        <script type="text/javascript" src="../resources/script/files.js"></script>
        <script type="text/javascript" src="../resources/script/filters.js"></script>
        <script type="text/javascript" src="../resources/script/utils/zapatec.js"></script>
        <script type="text/javascript" src="../resources/script/zptree/src/tree.js"></script>
        
        
        <script type="text/javascript" src="../resources/script/filter_by_trajectory.js"></script>
        
        
        
        <script type="text/javascript" src="../resources/script/cluster_functions.js"></script>
        
        
        
        <script type="text/javascript" src="../resources/script/dom-drag.js"></script>
        <script type="text/javascript" src="../resources/script/visualization_click_functions.js"></script>
        <script type="text/javascript" src="../resources/script/window_functions.js"></script>
        
    </head>
    
    <body onload="window_onload_main('visualization'); window_onload_data('cluster'); window_onload_clustering();" >
        
        <%
            // A little checking to make sure we are aware of what we're displaying
            if (!VisualizationManager.isClustered()) {
                VisualizationManager.setClustered();
            }
        %>
        
        <%@ include file="../template/top_navigation.jsp" %>
        <div id="page_body_container">
        <div id="page_body">
            
            <div id="sidebar_container">
                
                <%@ include file="../navigation/sidebar.jsp" %>
                
            </div>
            
            <div id="page_body_main">
                
                <div class="sub_nav_buttons">  
                    <a href="javascript: void(0);" onclick="image_type_switch('../SVisualizationManipulator?preserve');" class="button current"><span>Preserved</span></a>
                    <a href="javascript: void(0);" onclick="image_type_switch('../SVisualizationManipulator?collapse');" class="button"><span>Collapsed</span></a>
                </div>
                
                <h1>Visualization</h1>
                
                <%@ include file="sub_nav.jsp" %>
                
                <div id="page_body_content">
                    
                    
                    <%
            DatabaseDataAccess dda = new DatabaseDataAccess();
            boolean successfulConnection = dda.checkConnection();
            Database activeDB = DatabaseManager.getActiveDataBase();
            if (successfulConnection && activeDB != null) {
                // Finally, check if there is an xml file loaded.
                Document doc = XMLClusterFactory.getUniqueInstance().getDocument();
                if (doc != null) {
                    %>
                    
    
                    <div id="cluster_space">
                        <div class="loading_container" id="viz_loading_cont">
                            <p>
                                <b>Loading...</b>
                            </p>
                            <br />
                            <img src="../resources/images/loading.gif" />
                            
                        </div>                    
                        
                        <!-- Division is populated with the ajax windows accessed through the image map -->
                        <div id="divWindows"></div>
                        
                        <!-- Populated with the image map tied to the visualization "on load" -->
                        <div id="image_map"></div>
                        
                        <%                        } else {
                        %>
                        <div class="error">
                            Although there is an active database ("<%= activeDB.getName()%>"), there is not an active cluster file associated with the database. Load or create a cluster file from the side bar of a <a href="visualization.jsp">visualization</a>.
                        </div>
                        <%
                        }
                        if (!successfulConnection) {

                        %>
                        <div class="error">
                            Connection error
                        </div>
                        <%                        }
                        if (activeDB == null) {
                        %>
                        <div class="error">
                            No active database. <a href="../data/databases.jsp">Please load one</a>.
                        </div>
                        <%                }
            }
                        %>
                        
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>