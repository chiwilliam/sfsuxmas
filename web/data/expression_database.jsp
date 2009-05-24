<%@page import="java.util.Iterator" %>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>

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
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        window_onload_files();">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    <div id="sidebar_container">
                        <div id="sidebar">
                            
                            <!--<div class="sidebar_header">Back to...</div> -->
                            <div class="sidebar_padding">
                                <p>
                                    Return to <a href="../data/your_data.jsp">Your Data</a>
                                </p>
                            </div>
                        </div>
                    </div>
                    
                    <%
            int dataSetID = -1;
            try {
                dataSetID = Integer.parseInt(request.getParameter("data_id"));
            } catch (NumberFormatException ex) {
                    %>
                    <p class="error">
                        Error: Non numeric or null data ID for attribute: "data_id"
                    </p>
                    <%            }

            if (dataSetID >= 0) {
                ExpressionDataSet db = null;
                db = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
                    %>
                    <div id="page_body_main">
                        
                        <h1>Expression Data: <i><%= db.getName()%></i></h1>
                        
                        <div id="page_body_content">
                            
                            <h2>Basics</h2>
                            <p>
                                <ul>
                                    <li>ID: <%= db.getID()%></li>
                                    <li>Name: "<%= db.getName()%>"</li>
                                    <li>Description: "<%= db.getDescription()%>"</li>
                                </ul>
                            </p>
                            
                            <h2>Overview</h2>
                            
                            <p>
                                <a name="probes" />
                                <jsp:include page="../modules/probe_renderer.jsp">
                                    <jsp:param name="data_id" value="<%= dataSetID %>" />
                                </jsp:include>
                            </p>
                            <p>
                                <a name="time_periods" />
                                <jsp:include page="../data/time_periods.jsp">
                                    <jsp:param name="data_id" value="<%= dataSetID %>" />
                                </jsp:include>
                            </p>
                            <p>
                                <a name="samples" />
                                <jsp:include page="../data/samples.jsp">
                                    <jsp:param name="data_id" value="<%= dataSetID %>" />
                                </jsp:include>
                            </p>
                            
                        </div>
                    </div>
                    
                    <%
            }
                    %>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>