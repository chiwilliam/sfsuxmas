<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="filter.managers.*"%>
<%@page import="com.sfsu.xmas.filter.*"%>
<%@page import="xml.*"%>
<%@page import="java.util.*"%>
<%
            String filterType = request.getParameter("filter_type");
            FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));
            if (filterList != null) {
                if (filterType != null) {
                    if (filterType.equals("probe")) {
                        filterList = filterList.getProbeFilters();
                    } else if (filterType.equals("pathway")) {
                        filterList = filterList.getPathwayFilters();
                    } else if (filterType.equals("labels")) {
                        filterList = filterList.getLabelFilters();
                    } else if (filterType.equals("trajectory")) {
                        filterList = filterList.getTrajectoryShapeFilters();
                    } else if (filterType.equals("node")) {
                        filterList = filterList.getTrajectoryNodeFilters();
                    } else if (filterType.equals("go_term")) {
                        filterList = filterList.getGOTermFilters();
                    } else {
                        // unsupported type
                    }
                } else {
                    filterType = "";
                }
                int numberOfFilters = filterList.size();
                String ess = "";
                if (numberOfFilters != 1) {
                    ess = "s";
                }
%>
<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_content_holder" id="<%= filterType%>_filter_list_holder">
    <div class="corners_bar">
        <div class="corners_inner_top_left"></div>
        <div class="corners_inner_top_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
    <%
    if (filterList.size() <= 0) {
    %>
    <ul id="<%= filterType%>_FilterTree"></ul>
    <div class="warning">
        There are no <%= filterType%> filters active
    </div>
    <%        } else {
    %>
    <p class="table_summary">
        <%= numberOfFilters%> active <%= filterType%> filter<%= ess%>
    </p>
    <ul id="<%= filterType%>_FilterTree">
        <%
        int groupProbeFiltCount = 0;
        for (int i = 0; i < filterList.size(); i++) {
            AbstractFilter filter = (AbstractFilter) filterList.get(i);
            // int filterIndex = filterList.indexOf(filter);
            //String rowClass = "odd";
            //if (i % 2 == 0) {
            //    rowClass = "even";
            //}
            if (filter instanceof MultiProbeFilter) {
                MultiProbeFilter gpf = (MultiProbeFilter) filter;
                FilterList probes = gpf.getFilterList();
                // Add in probe data
                String exclusion = "<i>Isolation</i>";
                if (gpf.isExcluded()) {
                    exclusion = "<i>Exclusion</i>";
                }
        %>
        <li><b><%= gpf.getType()%></b>: <%= gpf.getSimpleDescription()%>, <%= exclusion%> - <a href="../SFilterManipulator?removeFilter=<%= filter.getListIndex()%>" target="_parent">Remove</a>
            <ul>
                <%
                for (int pInd = 0; pInd < probes.size(); pInd++) {
                    ProbeFilter pfilt = (ProbeFilter) probes.get(pInd);
                    //Probe p = pfilt.getProbe();
%>
                <li><b><%= pfilt.getType()%></b>: <%= pfilt.getAttributeID()%></li>
                <%
                }
                %>
            </ul>
        </li>
        <%
                groupProbeFiltCount++;
            } else {
        %>
        <li><b><%= filter.getType()%></b>: <%= filter.getSimpleDescription()%> - <a href="../SFilterManipulator?removeFilter=<%= filter.getListIndex()%>" target="_parent">Remove</a></li>
        <%
            }
        }
        %>
    </ul>
    <%
    }
    %>
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
<%
            }
%>