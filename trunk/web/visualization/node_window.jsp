<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="xml.*"%>
<%@page import="visualization.*"%>

        
<link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />

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

<div class="cancel_button">
    <a href="javascript: void(0);" onclick="return closeWindow('divRoot<%= divCount%>');">X</a>
</div> 
<div id="divhandle<%= divCount%>" class="titlebar">
    
    <% String urlParams = "?nodeID=" + nodeID + "&timePeriod=" + timePeriod + "&divID=" + divCount;%>
    <ul id="smallminitabs">
        <li>
            <a id="<%= divCount%>_window_nav_summary" 
               href="javascript: void(0);" 
               onclick="make_ajax_window_request('node_summary.jsp<%= urlParams%>', <%= divCount%>, 'summary' );" 
               class="current"
               alt="View a summary of this node"
               title="View a summary of this node">Summary</a>
        </li>
        <li>
            <a id="<%= divCount%>_window_nav_operators" 
               href="javascript: void(0);" 
               onclick="make_ajax_window_request('node_operators.jsp<%= urlParams%>', <%= divCount%>, 'operators' );"
               alt="View operators that apply to this node"
               title="View operators that apply to this node">Operators</a>
        </li>
        <li>
            <a id="<%= divCount%>_window_nav_probes" 
               href="javascript: void(0);" 
               onclick="make_ajax_window_request('node_probes.jsp<%= urlParams%>', <%= divCount%>, 'probes' );"
               alt="View probes from the current analysis that belong to this node"
               title="View probes from the current analysis that belong to this node">Probes</a>
        </li>
        <li>
            <a id="<%= divCount%>_window_nav_heat_map" 
               href="javascript: void(0);" 
               onclick="make_ajax_window_request('<%= "node_probes.jsp" + urlParams + "&heat_map=true"%>', <%= divCount%>, 'heat_map' );"
               alt="View an expression heat map for probes from the current analysis that belong to this node"
               title="View an expression heat map for probes from the current analysis that belong to this node">Heat Map</a>
        </li>
        <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null && FileGlobals.PATHWAYS_ENABLED) {
        %>
        <li><a id="<%= divCount%>_window_nav_pathways" href="javascript: void(0);" onclick="make_ajax_window_request('<%= "node_pathways.jsp" + urlParams%>', <%= divCount%>, 'pathways' );">Pathways</a></li>
        <% }%>
        <li>
            <a id="<%= divCount%>_window_nav_shape" 
               href="javascript: void(0);" 
               onclick="make_ajax_window_request('<%= "node_shape.jsp" + urlParams%>', <%= divCount%>, 'shape' );"
               alt="View the profiles of member probes in the context of the parent trajectory"
               title="View the profiles of member probes in the context of the parent trajectory">Shape</a>
        </li>
    </ul>
</div>
<div class="titlebar_footer"></div>

<div class="ajax_window_body_content<%= divCount%>">
    <iframe id="popout_content_<%= divCount%>" name="popout_content_<%= divCount%>" class="ajaxWindowFrame" src="node_summary.jsp<%= urlParams%>"></iframe>
</div>
