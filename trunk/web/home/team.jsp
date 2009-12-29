<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Home";
            String pageName = "Team";
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
                                <div class="sidebar_header">Who do I contact?</div>
                                <div class="sidebar_padding">
                                    <p>
                                        <b>Hui Yang</b>:<br /><img border="0" src="../resources/images/contact.gif" alt="Contact Information"/> <a href="mailto:huiyang@cs.sfsu.edu">huiyang@cs.sfsu.edu</a>
                                    </p>
                                    <p>
                                        <b>Rahul Singh</b>:<br /><img border="0" src="../resources/images/contact.gif" alt="Contact Information"/> <a href="mailto:rsingh@cs.sfsu.edu">rsingh@cs.sfsu.edu</a>
                                    </p>
                                    <br />
                                </div>
                                <div class="sidebar_header">What do I cite?</div>
                                <div class="sidebar_padding">
                                    <p>
                                        Ben Dalziel, Hui Yang, Rahul Singh, Matthew Gormley, Susan Fisher: XMAS: An Experiential Approach for Visualization, Analysis, and Exploration of Time Series Microarray Data. BIRD 2008: 16-31<br /><a href="http://dblp.uni-trier.de/rec/bibtex/conf/bird/DalzielYSGF08" target="_blank">BibTeX</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                        
                        <div id="page_body_main">
                            <h1>Meet the XMAS Team</h1>
                            <div id="page_body_content">
                                
                                <p>
                                    XMAS is a collaboration between members of the Departments of Computer Science and Biology at San Francisco State University, and a team of biologists from the University of California San Francisco.
                                </p>
                                
                                <table style="width: 100%;">
                                    <tr>
                                        <td style="text-align: center; width: 50%;"><img src="../resources/images/sfsu_logo.jpg" style="height: 40px; margin-top: 10px;" /></td>
                                        <td style="text-align: center; width: 50%;"><img src="../resources/images/ucsf_logo.gif" style="height: 40px; margin-top: 10px;" /></td>
                                    </tr>
                                </table>
                                
                                <div class="team_member_block">
                                    <img src="../resources/images/rahul.jpg" class="team_image" />
                                    <a name="singh" />
                                    <h3>Rahul Singh</h3>
                                    <div class="paper_links_block">
                                        <span class="paper_link_title"><b>Affiliations:</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li><a href="http://cs.sfsu.edu" target="_blank">Department of Computer Science</a> at San Francisco State University</li>
                                                <li>This research is partially funded by a grant from the National Science Foundation IIS-0644418 (CAREER)</li>
                                            </ul> 
                                        </div>
                                        <span class="paper_link_title"><b>Links</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li>Home page: <a href="http://tintin.sfsu.edu/" target="_blank">http://tintin.sfsu.edu/</a></li>
                                                <li><a href="http://dblp.uni-trier.de/db/indices/a-tree/s/Singh:Rahul.html">DBLP</a></li>
                                            </ul> 
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="team_member_block">
                                    <img src="../resources/images/hui.jpg" class="team_image" />
                                    <a name="yang" />
                                    <h3>Hui Yang</h3>
                                    <div class="paper_links_block">
                                        <span class="paper_link_title"><b>Affiliations:</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li><a href="http://cs.sfsu.edu" target="_blank">Department of Computer Science</a> at San Francisco State University</li>
                                                <li>This research is partially funded by a SFSU CCLS Mini Grants</li>
                                            </ul> 
                                        </div>
                                        <span class="paper_link_title"><b>Links</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li>Home page: <a href="http://cose-stor.sfsu.edu/~huiyang/" target="_blank">http://cose-stor.sfsu.edu/~huiyang/</a></li>
                                                <li><a href="http://dblp.uni-trier.de/db/indices/a-tree/y/Yang:Hui.html">DBLP</a></li>
                                            </ul> 
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="team_member_block">
                                    <img src="../resources/images/ben.jpg" class="team_image" />
                                    <a name="dalziel" />
                                    <h3>Ben Dalziel</h3>
                                    <div class="paper_links_block">
                                        <span class="paper_link_title"><b>Affiliations:</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li><a href="http://cs.sfsu.edu" target="_blank">Department of Computer Science</a> at San Francisco State University</li>
                                            </ul> 
                                        </div>
                                        <span class="paper_link_title"><b>Links</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li><a href="http://www.linkedin.com/in/bendalziel" target="_blank">Linkedin Profile</a></li>
                                                <li><a href="http://dblp.uni-trier.de/db/indices/a-tree/d/Dalziel:Ben.html">DBLP</a></li>
                                            </ul> 
                                        </div>
                                    </div>
                                </div>

                                <div class="team_member_block">
                                    <img src="../resources/images/william.jpg" class="team_image" />
                                    <a name="murad" />
                                    <h3>William Murad</h3>
                                    <div class="paper_links_block">
                                        <span class="paper_link_title"><b>Affiliations:</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li><a href="http://cs.sfsu.edu" target="_blank">Department of Computer Science</a> at San Francisco State University</li>
                                            </ul>
                                        </div>
                                        <span class="paper_link_title"><b>Links</b></span>
                                        <div class="paper_links">
                                            <ul>
                                                <li>Skype: whemurad</li>
                                                <li>MSN: whemurad@hotmail.com</li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                
                                <div id="ucsf" style="clear: both;">
                                    <h2 style="height: 40px;">Collaborators</h2>
                                    
                                    <div class="team_member_block">
                                        <img src="../resources/images/susan.jpg" class="team_image" />
                                        <a name="fisher" />
                                        <h3>Susan Fisher</h3>
                                        <div class="paper_links_block">
                                            <span class="paper_link_title"><b>Affiliations:</b></span>
                                            <div class="paper_links">
                                                <ul>
                                                    <li>Departments of Cell and Tissue Biology, Anatomy, Obstetrics, Gynecology and Reproductive Sciences</li>
                                                    <li>Pharmaceutical Chemistry Institute for Regeneration Medicine and the Human embryonic Stem Cell Program</li>
                                                </ul> 
                                            </div>
                                            <span class="paper_link_title"><b>Links</b></span>
                                            <div class="paper_links">
                                                <ul>
                                                    <li><a href="http://dentistry.ucsf.edu/fisherlab/index.html">Fisher Lab @ UCSF</a></li>
                                                    <li><a href="http://dentistry.ucsf.edu/fisherlab/contact_susan.html">Contact Information</a></li>
                                                </ul> 
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="team_member_block">
                                        <img src="../resources/images/jonathan.png" class="team_image" />
                                        <a name="jonathon" />
                                        <h3>Jonathan Stillman</h3>
                                        <div class="paper_links_block">
                                            <span class="paper_link_title"><b>Affiliations:</b></span>
                                            <div class="paper_links">
                                                <ul>
                                                    <li><a href="http://biology.sfsu.edu/" target="_blank">Department of Biology</a> at San Francisco State University</li>
                                                </ul> 
                                            </div>
                                            <span class="paper_link_title"><b>Links</b></span>
                                            <div class="paper_links">
                                                <ul>
                                                    <li><a href="http://online.sfsu.edu/~stillmaj/">Stillman Lab @ the Romberg Tiburon Center for Environmental Studies</a></li>
                                                    <li><a href="http://array.sfsu.edu/ilat/">Porcelain Crab Array Database (PCAD)</a></li>
                                                </ul> 
                                            </div>
                                        </div>
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