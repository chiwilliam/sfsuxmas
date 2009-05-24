<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Tutorials";
            String pageName = "Home";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
          ">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">
                    
                    <!-- 
                        <div id="sidebar_container">
                            <div id="sidebar">
                                
                            </div>
                        </div>
                        
                        -->
                        
                    <div id="page_body_main_full">
                        <h1>Data Integration</h1>
                        <div id="page_body_content">
                            
                            <h2>System Setup</h2>
                            <div class="tutorial">
                                <p>
                                    <i>N.B. Admin only</i>
                                </p>
                                <p>
                                    XMAS looks within a specific area of the host file system for files that can be integrated. It is unlikely that XMAS is looking in the correct location out of the box, so you can use the <b>Data Options</b> page accessible from the <a href="../data/your_data.jsp">Data Index</a> page to specify the root directory of your XMAS data integration:
                                </p>
                                <ol>
                                    <li><p>The interface indicates the validity of the specified directories. Here the data directory is valid (/Users/), but the sub directories for Expression and Knowledge Data cannot be found.</p></li>
                                    <img src="images/data_options_1.png" border="0"/>
                                    <li><p>The user can either create the required sub directories within the /Users/ directory, or point XMAS to a directory which has the correct structure. To do the latter, the user specifies a different directory path in the text box and clicks update.</p></li>
                                    <img src="images/data_options_2.png" border="0"/>
                                    <li><p>Put your data in the correct directories:</p></li>
                                    <img src="images/data_options_3.png" border="0"/>
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