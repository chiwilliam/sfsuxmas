<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="com.sfsu.xmas.database.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>

<link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />

<%
            int divCount = 0;
            String divCountString = request.getParameter("divID");
            if (divCountString != null) {
                divCount = Integer.parseInt(divCountString);
            }

            String probeID = "";
            String probeIDString = request.getParameter("probeID");
            if (probeIDString != null) {
                probeID = probeIDString;
            }

            int timePeriod = 0;
            String timePeriodString = request.getParameter("timePeriod");
            if (timePeriodString != null) {
                timePeriod = Integer.parseInt(timePeriodString);
            }
            Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(new String[]{probeID}, false);
            Probe probe = probes.get(probeID);
%>

<div class="cancel_button">
    <a href="javascript: void(0);" onclick="return closeWindow('<%= "divRoot" + divCount%>');">X</a>
</div> 
<div id="divhandle<%= divCount%>" class="titlebar">
    
    <% String urlParams = "?timePeriod=" + timePeriod + "&divID=" + divCount + "&probeID=" + probeID;%>
    <ul id="smallminitabs">
        <li><a id="window_nav_summary" href="javascript: void(0);" onclick="make_ajax_window_request('<%= "exact_summary.jsp" + urlParams%>', <%= divCount%>, 'summary' );" class="current">Summary</a></li>
        <li><a id="window_nav_operators" href="javascript: void(0);" onclick="make_ajax_window_request('<%= "exact_operators.jsp" + urlParams%>', <%= divCount%>, 'operators' );">Operators</a></li>
        
            <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            if (kDB != null && FileGlobals.PATHWAYS_ENABLED) {
            %>
            <li><a id="window_nav_pathways" href="javascript: void(0);" onclick="make_ajax_window_request('<%= "exact_pathways.jsp" + urlParams%>', <%= divCount%>, 'pathways' );">Pathways</a></li>
            <% } %>
    </ul>
</div>
<div class="titlebar_footer"></div>

<div class="ajax_window_body_content<%= divCount%>">
    <iframe id="popout_content_<%= divCount%>" name="popout_content_<%= divCount%>" class="ajaxWindowFrame" src="exact_summary.jsp<%= urlParams%>"></iframe>
</div>
