<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Tutorials";
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
                        
                        <h1>Tutorials</h1>
                        <div id="page_body_content">
                            
                            <h2>Exposing Differential Expression within a given Time Period</h2>
                            
                            <p>
                                Based on the experimental design and/or domain knowledge, users commonly enter analysis with expectations surrounding certain time periods and the phenotypic variation at them.
                            </p>
                            <p>
                                For example, in a study of the human maternal interface [<a href="http://endo.endojournals.org/cgi/content/abstract/148/3/1059" target="_blank">Winn06</a>, <a href="http://www.ncbi.nlm.nih.gov/projects/geo/query/acc.cgi?acc=GSE5999" target="_blank">GEO</a>], the data owner hypothesized that there would be increased negative differential gene expression towards the end of pregnancy, as the placenta prepared for delivery and began shutting down.
                            </p>
                            <p>
                                XMAS empowers users to query their data for these kinds of features using the Trajectory Filter Tool, described below.
                            </p>
                            
                            <p>
                                Tutorial slideshow notes:
                                <ol>
                                    <li>Tutorial title slide</li>
                                    <li>The default data view, showing the Trajectory Filter Tool loaded into the sidebar (RHS)</li>
                                    <li>The user specifies filter criteria in the 5th time period, building and refining their query with each click (yellow checked boxes)</li>
                                    <li>The filter is applied, resulting in a focused analysis on 10 probes that match the characteristics defined by the filter (positive differential expression at the final time period)</li>
                                    <li>The precise visualization is selected to inspect the expression profiles of the matching probes in detail</li>
                                    <li>The data collapse operator is applied to reduce the data to a common root, better facilitating pattern based inspection</li>
                                </ol>
                            </p>
                            
                            <div style="text-align: center;">
                                <iframe src='http://docs.google.com/EmbedSlideshow?docid=dgvwz2vs_346235nsfdp&amp;size=l' frameborder='0' width='700' height='559'></iframe>
                            </div>
                            
                            <p>
                                <h3><a href="index.jsp">Back to the tutorial index...</a></h3>
                            </p>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>