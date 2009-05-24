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
                <div id="page_body_block">
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Loading Data for Analysis</h1>
                            <div id="page_body_content">
                                
                                <h2>Loading an Expression Data</h2>
                                <div class="tutorial">
                                    <p>
                                        Select the data you require and click Load.
                                    </p>
                                    <img src="images/load_data_1.png" />
                                </div>
                                
                                <h2>Loading a Discretized Data Representation</h2>
                                <div class="tutorial">
                                    <p>
                                        With an Expression Data Set loaded, at the bottom of the <i>Your Data</i> page you will find a Discretized Data Representations module.
                                        To load a particular Discretized view of your data, simply click its accompanying "Load" link 
                                    </p>
                                    <img src="images/load_data_3.png" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>