<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "Your_Data";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/databases.js"></script>
        <script type="text/javascript" src="../resources/script/nav_switcher.js"></script>
        
        <script type="text/javascript" src="../resources/script/files.js"></script>
        
        <!-- Scripts for collapsable lists -->
        <script type="text/javascript" src="../resources/script/utils/zapatec.js"></script>
        <script type="text/javascript" src="../resources/script/zptree/src/tree.js"></script>
        
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
                        <h1>Your Data</h1>
                        <div id="page_body_content">
                            <p>
                                We have preinstalled some data sets for you to explore in XMAS. 
                                Use the tools below to load an <i>Expression Data Set</i>, and an appropriate <i>Knowledge Library</i>, then proceed to XMAS to begin experiencing your data.
                            </p>
                            
                            <h2>Expression Data</h2>
                            
                            <div class="col_header" onclick="toggle_area('your_data');" id="your_data_show_hide" title="Show/Hide Expression Data Sets">Expression data</div>
                            <div class="indented_div">
                                <div id="expression_database_load_message"></div>
                                <div id="your_data">
                                    <p class="block_summary">
                                        The Expression Data Sets listed below capture the expression of probes measured over a number of samples, and then grouped into time periods. 
                                        Click the data types for more information. You can load a data set by clicking its associated button.
                                    </p>
                                    <div id="expression_data_table">
                                        <%@ include file="databases.jsp" %>
                                    </div>
                                    
                                    
                                    
                                    
                                    <a href="../help/data_trajectory_files.jsp" 
                                       class="help_button" 
                                       alt="Help"
                                       title="Help"
                                       style="margin: 20px 20px 0 0;">
                                        <span>?</span>
                                    </a>
                                    <div class="col_header" onclick="toggle_area('your_files');" id="your_files_show_hide" title="Show/Hide">Trajectory Files</div>
                                    <div class="indented_div">
                                        <div id="file_load_message"></div>
                                        <div id="your_files">
                                            <p class="block_summary">
                                                These files are discretized representations of the data contained within the active Expression Data Set. 
                                                You can load an existing file or create a new one to your own specification.
                                            </p>
                                            <div id="trajectory_file_table">
                                                <%@ include file="files.jsp" %>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                            
                            <h2>Knowledge</h2>
                            
                            <div class="col_header" onclick="toggle_area('your_libraries');" id="your_libraries_show_hide" title="Show/Hide Knowledge Libraries">Knowledge Libraries</div>
                            <div class="indented_div">
                                <div id="knowledge_database_load_message"></div>
                                <div id="your_libraries">
                                    <p class="block_summary">
                                        Knowledge Libraries contain data that can be integrated into analysis to enrich the exploratory environment. 
                                        They can contain probe/gene information, labels and pathway information. You can load a Knowledge Library by clicking its associated button.
                                    </p>
                                    <div id="knowledge_data_table">
                                        <%@ include file="databases_integrated.jsp" %>
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