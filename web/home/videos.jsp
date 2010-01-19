<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Videos";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        <!-- Index page only - Google Webmaster Tools -->
        <meta name="verify-v1" content="1JVnTuE1ma/sE0usxzraO5fHUs1IWuH+w2hwxpbp28k=">
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        load_latest_news('latest_news_container');
        load_cookie_test();">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">
                    <div id="page_body_main_full">
                        <h1>Case Studies</h1>
                        <div id="page_body_content">
                            
                            <h2>Case Studies Playlist</h2>
                            
                            <p>
                                <a href="http://www.youtube.com/view_play_list?p=577C8F186A810BEB">XMAS Case Studies Playlist</a> on YouTube.
                            </p>
                            
                            
                            <h2>Embedded Case Studies</h2>
                            <p>
                                Use the navigation in the video player below to locate case studies.
                            </p>
                            <p>
                                <object width="480" height="385"><param name="movie" value="http://www.youtube.com/p/577C8F186A810BEB&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/p/577C8F186A810BEB&hl=en&fs=1" type="application/x-shockwave-flash" width="480" height="385" allowscriptaccess="always" allowfullscreen="true"></embed></object>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>