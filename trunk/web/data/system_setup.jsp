<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Data";
            String pageName = "System_Setup";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/options.js"></script>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
        window_onload_options();">
        
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
                                        XMAS looks within a specific area of the host file system for files that can be integrated. Use this tool to tell XMAS where to look.
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <a href="../help/system_setup.jsp" 
                               class="help_button" 
                               alt="Help"
                               title="Help"
                               style="margin: 20px 20px 0 0;">
                                <span>?</span>
                            </a>
                            <h1>System Setup</h1>
                            <div id="page_body_content">
                                
                                <div class="col_header" 
                                     onclick="toggle_area('data_directory');" 
                                     id="data_directory_show_hide" 
                                     alt="Show/Hide your Data Directory" 
                                     title="Show/Hide your Data Directory">Data Directory</div>
                                <div class="indented_div">
                                    <div id="data_directory">
                                        <p>
                                            Your working data directory is currently:
                                            <br />
                                            <pre style="text-align: center;"><%= FileGlobals.getRoot()%></pre>
                                        </p>
                                        <FORM ACTION="../SUpdateRootDir" METHOD="POST">
                                            <p>
                                                <blockquote>
                                                    <div class="entry">
                                                        <p>
                                                            Path: 
                                                            <input type="text" name="root_dir" id="root_dir" size="25" value="<%= FileGlobals.getRoot()%>"
                                                                   onchange="return check_root_dir();" 
                                                                   onblur="return check_root_dir();" 
                                                                   onkeyup="return check_root_dir();" />
                                                            <INPUT TYPE="SUBMIT" VALUE="Update" />
                                                            <span id="root_dir_msg" class="success">
                                                                
                                                            </span>
                                                        </p>
                                                    </div>
                                                </blockquote>
                                                Sample data paths:
                                                <ul>
                                                    <li><b>Windows</b>: C:\\xmas_files\</li>
                                                    <li><b>Mac</b>: /Users/{your_username}/Documents/xmas_files/</li>
                                                </ul>
                                            </p>
                                        </FORM>
                                    </div>
                                </div>
                                
                                <div class="col_header" 
                                     onclick="toggle_area('expression_data_directory');" 
                                     id="expression_data_directory_show_hide" 
                                     alt="Show/Hide your Expression Data Directory" 
                                     title="Show/Hide your Expression Data Directory">Expression Data Directory</div>
                                <div class="indented_div">
                                    <div id="expression_data_directory">
                                        <p>
                                            <input type="text" name="expression_data_dir" id="expression_data_dir" size="45" value="<%= FileGlobals.getExpressionDataRoot()%>" disabled />
                                            
                                            <span id="expression_data_dir_msg" class="success">
                                                
                                            </span>
                                        </p>
                                    </div>
                                </div>
                                
                                <div class="col_header" 
                                     onclick="toggle_area('knowledge_data_directory');" 
                                     id="knowledge_data_directory_show_hide" 
                                     alt="Show/Hide your Knowledge Data Directory" 
                                     title="Show/Hide your Knowledge Data Directory">Knowledge Data Directory</div>
                                <div class="indented_div">
                                    <div id="knowledge_data_directory">
                                        <p>
                                            <input type="text" name="knowledge_dir" id="knowledge_dir" size="45" value="<%= FileGlobals.getKnowledgeDataRoot()%>" disabled />
                                            <span id="knowledge_dir_msg" class="success">
                                                
                                            </span>
                                        </p>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>