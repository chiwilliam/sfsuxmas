<%@page import="com.sfsu.xmas.session.*"%>

<div class="sub_nav_buttons">
    <%
    if (false &&SessionAttributeManager.getActiveKnowledgeLibrary(request) != null) {
    %>
    <a class="button current" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/pathway_renderer.jsp', 'list');" id="sub_nav_list"><span>List</span></a>
    <a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/pathway_search.jsp', 'search');" id="sub_nav_search"><span>Search</span></a>
    <%            }
    %>
</div>

<div class="sidebar_header">Pathways</div>

<div class="sidebar_padding">
    <div id="sidebar_page_content">
        <div id="sidebar_pathway_area">
            <%@ include file="pathway_renderer.jsp" %>
        </div>
    </div>
</div>