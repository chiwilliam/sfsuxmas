<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
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
                        <h1>Local Installation</h1>
                        <div id="page_body_content">
                            <p>
                            <a href="../resources/images/system_arch.png"><img src="../resources/images/system_arch.png" style="width: 250px; float: right; border: 0;" /></a>
                            <span style="clear: both; float: right; text-align: center; width: 250px;"><a href="../resources/images/system_arch.png">Click to enlarge</a></span>
                            
                            <p>
                                There are three steps that you need to follow in order to install XMAS:
                            </p>
                            <ol>
                                <li>Install MySQL</li>
                                <li>Install a Java web container</li>
                                <li>Install XMAS into the web container</li>
                            </ol>
                            <p>
                                XMAS is written for Mozilla Firefox which can be downloaded free of charge from the <a href="http://www.mozilla.com/en-US/firefox/" target="_blank">Mozilla Homepage</a>.
                            </p>
                            
                            <div style="clear: both;">
                                <h2>Installation Instructions</h2>
                                <div class="indented_div">
                                    <p class="block_summary">
                                        <iframe src='http://docs.google.com/EmbedSlideshow?docid=dgvwz2vs_70cg6sq7dz' frameborder='0' width='410' height='342'></iframe>
                                    </p>
                                </div>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>1. MySQL</h3>
                                <p>
                                    <img src="../resources/images/mysql.png" style="float: right; height: 100px;" />
                                    XMAS requires MySQL to store and manage your data. If you have MySQL installed, create an administration account with the credentials specified below. If not, download the appropriate version for your system and install.
                                </p>
                                <p>
                                    We recommend MySQL 5.1 Community Edition, (Essentials) which is available here: <a href="http://dev.mysql.com/downloads/mysql/5.1.html" target="_blank">http://dev.mysql.com/downloads/mysql/5.1.html</a>. A basic/standard installation is sufficient.
                                </p>
                                <p>
                                    To configure the user account, run the post installation configuration tool, or modify the user table.
                                </p>
                                
                                
                                
                                <blockquote>
                                    <ul>
                                        <li>Username: "root"</li>
                                        <li>Password: "xmas"</li>
                                        <li>Enable TCP/IP Networking</li>
                                    </ul>
                                </blockquote>
                                
                                <b>Data Base Creation</b>
                                <p>
                                Once you have installed MySQL, run the SQL contained within the <a href="../home/download.jsp">setup file</a> to create a custom database.
                                </p>
                                <div class="paper_links_block">
                                    <span class="paper_link_title"><b>Related Links:</b></span>
                                    <div class="paper_links">
                                        <ul>
                                            <li><a href="http://dev.mysql.com/downloads/mysql/5.0.html#downloads" target="_blank">MySQL Downloads</a></li>
                                            <li><a href="http://dev.mysql.com/doc/refman/5.0/en/installing.html" target="_blank">MySQL Installation Guide</a></li>
                                            <li><a href="http://docs.google.com/Presentation?id=dgvwz2vs_70cg6sq7dz" target="_blank">XMAS Setup Guide</a></li>
                                        </ul> 
                                    </div>
                                </div>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>2. Java Web container</h3>
                                <p>
                                    <img src="../resources/images/java_cont.png" style="float: right; height: 100px;" />
                                    XMAS is a web application written in Java. It requires a container to enable it to run on your computer, such as:
                                </p>
                                <blockquote>
                                    <ul>
                                        <li><a href="http://tomcat.apache.org/" target="_blank">Apache Tomcat</a></li>
                                        <li><a href="https://glassfish.dev.java.net/" target="_blank">GlassFish</a></li>
                                    </ul>
                                </blockquote>
                                <p>
                                    We recommend the most recent version of TomCat 6.*, available here: <a href="http://tomcat.apache.org/download-60.cgi" target="_blank">http://tomcat.apache.org/download-60.cgi</a>. The Core  binary distribution is sufficient. You must have a Java Runtime environment (JRE) installed.
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>3. XMAS</h3>
                                <p>
                                    <img src="../resources/images/xmas.png" style="float: right; height: 100px;" />
                                    XMAS is contained within a WAR file. Download and drop it into the Web Apps (webapps) folder in your web application container to install. Restart your web contained.
                                </p>
                                <!-- <p style="text-align: center;">
                                        <a href="../files/XMAS.war.zip"><img src="../resources/images/download.png" style="height: 75px;" /></a>
                                    </p> -->
                            </div>
                            
                            <div style="clear: both;">
                                <h3>Firefox</h3>
                                <p>
                                    <img src="../resources/images/firefox.png" style="float: right; height: 60px;" />
                                    Once you have installed MySQL, a Java web continer and XMAS, you will be able to access XMAS from your Firefox web browser:
                                    
                                    <pre>http://localhost:8080/XMAS</pre>
                                </p>
                                <div class="paper_links_block">
                                    <span class="paper_link_title"><b>Related Links:</b></span>
                                    <div class="paper_links">
                                        <ul>
                                            <li><a href="http://www.mozilla.com/en-US/firefox/" target="_blank">Firefox Homepage</a></li>
                                        </ul> 
                                    </div>
                                </div>
                            </div>
                            
                            
                            <h2 style="margin-top: 30px;">Install Data</h2>
                            
                            <div>
                                <p>
                                    We provide a zip file containing <a href="../home/download.jsp">sample data</a> which can be used as a guide to integrate your own.
                                </p>
                            </div>
                            
                            <h2 style="margin-top: 30px;">Install A New Version</h2>
                            <div>
                                <p>
                                    To safely replace an existing installation with a new version of XMAS, follow the following steps:
                                </p>
                                <ol>
                                    <li><p>Shut down Tomcat</p></li>
                                    <li><p>Delete both the xmas directory and xmas.war from the webapps directory</p></li>
                                    <li><p>Download xmas.war, and drop it into the webapps folder</p></li>
                                    <li><p>Restart TomCat</p></li>
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>