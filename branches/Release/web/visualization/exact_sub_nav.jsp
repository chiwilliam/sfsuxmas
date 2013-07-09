<% String urlParams = "?probeID=" + probeID + "&timePeriod=" + timePeriod + "&divID=" + divCount;%>
<ul id="minitabs">
    <li id="window_nav_summary"><a href="exact_summary.jsp<%= urlParams%>">Summary</a></li>
    <li id="window_nav_pathways"><a href="exact_pathways.jsp<%= urlParams%>">Pathways</a></li>
    <li id="window_nav_samples"><a href="exact_samples.jsp<%= urlParams%>">Constituent Samples</a></li>
</ul>