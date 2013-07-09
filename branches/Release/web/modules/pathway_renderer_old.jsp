<%@page import="com.sfsu.xmas.data_access.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="com.sfsu.xmas.filter.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.highlight.*"%>
<%@page import="filter.*"%>
<%@page import="java.util.*" %>

<script type="text/javascript" src="../resources/script/probes_and_pathways.js"></script>
<script type="text/javascript" src="../resources/script/form_actions.js"></script>

<%
            KnowledgeDatabase kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            ExpressionDatabase eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            TrajectoryDocument td = SessionAttributeManager.getActiveTrajectoryFile(request);

            String identifier = SessionAttributeManager.getSessionID(request);

            boolean summary = !(request.getParameter("summary") == null);
            String basePageNavURL = "../modules/pathway_renderer.jsp";

            int itemsPerPage = 10;
            int summaryCount = 2;
            int pagesEitherSide = 5;
            int pageNumber = 1;
            int maxPageCount = 1;
            int dataSize = 0;
            int maxPathwayCard = 0;

            Pathways pathways = null;

            String urlParams = "";

            int firstProbeIndex = 0;
            int lastProbeIndex = 0;

            if (kDB != null) {

                //maxPathwayCard = kdb.getMaxPathwayCardinality();

                if (!(request.getParameter("search_results") == null)) {
                    //int[] rs = (int[]) session.getAttribute("PathwaySearchResults");
                    pathways = null; //new Pathways(kdb, rs);
// ProbeSearchResults psr = new ProbeSearchResults(probes);
// probes = psr.getFilteredResults();
                    urlParams = "search_results";
                } else if (!(request.getParameter("node") == null)) {
                    basePageNavURL = "node_pathways.jsp";
                    String nodeID = request.getParameter("nodeID");
                    String timePeriod = request.getParameter("timePeriod");
                    String divCount = request.getParameter("divCount");
                    urlParams = "divID=" + divCount + "&nodeID=" + nodeID + "&timePeriod=" + timePeriod;
                    TrajectoryDocument trajD = SessionAttributeManager.getActiveTrajectoryFile(request);
                    LeafNodes nodes = trajD.getLeafNodes(SessionAttributeManager.getSessionID(request), Integer.valueOf(nodeID), Integer.valueOf(timePeriod));
                    Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(nodes.getMatchingProbes());
                //pathways = kDB.getPathwaysForProbes(probes);
                } else if (!(request.getParameter("exact") == null)) {
                    basePageNavURL = "exact_pathways.jsp";
                    String nodeID = request.getParameter("nodeID");
                    String timePeriod = request.getParameter("timePeriod");
                    String divCount = request.getParameter("divCount");
                    String probeID = request.getParameter("probeID");

                    Probes probes = eDB.getProbes(new String[]{probeID});

                    urlParams = "divID=" + divCount + "&nodeID=" + nodeID + "&timePeriod=" + timePeriod;

                //pathways = kDB.getPathwaysForProbes(probes);
                } else if (!(request.getParameter("highlighted") == null)) {
                    ArrayList<Integer> high = HighlightManager.getUniqueInstance().getHighlightedPathways(identifier);

                    int[] pathIDs = new int[high.size()];
                    for (int i = 0; i < high.size(); i++) {
                        pathIDs[i] = high.get(i);
                    }
                    pathways = new Pathways(kDB, pathIDs);
                } else {
                    LeafNodes nodes = td.getLeafNodes(SessionAttributeManager.getSessionID(request));
                    Probes probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(nodes.getMatchingProbes());
                    pathways = kDB.getPathwaysForProbes(probes);
                }

                if (summary) {
                    itemsPerPage = summaryCount;
                }

                if (pathways != null) {
                    dataSize = pathways.size();
                    maxPageCount = (int) Math.ceil((double) dataSize / itemsPerPage);

                    pageNumber = 1;
                    String pageNo = request.getParameter("page");
                    if (pageNo != null) {
                        int tempPageNo = Integer.parseInt(pageNo);
                        if (tempPageNo > 0 && tempPageNo <= maxPageCount) {
// Specified page number is valid
                            pageNumber = tempPageNo;
                        }
                    }

                    firstProbeIndex = (pageNumber - 1) * itemsPerPage;
                    lastProbeIndex = Math.min(pageNumber * itemsPerPage, dataSize);
                }
            }
            String ess = "";
            if (dataSize != 1) {
                ess = "s";
            }
            int minPage = Math.max(1, pageNumber - pagesEitherSide);
            int maxPage = Math.min(maxPageCount, pageNumber + pagesEitherSide);
            if (summary) {
%>

<!--
START SIDEBAR MENU
-->
<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_top_line">
        Showing <%= Math.min(dataSize, itemsPerPage)%> of <%= dataSize%> <b>pathway<%= ess%></b> from the current analysis
    </div>
</div>
<div class="corners_bar">
    <div class="corners_connector_bar_full"></div>
</div>
<!--
<div class="corners_bar">
    <div class="corners_bottom_left"></div>
    <div class="corners_bottom_right"></div>
    <div class="corners_connector_bar"></div>
</div>
END SIDEBAR MENU
-->
<%
} else {
%>
<form>
    
    <!--
    START SIDEBAR MENU
    -->
    <div class="corners_bar">
        <div class="corners_top_left"></div>
        <div class="corners_top_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
    <div class="sidebar_tool_area">
        <div class="sidebar_tool_area_top_line">
            <%
    if (pageNumber - 1 > 0) {
            %>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?<%= urlParams%>');">&lt; First</a>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= pageNumber - 1%>&<%= urlParams%>');">&lt; Previous</a>
            <%
    }
            %>
            <b><%= Math.min(firstProbeIndex + 1, dataSize)%> - <%= lastProbeIndex%></b> of <b><%= dataSize%></b>
            <%
    if (pageNumber + 1 <= maxPageCount) {
            %>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= pageNumber + 1%>&<%= urlParams%>');">Next &gt;</a>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= maxPageCount%>&<%= urlParams%>');">Last &gt;</a>
            <%
    }
            %>
        </div>
        <div class="sidebar_tool_area_content">
            <div class="corners_bar">
                <div class="corners_inner_top_left"></div>
                <div class="corners_inner_top_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
            <div class="sidebar_tool_buttons">
                <% if (dataSize > 0) {%>
                <a href="javascript: void(0);" onclick="submit_pathway_list_action('../SPathwayListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'filter_isolate');" class="button"><span>Isolate</span></a>
                <a href="javascript: void(0);" onclick="submit_pathway_list_action('../SPathwayListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'filter_exclude');" class="button"><span>Exclude</span></a>
                <% }%>
            </div>
            <div class="corners_bar">
                <div class="corners_inner_bottom_left"></div>
                <div class="corners_inner_bottom_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
        </div>
        <div class="sidebar_tool_area_bottom_line">
            <% if (dataSize > 0) {%>
            <b>Select</b>: 
            <a href="javascript: void(0);" onclick="return check(<%= firstProbeIndex%>, <%= lastProbeIndex%>, 'pathway_checkbox_');" class="mock_button">ALL</a> (<%= Math.min(itemsPerPage, dataSize)%>), 
            <a href="javascript: void(0);" onclick="return un_check(<%= firstProbeIndex%>, <%= lastProbeIndex%>, 'pathway_checkbox_');" class="mock_button">NONE</a>
            <% }%>
        </div>
    </div>
    <div class="corners_bar">
        <div class="corners_connector_bar_full"></div>
    </div>
    <!--
