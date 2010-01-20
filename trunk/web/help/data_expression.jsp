<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
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
                            
                            <h2>Loading Your Expression Data</h2>
                            <div class="tutorial">
                                
                                <h3>File name and location</h3>
                                <p>  
                                    Expression files must be prefixed with ex_ and must be located in the Expression Data Directory as specified on the System Setup page.
                                </p>
                                
                                
                                <h3>File Structure</h3>
                                <a href="../help/images/data_upload_1.png">
                                    <img src="../help/images/data_upload_1.png"
                                         style="width: 40%; float: right; margin-left: 20px;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 1</b>: An example of a valid expression data file
                                </p>
                                <p>
                                    All data files used as input within XMAS must be tab delimited text files *.txt. Expression data must follow these format conventions: 
                                </p>
                                
                                <ul>
                                    <li><p>First column (shown in <i style="color: blue;">blue</i>): Unique probe identifiers - duplicates will be omitted</p></li>
                                    <li><p>First row (shown in <i style="color: orange;">orange</i>): Short sample names/descriptions</p></li>
                                    <li><p>Remaining data matrix (shown in <i style="color: green;">green</i>): Numeric expression values. Nulls can be left blank or replaced by a null string</p></li>
                                </ul>
                                <h3>Data Range</h3>
                                <p>
                                    Expression data should undergo a logarithmic transformation onto a linear scale prior to integration within XMAS. 
                                    Currently, XMAS will not automatically convert data. 
                                    We expect to support simple data transformations as part of the integration process in the near future.
                                </p>
                                
                                <div style="clear: both;">
                                    
                                    <h3>Selecting a File</h3>
                                    <a href="../help/images/data_upload_2.png">
                                        <img src="../help/images/data_upload_2.png"
                                             style="width: 40%; float: right; margin-left: 20px;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 2</b>: File selection and the resulting preview table
                                    </p>
                                    <p>
                                        The expression data installation page populates a drop down menu of files which meet the requirements described above. Selecting a file from the list updates a small preview table, which indicates the number of samples contained with the data, and lists them.
                                    </p>
                                </div>
                                
                                <div style="clear: both;">
                                    <h3>Name and describe your data</h3>
                                    <a href="../help/images/data_upload_3.png">
                                        <img src="../help/images/data_upload_3.png"
                                             style="width: 40%; float: right; margin-left: 20px;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 3</b>: Name and description data entry
                                    </p>
                                    <p>
                                        XMAS presents meta-data to aid you in the selection of the correct set. Specify a name and description for your data. If the name you specify has already been used, you will be prompted to specify an original one.
                                    </p>
                                </div>
                                
                                <div style="clear: both;">
                                    <h3>Tell XMAS about the structure of the data</h3>
                                    <a href="../help/images/data_upload_4.png">
                                        <img src="../help/images/data_upload_4.png"
                                             style="width: 40%; float: right; margin-left: 20px;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 4</b>: Time period sample distribution
                                    </p>
                                    <p>
                                        Time series microarray data commonly contains a number of replicate sample per time period. Select the number of time periods represented by your data, click Go and indicate how many samples fall into each time period (>= 1). The total number of samples distributed between time periods must equal the number of samples in the file, as indicated by the file preview box, in this case 36.
                                    </p>
                                </div>
                                
                                <div style="clear: both;">
                                    <h3>Install</h3>
                                    <a href="../help/images/data_upload_5.png">
                                        <img src="../help/images/data_upload_5.png"
                                             style="width: 40%; float: right; margin-left: 20px;"
                                             alt="" 
                                             title="" />
                                    </a>
                                    <p class="img_caption">
                                        <b>Fig. 5</b>: Install
                                    </p>
                                    <p>
                                        Review your installation parameters and click install.
                                    </p>
                                </div>
                                
                                
                            </div>
                            
                            <h2>Null values</h2>
                            <div class="tutorial">
                                
                                <p>
                                    Null values are handled during the data integration process, which begins once the user clicks install in the instructions above.
                                    Where possible, missing values within a time period are replaced by the median of the non-null values from the time period.
                                    If a time period contains all null values, it will be replaced by the median expression of the remaining, non-null sample values.
                                    If a probe has all null values, it will be omitted.
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