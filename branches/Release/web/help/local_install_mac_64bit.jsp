<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
            String pageName = "Local_Install_Mac_64bit";
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
                <div id="page_body_sub">

                    <div id="page_body_main_full">
                        <h1>Installation Guide</h1>
                        <div id="page_body_content">
                            <p>
                                XMAS standalone installation is divided into three main steps:
                            </p>
                            <ol>
                                <li>Download Installation Package</li>
                                <li>Watch the "how-to" installation videos</li>
                                <li>Install XMAS, following the steps presented in the "how-to" videos</li>
                            </ol>
                            <p>
                                <b>To install XMAS locally, watch our "how-to" videos and download the installation package.</b>
                            </p>

                            <div style="clear: both;">
                                <h3>Installation Package</h3>
                                <p>
                                    Download Link: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/mac_64bit.zip">Mac OS X 64-bit installation package</a>
                                </p>
                            </div>

                            <div style="clear: both;">
                                <h3>"How-To" videos</h3>
                                <p>
                                    Please watch our "How-To" video guides. They contain all the steps necessary to install XMAS.<br></br>
                                </p>
                                <p>
                                    <ol>
                                        <li>
                                            Installing supporting applications
                                            <br></br>
                                            <p style="margin-left: 40px;" class="block_summary">
                                                <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/qII7f1G0CFE&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/qII7f1G0CFE&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                                            </p>
                                            <br></br>
                                        </li>
                                        <li>
                                            Configuring environment for XMAS
                                            <br></br>
                                            <p style="margin-left: 40px;" class="block_summary">
                                                <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/8WC2WCgaiME&hl=en_US&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/8WC2WCgaiME&hl=en_US&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                                            </p>
                                            <br></br>
                                        </li>
                                        <li>
                                            Load dataset and run XMAS
                                            <br></br>
                                            <p style="margin-left: 40px;" class="block_summary">
                                                <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/VGKZDAPVsI8&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/VGKZDAPVsI8&hl=en&fs=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                                            </p>
                                        </li>
                                    </ol>
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