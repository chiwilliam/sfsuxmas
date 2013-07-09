<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Download";
            String pageName = "Data_Guides";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
    </head>
    
    <body onload="
        wol_top_nav('<%= parentPageName.toLowerCase()%>'); 
        wol_top_sub_nav('<%= pageName.toLowerCase()%>');
          ">
        
        <jsp:include page="../template/top_navigation.jsp">
            <jsp:param name="parent" value="<%= parentPageName %>" />
        </jsp:include>
        
        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">
                    
                    <div id="page_body_main_full">
                        <h1>Data Guides for Loading/Analyzing Your Own Data</h1>
                        <div id="page_body_content">
                            
                            <style>
                                .code {
                                    font-family: courier new;
                                }
                                .img_caption {
                                    text-align: right;
                                    float: right;
                                    clear: both;
                                    font-size: 10px;
                                    width: 40%;
                                }
                                .img_caption_l {
                                    text-align: left;
                                    font-size: 10px;
                                }
                            </style>
                            <h2>System Setup</h2>
                            <div class="tutorial">
                                <p>
                                    XMAS looks within a specific area of the host file system for files that can be integrated. 
                                    It is unlikely that XMAS is looking in the correct location out of the box, so you should use the <b>Data Options</b> page accessible from the <a href="../data/your_data.jsp">Data Index</a> page to specify the root directory of your XMAS data integration:
                                </p>
                                <ol>
                                    <li>
                                        <p>
                                            The interface indicates the validity of the specified directories. Here the data directory is valid (/Users/), but the sub directories for Expression and Knowledge Data cannot be found.
                                        </p>
                                        <a href="../help/images/data_options_1.png">
                                            <img src="../help/images/data_options_1.png" class="snap" style=""
                                                 alt="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" 
                                                 title="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" />
                                        </a>
                                        <p class="img_caption_l">
                                            <b>Fig. 1</b>: The data directory specification interface indicating a valid path, but the absense of the data sub directories
                                        </p>
                                    </li>
                                    <li>
                                        <p>
                                            You can either create the required sub directories within the /Users/ directory, or point XMAS to a directory which has the correct structure. 
                                            To do the latter, you need to specify a different directory path in the text box and clicks update.
                                        </p>
                                        <a href="../help/images/data_options_2.png">
                                            <img src="../help/images/data_options_2.png" class="snap" style=""
                                                 alt="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" 
                                                 title="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" />
                                        </a>
                                        <p class="img_caption_l">
                                            <b>Fig. 2</b>: The data directory specification interface indicating a valid path
                                        </p>
                                    </li>
                                    <li>
                                        <p>
                                            Put your data in the correct directories:
                                        </p>
                                        <a href="../help/images/data_options_3.png">
                                            <img src="../help/images/data_options_3.png" class="snap" style=""
                                                 alt="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" 
                                                 title="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" />
                                        </a>
                                        <p class="img_caption_l">
                                            <b>Fig. 3</b>: A snapshot of the directory structure required by XMAS - a single directory containing two directories; 1) "xd_expression" ; and 2) "xd_knowledge"
                                        </p>
                                    </li>
                                    
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>