<%@page import="com.sfsu.xmas.session.*"%>
<style type="text/css">
    #header_container {
        background-color: #222222;
        height: 75px;
    }
    .header {
        margin-left: auto;
        margin-right: auto;
        width: 980px;
    }
    ul#top_navigation_buttons{
        list-style: none;
        margin: 0;
        padding: 47px 0 0 0;
        font-size: 15px;
        text-align: left;
        white-space: nowrap;
    }
    ul#top_navigation_buttons li{
        display: inline;
        margin: 0 2px;
    }
    ul#top_navigation_buttons a{
        text-decoration: none;
        padding: 0 0 3px;
        border-bottom: 4px solid #BFCFFF;
        color: #999;
    }
    ul#top_navigation_buttons a.current{
        font-size: 16px;
        border-color: #FF9900;
        color: #FFFFFF;
        border-bottom-width: 7px;
    }
    ul#top_navigation_buttons a:hover{
        border-color: #9999FF;
        color: #FFFFFF;
    }
    
    
    ul#top_sub_navigation_buttons{
        list-style: none;
        margin: 0;
        padding: 10px 0 0 0;
        font-size: 12px;
        text-align: left;
        white-space: nowrap;
    }
    ul#top_sub_navigation_buttons li{
        display: inline;
        margin: 0 5px;
    }
    ul#top_sub_navigation_buttons a{
        text-decoration: none;
        padding: 3px 0 0 3px;
        border-left: 4px solid #FFCC80;
        color: #B36B00;
    }
    ul#top_sub_navigation_buttons a.current{
        font-size: 12px;
        border-color: #B36B00;
        color: #000000;
        padding-bottom: 2px;
        font-weight: bold;
    }
    ul#top_sub_navigation_buttons a:hover{
        border-color: #B36B00;
        color: #222222;
        padding-bottom: 2px;
    }
    
    .main_logo {
        float: right;
        height: 60px;
        border: 0;
        margin-top: 10px;
    }
    .header_footer {
        height: 26px;
        background-color: #FF9900;
        border-bottom: 1px solid #222222;
    }
</style>

<%
            String parentPage = request.getParameter("parent");
            parentPage = parentPage.toLowerCase();
%>
<div id="header_container">
    <div class="header">
        <a href="../home/">
            <img src="../resources/images/xmas_logo.png" class="main_logo" alt="XMAS Logo" title="XMAS Logo">
        </a>
        <ul id="top_navigation_buttons">
            <li>
                <a href="../home/index.jsp" 
                   id="top_nav_home" 
                   alt="Go to the home page" 
                   title="Go to the home page">Home</a>
            </li>
            <li>
                <a href="../data/your_data.jsp" 
                   id="top_nav_data"
                   alt="Manage your data" 
                   title="Manage your data">Data</a>
            </li>
            <li>
                <a href="../visualization/visualization.jsp" 
                   id="top_nav_xmas"
                   alt="Enter the Analysis Environment" 
                   title="Enter the Analysis Environment">XMAS*</a>
            </li>
            <li>
                <a href="../help/index.jsp" 
                   id="top_nav_help"
                   alt="View help files for XMAS" 
                   title="View help files for XMAS">Help</a>
            </li>
            <li>
                <a href="../tutorials/index.jsp" 
                   id="top_nav_tutorials"
                   alt="View tutorials for XMAS" 
                   title="View tutorials for XMAS">Tutorials</a>
            </li>
            <li>
                <a href="../util/admin.jsp"
                   id="top_nav_admin"
                   alt="Administer XMAS" 
                   title="Administer XMAS">Admin</a>
            </li>
            <%
            if (parentPage.equals("kegg")) {%>
            <li>
                <a href="../util/admin.jsp" 
                   id="top_nav_kegg" 
                   style="background-color: blue; color: white; padding: 50px 5px 3px 5px;"
                   alt="KEGG Pathways" 
                   title="KEGG Pathways">KEGG</a>
            </li>
            <% }%>
        </ul>
        
    </div>
