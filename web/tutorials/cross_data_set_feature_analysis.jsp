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
                            
                            <h2>Cross Data Set Feature Analysis</h2>
                            
                            <p>
                                Differentiating between data noise, background cell cycle variation and true biological involvement in the process under study, is a well-established challenge presented by the analysis of time series microarray data.
                                We believe that by providing context and augmenting the data under study through an information rich environment, we aid the user in making these decisions. 
                                One way we achieve this within XMAS is by enabling users to see how genes identified during the analysis of one data set, behave in a second, related data set.
                            </p>
                            
                            <p>
                                Many time series microarray experiments are composed of more than one data set, capturing the dynamics of a drug treatment along side a control, for example. 
                                Differences and similarities between gene expression profiles across complementary data sets provide valuable information to the user, from which they can derive better hypotheses and results.
                            </p>
                            
                            <p>
                                Tutorial slideshow notes:
                                <ol>
                                    <li>Tutorial title slide</li>
                                    <li>The user isolates a set of probes that share a common expression profile characteristic at the second time period, as indicated in the Trajectory Shape Filter Tool in loaded into the sidebar (RHS)</li>
                                    <li>Clicking the comparison button to the top left of the primary visualization, (only available when a secondary data set is loaded), renders the expression profiles of the probes under analysis for the active data set, (as seen in slide 2) alongside the expression profiles of the probes under analysis for the secondary data set (dashed lines)</li>
                                    <li>Highlighting a probe with the comparison still on enables users to assess any cross data set variability in context</li>
                                </ol>
                            </p>
                            
                            <div style="text-align: center;">
                                <iframe src='http://docs.google.com/EmbedSlideshow?docid=dgvwz2vs_363cczq95dh&amp;size=l' frameborder='0' width='700' height='559'></iframe>
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