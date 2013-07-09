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

                            <h2>Tackling Common Data Analysis Information Goals with XMAS</h2>

                            <p>
                                The pages listed below provide simple descriptions of how the operators, data views, visualizations and functionality of XMAS can be used to tackle commonly encoutered information goals.
                            </p>

                            <p>
                            <ul>
                                <li>Tutorial 1: <a href="../tutorials/time_period_differential_expression.jsp">Exposing Differential Expression within a given Time Period</a></li>
                                <li>Tutorial 2: <a href="../tutorials/surfacing_dynamic_expression_profiles.jsp">Surfacing Dynamic Expression Profiles</a></li>
                                <li>Tutorial 3: <a href="../tutorials/cross_data_set_feature_analysis.jsp">Cross Data Set Feature Analysis</a></li>
                                <li>Tutorial 4: <a href="../tutorials/tutorial_1.jsp">Exposing pathway involvement based on expression characteristics</a></li>
                            </ul>
                            </p>

                            <h2>Case Study Videos</h2>

                            <p>
                                We have put together a number of <a href="../home/videos.jsp">XMAS Case Study Videos</a> that we hope will provide a better insight into the intricacies of the system than static screen shots and textual descriptions.
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>