<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Data_Guides";
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
                        <h1>Data Guides for Loading/Analyzing Your Own Data</h1>
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
                                }
                            </style>
                            
                            <h2>Trajectory Files</h2>
                            <div class="tutorial">
                                
                                <h3>Creation</h3>
                                <a href="../help/images/da_trajectory_file_creation.png">
                                    <img src="../help/images/da_trajectory_file_creation.png" style="width: 40%; float: right;" class="snap"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: The trajectory file creation interface
                                </p>
                                <p>  
                                    An expression data set must be active in order to create a trajectory file. 
                                    On the <a href="../data/your_data.jsp">Your Data</a> page, locate the Trajectory Files block. 
                                    If any files exist, you can load one and proceed with analysis, otherwise you must create a new one.
                                </p>
                                <p>  
                                    Use the data summary visualization and statistics to choose a bin size. Name the file that you about to create and click "Create File".
                                </p>
                                
                                <h3>Collapsed and Preserved</h3>
                                <p>  
                                    You will notice that two files are created each time you run through the creation process, one of which has the suffix "_collapsed". These are utilized by the Collapse and Preserve Visual operators described in the Visualizations section of this Help documentation.
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