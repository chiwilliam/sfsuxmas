<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
            String pageName = "Local_Install";
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
                                XMAS local (standalone) installation is divided into two main steps:
                            </p>
                            <ol>
                                <li>Install Supporting Applications</li>
                                <li>Configure XMAS</li>
                            </ol>

                            <div style="clear: both;">
                                <p>
                                    <h2 style="margin-top: 20px;">Please watch our "How-To" video guides. It contains all the steps necessary to install XMAS</h2>
                                </p>
                                <div class="indented_div">
                                    <p>
                                        Windows Installation
                                    </p>
                                    <p class="block_summary">
                                        <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/isZBp4NBoMY&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/isZBp4NBoMY&hl=en&fs=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                                    </p>
                                </div>
                            </div>

                            <div style="clear: both;">
                                <h2 style="margin-top: 20px;">Install Supporting Applications</h2>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>1. Java SE Runtime Environment (JRE)</h3>
                                <p>
                                    Windows 32-bits: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/jre-6u17-windows-i586.exe">JRE 6u17 32-bits</a>
                                </p>
                                <p>
                                    Windows 64-bits: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/jre-6u17-windows-x64.exe">JRE 6u17 64-bits</a>
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>2. Apache Tomcat</h3>
                                <p>
                                     All Windows versions: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/apache-tomcat-6.0.20.exe">Apache Tomcat 6.0.20</a>
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>3. MySQL</h3>
                                <p>
                                    Windows 32-bits: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/mysql-essential-5.1.41-win32.msi">MySQL 5.1.41 32-bits</a>
                                </p>
                                <p>
                                    Windows 64-bits: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/mysql-essential-5.1.41-winx64.msi">MySQL 5.1.41 64-bits</a>
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>4. (Optional) Firefox and Flash Player</h3>
                                <p>
                                     Mozilla Firefox: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/Firefox Setup 3.5.6.exe">Mozilla Firefox 3.5.6</a>
                                </p>
                                <p>
                                     Firefox Flash Player: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/install_flash_player_firefox.exe">Firefox Flash Player 10.0.42.34</a>
                                </p>
                                <p>
                                     IE Flash Player: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/install_flash_player_ie.exe">IE Flash Player 10.0.42.34</a>
                                </p>
                            </div>
                            
                            <h2 style="margin-top: 20px;">Configure XMAS</h2>
                            
                            <div style="clear: both;">
                                <h3>1. Configure XMAS database</h3>
                                <p>
                                    SQL script: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/db.zip">MySQL database script</a>
                                </p>
                            </div>

                            <div style="clear: both;">
                                <h3>2. Deploy XMAS application</h3>
                                <p>
                                    WAR file: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/xmas.war">XMAS WAR file</a>
                                </p>
                            </div>

                            <div style="clear: both;">
                                <h3>3. Sample Data</h3>
                                <p>
                                    Sample Data: <a href="http://tintin.sfsu.edu:8080/xmas_downloads/xmas_files.zip">Sample Dataset</a>
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