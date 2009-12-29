<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Home";
            String pageName = "Home";
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
                <div id="page_body_block">
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                
                                <!--
                                <div class="sidebar_header"><a href="http://xmasupdates.blogspot.com/" target="_blank" alt="XMAS Updates Blog">Latest News</a></div>
                                <div style="font-size: 0.9em;">
                                    <div class="sidebar_padding">
                                        
                                        <div id="latest_news_container">
                                            <div class="error">JavaScript Disabled: XMAS will not function correctly</div>
                                        </div>
                                        
                                    </div>
                                </div>
                                -->
                                
                                <div class="sidebar_header">Requirements</div>
                                <div class="sidebar_padding">
                                    <img src="../resources/images/compatible_firefox.gif" style="height: 30px; float: right; padding: 10px 10px 10px 5px;" alt="Firefox Logo" title="Firefox Logo">
                                    <img src="../resources/images/compatible_safari.gif" style="height: 30px; float: right; padding: 10px 5px 10px 10px;" alt="Safari logo" title="Safari Logo">
                                    <p>
                                        XMAS works best in <a href="http://www.mozilla.com/en-US/firefox/" target="_blank">Firefox</a> or 
                                        <a href="http://www.apple.com/safari/" target="_blank">Safari</a>, and requires that both JavaScript and Cookies are enabled.
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Welcome to XMAS*</h1>
                            <div id="page_body_content">
                                <div id="cookie_test">
                                    <div class="error">JavaScript Disabled: XMAS will not function correctly</div>
                                </div>
                                <h2>Introduction</h2>
                                <img src="../resources/images/xmas_but_logo.png" style="float: right; height: 150px;" alt="XMAS Logo" title="XMAS Logo">
                                <p>
                                    Time series microarray analysis provides an invaluable insight into 
                                    the genetic progression of biological processes, such as pregnancy and disease. 
                                    Many algorithms and systems exist to meet the challenge of extracting knowledge from 
                                    the resultant data sets, but traditional methods limit user interaction, 
                                    and depend heavily on statistical, black box techniques. We propose 
                                    a new design philosophy based on <b><i>increased human computer synergy</i></b> to over come these 
                                    limitations, and facilitate an improved analysis experience.
                                </p>
                                <p>
                                    XMAS (eXperiential Microarray Analysis System) is an implementation of this 
                                    philosophy that supports a new kind of <b><i>sit forward</i></b> analysis through 
                                    visual interaction and interoperable operators. Domain knowledge, (such as 
                                    pathway information) is integrated directly into the system to aid users in their 
                                    analysis. In contrast to the sit back, algorithmic approach of traditional systems, 
                                    XMAS emphasizes interaction and the power, and knowledge transfer potential of facilitating 
                                    an analysis in which the user directly experiences the data.
                                </p>
                                
                                <h2>Software</h2>
                                <p>
                                    <b>To download and install XMAS locally, <a href="../help/local_install.jsp">click here</a>. You can also try our on-line demo version of XMAS <a href="../visualization/visualization.jsp">here</a>, after loading <a href="../data/your_data.jsp">sample dataset(s)</a></b>
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