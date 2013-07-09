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
                        <h1>Case Studies - Videos</h1>
                        <div id="page_body_content">
                            <table width="950px">
                                <tr>
                                    <td align="left" width="500px">
                                        <p>
                                            <h2>1. Negative Differential Expression at Term</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/93RQHtSMbi0&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/93RQHtSMbi0&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                    <td align="left" width="450px">
                                        <p>
                                            <h2>2. Comparative Analysis</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/hcvW0TkqJqQ&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/hcvW0TkqJqQ&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" width="500px">
                                        <p>
                                            <h2>3. Cross Data Set with Knowledge Feedback Loop</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/cPcbXtDF47Q&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/cPcbXtDF47Q&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                    <td align="left" width="450px">
                                        <p>
                                            <h2>4. Attribute Linking</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/zH9JoUh7hDU&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/zH9JoUh7hDU&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" width="500px">
                                        <p>
                                            <h2>5. Candidates for positive differential expression</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/3uPou9cyIdk&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/3uPou9cyIdk&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                    <td align="left" width="450px">
                                        <p>
                                            <h2>6. Placental Case Study</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/TScjKA9EIFg&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/TScjKA9EIFg&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left" width="500px">
                                        <p>
                                            <h2>7.1. Exploring a serendipitous observation (Part 1)</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/E9xnMqyxMB0&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/E9xnMqyxMB0&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                    <td align="left" width="450px">
                                        <p>
                                            <h2>7.2. Exploring a serendipitous observation (Part 2)</h2>
                                            <p>
                                                <object width="320" height="265"><param name="movie" value="http://www.youtube.com/v/E9xnMqyxMB0&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/E9xnMqyxMB0&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="320" height="265"></embed></object>
                                            </p>
                                            <br></br>
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>