<%@ page import="com.sfsu.xmas.data_sets.ExpressionDataSet" %>
<%@ page import="com.sfsu.xmas.session.SessionAttributeManager" %>
<%@ page import="com.sfsu.xmas.trajectory_files.TrajectoryFile" %>
<%@ page import="com.sfsu.xmas.trajectory_files.TrajectoryNode" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.TreeMap" %>
<%--
  Created by IntelliJ IDEA.
  User: da
  Date: 7/6/13
  Time: 8:26 PM
--%>

<%
    int imagesPerBlock = 9;
    String sessionID = SessionAttributeManager.getSessionID(request);
    if (sessionID == null) {
        sessionID = "";
    }

    int maxToRender = 20;
    int trajectoryCount = 0;
    String title = "";
    String pm = "";
    TreeMap<Integer, HashMap<Integer, TrajectoryNode>> tm = null;
    TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
    if (td != null) {
        title = "K-Means Clustering";

        Set<Integer> keys = tm.keySet();
        Iterator<Integer> it = keys.iterator();

        int blockCount = 0;
        int blockSize = 0;

        int min;
        int max;

        int sidebarMaxBin = 0;
        int sidebarMinBin = 0;
        int sidebarNumberOfTimePeriods = 0;
        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        if (td != null && eDB != null) {
            sidebarMaxBin = td.getMaximumSubtractiveDegree();
            sidebarMinBin = td.getMinimumSubtractiveDegree();
            sidebarNumberOfTimePeriods = eDB.getNumberOfTimePeriods();
        }
%>


<form>

<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_top_line">
        <%= title%>.
    </div>
    <div class="sidebar_tool_area_content">
        <div class="corners_bar">
            <div class="corners_inner_top_left"></div>
            <div class="corners_inner_top_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
        <div class="sidebar_tool_buttons">
            <a href="../help/dv_trajectories.jsp"
               class="help_button"
               alt="Help"
               title="Help">
                <span>?</span>
            </a>
            <input id="cluster_k" type="text" value="5" />
            <input type="button" onclick="return compute_clusters(  );" value="Cluster" />
          <input type="button" onclick="return filter_by_node_trajectories( <%= maxToRender%>, <%= sidebarMinBin%>, <%= sidebarMaxBin%>, <%= sidebarNumberOfTimePeriods%> );" value="Filter Selected" />
        </div>
        <div class="corners_bar">
            <div class="corners_inner_bottom_left"></div>
            <div class="corners_inner_bottom_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
    </div>
</div>
<div class="corners_bar">
    <div class="corners_connector_bar_full"></div>
</div>

<div class="sidebar_content_holder">
<div class="corners_bar">
    <div class="corners_inner_top_left"></div>
    <div class="corners_inner_top_right"></div>
    <div class="corners_inner_connector_bar"></div>
</div>

<div style="padding: 0 4px;">

<%
    while (it.hasNext() && trajectoryCount < maxToRender) {
        Integer key = it.next();
        HashMap<Integer, TrajectoryNode> nodes = tm.get(key);

        if (blockSize == 0) {
            max = 1000 - key;
%>


<div class="col_header" onclick="toggle_area('<%= "trajectories_" + blockCount%>');" id="trajectories_<%= blockCount%>_show_hide">
    <b><%= title%></b>:  Less than <%= pm%><%= 1000 - key%>
</div>
<div class="indented_div">
<div id="trajectories_<%= blockCount%>">

<table>
    <%
    }

    int trajectoryIndex = 0;
    Set<Integer> nodeIDs = nodes.keySet();
    Iterator<Integer> idsIT = nodeIDs.iterator();

    int tCount = blockSize;

    while (idsIT.hasNext()) {
        Integer nodeID = idsIT.next();
        TrajectoryNode node = nodes.get(nodeID);
        if (tCount % 3 == 0) {
    %>
    <tr>
        <%            }
        %>
        <td>
            <div class="corners_bar">
                <div class="corners_top_left"></div>
                <div class="corners_top_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
            <div class="traj_normal" id="node_cell_<%= trajectoryCount%>">
                <div class="sidebar_content_holder" id="">
                    <div class="corners_bar">
                        <div class="corners_inner_top_left"></div>
                        <div class="corners_inner_top_right"></div>
                        <div class="corners_inner_connector_bar"></div>
                    </div>
                    <input type="checkbox" onclick="return highlightTrajCell(<%= trajectoryCount%>);" id="node_check_<%= trajectoryCount%>" value="<%= nodeID%>" /> <br />
                    <a href="javascript: void(0);" onclick="return leafClick(event, <%= eDB.getNumberOfTimePeriods()%>, <%= nodeID%>)"><img src="../SGetTrajectoryShape?nodeID=<%= node.getID()%>&timePeriod=<%= node.getDepth()%>&width=120&height=70&legend=false" style="border: 0;" /></a>

                    <div class="corners_bar">
                        <div class="corners_inner_bottom_left"></div>
                        <div class="corners_inner_bottom_right"></div>
                        <div class="corners_inner_connector_bar"></div>
                    </div>
                </div>
            </div>
            <div class="corners_bar">
                <div class="corners_bottom_left"></div>
                <div class="corners_bottom_right"></div>
                <div class="corners_connector_bar"></div>
            </div>
        </td>
        <%
        if ((tCount + 1) % 3 == 0) {
        %>
    </tr>
    <%        }
        trajectoryIndex++;
        tCount++;
        trajectoryCount++;
    }
// trajectoryCount += nodes.size();
    blockSize += nodes.size();
    if (blockSize >= imagesPerBlock) {
        if ((tCount + 1) % 3 == 0) {
    %>
    <td></td>
    </tr>
    <%    }
    %>
</table>
</div>
</div>
<%
            blockSize = 0;
            blockCount++;
            min = 1000 - key;
        }
    }
    if (blockSize < imagesPerBlock && blockSize > 0) {
        if ((blockSize + 1) % 3 == 0) {
%>
<td></td>
</tr>
<%    }
%>
</table>
</div>
</div>

<%
    }
%>
</div>
</div>

</form>

<%
} else {
%>
<div class="error">No Expression Data loaded</div>
<%            }
%>