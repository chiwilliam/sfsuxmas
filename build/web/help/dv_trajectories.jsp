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
                        <h1>Trajectories</h1>
                        <div id="page_body_content">
                            
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
                                    width: 40%;
                                }
                            </style>
                            <div class="tutorial">
                                
                                <p>
                                    The Trajectories Data View is accessible through the Data tab in the sidebar.
                                </p>
                                
                                <h2>Quantification</h2>
                                
                                <a href="../help/images/dv_traj_view.png">
                                    <img src="../help/images/dv_traj_view.png"  class="snap"
                                         style="float: right; width: 35%;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: A snap shot of the Trajectories Data View 
                                </p>
                                
                                <p>
                                    Trajectories are quantified using two measures:
                                </p>
                                <ul>
                                    <li>
                                        <p>
                                            1) <b>Volatility</b>: If a discretized trajectory is defined by its temporally ordered sequence of expression values &lt;e<sub>1</sub>, e<sub>2</sub>, ..., e<sub>N</sub>&gt;, then the volatility of this trajectory is calculated as &sum;<sub>i=2,N</sub> (|e<sub>i</sub> - e<sub>i-1</sub>|). 
                                            These values can be used as an intuitive indication of potential interestingness. 
                                            For example, differentially expressed probes will generally have high volatility, as will probes that represent "noisy" data.
                                        </p>
                                    </li>
                                    <li>
                                        <p>
                                            2) <b>Linear Trend</b>: defined as &sum;<sub>i=2,N</sub>(e<sub>i</sub> - e<sub>i-1</sub>). 
                                            Like volatility, linear trend can provide insight into the relative interestingness of probes. 
                                            Volatility and linear trend can be applied to both discretized trajectories and expression profiles.
                                        </p>
                                    </li>
                                </ul>
                                <p>
                                    These measures are used to rank trajectories from the current analysis - the user can choose which measure to focus on using the buttons in the top right of the Trajectories Data View. 
                                    This simple surfacing mechanism provides an insight into some of the most interesting expression patterns at every stage of analysis.
                                </p>
                                
                                <div style="clear: both;">
                                    <h2>Find Similar</h2>
                                    <p>
                                        Through quantification, ranking and presentation in the Trajectory Data view, interest is likely to be established in one or more trajectory or pattern.
                                        
                                        Users can select trajectories of interest and click "Find Similar". This operator will prepopulate and apply a Trajectory Filter which matches the selected trajectories. 
                                        The user can then manipulate the filter to discover either more general or more specific trajectory patterns - directly broadening or narrowing the focus of analysis through each interaction.
                                    </p>
                                </div>
                                
                                <div style="clear: both;">
                                    <a href="../help/images/dv_traj_filt.png">
                                        <img src="../help/images/dv_traj_filt.png" class="snap" style="float: right; width: 45%;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    
                                    <p class="img_caption">
                                        <b>Fig. 3</b>: The resultant filter and data focus based on "Find similar" for the trajectories selected in Fig. 2.
                                    </p>
                                    
                                    <a href="../help/images/dv_traj_select.png">
                                        <img src="../help/images/dv_traj_select.png" class="snap"  style="width: 45%;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    
                                    <p class="img_caption_l">
                                        <b>Fig. 2</b>: Two similar, volatile trajectories selected within the trajectories data view
                                    </p>
                                </div>
                                
                                
                                
                                <div style="clear: both;">
                                    <h2>Multi probe Trajectories</h2>
                                    
                                    <a href="../help/images/dv_traj_hybrid.png">
                                        <img src="../help/images/dv_traj_hybrid.png"  class="snap"
                                             style="float: right; width: 35%;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 4</b>: Trajectories containing multiple probes are rendered as mini-hybrids
                                    </p>
                                    <p>
                                        The bold black line in each of the trajectory visualizations represents the mean expression of the trajectory as a whole.
                                        Many of the most volatile or dynamic trajectories contain a single probe, however others are composed of many probes.
                                        Such trajectories are rendered according to the Hybrid Visualization method, with the familiar black mean trajectory profile accompanied by red profiles representing the member probes.
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