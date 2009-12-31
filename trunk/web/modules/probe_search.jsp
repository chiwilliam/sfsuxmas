<%@page import="database_management.*" %>
<%@page import="data_structures.integrated.*" %>
<%@page import="search.*" %>

<h3>
    Search
</h3>

<p>
    Search for probes within the current data set:
</p>

<%
            SearchResultManager srm = SearchResultManager.getUniqueInstance();
            ProbeSearchResults psr = srm.getProbeSearchResult();
            String searchTerm = "";
            String searchAttribute = "";
            
            if (psr != null) {
                searchTerm = psr.getSearchTerm();
                searchAttribute = psr.getSearchAttribute();
            }
            
            System.out.println(searchAttribute);
%>

<!--
START SIDEBAR MENU
-->
<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_top_line">
        Enter your search criteria:
    </div>
    <div class="sidebar_tool_area_content">
        <div class="corners_bar">
            <div class="corners_inner_top_left"></div>
            <div class="corners_inner_top_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
        <div class="sidebar_tool_buttons">
            
            <form action="javascript: get(document.getElementById('probe_search'), '../SSearchProbe', '../modules/probe_renderer.jsp?results', 'search_results');" name="probe_search" id="probe_search">
                <INPUT type="text" name="search_term" id="search_term" size="15" value="<%= searchTerm%>" /> 
                <SELECT name="search_field" id="search_field" style="width: 100px;">
                    <%
            AnnotationDatabase probeSearchDB = AnnotationDatabaseManager.getActiveDataBase();
            if (probeSearchDB != null) {
                IntegratedProbeAnnotations intProbes = new IntegratedProbeAnnotations();
                intProbes = probeSearchDB.getProbes();
                ProbeAttributeHeaderList dropHeaders = intProbes.getProbeAttributes();
                for (int headIndex = 0; headIndex < dropHeaders.size(); headIndex++) {
                    String head = dropHeaders.get(headIndex).getAttribute();
                    String selected = "";
                    if (searchAttribute.equals(head)) {
                        selected = "SELECTED";
                    }
                    %>
                    <OPTION <%= selected%> value="<%= head%>"><%= head%></OPTION>
                    <%
                }
            }
                    %>
                </SELECT>
                <INPUT TYPE="SUBMIT" VALUE="Search" />
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
<!--
END SIDEBAR MENU
-->

<h3>
    Results
</h3>
<div id="search_results">
    <jsp:include page="../modules/probe_renderer.jsp">
        <jsp:param name="results" value="true" />
    </jsp:include>
</div>
