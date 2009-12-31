<%@page import="data_structures.*" %>
<%@page import="data_structures.integrated.*" %>
<%@page import="data_access.*" %>
<%@page import="database_management.*" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Set" %>
<%@page import="xml.*"%>
<%@page import="java.sql.ResultSet"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Probes in Your Data - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        <script type="text/javascript" src="../resources/script/databases.js"></script>
        <script type="text/javascript" src="../resources/script/probe_attributes.js"></script>
        
    </head>
    
    <body onload="window_onload_main('data'); window_onload_data('your_data'); window_onload_int_data('probe_attributes');">
        
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
            String basePageNavURL = "integrated_probe_attributes.jsp?database_name=" + databaseName + "&page=";
            AnnotationDatabase db;
            if (databaseName != null && databaseName.length() > 0) {
                db = new AnnotationDatabase(databaseName);
            } else {
                db = AnnotationDatabaseManager.getActiveDataBase();
            }
                            %>
                            <h2>Library: <%= databaseName%></h2>
                            
                            <%@ include file="integrated_database_sub_nav.jsp" %>
                            
                            <%
            int itemsPerPage = 50;
            int pagesEitherSide = 5;
                            %>
                            
                            <%
            IntegratedProbeAnnotations probes = new IntegratedProbeAnnotations();
            if (db != null) {
                probes = db.getProbes();
                databaseName = db.getName();
            }
            int dataSize = probes.size();
            int maxPageCount = (int) Math.ceil((double) dataSize / itemsPerPage);

            int pageNumber = 1;
            String pageNo = request.getParameter("page");
            if (pageNo != null) {
                int tempPageNo = Integer.parseInt(pageNo);
                if (tempPageNo > 0 && tempPageNo <= maxPageCount) {
                    // Specified page number is valid
                    pageNumber = tempPageNo;
                }
            }
            int minPage = Math.max(1, pageNumber - pagesEitherSide);
            int maxPage = Math.min(maxPageCount, pageNumber + pagesEitherSide);


            int firstProbeIndex = (pageNumber - 1) * itemsPerPage;
            int lastProbeIndex = pageNumber * itemsPerPage;
            HashMap<String, Boolean> attributeUsage = db.getAttributeUsage();
            ProbeAttributeHeaderList headers = probes.getProbeAttributes();
                            %>
                            
                            <form action="../SAddAttributeLink?database_name=<%= databaseName%>" method="POST">
                                <p>
                                    <b>Create an attribute link</b>
                                    <small>
                                        <a href="javascript: void(0);" onclick="toggle_area('create_probe_attribute');" id="create_probe_attribute_show_hide">Show</a>
                                    </small>
                                </p>
                                <div id="create_probe_attribute" style="display: none;">
                                    <p>
                                        Use this tool to associate external links with probe attributes. Use <b>%_IDENTIFIER_%</b> as the attribute identifier
                                        <blockquote>
                                            <div class="entry">
                                                <p>
                                                    <select name="attribute_name" id="attribute_name" style="width: 150px;">
                                                        <%
            for (int headIndex = 0; headIndex < headers.size(); headIndex++) {
                ProbeAttributeHeader head = headers.get(headIndex);
                boolean inUse = true;
                if (attributeUsage.containsKey(head.getAttribute())) {
                    inUse = (Boolean) attributeUsage.get(head.getAttribute());
                }
                                                        %>
                                                        <option value="<%= head.getAttribute()%>"><%= head.getAttribute()%></option>
                                                        <%
            }
                                                        %>
                                                    </select>
                                                    , Link: 
                                                    <input type="text" name="attribute_link" size="30" value="" />
                                                </p>
                                                <p>
                                                    <small>
                                                        Examples:
                                                        <ul>
                                                            <li>http://www.google.com/search?source=ig&hl=en&rlz=&=&q=%_IDENTIFIER_%&btnG=Google+Search</li>
                                                            <li>http://genome-www5.stanford.edu/cgi-bin/SMD/source/sourceResult?option=Name&choice=Gene&organism=Hs&criteria=%_IDENTIFIER_%</li>
                                                        </ul>
                                                        
                                                    </small>
                                                </p>
                                            </div>
                                        </blockquote>
                                    </p>
                                    
                                    <p style="text-align: center;">
                                        <input type="submit" value="Assign Link" />
                                    </p>
                                    
                                </div>
                            </form>
                            
                            
                            <p>
                                <b>Existing attribute links</b>
                                <small>
                                    <a href="javascript: void(0);" onclick="toggle_area('existing_probe_attributes');" id="existing_probe_attributes_show_hide">Show</a>
                                </small>
                            </p>
                            <div id="existing_probe_attributes" style="display: none;">
                                <p>
                                    <%

            HashMap<String, String> attriMap = db.getAttributeLinks();

                                    %>
                                    <table class="base_table" id="attri_link_table">
                                        <thead>
                                            <th>Attribute</th>
                                            <th>Link</th>
                                            <th />
                                            <th />
                                        </thead>
                                        <tbody>
                                            
                                            <%
            Set keys = attriMap.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                String attri = (String) it.next();
                                            %>
                                            <tr id="attribute_link_row_<%= attri%>">
                                                <td>
                                                    <%= attri%>
                                                </td>
                                                <td>
                                                    <input type="text" value="<%= (String) attriMap.get(attri)%>" size="35" />
                                                </td>
                                                <td>
                                                    <input type="button" value="Update" onclick="" disabled />
                                                </td>
                                                <td>
                                                    <input type="button" value="Delete" onclick="deleteLinkAssociation('<%= attri%>', '<%= databaseName%>');" />
                                                </td>
                                            </tr>
                                            <%
            }
                                            %>
                                        </tbody>
                                    </table>
                                </p>
                            </div>
                            
                            <p>This tool allows you to manage the probe attributed from within your active annotation database. Use the checkboxes to choose which attributes you want to display throughout the system.</p>
                            
                            
                            <%@ include file="../modules/page_navigation.jsp" %>
                            <p>
                                <table class="base_table">
                                    <thead>
                                        <tr>
                                            
                                            <form action="../SUpdateAttributeUsage?database_name=<%= databaseName%>" method="POST">
                                                
                                                <th><span style="font-variant: underlined;">ID:</span></th>
                                                <%
            for (int headIndex = 0; headIndex < headers.size(); headIndex++) {
                ProbeAttributeHeader head = headers.get(headIndex);
                boolean inUse = true;
                if (attributeUsage.containsKey(head.getAttribute())) {
                    inUse = (Boolean) attributeUsage.get(head.getAttribute());
                }
                                                %>
                                                <th style="">
                                                    <INPUT TYPE="CHECKBOX" name="attribute_checkbox_<%= head.getAttribute()%>" value="<%= head.getAttribute()%>" id="attribute_checkbox_<%= head.getAttribute()%>" <% if (inUse) {%>checked="checked"<% }%> />
                                                           <br />
                                                    <%= head.getAttribute()%>
                                                </th>
                                                <%
            }
                                                %>
                                                <th>
                                                    <p>
                                                        <INPUT TYPE="SUBMIT" VALUE="Update" />
                                                    </p>
                                                </th>
                                            </form>
                                            
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%



            for (int i = firstProbeIndex; i < lastProbeIndex && i < dataSize; i++) {
                IntegratedProbeAnnotation probe = (IntegratedProbeAnnotation) probes.get(i);
                ProbeAttributeList attributes = probe.getProbeAttributes();
                String rowClass = "odd";
                if (i % 2 == 0) {
                    rowClass = "even";
                }
                                        %>
                                        <tr class="<%= rowClass%>">
                                            <td><b><%= probe.getID()%></b></td>
                                            <%
                                                        for (int headIndex = 0; headIndex < attributes.size(); headIndex++) {
                                                            ProbeAttribute value = (ProbeAttribute) attributes.get(headIndex);
                                                            ProbeAttributeHeader pah = value.getHeader();
                                                            if (pah.isLinked()) {
                                            %>
                                            <td><a href="<%= value.getLink()%>"><%= value.getAttribute()%></a></td>
                                            <%
                                                } else {
                                            %>
                                            <td><%= value.getAttribute()%></td>
                                            <%
                                                            }
                                                        }
                                            %>
                                        </tr>
                                        
                                        <%
            }
                                        %>
                                        
                                    </tbody>
                                </table>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>