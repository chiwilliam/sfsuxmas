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

                            <h2>Tutorial 4: Exposing pathway involvement based on expression characteristics</h2>

                            <h3>Setup</h3>

                            <p>To reenact this tutorial, load the data from the data below from the '<a href="http://tintin.sfsu.edu:8080/xmas/data/your_data.jsp">Load Data</a>' page, and then proceed to '<a href="http://tintin.sfsu.edu:8080/xmas/visualization/visualization.jsp">Run XMAS</a>'.</p>

                            <table class="base_table">
                                <tbody>
                                    <tr><th>Expression Data</th><td>UCSF_Placental_Data</td></tr>
                                    <tr><th>Trajectory File</th><td>traj_one unit trajectories</td></tr>
                                    <tr><th>Knowledge Library</th><td>UCSF_Placental_Knowledge</td></tr>
                                </tbody>
                            </table>

                            <h3>Background</h3>

                            <p>
                                This simple tutorial show cases some of the key functionality of XMAS, enroute to a non trivial discovery
                            </p>

                            <div class="tutorial">

                                <ol>
                                    <li><p>We begin analysis with the primary visualization on the left (preserved, profile visualization selected) and the side bar data view on the right. The side bar is displaying a summary indicating the number of probes under analysis relative to the size of the data set, and any pathway or label involvement.</p></li>
                                    <img src="images/tutorial_1_1.png" border="0"/>
                                    <li><p>The user navigates to <i>Filters>Trajectory Shape</i> in the side bar. This presents an interactive, collapsed, discretized representation of the data. Check boxes are enabled for selection if trajectories exist in the corresponding coordinates. Numbers accompany each check box indicating the number of trajectories present.</p></li>
                                    <img src="images/tutorial_1_2.png" border="0"/>
                                    <li><p>The user selects three trajectory characteristic conditions, which combine to capture negative differential expression at the final time period. The interface updates to show the possible paths of trajectories that meet these new conditions.</p></li>
                                    <img src="images/tutorial_1_3.png" border="0"/>
                                    <li><p>The filter is applied, resulting in an analysis focusing on 39 probes. We are able to get a feel for the behavior of these probes immediately, recognizing the shared, user defined characteristic, but also many other sub patterns.</p></li>
                                    <img src="images/tutorial_1_4.png" border="0"/>
                                    <li><p>The user navigates to <i>Data > Labels</i> to reveal the involvement of two labels in this set of 39 probes; 1) DEGS; and 2) the Y chromosome.</p></li>
                                    <img src="images/tutorial_1_5.png" border="0"/>
                                    <li><p>The user chooses to exclude probes which are members of the DEG label, an action which can be traced and managed in the <i>Filters > Current</i> sidebar menu.</p></li>
                                    <img src="images/tutorial_1_6.png" border="0"/>
                                    <li><p>Navigating now to <i>Data > Pathways</i>, the resultant view surfaces a number of statistically significant pathways. The user highlights one, resulting in the exposure of the member probes, in context.</p></li>
                                    <img src="images/tutorial_1_7.png" border="0"/>
                                </ol>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>