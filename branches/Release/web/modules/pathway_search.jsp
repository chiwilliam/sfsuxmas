<h3>
    Search
</h3>

<p>
    Search for pathways within the current data set:
</p>

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
            <form action="javascript: get(document.getElementById('pathway_search'), '../SSearchPathway', '../modules/pathway_renderer.jsp?search_results', 'search_results');" name="pathway_search" id="pathway_search">    
                <input type="text" name="search_term" id="search_term" size="15" value="">
                <input TYPE="SUBMIT" VALUE="Search" />
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
    <%
            boolean resultsAvailable = !(request.getParameter("results") == null);
            if (resultsAvailable) {
    %>
    <%@ include file="../modules/pathway_renderer.jsp" %>
    <%            }
    %>
</div>