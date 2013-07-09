<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Visualizations";
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
                        <h1>Basic Visualizations & Interactions</h1>
                        
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
                            
                            <a href="../help/images/snap_viz.png">
                                <img src="../help/images/snap_viz.png"
                                     style="width: 50%; float: right; margin-left: 20px;"
                                     alt="" 
                                     title="" />
                            </a>
                            <p class="img_caption">
                                <b>Fig. 1</b>: The XMAS interface with the primary visualization area highlighted.
                            </p>
                            
                            <p>
                                &lt;&lt; <a href="../help/index.jsp">Back to Help index</a>
                            </p>
                            <p>
                                This section concentrates on the primary data visualization area,  as shown in Fig. 1. 
                                To access the XMAS interface containing the visualizations described below, click the <a href="../visualization/visualization.jsp">XMAS</a> link in the top navigation bar.
                                To enter analysis, you must have both an expression data set and a trajectory file loaded.
                            </p>
                            <div style="clear: both;">
                                
                                <h2>Trajectory Visualization</h2>
                                <img src="images/data_viz_1.png" 
                                     style="width: 40%; float: right; margin-left: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption">
                                    <b>Fig. 3</b>: Collapsed trajectories.
                                </p>
                                <p>
                                    The trajectory visualization is rendered based on the discretization (trajectory file) created during data integration. 
                                    The discretization operator associates probes based on similarities in expression profiles at a resolution defined by the user.
                                    The image below, for example, is based on a 0.5 unit discretization. 
                                    Nodes are colored to indicate movement - warm tones for increasing expression, and cold tones for decreasing expression.    
                                </p>
                                
                                <img src="images/snap_popout.png" 
                                     style="width: 25%; margin-right: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption_l" style="width: 25%;">
                                    <b>Fig. 2</b>: An in-place window containing contextual data and operators about the point of interaction - in this case a trajectory node (top left)
                                </p>
                                
                                <p>
                                    Each node is clickable, providing access to in-place inspection windows, as shown in Fig. 2. 
                                    These provide views of and functionality for the data defined by the localized context of the interaction (click).
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h2>Hybrid Visualization</h2>
                                <img src="images/data_viz_2.png" 
                                     style="width: 40%; float: right; margin-left: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption">
                                    <b>Fig. 4</b>: The Hybrid visualization indicates the presence of two well populated trajectory patterns (shown in green and gray), alongside 3-4 smaller, but similar patterns.
                                </p>
                                <p>
                                    The hybrid visualization renders the expression profiles of probes from the same trajectory in the same color, in the context of the mean parent trajectory profile, which is emphasized. 
                                    Trajectories with less than 3 members are not accompanied by the parent profile.
                                </p>
                                <p>
                                    The hybrid visualization bridges the gap between the discretized Trajectory, and the exact Profile visualizations.
                                    It emphasizes popular patterns and groups of probes, but without relying on visual discretization.
                                </p>
                                <p>
                                    <i>(Note, this operator can take up to 20 seconds to render)</i>
                                </p>
                            </div>
                            
                            
                            <div style="clear: both;">
                                <h2>Profile Visualization</h2>
                                <img src="images/data_viz_3.png" 
                                     style="width: 40%; float: right; margin-left: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption">
                                    <b>Fig. 5</b>: The Profile visualization color codes probes which can be visually mapped to probe data views in the sidebar, for example.
                                </p>
                                <p>
                                    Precise time period expression profiles are rendered for the data under analysis, up to 1000 probes.
                                </p>
                                <p>
                                    This visualization allows the user to observe behavior within trajectories, and examine detailed time course behavior for many indivdual probes simultaneously.
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h2>Visual Data Operators</h2>
                                
                                <h3>Collapse</h3>
                                <img src="images/data_viz_4.png" 
                                     style="width: 40%; float: right; margin-left: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption">
                                    <b>Fig. 6</b>: A collapsed view of 82 probes, which share a drop in expression at term.
                                </p>
                                <p>
                                    The collapse data operator performs a discretized vertical translation, which unites expression profiles at a single root bin. Expression movement is preserved, but it is now relative to this root, rather than the actual expression level.
                                </p>
                                <p>
                                    This operator finds similarly shaped trajectories regardless of where trajectories are located across different expression values. 
                                    Thus, by using this operator, trajectories whose member probes share highly correlated expression profiles, can be identified. 
                                    Each such trajectory corresponds to one co-expression pattern. 
                                    It is also possible to use this operator to identify inverse trajectories, offering insight into co regulation.
                                </p>
                            </div>
                            
                            <div style="clear: both;">
                                <h3>Preserve</h3>
                                <img src="images/data_viz_5.png" 
                                     style="width: 40%; float: right; margin-left: 20px;"
                                     class="snap"
                                     alt="" 
                                     title="" />
                                <p class="img_caption">
                                    <b>Fig. 7</b>: A preserved view of the same 82 probes shown in Fig. 6 at their actual expression levels
                                </p>
                                <p>The inverse operator to Collapse, returns data to its original expression range.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>