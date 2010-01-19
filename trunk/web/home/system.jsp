<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "System";
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
                                
                                
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>System Overview & Requirements</h1>
                            <div id="page_body_content">
                                <p>
                                <a href="../resources/images/system_arch.png"><img src="../resources/images/system_arch.png" style="width: 250px; float: right; border: 0;" /></a>
                                <span style="clear: both; float: right; text-align: center; width: 250px;"><a href="../resources/images/system_arch.png">Click to enlarge</a></span>
                                
                                <p>
                                    XMAS is available online as a <a href="../visualization/visualization.jsp">live deployment</a>, and as a <a href="../help/local_install.jsp">locally deployable</a> Java based Web Application requiring:
                                </p>
                                <ol>
                                    <li>MySQL</li>
                                    <li>A Java web container</li>
                                </ol>
                                <p>
                                    XMAS is written for Mozilla Firefox which can be downloaded free of charge from the <a href="http://www.mozilla.com/en-US/firefox/" target="_blank">Mozilla Homepage</a>.
                                </p>
                                
                                <h1 style="margin-top: 30px;">Data</h1>
                                
                                <div>
                                    <p>
                                    We provide a zip file containing <a href="../files/sample_data.zip">sample data</a> which can be used in conjunction with our <a href="../help/data_guides.jsp">help documentation</a>, to integrate your own.
                                    </p>
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