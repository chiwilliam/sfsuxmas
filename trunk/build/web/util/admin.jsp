<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="com.sfsu.xmas.session.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Admin";
            String pageName = "Admin";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    
                    <div id="page_body_sub">
                        
                        <div id="sidebar_container">
                            <div id="sidebar">
                                <div class="sidebar_header">Installation Health Check</div>
                                <div class="success">
                                    Installation successful
                                </div>
                                <%@ include file="../modules/database_connection.jsp" %>
                                <%@ include file="../modules/database_active.jsp" %>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Admin request</h1>
                            <div id="page_body_content">
                                <%
            if (!SessionAttributeManager.isAdmin(request)) {
                                %>
                                <form action="../SProcessAdminRequest" >
                                    <p>
                                        <input type="password" name="admin_pass" />
                                        <input type="submit" value="Submit" />
                                    </p>
                                </form>
                                <% } else {%>
                                You are already an Admin.
                                
                                <p>
                                    <a href="../SRefreshFactories">Refresh Factories</a>
                                </p>
                                
                                <% }%>
                                

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>