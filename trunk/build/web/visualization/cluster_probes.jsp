<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/modules.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/window_styling.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        <script type="text/javascript" src="../resources/script/probe_list.js"></script>
        
    </head>
    
    <body onload="window_onload_window('probes');">
        
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
        %>
        
        <%@ include file="cluster_sub_nav.jsp" %>
        
        <div class="ajax_window_body_content">
                
                <%
            String basePageNavURL = "cluster_probes.jsp?divID=" + divCountString + "&nodeID=" + nodeIDString + "&page=";
            XMLClusterFactory fact = XMLClusterFactory.getUniqueInstance();
            ClusterDocument doc = new ClusterDocument(fact.getDocument());
            Probeset probes = doc.getProbesForCluster(nodeID);
                %>
                
                <%@ include file="../modules/probe_renderer.jsp" %>
                
        </div>
        
    </body>
    
</html>
