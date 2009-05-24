<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
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
                
                <div id="page_body_main_full">
                    <h1>Help</h1>
                    <div id="page_body_content">
                        
                        <p>
                            These pages provide detailed information about many aspects and components of XMAS.
                        </p>
                        
                        <style>
                            
.help_cat {
    border: 1px solid #BFCFFF;
    padding: 10px 20px 20px 20px;
    margin-bottom: 10px;
}
                        </style>
                        
                        <h2>Using XMAS</h2>
                        <div class="help_cat">
                            <h3><a href="../help/getting_started.jsp">Getting Started</a></h3>
                            <p>
                                An introductory overview of XMAS.
                            </p>
                        </div>
                        <div class="help_cat">
                            <h3><a href="../help/visualizations.jsp">Visualizations</a></h3>
                            <p>
                                A suite of complementary data visualizations are available to users within XMAS. This section describes the basics of each.
                            </p>
                        </div>
                        <div class="help_cat">
                            <h3><a href="../help/data_views.jsp">Data Views & Operators</a></h3>
                            <p>
                                XMAS uses a number of data views to present data and provide an interface into which the user can directly apply operators.
                                Each data view is described along with any relavent operators.
                            </p>
                        </div>
                        
                        
                        <h2>Local Installation</h2>
                        
                            <p>
                                Many of these instructions, particularly relating to data management, require you to be authenticated as an administrator. 
                                To do this simply access the <a href="../util/admin.jsp">admin page</a> and enter the following passphrase: "xmas" (only valid for local installations).
                            </p>
                            
                        <div class="help_cat">
                            <h3><a href="../help/local_install.jsp">Local Install</a></h3>
                            <p>
                                There are a number of stages in the installation of XMAS. 
                                Due to the technical nature of some of these components you might require assistance from an IT professional during setup.
                            </p>
                        </div>
                        <div class="help_cat">
                            <h3><a href="../help/data_guides.jsp">Data Guides</a></h3>
                            <p>
                                These steps are only required for local installation, but you might find some useful information about the data managed within XMAS. 
                                The live deployment has data preinstalled, which can be loaded on demand.
                                There are also sample data files available from the Downloads page.
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