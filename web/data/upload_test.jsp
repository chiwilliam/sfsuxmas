<%@page import="Globals.FileUpload"%>
<%@page import="data_upload.DataFileManager"%>
<%@page import="java.io.File"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>Upload Data: Integrate Your Microarray Data - XMAS: Experiential Microarray Analysis System</title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
        <script type="text/javascript" src="../resources/script/upload.js"></script>
        
    </head>
    
    <body onload="
        window_onload_main('data'); 
        window_onload_data('upload'); 
        nav_upload('upload'); 
        window_onload_upload();">
        
        <%@ include file="../template/top_navigation.jsp" %>
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">
                    
                    <div id="sidebar_container">
                        <div id="sidebar">
                            <%@ include file="../modules/database_connection.jsp" %>
                        </div>
                    </div>
                    
                    <div id="page_body_main">
                        
                        <h1>Data</h1>
                        
                        <%@ include file="sub_nav.jsp" %>
                        
                        <div id="page_body_content">
                            <h2>Upload</h2>
                            
                            <%@ include file="upload_sub_nav.jsp" %>
                            
                            <%
            if (!successfulConnection) {
                            %>
                            <div class="error">
                                Database connection failure! Check MySQL is running and retry.
                            </div>
                            <%                        } else {
                            %>
                            <FORM ACTION="../SUploadFile" METHOD="POST" enctype="multipart/form-data">
                                <%
                                boolean errorMessageAvailable = !(request.getParameter("error") == null);
                                if (errorMessageAvailable) {
                                %>
                                <div class="error">
                                    <%= "There was an error processing your request"%>
                                </div>
                                <%
                                }
                                %>
                                
                                <blockquote>
                                    <div class="entry">
                                        <p>
                                            <input type="file"  name="file" id="file" />
                                        </p>
                                    </div>
                                </blockquote>
                                <p style="text-align: center;">
                                    <INPUT TYPE="SUBMIT" VALUE="Import data" />
                                </p>
                                
                            </FORM>
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