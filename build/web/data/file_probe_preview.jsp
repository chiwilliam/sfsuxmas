<%@page import="database_management.*" %>
<%@page import="java.util.Iterator" %>
<%@page import="data_upload.*" %>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Your Data - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
    </head>
    
    <body onload="return window_onload_file_preview('probes');">
        
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
                Probes probes = fr.getProbes(0, 50);
            %>
            
            <h2><%= probes.size() %>+ Probes in file:</h2>
            
            <p>Here are the first <%= probes.size() %></p>
            
            <div class="data">
                
                <%= probes.toTable() %>
                
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