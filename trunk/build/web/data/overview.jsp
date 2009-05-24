<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "Overview";
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
                <div id="page_body_block">
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                <div class="sidebar_header">Data Health Check</div>
                                <%@ include file="../modules/database_connection.jsp" %>
                                <%@ include file="../modules/database_active.jsp" %>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Data Overview</h1>
                            <div id="page_body_content">
                                <p>
                                    There are three types of data storage within XMAS:
                                    <ol>
                                        <li><b>Expression data</b>: Captures your uploaded expression data precisely in a flexible format</li>
                                        <li><b>Knowledge libraries</b>: Hold knowledge that you want XMAS to be aware of</li>
                                        <li><b>Visualization files</b>: Facilitate visualization and expression profile analysis</li>
                                    </ol>
                                    Data is integrated into XMAS through the "Upload" tab above. There are three ways to integrate expression data and domain knowledge. These are described below.
                                </p>
                                <p>
                                    <b>Data upload</b> <a href="upload_data.jsp">Go here</a>
                                    <br />
                                    Choose a tab delimited expression matrix from your working directory, give it a name and your data will be ready to use within seconds.
                                </p>
                                <p>
                                    <b>Library creation</b> <a href="upload_integration_data.jsp">Go here</a> <i><small>(optional)</small></i>
                                    <br />
                                    Although optional, integrating knowledge into XMAS and having that knowledge presented during analysis is a powerful feature. It is also simple to setup. Choose a tab delimited probe attribute file (like the ones which accompany Affymetrix chips), give your library a name and within seconds this knowledge will be full integrated into XMAS.
                                </p>
                                <p>
                                    <b>Add to library</b> <a href="upload_add_to_library.jsp">Go here</a> <i><small>(optional)</small></i>
                                    <br />
                                    You probably know a lot more about your probes than XMAS does. After creating a library, you can add additional information to your probes, such as pathway data, annotations or results from prior analysis etc.
                                </p>
                                
                                
                                <p style="text-align: center;">
                                    
                                    <img src="../resources/images/data_flow.png" style="width: 500px;" />
                                    
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