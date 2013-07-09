<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.monitoring.*"%>
<%@page import="com.sfsu.xmas.util.*"%>
<%@page import="xml.*"%>
<%@page import="visualization.*"%>
<%@page import="com.sfsu.xmas.filter.*"%>

<div class="sidebar_header"><span style="cursor: text">Analysis Summary</span></div>

<div class="sidebar_padding">
    <div class="col_header" onclick="toggle_area('probes');" id="probes_show_hide" title="Show/Hide Analysis Focus Data">Analysis Focus</div>
    <div class="indented_div">
        <div id="probes">
            <p class="block_summary">
                This module shows how focused <b style="color: blue;">your analysis</b> is, with respect to the size of the active <b style="color: gray;">Expression Data Set</b>
            </p>
            <div class="corners_bar">
                <div class="corners_top_left"></div>
                <div class="corners_top_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
            <div class="sidebar_content_holder">
                <div class="corners_bar">
                    <div class="corners_inner_top_left"></div>
                    <div class="corners_inner_top_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
                <%
            int probeCount = 0;
            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            String sessionID = SessionAttributeManager.getSessionID(request);

            ExecutionTimer et = new ExecutionTimer();
            et.start();
            
            Probes probes = null;
            if (SessionAttributeManager.getActiveTrajectoryFile(request) != null && FilterManager.getUniqueInstance().getFilterListForIdentifier(sessionID).hasTrajFileBasedFilters()) {
                probes = new Probes(eDB.getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getProbes(sessionID));
                probeCount = probes.size();
            } else if (FilterManager.getUniqueInstance().getFilterListForIdentifier(sessionID).size() > 0) {
                probes = eDB.getProbes(sessionID, false);
                probeCount = probes.size();
            } else {
                probeCount = eDB.getNumberOfProbes();
            }

            int sizeOfData = 0;

            if (eDB != null) {
                sizeOfData = eDB.getNumberOfProbes();
                double unit = 0.0;
                if (sizeOfData > 0) {
                    unit = (double) 270 / (double) sizeOfData;
                }
                int sizeOfAnalysis = probeCount;

                int intersectionWidth = Math.max((int) Math.round(sizeOfAnalysis * unit), 0);
                if (intersectionWidth == 0) {
                    intersectionWidth = (int) Math.ceil(sizeOfAnalysis * unit);
                }
                int probesWidth = Math.max((int) Math.round((sizeOfData - sizeOfAnalysis) * unit), 0);

                %>
                <p class="table_summary">
                    You have focused analysis on <b>
                        <% if (sizeOfAnalysis > 0 && sizeOfData > 0) {%>
                        <%= NumberUtils.getDoubleToTwoDecimalPlaces((double) ((double) sizeOfAnalysis / sizeOfData) * 100)%>
                        <% } else {%>
                        0
                        <% }%>
                    %</b> of the Expression Data Set.
                </p>
                
                <table class="precision_recall">
                    <tr>
                        <td></td>
                        <td></td>
                        <td style="background-color: #CCCCCC; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar_thin bar_data" style="width: <%= probesWidth%>px;"></div></td>
                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar_thin bar_probes" style="width: <%= intersectionWidth%>px;"></div></td>
                    </tr>
                    <tr>
                        <td style="padding-right: 2px;">Probes:</td>
                        <td style="padding-right: 4px;"><%= sizeOfAnalysis%></td>
                        <td style="background-color: #CCCCCC; border-left: 1px solid gray; width: <%= probesWidth%>px;"></td>
                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_probes" style="width: <%= intersectionWidth%>px;"></div></td>
                    </tr>
                    <tr>
                        <td style="padding-right: 2px;">Data set:</td>
                        <td style="padding-right: 4px;"><%= sizeOfData%></td>
                        <td style="background-color: #CCCCCC; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar  bar_data" style="width: <%= probesWidth%>px;"></div></td>
                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_data" style="width: <%= intersectionWidth%>px;"></div></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td style="background-color: #CCCCCC; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar_thin bar_data" style="width: <%= probesWidth%>px;"></div></td>
                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar_thin bar_probes" style="width: <%= intersectionWidth%>px;"></div></td>
                    </tr>
                </table>
                
                <% } else {%>
                <div class="error">
                    No Expression Data Loaded
                </div>
                <% }%>
                
                <div class="corners_bar">
                    <div class="corners_inner_bottom_left"></div>
                    <div class="corners_inner_bottom_right"></div>
                    <div class="corners_inner_connector_bar"></div>
                </div>
            </div>
            <div class="corners_bar">
                <div class="corners_bottom_left"></div>
                <div class="corners_bottom_right"></div>
                <div class="corners_connector_bar"></div>
            </div>    
        </div>
    </div>
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null) {
                if (kDB.getNumberOfPathways() > 0) {
    %>
    
    <div class="col_header" onclick="toggle_area('pathways');" id="pathways_show_hide" title="Show/Hide Pathway Data">Pathways</div>
    <div class="indented_div">
        <div id="sidebar_messages">
            
        </div>
        <div id="pathways">
            <p class="block_summary">
                This module shows the pathways represented in the current analysis, 
                ordered by recall. You can isolate or exclude probes that are members 
                of a pathway by selecting one, and clicking the apropriate operator. 
                Highlight a pathway by clicking its star.
            </p>
            <jsp:include page="../modules/pathway_renderer.jsp">
                <jsp:param name="summary" value="true" />
            </jsp:include>
        </div>
    </div>
    
    <%        }
        if (kDB.getNumberOfLabels() > 0) {
    %>
    
    <div class="col_header" onclick="toggle_area('labels');" id="labels_show_hide" title="Show/Hide Label Data">Labels</div>
    <div class="indented_div">
        <div id="sidebar_messages">
            
        </div>
        <div id="labels">
            <p class="block_summary">
                This module shows the labels represented in the current analysis, 
                ordered by recall. You can isolate or exclude probes that are members 
                of a label by selecting one, and clicking the apropriate operator. 
                Highlight a label by clicking its star.
            </p>
            <jsp:include page="../modules/label_renderer.jsp">
                <jsp:param name="summary" value="true" />
            </jsp:include>
        </div>
    </div>
    
    <%                }
            }

            FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));
            if (filterList.size() > 0) {
    %>
    
    <div class="col_header" onclick="toggle_area('filters');" id="filters_show_hide" title="Show/Hide Current Filter Data">Active Filters</div>
    <div class="indented_div" style="overflow-x: scroll;">
        <div id="filters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="summary" value="true" />
            </jsp:include>
        </div>
    </div>
    
    <%            }
    %>
    
</div>