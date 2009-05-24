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
                            <h1>Data Integration</h1>
                            <div id="page_body_content">
                                
                                <h2>Visualizations</h2>
                                <div class="tutorial">
                                    <ul>
                                        <li><p><b>Trajectory Visualization</b>: The trajectory visualization is rendered based on the discretization you performed during data integration. The image below is based on a 0.5 unit discretization. Nodes are colored to indicate movement - warm tones for increasing expression, and cold tones for decreasing expression.</p></li>
                                        <img src="images/data_viz_1.png" />
                                        <li><p><b>Hybrid Visualization</b>: The hybrid visualization renders the expression profiles of probes from the same trajectory in the same color, in the context of the mean parent trajectory profile, which is emphasized. Trajectories with less than 3 members are not accompanied by the parent profile.</p></li>
                                        <img src="images/data_viz_2.png" />
                                        <li><p><b>Profile Visualization</b>: Precise time period expression profiles are rendered for the data under analysis, up to 1000 probes.</p></li>
                                        <img src="images/data_viz_3.png" />
                                    </ul>
                                </div>
                                
                                <h2>Visual Data Operators</h2>
                                <div class="tutorial">
                                    <ul>
                                        <li><p><b>Collapse</b>: The collapse data operator performs a discretized vertical translation, which reduces the initial expression range to a single bin for shape-based analysis.</p></li>
                                        <img src="images/data_viz_4.png" />
                                        <li><p><b>Preserve</b>: The inverse operator to Collapse, returns data to its original expression range.</p></li>
                                        <img src="images/data_viz_5.png" />
                                    </ul>
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