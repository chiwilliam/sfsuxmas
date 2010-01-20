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
                            
                            <h2>Surfacing Dynamic Expression Profiles</h2>
                            
                            <p>
                                Genes with dynamic expression profiles, i.e. those with non-static gene expression, are commonly of most interest to biologists. Although dynamic expression is not proof of differential expression, (dynamic expression can just as easily be attributed to data noise or cell cycle variation) any insight that can be drawn from these genes, biologically significant or otherwise, is an important step in the analysis process.
                            </p>
                            
                            <p>
                                XMAS makes it possible to surface the most dynamic genes at any stage of analysis and use them as the basis for more focused analysis.
                            </p>
                            
                            <p>
                                <b>Global vs. Local</b>: XMAS makes it possible to surface the most dynamic genes at every stage of analysis, no matter how focused the analysis is. This means that a user can just as easily highlight the most dynamic expression patterns within a sub set of a pathway, as the entire data set.
                            </p>
                            <p>
                                <b>Volatility vs. Trend</b>: XMAS provides two measures of dynamic expression: 1) Volatility which measures how much an expression profile moves, regardless of direction; and 2) Linear Trend which measures the magnitude and direction of the expression profile in a single direction.
                            </p>
                            
                            <p>
                                Tutorial slideshow notes:
                                <ol>
                                    <li>Tutorial title slide</li>
                                    <li>The default data view, showing the "<i>Data &gt; Trajectories</i>" data view loaded into the sidebar (RHS). Trajectories are ranked (by default) by <b>Volatility</b>. These are, therefore, the more volatile expression profiles in the data set under analysis (no filters)</li>
                                    <li>The user selects the top two trajectories based on their similarity, determined through visual inspection</li>
                                    <li>The filter is applied in the form of a Trajectory Shape Filter, which is dynamically defined by the shapes of the trajectories selected in the previous step</li>
                                    <li>The precise visualization is selected to inspect the expression profiles of the matching probes in detail. The "<i>Data &gt; Probes</i>" data view is loaded into the sidebar to see which probes these are</li>
                                    <li><b>A second example now:</b> The user applies a Label filter to focus analysis on a set of probes determined to be differentially expressed through prior analysis</li>
                                    <li>The "<i>Data &gt; Trajectories</i>" data view is again loaded into the sidebar (RHS), but this time, the "Linear Trend" button is selected - this presents the most dynamic expression profiles as defined by this metric</li>
                                    <li>The user selects a number of trajectories which reduce their expression slightly, until the final time period where the expression of each of them drops off further</li>
                                    <li>The corresponding Trajectory Shape Filter is built and applied automatically, resulting in an analysis focused on probes from the defined label, that express with these user defined expression profile characteristics</li>
                                </ol>
                            </p>
                            
                            <div style="text-align: center;">
                                <iframe src='http://docs.google.com/EmbedSlideshow?docid=dgvwz2vs_354f6rgmtft&amp;size=l' frameborder='0' width='700' height='559'></iframe>
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