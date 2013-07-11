<%@page import="xml.*"%>
<%@page import="java.util.*"%>
<%@page import="visualization.*"%>

<div class="sub_nav_buttons">
    <a class="button current" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/trajectories_volatility.jsp', 'volatility');" id="sub_nav_volatility"><span>Volatility</span></a>
    <a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/trajectories_trend.jsp', 'trend');" id="sub_nav_trend"><span>Linear Trend</span></a>
    <%--<a class="button" href="javascript: void(0);" onclick="make_sub_nav_request('../modules/trajectories_clusters.jsp', 'trend');" id="sub_nav_clusters"><span>Clusters</span></a>--%>
</div>

<div class="sidebar_header">Data > Trajectories</div>

<div class="sidebar_padding">
    <jsp:include page="../modules/trajectories_volatility.jsp" />
</div>
