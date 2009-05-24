<%@page import="data_structures.*"%>
<%@page import="xml.*"%>
<%@page import="java.util.Iterator"%>

<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/modules.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/window_styling.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        <script type="text/javascript" src="../resources/script/filter_page.js"></script>
        
    </head>
    
    <body onload="window_onload_window('pathways');">
        
        <%
            int divCount = 0;
            String divCountString = request.getParameter("divID");
            if (divCountString != null) {
                divCount = Integer.parseInt(divCountString);
            }

            int nodeID = 0;
            String nodeIDString = request.getParameter("nodeID");
            if (nodeIDString != null) {
                nodeID = Integer.parseInt(nodeIDString);
            }

            int timePeriod = 0;
            String timePeriodString = request.getParameter("timePeriod");
            if (timePeriodString != null) {
                timePeriod = Integer.parseInt(timePeriodString);
            }
        %>
        
        <%@ include file="cluster_sub_nav.jsp" %>
        
        <div class="ajax_window_body_content">
            
            <p>A summary of the pathway overlap with the cluster you clicked:</p>
            
            <div class="dataTable">
                
                <%
            XMLClusterFactory fact = XMLClusterFactory.getUniqueInstance();
            ClusterDocument doc = new ClusterDocument(fact.getDocument());
            Probeset probes = doc.getProbesForCluster(nodeID);
            if (probes != null) {
                PathwayDataAccess ada = new PathwayDataAccess();
                Pathways pathways = ada.getPathwaysForProbes(probes);

                %>
                
                <%@ include file="../modules/pathway_renderer.jsp" %>
                
                <%
            }
                %>
                
            </div>
            
        </div>
        
    </body>
    
</html>
