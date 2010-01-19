<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Team";
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
                                <div class="sidebar_header">For details on experiential computing see</div>
                                <div class="sidebar_padding">
                                    <p>
                                        R. Singh and R. Jain R. Singh and R. Jain, From Information-Centric to Experiential Environments, in  Interactive Computation: The New Paradigm, D. Goldin, S. Smolka, and P. Wegner, eds., Springer Verlag, pp. 323-351, 2006
                                        <br /><a href="http://tintin.sfsu.edu/papers/InteractiveComputation-NewParadigm-BookChapter.pdf" target="_blank">PDF</a>
                                    </p>
                                    <p>
                                        Jain, R, Experiential computing. Commun. ACM Vol. 46, No. 7, pp. 48-55, 2003
                                        <br /><a href="http://ngs.ics.uci.edu/pdf/mv_cacm_experiential_computing.pdf" target="_blank">PDF</a>

                                    </p>
                                </div>
                            </div>
                        </div>

                        <div id="page_body_main">
                            <h1>Publications</h1>
                            <div id="page_body_content">
                                
                                <blockquote>
                                    
                                    <div class="paper_title">
                                        <p>
                                            Ben Dalziel, Hui Yang, Rahul Singh, Matthew Gormley, Susan Fisher: "XMAS: An Experiential Approach for Visualization, Analysis, and Exploration of Time Series Microarray Data." BIRD 2008: 16-31
                                            <br /><a href="http://dblp.uni-trier.de/rec/bibtex/conf/bird/DalzielYSGF08" target="_blank">BibTeX</a> | <a href="../files/xmas_bird_paper_2008.pdf">PDF</a>
                                        </p>
                                    </div>
                                    <div class="paper_quote">
                                        <br/><b>ABSTRACT</b>: Time series microarray analysis provides an invaluable insight into
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
                                </blockquote>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>