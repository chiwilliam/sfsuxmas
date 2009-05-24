
<%@page import="database_management.*" %>
<%@page import="java.util.Iterator" %>
<%@page import="data_upload.*" %>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        
    </head>
    
    <body onload="return window_onload_file_preview('samples');">
        
        <%
            String fileName = request.getParameter("fileName");
        %>
        
        <h1>File preview</h1>
        
        <%@ include file="file_preview_sub_nav.jsp" %>
        
        <div id="page_body_content">
            
            <%
            if (fileName != null && fileName.length() > 0) {
            %>
            
            <%
                FileReader fr = new FileReader(fileName, null);
                Samples samples = fr.getSamples();
            %>
            
            <h2><%= samples.size() %> Samples in file:</h2>
            
            <div class="data">
                <%= samples.toTable() %>
            </div>
            
            <%
            } else {
            %>
            
            <h2>Null file name</h2>
            
            <%
            }
            %>
            
        </div>
    </body>
</html>