<div class="corners_bar">
    <div class="corners_bottom_left"></div>
    <div class="corners_bottom_right"></div>
    <div class="corners_connector_bar"></div>
</div>
END SIDEBAR MENU
-->
    <%
            }
    %>
    
    <div class="sidebar_content_holder">
        <div class="corners_bar">
            <div class="corners_inner_top_left"></div>
            <div class="corners_inner_top_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
        <%
            if (dataSize <= 0) {
                if (!(request.getParameter("highlighted") == null)) {%>
        <div class="warning">
            You have not highlighted any pathways
        </div>
        <% } else {%>
        <div class="no_data">
            No pathways represented in the data
        </div>
        <% }
        } else {
        %>
        <table class="window-large_quant_data">
            <thead>
                <tr>
                    <% if (!summary) {%>
                    <th />
                    <th />
                    <% }%>
                    <th>Description</th>
                    <th>Representation</th>
                </tr>
            </thead>
            <tbody>
                <%
            ArrayList<Integer> highIDs = HighlightManager.getUniqueInstance().getHighlightedPathways(identifier);
            FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);
            ArrayList<Integer> filtIDs = new ArrayList<Integer>();
            if (fl != null) {
                FilterList pathwayFilters = fl.getPathwayFilters();
                Iterator it = pathwayFilters.iterator();
                while (it.hasNext()) {
                    PathwayFilter filt = (PathwayFilter) it.next();
                    filtIDs.add(filt.getID());
                }
            }

            for (int i = firstProbeIndex; i < lastProbeIndex && i < dataSize; i++) {
                if (summary && i >= summaryCount) {
                    break;
                }
                Pathway path = (Pathway) pathways.get(i);
                String rowClass = "odd";
                if (i % 2 == 0) {
                    rowClass = "even";
                }
                String imgUrl = "unhigh.png";
                if (highIDs != null && highIDs.contains(path.getID()) && !summary) {
                    rowClass = "highlighted";
                    imgUrl = "high.png";
                }
                %>
                <tr class="<%= rowClass%>" id="pathway_row_<%= path.getID()%>">
                    <% if (!summary) {%>
                    <td><% if (filtIDs.contains(path.getID()) && !summary) {%>* <% }%><input TYPE="CHECKBOX" name="pathway_checkbox_<%= path.getID()%>" value="<%= path.getID()%>" id="pathway_checkbox_<%= i%>" /></td>
                    <td>
                        <img src="../resources/images/<%= imgUrl%>" name="pathway_checkbox_<%= path.getID()%>" id="pathway_button_<%= i%>" onclick="return highlightPathway(<%= path.getID()%>, <%= i%>);" />
                    </td>
                    <% }%>
                    <td>
                        <%
                    String attHead = path.getDescription();
                    if (attHead.length() > 9) {
                        attHead = attHead.substring(0, 7);
                    }
                        %>
                        <b>
                            <span id="attri_head_<%= path.getID()%>"><%= attHead%></span>
                            <span id="attri_head_<%= path.getID()%>_full" style="display: none;"><%= path.getDescription()%></span>
                        </b>
                        <%
                    if (path.getDescription().length() > 9) {
                        %>
                        <a href="javascript: void(0);" onclick="return grow('attri_head_<%= path.getID()%>');" id="attri_head_<%= path.getID()%>_G">...</a>
                        <a href="javascript: void(0);" onclick="return shrink('attri_head_<%= path.getID()%>');" id="attri_head_<%= path.getID()%>_S" style="display: none;">(less)</a>
                        <%                }
                        %>
                    </td>
                    <td>
                        <%

                    double unit = (double) 100 / (double) maxPathwayCard;
                    int setSize = path.getTotalCardinality();
                    int setCoverage = 0; //path.getUnfilteredCardinality();
                    int setPrecision = 0; //path.getFilteredCardinality();
%>
                        <table class="multi_member_table">
                            <tr>
                                <td>Current</td>
                                <td style="width: 100px;"><div style="background-color: green; width: <%= Math.max(Math.round(setPrecision * unit), 1)%>px; height: 10px;"></div></td>
                                <td><%= setPrecision%></td>
                            </tr>
                            <tr>
                                <td>Data</td>
                                <td style="width: 100px;"><div style="background-color: grey; width: <%= Math.max(Math.round(setCoverage * unit), 1)%>px; height: 10px;"></div></td>
                                <td><%= setCoverage%></td>
                            </tr>
                            <tr>
                                <td>Pathway</td>
                                <td style="width: 100px;"><div style="background-color: maroon; width: <%= Math.max(Math.round(setSize * unit), 1)%>px; height: 10px;"></div></td>
                                <td><%= setSize%></td>
                            </tr>
                        </table>
                        
                    </td>
                    <!-- <td><a href="../SFilterManipulator?filterType=4&pathwayID=<%= path.getID()%>&exclude" target="_parent" style="text-decoration: none;">X</a></td> -->
                </tr>
                <%
            }%>
            </tbody>
        </table>
        <% }%>
        
        <div class="corners_bar">
            <div class="corners_inner_bottom_left"></div>
            <div class="corners_inner_bottom_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
    </div>
    
    <div class="sidebar_tool_area">
        <div class="sidebar_tool_area_bottom_line">
            <% if (!summary) {
                if (pageNumber - 1 > 0) {
            %>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?<%= urlParams%>');">&lt; First</a>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= pageNumber - 1%>&<%= urlParams%>');">&lt; Previous</a>
            <%
     }
            %>            <% if (dataSize > 0) {%>
            <b><%= Math.min(firstProbeIndex + 1, dataSize)%> - <%= lastProbeIndex%></b> of <b><%= dataSize%></b>
            <% }%>
            <%
     if (pageNumber + 1 <= maxPageCount) {
            %>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= pageNumber + 1%>&<%= urlParams%>');">Next &gt;</a>
            <a href="javascript: void(0);" onclick="pathway_refresh('<%= basePageNavURL%>?page=<%= maxPageCount%>&<%= urlParams%>');">Last &gt;</a>
            <%
     }
 } else {
     if (dataSize > 0) {%>
            <a onclick="make_nav_request('../modules/pathway_list.jsp');" href="javascript: void(0);">More...</a>
            <%  }
            }
            %>
        </div>
    </div>
    
    <div class="corners_bar">
        <div class="corners_bottom_left"></div>
        <div class="corners_bottom_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
    
</form>