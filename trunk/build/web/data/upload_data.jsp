<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="java.io.File"%>
<%@page import="com.sfsu.xmas.dao.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "Upload";
            String uploadPageName = "Upload";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/upload.js"></script>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        window_onload_upload();
        wol_upload_sub_nav('<%= uploadPageName.toLowerCase()%>');">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_block">
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                <div class="sidebar_header">About this tool</div>
                                <div class="sidebar_padding">
                                    <p>
                                        Use this tool to install an Expression Data Set from a tab delimeted file.
                                    </p>
                                </div>
                                <div class="sidebar_header_s">Supported File Types</div>
                                <div class="sidebar_padding">
                                    <p>
                                        Expression data files must be prefixed with "<%= FileGlobals.EXPRESSION_DATA_FILE_PREFIX%>"
                                    </p>
                                    <p>
                                        All data files must be tab delimeted text files i.e. *.<%= FileGlobals.DATA_FILE_EXTENSION%>
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <a href="../help/data_expression.jsp" 
                               class="help_button" 
                               alt="Help"
                               title="Help"
                               style="margin: 20px 20px 0 0;">
                                <span>?</span>
                            </a>
                            
                            <h1>Install Expression Data</h1>
                            <%@ include file="upload_sub_nav.jsp" %>
                            <div id="page_body_content">
                                <FORM ACTION="../SUploadData" METHOD="POST" onSubmit="return validate_upload();">
                                    
                                    <%
            int numberOfFileOptions = 0;
            File[] files = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getDataFileDAO().getExpressionDataFiles();
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
                                            <ul class="compact"><li>prefixed with "<b><%= FileGlobals.EXPRESSION_DATA_FILE_PREFIX%></b>"</li></ul>
                                            <ul class="compact"><li>a tab delimeted file with the file extension "<%= FileGlobals.DATA_FILE_EXTENSION%>"</li></ul>
                                            e.g. "<%= FileGlobals.getKnowledgeDataRoot()%><%= FileGlobals.EXPRESSION_DATA_FILE_PREFIX%>test_data.<%= FileGlobals.DATA_FILE_EXTENSION%>".
                                        </p>
                                        
                                        <%
            if (files != null && files.length > 0) {
                                        %>
                                        <table>
                                            <tr>
                                                <td>
                                                    File: <select name="file_name" id="file_name" style="width: 200px;"
                                                                  onchange="return populate_preview_table();" 
                                                                  onblur="return populate_preview_table();" 
                                                                  onkeyup="return populate_preview_table();">
                                                        <%
                                            if (files != null) {
                                                for (int i = 0; i < files.length; i++) {
                                                    File file = files[i];
                                                    String fileName = file.getName();
                                                    String[] periodTokens = fileName.split("\\.");
                                                    if (periodTokens.length > 1) {
                                                        String extension = periodTokens[periodTokens.length - 1]; // final dot
                                                        String name = fileName.substring(0, fileName.length() - extension.length() - 1);
                                                        numberOfFileOptions++;
                                                        %>
                                                        <option value="<%= name%>"><%= name%></option>
                                                        <%
                                                    }
                                                }
                                            }
                                                        %>
                                                    </select>
                                                </td>
                                                <td>
                                                    <div id="file_preview" style="display: block;">
                                                        <p>
                                                            <table class="file_preview">
                                                                <thead>
                                                                    <tr>
                                                                        <td />
                                                                        <th>
                                                                            #
                                                                        </th>
                                                                        <th>
                                                                            Sample Descriptions
                                                                        </th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <tr>
                                                                        <th>Samples</th>
                                                                        <td id="preview_sample_count">-</td>
                                                                        <td id="preview_samples"></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </p>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                        <%
                                        } else {
                                        %>
                                        <div class="error">No Files</div>
                                        <%            }
                                        %>
                                    </div>
                                    
                                    
                                    <%
            if (files != null && files.length > 0) {
                                    %>
                                    <h3>Name and describe the data:</h3>
                                    <div class="indented_div">
                                        <table>
                                            <tr>
                                                <td>Data Name:</td>
                                                <td>
                                                    <input type="text" name="database_name" id="database_name" size="20" value="" 
                                                           onchange="return check_expression_data_name();" 
                                                           onblur="return check_expression_data_name();" 
                                                           onkeyup="return check_expression_data_name();" />
                                                </td>
                                                <td>
                                                    <span id="file_name_error" class="error">
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td>Data Description:</td>
                                                <td colspan="2">
                                                    <textarea name="database_description" id="database_description" cols="36" rows="5" wrap="virtual"></textarea>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    
                                    
                                    <h3>How many time periods are represented by your data?</h3>
                                    <div class="indented_div">
                                        <p>
                                            Number of Time Periods:
                                            <select name="timePeriodCount" id="timePeriodCount" style="width: 100px;">
                                                <%    for (int i = 2;
                                                i <= 60; i++) {
                                                %>
                                                <option value="<%= i%>"><%= i%></option>
                                                <%
                                        }
                                                %>
                                            </select>
                                            
                                            <input type="button" value="Go" onclick="return get_time_period_descriptors();" />
                                        </p>
                                        <div id="timePeriodDescriptor"></div>
                                    </div>
                                    
                                    <div id="intall_button" style="display: none;">
                                        
                                        <h3>Install</h3>
                                        <div class="indented_div">
                                            <p style="text-align: center;">
                                                <INPUT TYPE="SUBMIT" VALUE="Install Expression Data" id="upload_button" disabled />
                                            </p>
                                            <p>
                                                <i style="font-size: 11px;">Note: Installation might take up to 8 minutes</i>
                                            </p>
                                        </div>
                                        
                                        <p>
                                            <div id="file_creation_message"></div>
                                        </p>
                                        
                                    </div>
                                    <%            }
                                    %>
                                </FORM>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>