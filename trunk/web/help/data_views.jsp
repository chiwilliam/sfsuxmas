<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Data_Views";
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
                        <h1>Data Views & Operators</h1>
                        
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
                        <div id="tutorial">
                            
                            
                            <a href="../help/images/snap_sidebar.png">
                                <img src="../help/images/snap_sidebar.png"
                                     style="width: 50%; float: right; margin-left: 20px;"
                                     alt="" 
                                     title="" />
                            </a>
                            <p class="img_caption">
                                <b>Fig. 1</b>: The XMAS interface with the sidebar area highlighted.
                            </p>
                            
                            <p>
                                &lt;&lt; <a href="../help/index.jsp">Back to Help index</a>
                            </p>
                            <p>
                                This section concentrates on the sidebar area, as shown in Fig. 1. 
                                To access the XMAS interface containing the visualizations described below, click the <a href="../visualization/visualization.jsp">Run XMAS</a> link in the top navigation bar.
                                To enter analysis, you must have both an expression data set and a trajectory file loaded.
                            </p>
                            <div style="clear: both;">
                                
                                <h3>Summary</h3>
                                
                                <p>
                                    The summary view unites abridged Pathways and Labels Data views (if available) with a module that indicates how focused analysis is.
                                </p>
                                
                                <h3>Data</h3>
                                
                                <ul style="">
                                    
                                    <li><p><a href="../help/dv_probes.jsp">Probes</a></p></li>
                                    
                                    <li style="clear: both;"><p><a href="../help/dv_trajectories.jsp">Trajectories</a></p></li>
                                    
                                    <li style="clear: both;"><p><a href="../help/dv_pathways.jsp">Pathways</a></p></li>
                                    
                                    <li style="clear: both;"><p><a href="../help/dv_labels.jsp">Labels</a></p></li>
                                    
                                </ul>
                                
                                
                                <h3>Filters</h3>
                                
                                <ul style="">
                                    <li style="clear: both;">
                                        <p>
                                            <b>Current</b>: Displays the filters applied to the current analysis
                                        </p>
                                    </li>                                
                                    <li style="clear: both;"><p><a href="../help/dv_trajectory_filter.jsp">Trajectory Filter</a></p></li>
                                    <li style="clear: both;">
                                        <p>
                                            <b>Remove All</b>: Removes all active filters from the current analysis
                                        </p>
                                    </li>
                                </ul>
                                
                                <h3>Highlights</h3>
                                
                                <ul style="">
                                    <li style="clear: both;">
                                        <p>
                                            <b>Current</b>: Displays the highlighted features of the current analysis
                                        </p>
                                    </li>
                                    <li style="clear: both;">
                                        <p>
                                            <b>Remove All</b>: Stops highlighting any highlighted features
                                        </p>
                                    </li>
                                </ul>
                                
                                
                                <h3>Export</h3>
                                <p>
                                    Provides access to a tab delimeted file containing probe identifiers for the current analysis
                                </p>
                                
                                <h3>Capture</h3>
                                <p>
                                    Presents options to save and load analysis
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