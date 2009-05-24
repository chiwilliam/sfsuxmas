<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "";
            String pageName = "This Page Has Moved";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" language="JavaScript">
            <!--
            setTimeout("top.location.href = 'system.jsp'",500);
            //-->
        </script>
    </head>
    
    <body>
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                <div style="font-size: 0.9em;">
                                    <div class="sidebar_header">Tip</div>
                                    <div class="sidebar_padding">
                                        <img src="../resources/images/firefox.png" style="height: 40px; float: right;" />
                                        <p>
                                            XMAS works best in <a href="http://www.mozilla.com/en-US/firefox/" target="_blank">Firefox</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>This page has moved...</h1>
                            <div id="page_body_content">
                                
                                <h2>We'll redirect you in: </h2>
                                
                                <p>
                                    Information about our software can now be found on the <a href="../system.jsp">System Description</a> page. Please follow that link if the redirect does not happen within 5 seconds.
                                </p>
                                <p>
                                    Please update your bookmarks.
                                </p>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>