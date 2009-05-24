<%@ include file="../template/file_header.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml" >
    
    <head>
        
        <title>XMAS &copy; 2007</title>
        
        <link rel="stylesheet" type="text/css" href="../resources/styles/main.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/modules.css" />
        <link rel="stylesheet" type="text/css" href="../resources/styles/window_styling.css" />
        
        <script type="text/javascript" src="../resources/script/main.js"></script>
        
    </head>
    
    <body onload="window_onload_window('summary');">
        
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
            
            <p>A summary of the cluster you clicked:</p>
            
            <div class="dataTable">
                
                <table>
                    <thead>
                        <tr>
                            <th colspan="2" >Cluster</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>ID:</td>
                            <td><%= nodeID %></td>
                        </tr>
                        <tr>
                            <td>Filters:</td>
                            <td>
                                <a href="../SFilterManipulator?filterType=5&clusterID=<%= nodeID %>" target="_parent">Isolate</a> | 
                                <a href="../SFilterManipulator?filterType=5&clusterID=<%= nodeID %>&exclude" target="_parent">Exclude</a>
                            </td>
                        </tr>
                    </tbody>
                </table>
                
            </div>
            
        </div>
        
    </body>
    
</html>
