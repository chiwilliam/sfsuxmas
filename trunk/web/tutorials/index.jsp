<%@page import="com.sfsu.xmas.globals.*"%>

<%@ include file="../template/file_header.jsp" %>

<%
            String parentPageName = "Tutorials";
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
                        
                        <h1>Tutorials</h1>
                        <div id="page_body_content">
                            
                            <h2>Tackling Common Data Analysis Information Goals with XMAS</h2>
                            
                            <p>
                                The pages listed below provide simple descriptions of how the operators, data views, visualizations and functionality of XMAS can be used to tackle common information goals.
                            </p>
                            
                            <p>
                                <ul>
                                    <li><a href="time_period_differential_expression.jsp">Exposing Differential Expression within a given Time Period</a></li>
                                    <li><a href="surfacing_dynamic_expression_profiles.jsp">Surfacing Dynamic Expression Profiles</a></li>
                                    <li><a href="cross_data_set_feature_analysis.jsp">Cross Data Set Feature Analysis</a></li>
                                </ul>
                            </p>
                            
                            <h2>Detailed Case Studies</h2>
                            
                            <h3>NEW: Video Case Studies</h3>
                            <p>
                                We have put together a number of <a href="../home/videos.jsp">XMAS Case Study Videos</a> that we hope will provide a better insight into the intricacies of the system than static screen shots and textual descriptions.
                            </p>
                            
                            <h3>Exposing Calcium Regulation At Term</h3>
                            <p>
                                This simple tutorial demonstrates some of XMAS' core functionality, en-route to a non-trivial discovery. View the <a href="../tutorials/tutorial_1.jsp">Tutorial</a>
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>