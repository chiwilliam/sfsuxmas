<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
            String pageName = "Local_Install_Mac";
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
                                XMAS standalone installation for MAC OS X is divided into three main steps:
                            </p>
                            <b><ul>
                                <li>Installing supporting applications</li>
                                <li>Configuring environment for XMAS</li>
                                <li>Load dataset and run XMAS</li>
                            </ul></b>
                            <p>
                                <span style="text-align:justify;">
                                <h2><span style="font-size:18px;">Installing supporting applications</span></h2>
                                <ol>
                                    <li>
                                        Update JAVA
                                        <ul>
                                            <li>Apple computers supply their own version of Java. Use the Software Update feature
                                            available on the Apple Menu to check if you have the latest version of Java for your Mac.
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        Install MySQL
                                        <ul>
                                            <li>
                                                Click <a target="_blank" href="http://dev.mysql.com/downloads/mysql/#downloads">here</a> to download MySQL
                                            </li>
                                            <li>
                                                Unpack the DMG archive and install MySQL
                                            </li>
                                            <li>
                                                Start MySQL by going to Preferences and clicking on the MySQL button
                                            </li>
                                            <li>
                                                Te set up a MySQL user for xmas, open a terminal window (Applications > Utilities > Terminal) and run the following command:
                                                <ul>
                                                    <li>
                                                        <i>/usr/local/mysql/bin/mysqladmin -u root password xmas</i>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        Install Tomcat
                                        <ul>
                                            <li>
                                                Click <a target="_blank" href="http://www.eng.lsu.edu/mirrors/apache/tomcat/tomcat-5/v5.5.28/bin/apache-tomcat-5.5.28.tar.gz">here</a> to download Tomcat
                                            </li>
                                            <li>
                                                Mac OS X will probably extract the file to a .tar file in your downloads directory.
                                                Move this .tar file to <i>/Users/{your username}/xmas/</i>
                                                <br></br>*Here and thereafter, always replace <i>{your username}</i> by your actual username!
                                            <li>
                                                Double-click the .tar file at your <i>/Users/{your username}/xmas/</i> folder to extract it.
                                                A new folder <i>/Users/{your username}/xmas/apache-tomcat-5.5.28/</i> should be created.
                                            </li>
                                            <li>
                                                Open and edit the file <i>/Users/{your username}/xmas/apache-tomcat-5.5.28/bin/startup.sh</i> by adding the two
                                                following lines right after the line that starts with EXECUTABLE:
                                                <i>
                                                    <ul>
                                                        <li>
                                                            export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.5/Home
                                                        </li>
                                                        <li>
                                                            export CATALINA_HOME=/Users/{your username}/xmas/apache-tomcat-5.5.28
                                                        </li>
                                                    </ul>
                                                </i>
                                            </li>
                                            <li>
                                                Start Tomcat:
                                                <ul>
                                                    <li>
                                                        Open a terminal window (Applications > Utilities > Terminal) and run the following commands:
                                                        <ul>
                                                            <li>
                                                                <i>cd /Users/{your username}/xmas/apache-tomcat-5.5.28/bin/</i>
                                                            </li>
                                                            <li>
                                                                <i>./startup.sh</i>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                Test if Tomcat is working by opening your web browser and accessing <a target="_blank" href="http://localhost:8080/">http://localhost:8080/</a>
                                            </li>
                                        </ul>
                                    </li>
                                </ol>
                                </span>
                            </p>
                            <p>
                                <h2><span style="font-size:18px;">Configuring environment for XMAS</span></h2>
                                <ol>
                                    <li>
                                        Download the installation package <a target="_blank" href="http://tintin.sfsu.edu:8080/xmas_downloads/mac.zip">here</a>
                                    </li>
                                    <li>
                                        Extract the files to <i>/Users/{your username}/xmas/</i>
                                    </li>
                                    <li>
                                        Stop Tomcat:
                                        <ul>
                                            <li>
                                                Open a terminal window (Applications > Utilities > Terminal) and run the following commands:
                                                <ul>
                                                    <li>
                                                        <i>cd /Users/{your username}/xmas/apache-tomcat-5.5.28/bin/</i>
                                                    </li>
                                                    <li>
                                                        <i>./shutdown.sh</i>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        Copy the file <i>/Users/{your username}/xmas/xmas.war</i> to <i>/Users/{your username}/xmas/apache-tomcat-5.5.28/webapps/xmas.war</i> by using the following command:
                                        <ul>
                                            <li>
                                                <i>cp /Users/{your username}/xmas/xmas.war /Users/{your username}/xmas/apache-tomcat-5.5.28/webapps/xmas.war</i>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        Start Tomcat:
                                        <ul>
                                            <li>
                                                Open a terminal window (Applications > Utilities > Terminal) and run the following commands:
                                                <ul>
                                                    <li>
                                                        <i>cd /Users/{your username}/xmas/apache-tomcat-5.5.28/bin/</i>
                                                    </li>
                                                    <li>
                                                        <i>./startup.sh</i>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        Create XMAS database:
                                        <ul>
                                            <li>
                                                Open a terminal window (Applications > Utilities > Terminal) and run the following commands:
                                                <ul>
                                                    <li>
                                                        <i>mysql -u root -p</i>
                                                    </li>
                                                    <li>
                                                        Enter the password xmas and hit enter
                                                    </li>
                                                    <li>
                                                        <i>source /Users/{your username}/xmas/db.sql</i>
                                                    </li>
                                                    <li>
                                                        <i>exit</i>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                </ol>
                            </p>
                            <p>
                                <h2><span style="font-size:18px;">Load dataset and Run XMAS</span></h2>
                                <ol>
                                    <li>
                                        Please watch the following "How-To" video guide. It contains all the steps necessary to Load a dataset and Run XMAS.                                        
                                    </li>
                                    <li>
                                        For the Data Directory path under Load Data > System Setup, please enter the following path:
                                        <ul>
                                            <li>
                                                <i>/Users/{your username}/xmas/xmas_files/</i>
                                            </li>
                                        </ul>
                                    </li>
                                </ol>
                                <p style="margin-left: 40px;" class="block_summary">
                                    <object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/laPWZkp0MJ8&hl=en&fs=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/laPWZkp0MJ8&hl=en&fs=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344"></embed></object>
                                </p>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>