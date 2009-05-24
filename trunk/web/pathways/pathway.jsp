<%@page import="com.sfsu.xmas.kegg.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "XMAS";
            String pageName = "";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
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
    
    
    
    <!--
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        
        -->
    
    <body onload="
        get_pathway_page('<%= PathwayAccessor.getPathwayURLForProbes(null)%>');    
            ">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="pathway_container" style="margin-left: auto; margin-right: auto;">
                
                <!--
                <iframe src="<%= PathwayAccessor.getPathwayURLForProbes(null)%>" style="width: 1200px; height: auto; border: 0;">
                    
                </iframe>
                
                -->
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>