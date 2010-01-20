<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Help";
            String pageName = "Home";
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
                        <h1>Probes</h1>
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
                            <div class="tutorial">
                                
                                <p>
                                    There are three complementary data views for Probes (shown in Fig. 1-3) and a set of operators which are described below. The Probe Data View is accessible through the Data tab in the sidebar, and also in trajectory node in-place inspection windows.
                                </p>
                                
                                <table style="width: 100%;">
                                    <thead>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <a href="../help/images/dv_probes_data.png">
                                                    <img src="../help/images/dv_probes_data.png"  class="snap"
                                                         style="width: 250px;"
                                                         alt="Probe Data View" 
                                                         title="Probe Data View" />
                                                </a>
                                                <p class="img_caption_l">
                                                    <b>Fig. 1</b>: Probe Data with linked attributes
                                                </p>
                                            </td>
                                            <td>
                                                <a href="../help/images/dv_probes_labels.png">
                                                    <img src="../help/images/dv_probes_labels.png"  class="snap"
                                                         style="width: 250px;"
                                                         alt="Probe Data View" 
                                                         title="Probe Data View" />
                                                </a>
                                                <p class="img_caption_l">
                                                    <b>Fig. 2</b>: Individual probe label membership
                                                </p>
                                            </td>
                                            <td>
                                                <a href="../help/images/dv_probes_heat.png">
                                                    <img src="../help/images/dv_probes_heat.png"  class="snap"
                                                         style="width: 250px;"
                                                         alt="Probe Data View" 
                                                         title="Probe Data View" />
                                                </a>
                                                <p class="img_caption_l">
                                                    <b>Fig. 3</b>: Probe Expression Heatmap
                                                </p>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                
                                <h2>Probe Data View & attribute linking</h2>
                                
                                <a href="../help/images/dv_probes_linking.png">
                                    <img src="../help/images/dv_probes_linking.png"
                                         style="width: 50%; float: right; margin-left: 20px;"
                                         alt="Probe Data Type linking" 
                                         title="Probe Data Type linking" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 4</b>: Probe Data Type linking
                                </p>
                                <p>
                                    The Probe Data View presents probe identifiers alongside any data to which they map in the active Knowledge Library. 
                                    If a data value extends beyond the width of their column, the user can see the complete value by hovering their mouse over it.
                                    In Fig. 1, data types include Gene Title, Symbol, Unigene ID and Choromosomal Location. 
                                    Two of these data types, namely Symbol and Unigene ID, have been assigned URL patterns which activate attribute values as shown in Fig. 4.
                                </p>
                                <p>
                                    Each data type can be assigned a generalized URL by the user, as described in the <a href="../help/data_probes.jsp">Probe & Gene Data Guide</a>. 
                                    At display time, these URLs are fully qualified by replacing the wild card string with the actual data type value.
                                    The sample URLs below support Fig. 4, and show how XMAS provides instant access to deeper analysis of individual probes, through Google Web, and Stanford SOURCE searches.
                                </p>
                                
                                <p>
                                    Google Search:
                                    <ul>
                                        <li><p class="code">http://www.google.com/search?q=<b>%_PDT_%</b>&ie=utf-8&oe=utf-8</p></li>
                                        <li><p class="code">http://www.google.com/search?q=<b>USH1C</b>&ie=utf-8&oe=utf-8</p></li>
                                    </ul>
                                </p>
                                <p>
                                    <a href="http://smd-www.stanford.edu/cgi-bin/source/sourceSearch" target="_blank">SOURCE</a> Search:
                                    <ul>
                                        <li><p class="code">http://smd-www.stanford.edu/cgi-bin/source/sourceResult?choice=Gene&option=CLUSTER&criteria=<b>%_PDT_%</b></p></li>
                                        <li><p class="code">http://smd-www.stanford.edu/cgi-bin/source/sourceResult?choice=Gene&option=CLUSTER&criteria=<b>Hs.502072</b></p></li>
                                    </ul>
                                </p>
                                
                                <div style="clear: both;">
                                    <h2>Probe Labels</h2>
                                    <p>
                                        The view shown in Fig. 2 simply displays any Label memberships, on a probe-by-probe basis. 
                                        Label names are accompanied by descriptions.
                                    </p>
                                </div>
                                
                                <h2>Probe Expression Heatmaps</h2>
                                <a href="../help/images/dv_probes_heat_sample.png">
                                    <img src="../help/images/dv_probes_heat_sample.png" style="width: 40%; float: right;"
                                         alt="" 
                                         title="" />
                                </a>
                                <p class="img_caption">
                                    <b>Fig. 5</b>: A snap shot of the Probe Data View accompanying a Profile Visualization, with a single probe highlighted
                                </p>
                                <p>
                                    The view shown in Fig. 3 pairs probe IDs with a visual representation of their expression levels across the complete set of samples, and two profile measures: 1) Volatility; and 2) Linear Trend.
                                    The heat map color palette stretches from Blue to Red.
                                </p>
                                <p>
                                    Providing a visualization composed of samples expression values enables the user to both assess reproducability within time periods, and identify cross probe/sample patterns. Such representations complement both the trajectory and profile visualizations.
                                    Fig. 5 shows two highly correlated heatmaps (201909_at and 205000_at), with similar profile measure values. Cross referencing with the Probe Data view confirmed that these are replicates for the same gene, XIST (Hs.529901).
                                </p>
                                
                                <h2>Highlight</h2>
                                <p>
                                    The Highlight operator is activated and deactivated by toggling the star (<img src="../resources/images/unhigh.png" /> / <img src="../resources/images/high.png" />)
                                    that accompanies each supported data type (e.g. pathways and probes).
                                    Highlighing a probe reveals its behavior within analysis, without the need to filter.
                                </p>
                                
                                <div style="clear: both; text-align: center;">
                                    <a href="../help/images/dv_probes_highlight.png">
                                        <img src="../help/images/dv_probes_highlight.png" class="snap" style="width: 80%;"
                                             alt="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" 
                                             title="A snap shot of the Pathway Data View accompanying a Profile Visualization, with a pathway highlighted" />
                                    </a>
                                    <p class="img_caption_l">
                                        <b>Fig. 6</b>: A snap shot of the Probe Data View accompanying a Profile Visualization, with a single probe highlighted
                                    </p>
                                </div>
                                
                                <h2>Isolate</h2>
                                <p>
                                    Probes can be isolated by selecting one or more in the data view, and clicking the Isolate button in the operator menu.
                                    This has the effect of focusing analysis on the selected probes.
                                </p>
                                
                                <h2>Exclude</h2>
                                <p>
                                    Probes can be excluded by selecting one or more in the data view, and clicking the Exclude button in the operator menu.
                                    This has the effect of removing the selected probes from analysis.
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