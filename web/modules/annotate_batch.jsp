<%@page import="annotations.*"%>
<%@page import="data_structures.*"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/modules.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        <script type="text/javascript" src="../resources/script/annotations.js"></script>
        
    </head>
    
    <body onload="return window_onload_annotations('batch');">
        
        <h2>Annotation Manager</h2>
        
        <%@ include file="sub_nav_annotations.jsp" %>
        
        <div id="page_body_content" class="dataTable">
            
            <p>This tool enables you to tag a whole load of probes at the same time.</p>
            
            
            <form action="../SAnnotateBatch" method="POST">
                
                <p class="instruction">
                    1. Choose an annotation
                </p>
                <select style="width: 150px;" name="annotationID">
                    <%
            AnnotationDataAccess ada = new AnnotationDataAccess();
            Annotations annotations = ada.getAnnotations();
            if (annotations != null) {
                for (int i = 0; i < annotations.size(); i++) {
                    Annotation annotation = (Annotation) annotations.get(i);
                    %>
                    <option value="<%= annotation.getID()%>"><%= annotation.getDescription()%></option>
                    <%
                }
            }
                    %>
                </select>            
                
                <p class="instruction">
                    2. Enter your probe IDs
                </p>
                
                <input type="text" name="batch_of_probe_ids" id="batch_of_probe_ids" size="50" value="">
                <i>(Seperate IDs with a comma, e.g. 206059_at, 202381_at,...)</i>
                
                <p>
                    <input TYPE="SUBMIT" VALUE="Okay, tag this set" />
                </p>
            </form>
            
        </div>
        
    </body>
    
</html>