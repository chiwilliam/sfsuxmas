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
                        <h1>Trajectory Filter</h1>
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
                                
                                <div style="clear: both;">
                                    <a href="../help/images/dv_trajfilter_start.png">
                                        <img src="../help/images/dv_trajfilter_start.png" class="snap" style="width: 40%; float: right;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 1</b>: With a new analysis, the user chooses to enter the data through a shared trajectory characteristic.
                                    </p>
                                </div>
                                
                                <p>
                                    The trajectory filter interface is constructed dynamically to display the trajectory shape patterns in the data set 
                                    It is built upon the active trajectory file. 
                                    The numbers in each cell indicate the number of trajectories which exhibit the characteristic defined by the coordinates (time period, expression movement).
                                    As the user interacts with it, the interface updates to hide impossible paths, and refresh trajectory counts.
                                    These reflective interactions alone provide insight into the dynamic behavior of the data.
                                </p>
                                
                                
                                <div style="clear: both;">
                                    <h2>Drawing characteristics of interest</h2>
                                    <a href="../help/images/dv_trajfilter_steps.png">
                                        <img src="../help/images/dv_trajfilter_steps.png" class="snap" style="width: 40%; float: right;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 2</b>: A series of screenshots capturing user interactions and interface updates during the specification of a trajectory filter
                                    </p>
                                </div>
                                <p>
                                    The user begins by specifying an increase in expression at time period 4. This is followed by a requirement that matching trajectories must then reduce their expression at time period 5.
                                </p>
                                
                                
                                <div style="clear: both;">
                                    <h2>Filtering</h2>
                                    <a href="../help/images/dv_trajfilter_end.png">
                                        <img src="../help/images/dv_trajfilter_end.png" class="snap" style="width: 40%; float: right;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 3</b>: The result of applying the operator - a number of trajactories matching the defined pattern
                                    </p>
                                </div>
                                <p>
                                    As defined by the filter, 5 trajectories are returned which share the user specified characteristics.
                                </p>
                                
                                
                                <div style="clear: both;">
                                    <h2>Reset</h2>
                                    <p>
                                        To reset analysis, or remove the active trajectory filter, uncheck any conditions and click Update Filter (to effectively set a blank filter), or navigate to the "Filters > Remove All" operator in the sidebar navigation menu (note this will remove all filters, not just the trajectory filter).
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