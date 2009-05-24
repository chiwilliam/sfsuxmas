<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="com.sfsu.xmas.filter.*"%>

<div class="sidebar_header">Filtered Data</div>

<div class="sidebar_padding">
    
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
            <div class="sidebar_tool_buttons">
                <form action="../SFilterManipulator?removeAll">
                    <input type="submit" value="Remove All Filters" />
                </form>
                
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
    
    <div class="col_header" onclick="toggle_area('probe_fiters');" id="probe_fiters_show_hide">Filtered Probes</div>
    <div class="indented_div">
        <div id="probe_fiters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="probe" />
            </jsp:include>
        </div>
    </div>
    
    <div class="col_header" onclick="toggle_area('trajectory_fiters');" id="trajectory_fiters_show_hide">Trajectory Filter</div>
    <div class="indented_div">
        <div id="trajectory_fiters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="trajectory" />
            </jsp:include>
        </div>
    </div>
    
    <div class="col_header" onclick="toggle_area('node_fiters');" id="node_fiters_show_hide">Trajectory Node Filters</div>
    <div class="indented_div">
        <div id="node_fiters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="node" />
            </jsp:include>
        </div>
    </div>
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null) {

                FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));
                if (filterList.getPathwayFilters().size() > 0) {

    %>
    
    <div class="col_header" onclick="toggle_area('pathway_fiters');" id="pathway_fiters_show_hide">Pathway Filters</div>
    <div class="indented_div">
        <div id="pathway_fiters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="pathway" />
            </jsp:include>
        </div>
    </div>
    
    <%        }
        if (filterList.getLabelFilters().size() > 0) {
    %>
    
    <div class="col_header" onclick="toggle_area('label_filters');" id="label_filters_show_hide">Label Filters</div>
    <div class="indented_div">
        <div id="label_filters">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="labels" />
            </jsp:include>
        </div>
    </div>
    
    <%                }
        if (filterList.getGOTermFilters().size() > 0) {
    %>
    
    <div class="col_header" onclick="toggle_area('go_term_filters');" id="go_term_filters_show_hide">GO Term Filters</div>
    <div class="indented_div">
        <div id="go_term">
            <jsp:include page="../modules/filter_renderer.jsp">
                <jsp:param name="filter_type" value="go_terms" />
            </jsp:include>
        </div>
    </div>
    
    <%                }
            }
    %>
    
</div>