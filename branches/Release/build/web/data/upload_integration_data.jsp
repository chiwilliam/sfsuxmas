<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.globals.*"%>
<%@page import="java.io.File"%>
<%@page import="com.sfsu.xmas.session.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "Upload";
            String uploadPageName = "Create_Knowledge_Library";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/upload_integration.js"></script>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
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
                                
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <a href="../help/data_probes.jsp" 
                               class="help_button" 
                               alt="Help"
                               title="Help"
                               style="margin: 20px 20px 0 0;">
                                <span>?</span>
                            </a>
                            <h1>Create a Knowledge Library</h1>
                            <%@ include file="upload_sub_nav.jsp" %>
                            <div id="page_body_content">
                                <FORM ACTION="../SCreateNewKnowledgeDatabase" METHOD="POST" onSubmit="return validate_library_upload();">
                                    
                                    <h3>Name and describe the data:</h3>
                                    <div class="indented_div">
                                        <table>
                                            <tr>
                                                <td>Data Name:</td>
                                                <td>
                                                    <input type="text" name="database_name" id="database_name" size="20" value="" 
                                                           onchange="return check_library_database_name();" 
                                                           onblur="return check_library_database_name();" 
                                                           onkeyup="return check_library_database_name();" />
                                                </td>
                                                <td>
                                                    <span id="data_name_error" class="error">
                                                        You must specify a name
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
                                    
                                    <h3>Install!</h3>
                                    <div class="indented_div">
                                        <p style="text-align: center;">
                                            <INPUT TYPE="SUBMIT" VALUE="Create Knowledge Library" />
                                        </p>
                                    </div>
                                    
                                    <p>
                                        <div id="knowledge_library_creation_message"></div>
                                    </p>
                                    
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