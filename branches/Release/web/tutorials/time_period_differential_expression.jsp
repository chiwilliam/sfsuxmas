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
            <jsp:param name="parent" value="<%= parentPageName%>" />
        </jsp:include>

        <div id="page_body_container">
            <div id="page_body">
                <div id="page_body_sub">

                    <div id="page_body_main_full">

                        <h1>Tutorials</h1>
                        <div id="page_body_content">

                            <p>
                                &laquo; <a href="../tutorials/index.jsp">Back to Tutorial index</a>
                            </p>

                            <p>
                                These tutorials use data pre-installed in the Web Demo version of XMAS, hosted at: <a href="http://tintin.sfsu.edu:8080/xmas/data/your_data.jsp">http://tintin.sfsu.edu:8080/xmas/data/your_data.jsp</a>. To reenact the scenarios documented in each tutorial, you should load the required data from the '<a href="http://tintin.sfsu.edu:8080/xmas/data/your_data.jsp">Load Data</a>' page, and then proceed to '<a href="http://tintin.sfsu.edu:8080/xmas/visualization/visualization.jsp">Run XMAS</a>'.
                            </p>

                            <h2>Tutorial 1: Exposing Differential Expression within a given Time Period</h2>

                            <h3>Setup</h3>

                            <p>To reenact this tutorial, load the data from the data below from the '<a href="http://tintin.sfsu.edu:8080/xmas/data/your_data.jsp">Load Data</a>' page, and then proceed to '<a href="http://tintin.sfsu.edu:8080/xmas/visualization/visualization.jsp">Run XMAS</a>'.</p>

                            <table class="base_table">
                                <tbody>
                                    <tr><th>Expression Data</th><td>UCSF_Placental_Data</td></tr>
                                    <tr><th>Trajectory File</th><td>traj_half unit trajectories</td></tr>
                                    <tr><th>Knowledge Library</th><td>UCSF_Placental_Knowledge</td></tr>
                                </tbody>
                            </table>

                            <h3>Background</h3>

                            <p>
                                Based on the experimental design and/or domain knowledge, users commonly enter analysis with expectations surrounding certain time periods and the variation in expression between them.
                            </p>
                            <p>
                                For example, in a study of the human maternal interface [<a href="http://endo.endojournals.org/cgi/content/abstract/148/3/1059" target="_blank">Winn06</a>, <a href="http://www.ncbi.nlm.nih.gov/projects/geo/query/acc.cgi?acc=GSE5999" target="_blank">GEO</a>], the data owner hypothesized that there would be increased negative differential gene expression towards the end of pregnancy, as the placenta prepared for delivery and began shutting down.
                            </p>
                            <p>
                                XMAS empowers users to query their data for these kinds of features using the <a href="../help/dv_trajectory_filter.jsp">Trajectory Filter Tool</a>, described below.
                            </p>

                            <h3>Slide Notes</h3>

                            <p>
                            <ol>
                                <li>Tutorial title slide</li>
                                <li>Upon 'Running XMAS', you will be presented with the primary analysis interface. Select the 'Trajectory Viz' visualization type in the nav bar at the top of the page. To access the <a href="../help/dv_trajectory_filter.jsp">Trajectory Filter Tool</a>, turn your attention to the <a href="../help/data_views.jsp">sidebar menu</a> at the top of the right hand side bar. Hover over <b>Filters</b>, and select <b>Traj' Shape</b>.</li>
                                <li>Specify filter criteria in the 5th time period, building and refining the query by clicking the check boxes. Ensure only the four highlighted in the screenshot are highlighted (yellow checked boxes).</li>
                                <li>Clicking 'Update Filter' applies the filter. The result is an update of context, and an analysis focused on the 10 probes, which match the characteristics defined by the filter. The probes share a user defined characteristic, namely positive differential expression at the final time period, i.e. their expression increases between time period 4 and 5.</li>
                                <li>Switching to the <a href="../help/visualizations.jsp">Precise Visualization</a> (by clicking 'Profile Viz' in the menu at the top of the page) we are able to inspect the expression profiles of the matching probes in detail.</li>
                                <li>The <a href="../help/visualizations.jsp">data collapse operator</a> (see 'Visual Data Operators' &gt; 'Collapse') is applied to reduce the data to a common root, better facilitating pattern based inspection.</li>
                            </ol>
                            </p>

                            <h3>Summary</h3>

                            <p>
                                We have shown how the Trajectory Filter Tool can be used to explore differential expression over time.
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