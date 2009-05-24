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
                            
                            <h2>Label Data</h2>
                            <div class="tutorial">
                                
                                <p>
                                    Label data is added to an active Knowledge Library. To create a Knowledge Library, you must <a href="../help/data_probes.jsp">integrate Probe/Gene</a> data.
                                    The probe identifiers in the Label Data file described below should match the probes that form the core of the active Knowledge Library.
                                </p>
                                
                                <h3>File name and location</h3>
                                <a href="../help/images/da_label_data_creation.png">
                                    <img src="../help/images/da_label_data_creation.png" style="width: 40%; float: right;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: The label data integration interface
                                </p>
                                <p>  
                                    Label data files must be prefixed with la_ and must be located in the Knowledge Data Directory as specified on the System Setup page.
                                </p>
                                
                                <h3>File Structure</h3>
                                <p>
                                    All data files used as input within XMAS must be tab delimited text files *.txt. Expression data must follow these structural conventions: 
                                </p>
                                <ul>
                                    <li><p>First column: Unique probe identifiers - duplicates will be omitted</p></li>
                                </ul>
                                
                                <h3>Selecting a File</h3>
                                <p>
                                    The data installation page populates a drop down menu of files which meet the requirements described above. Select a file from the list.
                                </p>
                                
                                <h3>Enter a Label and Description</h3>
                                <p>
                                    This will be the heading under which the probes are united.
                                    The name should be short and the description more verbose.
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