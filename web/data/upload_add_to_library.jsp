<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="java.io.File"%>
<%@page import="com.sfsu.xmas.session.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "Upload";
            String uploadPageName = "Add_to_Library";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >

<head>
    
    <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
    
    <%@ include file="../template/base_imports.jsp" %>
    
    <script type="text/javascript" src="../resources/script/upload_integration.js"></script>
    <script type="text/javascript" src="../resources/script/probes_and_pathways.js"></script>
    
</head>


<body onload="
    wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
    wol_top_sub_nav('<%= pageName.toLowerCase()%>');
    wol_upload_sub_nav('<%= uploadPageName.toLowerCase()%>');">

<jsp:include page="../template/top_navigation.jsp">
    <jsp:param name="parent" value="<%= parentPageName %>" />
</jsp:include>

<%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
%>

<div id="page_body_container">
    <div id="page_body">
        <div id="page_body_block">
            <div id="page_body_sub">
                
                <div id="sidebar_container">
                    <div id="sidebar">
                        <div class="sidebar_header">Active Knowledge Library</div>
                        <div class="sidebar_padding">
                            <p>
                                <%
            if (kDB == null) {
                                %>
                                <div class="error">No Active Knowledge Library</div>
                                <%                                } else {
                                %>
                                <div class="success">Active Knowledge Library = "<%= kDB.getName()%>"</div>
                                <% }%>
                            </p>
                        </div>
                        <div class="sidebar_header_s">File Types</div>
                        <div class="sidebar_padding">
                            <p>
                                All files must be tab delimeted with the extension *<%= FileGlobals.DATA_FILE_EXTENSION%>.
                            </p>
                        </div>
                    </div>
                </div>
                
                <div id="page_body_main">
                    
                    <h1>Add to Knowledge Library</h1>
                    <%@ include file="upload_sub_nav.jsp" %>
                    
                    <div id="page_body_content">
                        These tools enable you to add more data to the active Knowledge Library.
                        <%
            if (kDB == null) {
                        %>
                        <div class="error">No Active Knowledge Library</div>
                        <%                                } else {
                        %>
                        
                        <a href="../help/data_pathways.jsp" 
                           class="help_button" 
                           alt="Help"
                           title="Help"
                           style="margin: 20px 20px 0 0;">
                            <span>?</span>
                        </a>
                        
                        
                        
                        
                        
                        
                        
                        <h2>Install Probe Attributes</h2>
                        <form action="../SIntegrateProbeAttributes" method="POST">
                        
                                    <%
            int numberOfFileOptions = 0;
            // ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            File[] files = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getDataFileDAO().getProbeAttributeDataFiles();
                                    %>
                                    <h3>Select a file to use:</h3>
                                    <div class="indented_div">
                                        <style>
                                            .compact {
                                                padding: 0;
                                                margin: 0 0 0 30px;
                                            }
                                        </style>
                                        <p>
                                            Probe data files must be:
                                            <ul class="compact"><li>located here "<%= FileGlobals.getKnowledgeDataRoot()%>"</li></ul>
                                            <ul class="compact"><li>prefixed with "<b><%= FileGlobals.PROBE_DATA_FILE_PREFIX%></b>"</li></ul>
                                            <ul class="compact"><li>a tab delimeted file with the file extension "<%= FileGlobals.DATA_FILE_EXTENSION%>"</li></ul>
                                            e.g. "<%= FileGlobals.getKnowledgeDataRoot()%><%= FileGlobals.PROBE_DATA_FILE_PREFIX%>test_data.<%= FileGlobals.DATA_FILE_EXTENSION%>".
                                        </p>
                                        <%
            if (files != null && files.length > 0) {
                                        %>
                                        <p>
                                            File: <select name="file_name" id="file_name" style="width: 300px;">
                                                <%
                                            for (int i = 0; i < files.length; i++) {
                                                String fileName = files[i].getName();
                                                String[] periodTokens = fileName.split("\\.");
                                                if (periodTokens.length > 1) {
                                                    String extension = periodTokens[periodTokens.length - 1];
                                                    String name = fileName.substring(0, fileName.length() - extension.length() - 1);
                                                    numberOfFileOptions++;
                                                %>
                                                <option value="<%= name%>"><%= name%></option>
                                                <%
                                                }
                                            }
                                                %>
                                            </select>
                                        </p>
                                        <%
                                        } else {
                                        %>
                                        <div class="error">No Files</div>
                                        <%            }
                                        %>
                                    </div>
                            
                            <h3>Install</h3>
                            <div class="indented_div">
                                <p style="text-align: center;">
                                    <input TYPE="SUBMIT" VALUE="Integrate pathway data">
                                </p>
                            </div>
                        </form>
                        
                        
                        
                        
                        
                        
                        <h2>Install Pathway Data</h2>
                        <form action="../SIntegratePathwayData" method="POST">
                            
                            <%
                            File[] pathwayFiles = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getDataFileDAO().getPathwayDataFiles();
                            %>
                            
                            <h3>Select a file to use:</h3>
                            <div class="indented_div">
                                
                                <%
                            if (pathwayFiles != null && pathwayFiles.length > 0) {
                                %>
                                <p>
                                    File: <select name="pathway_file_name" id="pathway_file_name" style="width: 200px;">
                                        <%
                                    if (pathwayFiles != null) {
                                        for (int i = 0; i < pathwayFiles.length; i++) {
                                            String fileName = pathwayFiles[i].getName();

                                            String[] periodTokens = fileName.split("\\.");
                                            if (periodTokens.length > 1) {
                                                String extension = periodTokens[periodTokens.length - 1];
                                                String name = fileName.substring(0, fileName.length() - extension.length() - 1);
                                                if (extension.equals("txt")) {
                                        %>
                                        <option value="<%= name%>"><%= name%></option>
                                        <%
                                                }
                                            }
                                        }
                                    }
                                        %>
                                    </select>
                                </p>
                                <%
                                } else {
                                %>
                                <div class="error">No Files</div>
                                <%            }
                                %>
                            </div>
                            
                            <h3>Install</h3>
                            <div class="indented_div">
                                <p style="text-align: center;">
                                    <input TYPE="SUBMIT" VALUE="Integrate pathway data">
                                </p>
                            </div>
                        </form>
                        
                        
                        
                        <h2>Install Gene Ontology Data</h2>
                        <form action="../SIntegrateGOData" method="POST">
                            
                            <%
                            File[] goFiles = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getDataFileDAO().getGODataFiles();
                            %>
                            
                            <h3>Select a file to use:</h3>
                            <div class="indented_div">
                                
                                <%
                            if (goFiles != null && goFiles.length > 0) {
                                %>
                                <p>
                                    File: <select name="go_file_name" id="go_file_name" style="width: 200px;">
                                        <%
                                    if (goFiles != null) {
                                        for (int i = 0; i < goFiles.length; i++) {
                                            String fileName = goFiles[i].getName();

                                            String[] periodTokens = fileName.split("\\.");
                                            if (periodTokens.length > 1) {
                                                String extension = periodTokens[periodTokens.length - 1];
                                                String name = fileName.substring(0, fileName.length() - extension.length() - 1);
                                                if (extension.equals("txt")) {
                                        %>
                                        <option value="<%= name%>"><%= name%></option>
                                        <%
                                                }
                                            }
                                        }
                                    }
                                        %>
                                    </select>
                                </p>
                                <%
                                } else {
                                %>
                                <div class="error">No Files</div>
                                <%            }
                                %>
                            </div>
                            
                            <h3>Install</h3>
                            <div class="indented_div">
                                <p style="text-align: center;">
                                    <input TYPE="SUBMIT" VALUE="Integrate GO data">
                                </p>
                            </div>
                        </form>
                        
                        <a href="../help/data_labels.jsp" 
                           class="help_button" 
                           alt="Help"
                           title="Help"
                           style="margin: 20px 20px 0 0;">
                            <span>?</span>
                        </a>
                        <h2>Label/Group Probes</h2>
                        <p>
                            Probes are inherently related to other probes at many levels of abstraction. Examples include probes that share a common chromosome, and differentially expressed probes that are extracted from statistical analysis.
                        </p>
                        <p>
                            It is possible to assign such groupings based on observations and shared characteristics within analysis. This tool provides complementary functionality to group a predefined list of probe identifiers.
                        </p>
                        
                        <form name="batch_labeler_form" action="../SLabelProbeBatch" method="POST">
                        
                        
                        <%
                        File[] labelFiles = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getDataFileDAO().getLabelDataFiles();
                        %>
                        
                        <h3>Select a file to use:</h3>
                        <div class="indented_div">
                            
                            <%
                            if (labelFiles != null && labelFiles.length > 0) {
                            %>
                            
                            <p>
                                File containing probe identifiers: <select name="probe_id_file_name" id="file_name" style="width: 200px; margin-left: 10px;">
                                    <%
                                if (labelFiles != null) {
                                    for (int i = 0; i < labelFiles.length; i++) {
                                        String fileName = labelFiles[i].getName();

                                        String[] periodTokens = fileName.split("\\.");
                                        if (periodTokens.length > 1) {
                                            String extension = periodTokens[periodTokens.length - 1];
                                            String name = fileName.substring(0, fileName.length() - extension.length() - 1);
                                    %>
                                    <option value="<%= name%>"><%= name%></option>
                                    <%
                                        }
                                    }
                                }
                                    %>
                                </select>
                            </p>
                            <%
                            } else {
                            %>
                            <div class="error">No Files</div>
                            <%            }
                            %>
                        </div>
                        
                        <%
                            if (labelFiles != null && labelFiles.length > 0) {
                        %>
                        <h3>Choose a label to assign:</h3>
                        <div class="indented_div">
                            <%
                            Labels labels = kDB.getLabels();
                            boolean hasExistingLabels = labels != null && labels.size() > 0;
                            if (hasExistingLabels) {%>
                            <table width="100%">
                                
                                <tr>
                                    <td width="30px"><input type="radio" name="label_type" value="existing_label" onclick="change_label_type();" disabled></td>
                                    <td>
                                        <div class="col_header_collapsed" id="existing_label_show_hide">Use an existing label: [<i>Not currently supported</i>]</div>
                                        <div class="indented_div">
                                            <div id="existing_label" style="display: none;">
                                                <table width="100%">
                                                    <tr>
                                                        <td width="30%">Label:</td>
                                                        <td width="70%">
                                                            <%@ include file="../labels/label_select.jsp" %>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                
                                <tr>
                                <td><input type="radio" name="label_type" value="new_label" onclick="change_label_type();"></td>
                                <td>
                                <% }%>
                                <div class="col_header_collapsed" id="new_label_show_hide" <% if (!hasExistingLabels) {%>onclick="toggle_area('new_label');"<% }%>>Create and use a new label:</div>
                                <div class="indented_div">
                                <div id="new_label" <% if (hasExistingLabels) {%>style="display: none;"<% }%>>
                                     <table width="100%">
                                <tr>
                                    <td width="30%">New Label Name:</td>
                                    <td width="70%">
                                        <input type="text" name="label_name" id="label_name" size="20" value="" />
                                    </td>
                                </tr>
                                <tr valign="top">
                                    <td>New Label Description:</td>
                                    <td colspan="2">
                                        <textarea name="label_description" id="label_description" cols="36" rows="5" wrap="virtual"></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <% if (hasExistingLabels) {%>
                    </td>
                    </tr>
                    </table>
                    <% }%>
                </div>
                
                <h3>Group Probes</h3>
                <div class="indented_div">
                    <p style="text-align: center;">
                        <input TYPE="SUBMIT" VALUE="Label Probes" />
                    </p>
                </div>
                <%            }
                %>
                </form>
                <%            }
                %>
                
            </div>
        </div>
    </div>
</div>
</div>
</div>
<%@ include file="../template/footer.jsp" %>
</body>
</html>