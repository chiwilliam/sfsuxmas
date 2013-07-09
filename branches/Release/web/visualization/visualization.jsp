<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>

<%@page import="visualization.*"%>
<%@page import="xml.*"%>
<%@page import="org.w3c.dom.Document"%>

<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
//response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
//response.setHeader("Pragma", "no-cache"); //HTTP 1.0
//response.setDateHeader("Expires", 0); //prevents caching at the proxy server

            String sideBarUrl = "../modules/summary.jsp";
            if (request.getParameter("sb") != null) {
                String sbParam = (String) request.getParameter("sb");
                sideBarUrl = "../modules/" + sbParam + ".jsp";
            }

            String parentPageName = "XMAS";
            String pageName = "Visualization";

            if (SessionAttributeManager.isProfileVisualization(request)) {
                pageName = "Profile";
            } else if (SessionAttributeManager.isHybridVisualization(request)) {
                pageName = "Hybrid";
            } else {
                pageName = "Trajectory";
            }
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        <title><%= pageName%> Visualization - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/probes_and_pathways.js"></script>
        <script type="text/javascript" src="../resources/script/form_actions.js"></script>
        
        <script type="text/javascript" src="../resources/script/filter_by_trajectory.js"></script>
        <script type="text/javascript" src="../resources/script/files.js"></script>
        <script type="text/javascript" src="../resources/script/visualization.js"></script>
        
        <script type="text/javascript" src="../resources/script/nav_switcher.js"></script>
        
        <!-- Scripts for collapsable lists -->
        <script type="text/javascript" src="../resources/script/utils/zapatec.js"></script>
        <script type="text/javascript" src="../resources/script/zptree/src/tree.js"></script>
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        window_onload_visualization();
        make_nav_request('<%= sideBarUrl%>');
        window_onload_data_source('<%
            if (SessionAttributeManager.isPrimaryExpressionDatabaseActive(request)) {
          %>primary<%          } else {
          %>secondary<%            }%>');
          ">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    
                    <div id="sidebar_container">
                        <%@ include file="../navigation/sidebar.jsp" %>
                    </div>
                    
                    <div id="page_body_main">
                        <div class="sub_nav_buttons">
                            <!--
                            
                            Preserved/Collapsed Buttons
                            
                            -->
                            <%
            String preserved = " disabled";
            String collapsed = " style=\"cursor: pointer; cursor: hand;\" ";
            if (!SessionAttributeManager.isPreserved(request)) {
                preserved = collapsed;
                collapsed = " disabled";
            }
            String profTraj = "Profiles";
            if (SessionAttributeManager.isTrajectoryVisualization(request)) {
                profTraj = "Trajectories";
            }
                            %>
                            <input type="button" <%= preserved%> 
                                   onclick="image_type_switch('../SVisualizationManipulator?preserved=true', 'preserved');" 
                                   id="button_preserved" 
                                   value="Preserve <%= profTraj%>" 
                                   alt="Preserves the expression magnitude of data under analysis" 
                                   title="Preserves the expression magnitude of data under analysis" />
                            <input type="button" <%= collapsed%> 
                                   onclick="image_type_switch('../SVisualizationManipulator?preserved=false', 'collapsed');" 
                                   id="button_collapsed" 
                                   value="Collapse <%= profTraj%>" 
                                   alt="Vertically translates the data under analysis to the same origin" 
                                   title="Vertically translates the data under analysis to the same origin" />
                        </div>
                        
                        <h1>Visualization</h1>
                        
                        <div id="page_body_content">
                            <%
            // DatabasesDA dda = new DatabasesDA();
            //boolean successfulConnection = DatabaseConnectionManager.isConnectionLive();
            ExpressionDataSet activeDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            if (activeDB != null) {
                String messageID = (String) session.getAttribute("message_id");

                // Try to retrieve the message from the globals file:
                //UIMessages mess = UIMessages.getUniqueInstance();
                //String message = mess.getMessage(messageID);

                // Finally, check if there is an xml file loaded.
                TrajectoryFile trajDoct = SessionAttributeManager.getActiveTrajectoryFile(request);
                if (trajDoct == null) {
                            %>
                            
    
                            <div class="error">
                                Although there is an active database ("<%= activeDB.getName()%>"), there is not an active trajectory file associated with the database. 
                                <a href="../data/your_data.jsp">Load or create</a> a trajectory file.
                            </div>
                            <%
                                }
                            %>
                            
                            <div id="source_buts">
                                <%@ include file="sub_nav_image_source.jsp" %>
                            </div>
                            
                            <div id="visualization_container">
                                <div id="visualization">
                                    <div id="visualization_loading">
                                        <div class="loading_container" id="viz_loading_cont" style="width: 564px; background-color: #FFFFFF; padding: 25px 0; height: 0px; z-index: 50; position: relative; top: 0; left: 0px; margin-bottom: -42px; overflow: hidden; display: inline;">
                                            <div id="loading_content" style="z-index: 50;">
                                                <p>
                                                    <b>Loading...</b>
                                                </p>
                                                <br />
                                                <img src="../resources/images/loading.gif" />
                                            </div>
                                        </div>
                                        <img src="" alt="" id="main_image" usemap="#Visualization_map" />
                                    </div>
                                </div>
                            </div>
                            
                            <% if (SessionAttributeManager.isProfileVisualization(request) || SessionAttributeManager.isHybridVisualization(request)) {%>
                            <p class="image_note" style="font-style: italic; text-align: right; font-size: 0.8em;"><b>Note</b>: Large data sets (> 1000 probes) may be reduced for faster rendering of Profile and Hybrid visualizations</p>
                            <% }%>
                            <!-- Division is populated with the ajax windows accessed through the image map -->
                            <div id="divWindows"></div>
                            
                            <!-- Populated with the image map tied to the visualization "on load" -->
                            <div id="image_map"></div>
                            
                        </div>
                        <%

                            } else {
                                if (activeDB == null) {
                        %>
                        <div class="error" style="margin-top: 50px;">
                            There is not an active <i>Primary</i> Expression data set. <a href="../data/your_data.jsp">Please load one</a> to proceed with analysis.
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