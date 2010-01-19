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
                            
                            <p>
                                Download and install XMAS by following the <a href="../help/local_install_windows_32bit.jsp">Local Installation</a> instructions.
                            </p>
                            
                            <p>
                                &lt;&lt; <a href="../help/index.jsp">Back to Help index</a>
                            </p>
                            
                            <style>
                                i {
                                    font-size: 10px;
                                }
                            </style>
                            
                            <ul style="">
                                <li><p><a href="../help/system_setup.jsp">System Setup</a>: <i>[Local installation only]</i></p></li>
                                <img src="../help/images/data_type_setup.png" style="float: right;" />
                                <p>- XMAS looks for your data files in a specific location on your computer. Make sure you tell XMAS where to look, and have the correct directory structure and naming conventions. Individual file formats are described in the sections below.</p>
                            </ul>
                            
                            <h2>Expression Data</h2>
                            <ul>
                                <li style="clear: both;"><p><a href="../help/data_expression.jsp">Expression Data</a>: <i>[Local installation only]</i></p></li>
                                <img src="../help/images/data_type_expression.png" style="float: right;" />
                                <p>- Expression data is the focus of analysis, capturing the expression of thousands of probes (genes) over a series of samples, grouped into time periods</p>
                                
                                <br />
                                <li style="clear: both;"><p><a href="../help/data_trajectory_files.jsp">Trajectory Files</a></p></li>
                                <p>- Trajectory files are discretized representations of an expression data set.</p>
                            </ul>
                            
                            <h2>Knowledge</h2>
                            <ul>
                                <li style="clear: both;"><p><a href="../help/data_probes.jsp">Probe & Gene Data</a>: <i>[Local installation only] | [Optional]</i></p></li>
                                <img src="../help/images/data_type_probes.png" style="float: right;" />
                                <p>- Probes are identifiers of genes on a microarray chip. This data augments a single probe ID with many other categories of data relating to the gene it maps to. Examples include descriptions, other common IDs and chromosome location data.</p>
                                
                                <br />
                                <li style="clear: both;"><p><a href="../help/data_pathways.jsp">Pathway Data</a>: <i>[Local installation only] | [Optional]</i></p></li>
                                <img src="../help/images/data_type_pathways.png" style="float: right;" />
                                <p>- A pathway is a set of interactions between a group of genes. It is possible to integrate pathway membership data so that these known interactions and relationships might enrich analysis.</p>
                                
                                <br />
                                <li style="clear: both;"><p><a href="../help/data_labels.jsp">Labels</a>: <i>[Local installation only] | [Optional]</i></p></li>
                                <img src="../help/images/data_type_labels.png" style="float: right;" />
                                <p>- Labels provide users with the ability to group probes under a common label. Originally used to facilitate analysis in the context of differentially expressed genes, labels are flexible enough to capture other groupings such as chromosomes and GO functional categories.</p>
                            </ul>
                            
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>