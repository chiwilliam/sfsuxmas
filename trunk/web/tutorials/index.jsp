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
                            
                            <p>
                                A selection of tutorials guide your through simple, illustrative analyses.
                            </p>
                            
                            <h2>Exposing Calcium Regulation At Term</h2>
                            <p>
                                This simple tutorial show cases some of the key functionality of XMAS, enroute to a non trivial discovery
                            </p>
                            <p>
                                View the <a href="../tutorials/tutorial_1.jsp">Tutorial</a>
                                </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>