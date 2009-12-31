<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
            String pageName = "Local_Install_Windows_32bit";
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
                        <h1>Quick Installation Guide</h1>
                        <div id="page_body_content">
                            <p>
                                XMAS standalone installation is divided into three main steps:
                            </p>
                            <ol>
                                <li>Watch the "how-to" installation video</li>
                                <li>Download Installation Package</li>
                                <li>Install XMAS, following the steps presented in the "how-to" video</li>
                            </ol>
                            <p>
                                <b>To install XMAS locally, watch our "how-to" video and download the installation package.</b>
                            </p>

                            <div style="clear: both;">
                                <h3>Installation Package</h3>
                                <p>
                                    Download Link: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/windows_32bit.zip">Windows 32-bit installation package</a>
                                </p>
                            </div>

                            <div style="clear: both;">
                                <h3>"How-To" video</h3>
                                <p>
                                    Please watch our "How-To" video guides. It contains all the steps necessary to install XMAS.<br></br>
                                </p>
                                <p class="block_summary">
                                        <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/isZBp4NBoMY&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/isZBp4NBoMY&hl=en&fs=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
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