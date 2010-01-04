<%@page import="database_management.*"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        <script type="text/javascript" src="../resources/script/files.js"></script>
        
        <script src="utils/zapatec.js" type="text/javascript"></script>
        <script src="zptree/src/tree.js" type="text/javascript"></script>
        
    </head>
    
    <body onload="return window_onload()">
        
        <h2>Database Selector</h2>
        
        
        <div id="page_body_content" class="dataTable">
            <p>
                <%

            Database activeDB = DatabaseManager.getActiveDataBase();

            if (activeDB == null) {
                %>
                You do not currently have a database loaded. Please load one from the selection box below.
                <%
                            } else {
                %>
                You currently have the following database loaded: 
                <blockquote><b style="color: #66CC00;"><%= activeDB.getName() %></b></blockquote>
                <%
            }
                %>
            </P>
            
            <form action="../SLoadDatabase?module&returnPage=files" method="POST">
                
                <div id="database_select"><img src="../resources/images/loading.gif" /></div>
                
                <span class="subtext">
                    Not seeing any databases to load?
                    <ul>
                        <li>Have you <a href="../upload_data.jsp" target="_parent">created</a> any yet?</li>
                        <li>Is your database running?</li>
                    </ul>
                </span>
                
                <p><input TYPE="submit" VALUE="Load Database" id="database_select_button" /><p>
                
            </form>
            
        </div>
        
        <h2>File Loader</h2>
        
        <div id="page_body_content">
            
            <p>The files you have created are below. You can <a href="javascript:void(0)" onclick="return refresh_file_table()">Refresh</a> the file list.</p>
            
            <div id="file_load_message"></div>
            
            <div id="file_list_holder"><img src="../resources/images/loading.gif" /></div>
            
            <!--<div id="filter_list_holder"></div> -->
            
            <div style="margin:10px; font-size: 10px;"><a href="http://www.zapatec.com/website/main/products/prod3/">Zapatec Javascript Tree</a></div>
            
        </div>
        
        <h2>File Creation</h2>
        
        <div id="page_body_content" class="dataTable">
            
            <div id="file_creation_message"></div>
            
            <p><a href="javascript:void(0)" onclick="return generate_file(0)">Create files</a></p>
            
        </div>
        
    </body>
    
</html>