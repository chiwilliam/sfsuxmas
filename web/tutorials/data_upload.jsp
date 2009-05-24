<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Tutorials";
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
                        <h1>Data Integration</h1>
                        <div id="page_body_content">
                            
                            <h2>Expression Data Installation</h2>
                            <div class="tutorial">
                                <p>
                                    <i>N.B. Admin only</i>
                                </p>
                                
                                <h3>Data Requirements</h3>
                                
                                <div class="col_header" onclick="toggle_area('fname_loc');" id="fname_loc_show_hide">File name and location</div>
                                <div class="indented_div">
                                    <div id="fname_loc">
                                        <p>  
                                            Expression files must be prefixed with ex_ and must be located in the Expression Data Directory as specified on the Data Options Page.
                                        </p>
                                    </div>
                                </div>
                                
                                <div class="col_header" onclick="toggle_area('file_structure');" id="file_structure_show_hide">File Structure</div>
                                <div class="indented_div">
                                    <div id="file_structure">
                                        <p>
                                            All data files used as input within XMAS must be tab delimited text files *.txt. Expression data must follow these structural conventions: 
                                        </p>
                                        
                                        <ul>
                                            <li><p>First column (<i style="color: blue;">blue</i>): Unique probe identifiers - duplicates will be omitted</p></li>
                                            <li><p>First row (<i style="color: orange;">orange</i>): Short sample names/descriptions</p></li>
                                            <li><p>Remaining data matrix (<i style="color: green;">green</i>): Numeric expression values. Nulls can be left blank or replaced by a null string</p></li>
                                        </ul>
                                        <img src="images/data_upload_1.png" />
                                    </div>
                                </div>
                                <br />
                                <h3>Data Installation</h3>
                                
                                <div class="col_header" onclick="toggle_area('file_selection');" id="file_selection_show_hide">Selecting a File</div>
                                <div class="indented_div">
                                    <div id="file_selection">
                                        <p>
                                            The expression data installation page populates a drop down menu of files which meet the requirements described above. Selecting a file from the list updates a small preview table, which indicates the number of samples contained with the data, and lists them.
                                        </p>
                                        <img src="images/data_upload_2.png" />
                                    </div>
                                </div>
                                
                                <div class="col_header" onclick="toggle_area('name_file');" id="name_file_show_hide">Name and describe your data</div>
                                <div class="indented_div">
                                    <div id="name_file">
                                        <p>
                                            XMAS presents meta-data to aid you in the selection of the correct set. Specify a name and description for your data. If the name you specify has already been used, you will be prompted to specify an original one.
                                        </p>
                                        <img src="images/data_upload_3.png" />
                                    </div>
                                </div>
                                
                                <div class="col_header" onclick="toggle_area('no_tps');" id="no_tps_show_hide">Tell XMAS about the structure of the data</div>
                                <div class="indented_div">
                                    <div id="no_tps">
                                        <p>
                                            Time series microarray data commonly contains a number of replicate sample per time period. Select the number of time periods represented by your data, click Go and indicate how many samples fall into each time period (>= 1). The total number of samples distributed between time periods must equal the number of samples in the file, as indicated by the file preview box, in this case 36.
                                        </p>
                                        <img src="images/data_upload_4.png" />
                                    </div>
                                </div>
                                
                                <div class="col_header" onclick="toggle_area('import');" id="import_show_hide">Install</div>
                                <div class="indented_div">
                                    <div id="import">
                                        <p>
                                            Review your installation parameters and click install.
                                        </p>
                                        <img src="images/data_upload_5.png" />
                                    </div>
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