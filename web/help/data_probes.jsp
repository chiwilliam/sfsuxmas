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
                            
                            <h2>Probe & Gene Data</h2>
                            <div class="tutorial">
                                
                                <p>
                                    Integrating probe/gene data creates a new Knowledge library. It is possible to add <a href="../help/data_pathways.jsp">Pathways</a> and <a href="../help/data_labels.jsp">Labels</a> to a knowledge library, but the probe/gene data forms the required core.
                                </p>
                                
                                <h3>File name and location</h3>
                                <a href="../help/images/data_probes_data.png">
                                    <img src="../help/images/data_probes_data.png" style="width: 40%; float: right;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: A sample probe data file
                                </p>
                                <p>  
                                    Probe data files must be prefixed with pr_ and must be located in the Knowledge Data Directory as specified on the System Setup page.
                                </p>
                                
                                <h3>File Structure & Format</h3>
                                <p>
                                    All data files used as input within XMAS must be tab delimited text files *.txt. Expression data must follow these structural conventions: 
                                </p>
                                <ul>
                                    <li><p>First column (shown in <i style="color: blue;">blue</i>): Unique probe identifiers - duplicates will be omitted</p></li>
                                    <li><p>First row (shown in <i style="color: orange;">orange</i>): Data type names/headings</p></li>
                                    <li><p>Remaining data matrix (shown in <i style="color: green;">green</i>): Probe data values</p></li>
                                </ul>
                                
                                
                                <div style="clear: both;">
                                    <h3>Selecting a File</h3>
                                    <a href="../help/images/da_probe_data_creation.png">
                                        <img src="../help/images/da_probe_data_creation.png" style="width: 40%; float: right;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 2</b>: The data integration interface
                                    </p>
                                    <p>
                                        The data installation page populates a drop down menu of files which meet the requirements described above. Select a file from the list.
                                    </p>
                                    
                                    <h3>Name and describe your data</h3>
                                    <p>
                                        Specify a name and description for your probe/gene data. 
                                        If the name you specify has already been used, you will be prompted to specify an original one.
                                        XMAS presents this user defined meta-data to aid you in the selection of the correct set.
                                    </p>
                                </div>
                                
                            </div>
                            
                            <div style="clear: both;">
                            <h2>Managing your Probe & Gene Data</h2>
                            <div class="tutorial">
                                
                                <h3>Adding External Hyperlinks</h3>
                                <a href="../help/images/da_probe_data_links.png">
                                    <img src="../help/images/da_probe_data_links.png" style="width: 40%; float: right;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 3</b>: Probe data type external hyperlinks manager
                                </p>
                                <p>
                                    From the "Your Data" page, navigate to the newly created Knowledge Library by clicking its name in the table of available sets. 
                                    Fig. 3 shows the top module of the knowledge management page.
                                    It lists probe data types along with data entry fields for specifying generalized URL patterns. 
                                    Follow the in page instructions to link probe data values to external applications and repositories.
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