<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="java.util.Iterator" %>
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
        <script type="text/javascript" src="../resources/script/probe_attributes.js"></script>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    <div id="sidebar_container">
                        <div id="sidebar">
                            
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
                KnowledgeDataSet db = null;
                db = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(dataSetID, true);
                    %>
                    <div id="page_body_main">
                        
                        <h1>Knowledge Data: <i><%= db.getName()%></i></h1>
                        
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
                            
                            <div class="col_header" onclick="toggle_area('probe_data_types');" id="probe_data_types_show_hide">Probe Data Types</div>
                            <div class="indented_div">
                                <a name="probe_data_types" />
                                <div id="probe_data_types">
                                    <jsp:include page="../data/probe_data_types.jsp">
                                        <jsp:param name="data_id" value="<%= dataSetID %>" />
                                    </jsp:include>
                                </div>
                                
                                <div id="link_assignment_div">
                                    
                                </div>
                            </div>
                            
                            <div class="col_header" onclick="toggle_area('labels');" id="labels_show_hide">Labels</div>
                            <div class="indented_div">
                                <div id="labels">
                                    <a name="labels" />
                                    <jsp:include page="../data/labels.jsp">
                                        <jsp:param name="data_id" value="<%= dataSetID %>" />
                                    </jsp:include>
                                </div>
                            </div>
                            
                            <div class="col_header" onclick="toggle_area('pathways');" id="pathways_show_hide">Pathways</div>
                            <div class="indented_div">
                                <div id="pathways">
                                    <a name="pathways" />
                                    <jsp:include page="../data/pathways.jsp">
                                        <jsp:param name="data_id" value="<%= dataSetID %>" />
                                    </jsp:include>
                                </div>
                            </div>
                            
                            <div class="col_header" onclick="toggle_area('go_terms');" id="go_terms_show_hide">GO Terms</div>
                            <div class="indented_div">
                                <div id="go_terms">
                                    <a name="go_terms" />
                                    <jsp:include page="../data/go_terms.jsp">
                                        <jsp:param name="data_id" value="<%= dataSetID %>" />
                                    </jsp:include>
                                </div>
                            </div>
                            <%
            }
                            %>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>