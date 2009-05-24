<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Admin";
            String pageName = "Cookies";
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
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Your Session Cookies</h1>
                            <div id="page_body_content">
                                <table class="base_table">
                                    <thead>
                                        <tr>
                                            <th>Key</th>
                                            <th>Value</th>
                                            <th />
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%
            Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                                    %>
                                        <tr>
                                            <td>"<b><%= cookie.getName()%></b>"</td>
                                            <td>"<%= cookie.getValue()%>"</td>
                                            <td><a href="../SRemoveCookie?cookie_key=<%= cookie.getName()%>">Delete</a></td>
                                        </tr>
                                    <%
                }
            }

                                    %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>