</div>
<div class="header_footer">
    <div class="header">
        <%
            if (parentPage == null) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../home/index.jsp" 
                   id="top_sub_nav_home"
                   alt="Error" 
                   title="Error">ERROR</a>
            </li>
        </ul>
        <%        } else {
            if (parentPage.equals("home")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../home/index.jsp" 
                   id="top_sub_nav_home"
                   alt="Go to the Home Page" 
                   title="Go to the Home Page">Home Page</a>
            </li>
            <li>
                <a href="../home/system.jsp" 
                   id="top_sub_nav_system"
                   alt="Go to the System Description" 
                   title="Go to the System Description">System Description & Requirements</a>
            </li>
            <li>
                <a href="../home/download.jsp" 
                   id="top_sub_nav_download"
                   alt="Go to the Download Page" 
                   title="Go to the Download Page">Download</a>
            </li>
            <li>
                <a href="../home/publications.jsp" 
                   id="top_sub_nav_publications"
                   alt="View the latest publications about XMAS" 
                   title="View the latest publications about XMAS">Publications</a>
            </li>
            <li>
                <a href="../home/team.jsp" 
                   id="top_sub_nav_team"
                   alt="Meet the XMAS Team" 
                   title="Meet the XMAS Team">Team</a>
            </li>
            <li>
                <a href="../home/videos.jsp" 
                   id="top_sub_nav_videos"
                   alt="See XMAS related Videos" 
                   title="See XMAS related Videos">Videos</a>
            </li>
        </ul>
        <%        } else if (parentPage.equals(
                "data")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../data/your_data.jsp" 
                   id="top_sub_nav_your_data"
                   alt="Manage your data" 
                   title="Manage your data">Your Data</a>
            </li>
            <% if (SessionAttributeManager.isAdmin(request)) {%>
            <li>
                <a href="../data/system_setup.jsp" 
                   id="top_sub_nav_system_setup"
                   alt="Specify the data directory" 
                   title="Specify the data directory">System Setup</a>
            </li>
            <li>
                <a href="../data/upload_data.jsp" 
                   id="top_sub_nav_upload"
                   alt="Install new data or add to existing sets" 
                   title="Install new data or add to existing sets">Install Data</a>
            </li>
            <% }%>
        </ul>
        <%        } else if (parentPage.equals(
                "admin")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../util/admin.jsp" 
                   id="top_sub_nav_admin"
                   alt="Login" 
                   title="Login">Admin Login</a>
            </li>
            <li>
                <a href="../util/cookies.jsp" 
                   id="top_sub_nav_cookies"
                   alt="Manage your cookies" 
                   title="Manage your cookies">Cookies</a>
            </li>
        </ul>
        <%                } else if (parentPage.equals(
                "xmas")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="javascript: void(0);" 
                   onclick="image_type_switch('../SVisualizationManipulator?trajectory', 'trajectory'); 
                       window_onload_data('trajectory');" 
                   id="top_sub_nav_trajectory"
                   alt="View a discretized trajectory visualization" 
                   title="View a discretized trajectory visualization">Trajectory Viz</a>
            </li>
            <li>
                <a href="javascript: void(0);" 
                   onclick="image_type_switch('../SVisualizationManipulator?hybrid'); 
                       window_onload_data('hybrid');" 
                   id="top_sub_nav_hybrid"
                   alt="View a profile visualization with key trajectories highlighted" 
                   title="View a profile visualization with key trajectoshuries highlighted">Hybrid Viz</a>
            </li>
            <li>
                <a href="javascript: void(0);" 
                   onclick="image_type_switch('../SVisualizationManipulator?profile', 'profile'); 
                       window_onload_data('profile');" 
                   id="top_sub_nav_profile"
                   alt="View a visualization composed of precise expression profiles" 
                   title="View a visualization composed of precise expression profiles">Profile Viz</a>
            </li>
        </ul>
        <%                } else if (parentPage.equals("kegg")) {
            String pathway = request.getParameter("pathway");
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../pathways/pathway.jsp" 
                   id="top_sub_nav_pathway"
                   alt="Sample Pathway" 
                   title="Sample Pathway">Pathway: <%= pathway%></a>
            </li>
        </ul>
        <%                } else if (parentPage.equals("help")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../help/index.jsp" 
                   id="top_sub_nav_home"
                   alt="Help Home" 
                   title="Help Home">Home</a>
            </li>
            <li>
                <a href="../help/getting_started.jsp" 
                   id="top_sub_nav_getting_started"
                   alt="Help - Getting Started" 
                   title="Help - Getting Started">Getting Started</a>
            </li>
            <li>
                <a href="../help/visualizations.jsp" 
                   id="top_sub_nav_visualizations"
                   alt="Help - Visualizations" 
                   title="Help - Visualizations">Visualizations</a>
            </li>
            <li>
                <a href="../help/data_views.jsp" 
                   id="top_sub_nav_data_views"
                   alt="Help - Data Views & Operators" 
                   title="Help - Data Views & Operators">Data Views & Operators</a>
            </li>
            <li>
                <a href="../help/local_install.jsp" 
                   id="top_sub_nav_local_install"
                   alt="Help - Local Install" 
                   title="Help - Local Install">Local Installation</a>
            </li>
            <li>
                <a href="../help/data_guides.jsp" 
                   id="top_sub_nav_data_guides"
                   alt="Help - Data" 
                   title="Help - Data">Data Guides</a>
            </li>
        </ul>
        <%                } else if (parentPage.equals("tutorials")) {
        %>
        <ul id="top_sub_navigation_buttons">
            <li>
                <a href="../tutorials/index.jsp" 
                   id="top_sub_nav_home"
                   alt="Tutorials Home" 
                   title="Tutorials Home">Home</a>
            </li>
        </ul>
        <%                }


            }
        %>
    </div>
</div>