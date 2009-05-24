<div class="sub_nav_buttons">
    
    <a href="../help/dv_probes.jsp" 
       class="help_button" 
       alt="Help"
       title="Help">
        <span>?</span>
    </a>
    <a class="button current" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/probe_renderer.jsp', 'list');" id="sub_nav_list"><span>Data</span></a>
    <%
            KnowledgeDataSet navKDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (navKDB != null) {%>
    <a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/probe_labels.jsp', 'labels');" id="sub_nav_labels"><span>Labels</span></a>
    <% }%>
    <a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/probe_heat_map.jsp', 'heat_map');" id="sub_nav_heat_map"><span>Heat Map</span></a>
    <!-- <a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/probe_search.jsp', 'search');" id="sub_nav_search"><span>Search</span></a> -->
</div>

<div class="sidebar_header">Data > Probes</div>

<div class="sidebar_padding">
    <div id="sidebar_messages">
        
    </div>
    <div id="sidebar_page_content">
        <div id="sidebar_probe_area">
            <%@ include file="../modules/probe_renderer.jsp" %>
        </div>
    </div>
</div>