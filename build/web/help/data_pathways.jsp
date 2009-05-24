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
                            
                            <h2>Pathway Data</h2>
                            <div class="tutorial">
                                
                                <p>
                                    Pathway data is added to an active Knowledge Library. To create a Knowledge Library, you must <a href="../help/data_probes.jsp">integrate Probe/Gene</a> data.
                                    The probe identifiers in the Pathway Data file described below should match the probes that form the core of the active Knowledge Library.
                                </p>
                                
                                <h3>File name and location</h3>
                                <a href="../help/images/da_pathway_data_sample.png">
                                    <img src="../help/images/da_pathway_data_sample.png" style="width: 40%; float: right;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: A sample pathway data file
                                </p>
                                <p>  
                                    Pathway data files must be prefixed with pa_ and must be located in the Knowledge Data Directory as specified on the System Setup page.
                                </p>
                                
                                <h3>File Structure</h3>
                                <p>
                                    All data files used as input within XMAS must be tab delimited text files *.txt. Expression data must follow these structural conventions: 
                                </p>
                                <ul>
                                    <li><p>First column (shown in <i style="color: blue;">blue</i>): Unique probe identifiers - duplicates will be omitted</p></li>
                                    <li><p>Second column (shown in <i style="color: green;">green</i>): Pathway names and sources delimeted as follows:
                                    <pre>name // source /// name // source /// ...</pre></p></li>
                                </ul>
                                
                                <div style="clear: both;">
                                    <h3>Selecting a File</h3>
                                    <a href="../help/images/da_pathway_data_creation.png">
                                        <img src="../help/images/da_pathway_data_creation.png" style="width: 40%; float: right;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 2</b>: The pathway data integration interface
                                    </p>
                                    <p>
                                        The data installation page populates a drop down menu of files which meet the requirements described above. Select a file from the list.
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