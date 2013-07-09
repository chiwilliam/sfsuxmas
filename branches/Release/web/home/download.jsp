<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Home";
            String pageName = "Download";
%>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title><%= pageName%> - <%= SiteGlobals.SITE_PAGE_TITLE%></title>
        
        <%@ include file="../template/base_imports.jsp" %>
        
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
                    <div id="page_body_sub">
                        <div id="sidebar_container">
                            <div id="sidebar">
                                
                                <div class="sidebar_header">Data format?</div>
                                <div class="sidebar_padding">
                                    <p>
                                        The sample data, available here, provides working examples of the data types and formats required.
                                    </p>
                                </div>
                                
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Download</h1>
                            <div id="page_body_content">
                                
                                <p>
                                    For additional downloads, visit our <a href="http://code.google.com/p/sfsuxmas/downloads/list" target="_blank">Project Download Page</a>
                                </p>
                                
                                <h2>xmas.war</h2>
                                
                                <p>
                                    XMAS is available for download and local deployment through the link below. Check the <a href="../home/system.jsp">System Description</a> page for requirements (MySQL and a Java web container for example).
                                </p>
                                <br />
                                <p style="text-align: center;">
                                    <a href="../files/xmas.war"><img src="../resources/images/download.png" style="width: 150px;" /></a>
                                    <pre style="text-align: center;">xmas.war</pre>
                                </p>
                                
                                <h2>Database Script</h2>
                                
                                <p>
                                    XMAS requires a database to store the data that you want to analyze. The file below contains commands which will setup a new, custom database within your MySQL installation for use within XMAS.
                                </p>
                                <br />
                                <p style="text-align: center;">
                                    <a href="../files/db_setup.sql"><pre style="text-align: center;">db_setup.sql</pre></a>
                                </p>
                                
                                <h2>Sample Data</h2>
                                
                                <p>
                                    The zip file linked below contains sample data, which can be integrated into XMAS, or used as a template for your own data. 
                                    The complete expression data file is available from the <a href="http://www.ncbi.nlm.nih.gov/projects/geo/query/acc.cgi?acc=GSE5999">GEO Accession Viewer</a>, (specifically the Series Matrix File(s) - you will need to <a href="../help/data_expression.jsp">format the file</a> prior to integration).
                                </p>
                                <p>
                                    To obtain additional data for analysis, we recommend <a href="http://www.ncbi.nlm.nih.gov/sites/GDSbrowser/" target="_blank">Browsing DataSets</a> with the key words "time course". 
                                    Accompanying knowledge files can be obtained through links to the appropriate chips sets (platforms). 
                                    For the data set linked above (<a href="http://www.ncbi.nlm.nih.gov/projects/geo/query/acc.cgi?acc=GSE5999" target="_blank">Series GSE5999</a>) for example, the Platforms sub category links to pages for each of the chips used. 
                                    Visiting one of these pages - <a href="http://www.ncbi.nlm.nih.gov/projects/geo/query/acc.cgi?acc=GPL96" target="_blank">Affymetrix GeneChip Human Genome U133 Array Set HG-U133A</a> for example - the "Download full table..." provides access to a data file which can be formatted into a <a href="../help/data_pathways.jsp">pathways</a> or <a href="../help/data_probes.jsp">probes/genes</a> data file, for integration.
                                </p>
                                <br />
                                <p style="text-align: center;">
                                    <a href="../files/sample_data.zip"><pre style="text-align: center;">sample_data.zip</pre></a>
                                </p>
                                
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>