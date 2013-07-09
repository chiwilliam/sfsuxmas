<%@page import="database_management.*" %>
<%@page import="search.*" %>

<div class="sidebar_padding">
    <div id="sidebar_page_content">
        <div id="sidebar_probe_area">
            <jsp:include page="../modules/probe_renderer.jsp">
                <jsp:param name="heat_map" value="true" />
            </jsp:include>
        </div>
    </div>
</div>