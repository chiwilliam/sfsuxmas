<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Home";
%>"

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
                    <h1>Getting Started</h1>
                    
                    <style>
                        .code {
                            font-family: courier new;
                        }
                        .img_caption {
                            text-align: right;
                            float: right;
                            clear: both;
                            font-size: 10px;
                            width: 40%;
                        }
                        .img_caption_l {
                            text-align: left;
                            font-size: 10px;
                        }
                    </style>
                    <div class="tutorial">
                        
                        
                        <p>
                            &lt;&lt; <a href="../help/index.jsp">Back to Help index</a>
                        </p>
                        
                        <p>
                            To access the XMAS interface containing the visualizations described below, click the <a href="../visualization/visualization.jsp">Run XMAS</a> link in the top navigation bar.
                            To enter analysis, you must have both an expression data set and a trajectory file loaded.
                        </p>
                        <p>
                            The interface is organized into three primary regions:
                            <ol>
                                <li>
                                    <p>
                                        <b>Top Navigation</b>: Black and orange bars at the top of the page
                                    </p>
                                </li>
                                <li>
                                    <p>
                                        <b>Primary Visualization</b>: The area below the top navigation which is not the sidebar
                                    </p>
                                </li>
                                <li>
                                    <p>
                                        <b>Sidebar</b>: the raised panel on the right hand side of the screen with a gray background (has its own navigation    )
                                    </p>
                                </li>
                            </ol>
                        </p>
                        <p>
                            The top navigation is self explainatory. The visualization and sidebar areas are identified below and described in linked pages.
                        </p>
                        
                        <div style="text-align: center;"">
                             <a href="../help/images/snap_whole.png">
                                <img src="../help/images/snap_whole.png"
                                     style="width: 80%; margin: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                            </a>
                            <p class="img_caption_l">
                                <b>Fig. 1</b>: The XMAS interface
                            </p>
                        </div>
                        
                        <div style="clear: both;">
                            <h2><a href="../help/visualizations.jsp">Primary Data Visualization</a></h2>
                            
                            <div style="text-align: center;">
                                <a href="../help/images/snap_viz.png">
                                    <img src="../help/images/snap_viz.png"
                                         style="width: 50%; margin: 10px;"
                                         class="snap"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption_l">
                                    <b>Fig. 2</b>: The XMAS interface with the primary visualization area highlighted.
                                </p>
                                <p>
                                    
                                </p>
                            </div>
                        </div>
                        
                        <div style="clear: both;">
                            <h2><a href="../help/data_views.jsp">Sidebar</a></h2>
                            
                            <div style="text-align: center;">
                                <a href="../help/images/snap_sidebar.png">
                                    <img src="../help/images/snap_sidebar.png"
                                         style="width: 50%; margin: 10px;"
                                         class="snap"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption_l">
                                    <b>Fig. 3</b>: The XMAS interface with the sidebar area highlighted.
                                </p>
                                <p>
                                    
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