<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Home";
            String pageName = "Publications";
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
                                <div class="sidebar_header">Most Recent</div>
                                <div class="sidebar_padding">
                                    <p>
                                        Ben Dalziel, Hui Yang, Rahul Singh, Matthew Gormley, Susan Fisher: XMAS: An Experiential Approach for Visualization, Analysis, and Exploration of Time Series Microarray Data. BIRD 2008: 16-31
                                        <br /><a href="http://dblp.uni-trier.de/rec/bibtex/conf/bird/DalzielYSGF08" target="_blank">BibTeX</a> | <a href="../files/xmas_bird_paper_2008.pdf">PDF</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Publications</h1>
                            <div id="page_body_content">
                                
                                <h2>2008</h2>
                                
                                <a name="bird_08" /><h3>July, 7-9: BIRD 2008</h3>
                                <h4>Technical University of Vienna, Austria</h4>
                                
                                <blockquote>
                                    
                                    <div class="paper_title">"XMAS: An Experiential Approach for Visualization, Analysis, and Exploration of Time Series Microarray Data"</div>
                                    <div class="paper_authors">Ben Dalziel, Hui Yang, Rahul Singh, Matthew Gormley, and Susan Fisher</div>
                                    <div class="paper_quote">
                                        <b>ABSTRACT</b>: Time series microarray analysis provides an invaluable insight into 
                                        the genetic progression of biological processes, such as pregnancy and disease. 
                                        Many algorithms and systems exist to meet the challenge of extracting knowledge from 
                                        the resultant data sets, but traditional methods limit user interaction, 
                                        and depend heavily on statistical, black box techniques. In this paper we present 
                                        a new design philosophy based on increased human computer synergy to over come these 
                                        limitations, and facilitate an improved analysis experience. We present an implementation 
                                        of this philosophy, XMAS (eXperiential Microarray 
                                        Analysis System) which supports a new kind of "sit forward" analysis through 
                                        visual interaction and interoperable operators. Domain knowledge, (such as 
                                        pathway information) is integrated directly into the system to aid users in their 
                                        analysis. In contrast to the sit back, algorithmic approach of traditional systems, 
                                        XMAS emphasizes interaction and the power, and knowledge transfer potential of facilitating 
                                        an analysis in which the user directly experiences the data. 
                                        Evaluation demonstrates the significance and necessity of such a philosophy 
                                        and approach, proving the efficacy of XMAS not only as tool for validation  
                                        and sense making, but also as an unparalleled source of serendipitous results.
                                    </div>
                                    <div class="paper_quote">
                                        <b>Full Text</b>: Available from the links below.
                                    </div>
                                </blockquote>
                                <div class="paper_links_block">
                                    <span class="paper_link_title"><b>Related Links:</b></span>
                                    <div class="paper_links">
                                        <ul>
                                            <li><a href="../files/xmas_bird_paper_2008.pdf">PDF</a></li>
                                            <li><a href="http://www.birdconf.org" target="_blank">Bioinformatics Research and Development (BIRD)</a></li>
                                            <li><a href="http://www.springer.com/computer/computational+biology+and+bioinformatics/book/978-3-540-70598-7" target="_blank">Communications in Computer and Information Science (Springer Verlag)</a></li>
                                            <li><a href="http://www.tuwien.ac.at/tu_vienna/" target="_blank">Vienna University of Technology</a></li>
                                        </ul> 
                                    </div>
                                    
                                    <span class="paper_link_title"><b>Related Files:</b></span>
                                    <div class="paper_links">
                                        <ul>
                                            <li><a href="../files/xmas_bird_presentation_2008.ppt" target="_blank">Presentation on XMAS</a> given by Rahul Singh at BIRD '08 and the University of Vienna</li>
                                        </ul> 
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