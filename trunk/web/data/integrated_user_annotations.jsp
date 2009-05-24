<%@page import="data_structures.*" %>
<%@page import="data_structures.integrated.*" %>
<%@page import="data_access.*" %>
<%@page import="database_management.*" %>
<%@page import="java.util.Iterator" %>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Probes in Your Data - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/databases.js"></script>
        
    </head>
    
    <body onload="
        window_onload_main('data'); 
        window_onload_data('your_data'); 
        window_onload_int_data('user_annotations');">
        
        <%@ include file="../template/top_navigation.jsp" %>
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">
                    <div id="page_body_main">
                        
                        <h1>Data</h1>
                        
                        <%@ include file="sub_nav.jsp" %>
                        
                        <div id="page_body_content">
                            
                            <%
            String databaseName = request.getParameter("database_name");

            AnnotationDatabase db;
            if (databaseName != null && databaseName.length() > 0) {
                db = new AnnotationDatabase(databaseName);
            } else {
                db = AnnotationDatabaseManager.getActiveDataBase();
                databaseName = db.getName();
            }
            IntegratedUserAnnotations annotations = db.getUserAnnotations();

            int itemsPerPage = 50;
            int pageNumber = 1;
            String pageNo = request.getParameter("page");
            if (pageNo != null) {
                int tempPageNo = Integer.parseInt(pageNo);
                if (tempPageNo > 0 && ((tempPageNo - 1) * itemsPerPage) < annotations.size()) {
                    pageNumber = tempPageNo;
                }
            }
                            %>
                            
                            <h2><%= databaseName%></h2>
                            
                            <%@ include file="integrated_database_sub_nav.jsp" %>
                            
                            <p>This tool allows you to manage the User Defined Annotations in your active database.</p>
                            
                            <p><b><%= annotations.size()%></b> user defined annotations in database.</p>
                            
                            <%
            int dataSize = annotations.size();
            String basePageNavURL = "integrated_probes.jsp?database_name=" + databaseName + "&page=";
            int maxPageCount = (int) Math.ceil((double) dataSize / itemsPerPage);
            int minPage = Math.max(1, pageNumber - 5);
            int maxPage = Math.min(maxPageCount, pageNumber + 5);
                            %>
                            
                            <%
            if (dataSize > itemsPerPage) {
                            %>
                            <%@ include file="../modules/page_navigation.jsp" %>
                            <%            }
                            %>
                            
                            <table class="base_table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Description</th>
                                        <th>Source</th>
                                        <th>Cardinality</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
            for (int i = (pageNumber - 1) * itemsPerPage; i < (pageNumber * itemsPerPage) && i < annotations.size(); i++) {
                IntegratedUserAnnotation anno = (IntegratedUserAnnotation) annotations.get(i);
                String rowClass = "odd";
                if (i % 2 == 0) {
                    rowClass = "even";
                }
                                    %>
                                    <tr class="<%= rowClass%>">
                                        <td>
                                            <%= anno.getID()%>
                                        </td>
                                        <td>
                                            <%= anno.getDescription()%>
                                        </td>
                                        <td>
                                            User
                                        </td>
                                        <td>
                                            <%= anno.getTotalCardinality()%>
                                        </td>
                                    </tr>
                                    <%
            }
                                    %>
                                </tbody>
                            </table>
                            
                            <%
            if (dataSize > itemsPerPage) {
                            %>
                            <%@ include file="../modules/page_navigation.jsp" %>
                            <%            }
                            %>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>