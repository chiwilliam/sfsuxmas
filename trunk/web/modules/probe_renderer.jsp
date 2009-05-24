<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.expression.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.probe_data.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="com.sfsu.xmas.highlight.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="xml.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="com.sfsu.xmas.filter.*"%>

<%@page import="database_management.*" %>
<%@page import="java.util.*" %>
<%@page import="xml.*"%>
<%@page import="visualization.*"%>
<%@page import="search.*"%>

<script type="text/javascript" src="../resources/script/probes_and_pathways.js"></script>
<script type="text/javascript" src="../resources/script/form_actions.js"></script>

<%
            String basePageNavURL = "../modules/probe_renderer.jsp";
            String scriptDivID = "";
//int maxStringSize = 10;
//String abbreviator = " >";
//String minimizer = " <";

            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);

            Probes probes = null;
            String urlParams = "";

            boolean highlightRequest = false;


            String databaseID = request.getParameter("data_id");
            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

            boolean pcadRelevant = eDB != null && eDB.isPCADRelavent();
            PCADDAO pcadDAO = null;
            if (pcadRelevant) {
                pcadDAO = PCADDAO.getInstance();
            }

            if (databaseID != null) {
                // From a DB
                int dataSetID = -1;
                try {
                    dataSetID = Integer.parseInt(databaseID);
                } catch (NumberFormatException ex) {
%>
<p class="error">
    Error: Non numeric or null data ID for attribute: "data_id"
</p>
<%                }
                basePageNavURL = "probes.jsp";
                urlParams = "database_name=" + databaseID;
                if (dataSetID >= 0) {
                    eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
                }
                if (eDB != null) {
                    probes = eDB.getProbes();
                    databaseID = eDB.getName();
                }
//            } else if (!(request.getParameter("results") == null)) {
//                SearchResultManager srm = SearchResultManager.getUniqueInstance();
//                ProbeSearchResults psr = srm.getProbeSearchResult();
//                if (psr != null) {
//                    probes = psr.getResults();
//                } else {
//                    probes = new Probes();
//                }
//                urlParams = "results";
            } else if (!(request.getParameter("node") == null)) {
                String nodeID = request.getParameter("nodeID");
                String timePeriod = request.getParameter("timePeriod");
                String divCount = request.getParameter("divCount");
                basePageNavURL = "node_probes.jsp";
                scriptDivID = ", true, " + divCount;
                urlParams = "divID=" + divCount + "&nodeID=" + nodeID + "&timePeriod=" + timePeriod;
                TrajectoryFile trajD = SessionAttributeManager.getActiveTrajectoryFile(request);
                LeafNodes nodes = trajD.getLeafNodes(SessionAttributeManager.getSessionID(request), Integer.valueOf(nodeID), Integer.valueOf(timePeriod));
                probes = SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getProbes(nodes.getMatchingProbes(), false);
            } else if (!(request.getParameter("highlighted") == null)) {
                highlightRequest = true;
                ArrayList<String> highProbes = HighlightManager.getUniqueInstance().getHighlightedProbes(SessionAttributeManager.getSessionID(request));
                String[] probeIDs = new String[highProbes.size()];
                for (int i = 0; i < highProbes.size(); i++) {
                    probeIDs[i] = highProbes.get(i);
                }
                if (eDB != null) {
                    probes = eDB.getProbes(probeIDs, false);
                }
            } else {
// Get probes in current view
//Probes allProbes = probeSam.getActiveDatabase().getProbes();
                TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
                String sessionID = SessionAttributeManager.getSessionID(request);
                if (eDB != null && td != null && FilterManager.getUniqueInstance().getFilterListForIdentifier(sessionID).hasTrajFileBasedFilters()) {
                    probes = eDB.getProbes(td.getProbes(sessionID), false);
                } else {
                    probes = eDB.getProbes(sessionID, false);
                }
            }
            int smallerList = 25;
            boolean all = !(request.getParameter("view_all") == null);
            int itemsPerPage = smallerList;
            int pagesEitherSide = 5;

            boolean heatmapRequest = !(request.getParameter("heat_map") == null);
            if (heatmapRequest) {
                heatmapRequest = Boolean.valueOf(request.getParameter("heat_map"));
            }
            if (heatmapRequest) {
                if (urlParams.length() > 0) {
                    urlParams = urlParams + "&";
                }
                urlParams = urlParams + "heat_map=true";
            }

            boolean labelsRequest = !(request.getParameter("labels") == null);
            if (labelsRequest) {
                labelsRequest = Boolean.valueOf(request.getParameter("labels"));
            }
            if (labelsRequest) {
                if (urlParams.length() > 0) {
                    urlParams = urlParams + "&";
                }
                urlParams = urlParams + "labels=true";
            }

            Labels labels = null;
            if (kDB != null) {
                labels = kDB.getLabels();
            }

            int dataSize = 0;
            if (probes != null) {
                dataSize = probes.size();
            }
            if (all) {
                itemsPerPage = dataSize;
            }
            int maxPageCount = (int) Math.ceil((double) dataSize / itemsPerPage);

            int pageNumber = 1;
            String pageNo = request.getParameter("page");
            if (pageNo != null) {
                int tempPageNo = Integer.parseInt(pageNo);
                if (tempPageNo > 0 && tempPageNo <= maxPageCount) {
                    // Specified page number is valid
                    pageNumber = tempPageNo;
                }
            }
            int minPage = Math.max(1, pageNumber - pagesEitherSide);
            int maxPage = Math.min(maxPageCount, pageNumber + pagesEitherSide);

            boolean displayAttributes = !(kDB == null || (databaseID == null && (heatmapRequest || (labelsRequest && labels != null))));

            int firstProbeIndex = (pageNumber - 1) * itemsPerPage;
            int lastProbeIndex = Math.min(pageNumber * itemsPerPage, dataSize);
%>

<%
            if (databaseID == null) {
%>

<div id="check_actions_message"> </div>

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
        <div style="float: right;">
            <% if (dataSize > 0) {%>
            <b>Select</b>: 
            <a href="javascript: void(0);" onclick="return check(<%= firstProbeIndex%>, <%= lastProbeIndex%>, 'probe_checkbox_');" class="mock_button">ALL</a> (<%= Math.min(itemsPerPage, dataSize)%>), 
            <a href="javascript: void(0);" onclick="return un_check(<%= firstProbeIndex%>, <%= lastProbeIndex%>, 'probe_checkbox_');" class="mock_button">NONE</a>
            <% }%>
        </div>
        Selected probe actions:
    </div>
    <div class="sidebar_tool_area_content">
        <div class="corners_bar">
            <div class="corners_inner_top_left"></div>
            <div class="corners_inner_top_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
        <div class="sidebar_tool_buttons">
            <% if (dataSize > 0) {%>
            <INPUT type="button" 
                   onclick="submit_probe_list_action('../SProbeListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'filter_isolate');" 
                   alt=""
                   title="" value="Isolate Selected">
            <INPUT type="button" 
                   onclick="submit_probe_list_action('../SProbeListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'filter_exclude');" 
                   alt=""
                   title="" value="Exclude Selected">
            <%
     if (kDB != null) {
            %>
            <!-- <a href="javascript: void(0);" onclick="submit_probe_list_action('../SProbeListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'filter_annotate_remove');" class="button"><span>Label</span></a> -->
            
            <%@ include file="../labels/label_select.jsp" %>
            <INPUT type="button" 
                   onclick="submit_probe_list_action('../SProbeListActions', <%= firstProbeIndex%>, <%= lastProbeIndex%>, 'label_probes');" 
                   alt="Add this label to the selected probes"
                   title="Add this label to the selected probes" value="Label">
            <%
     }
            %>
            <% }%>
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
<%
} else {
    // Showing probes for a particular DB
%>
<div class="corners_bar">
    <div class="corners_top_left"></div>
    <div class="corners_top_right"></div>
    <div class="corners_connector_bar"></div>
</div>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_top_line">
        <h3>Probes</h3>
    </div>
</div>
<%            }
            ArrayList<String> highIDs = HighlightManager.getUniqueInstance().getHighlightedProbes(SessionAttributeManager.getSessionID(request));
%>

<div class="sidebar_content_holder" style="overflow-x: scroll;">
    <div class="corners_bar">
        <div class="corners_inner_top_left"></div>
        <div class="corners_inner_top_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
    <%
            if (dataSize <= 0) {
                if (highlightRequest) {%>
    <div class="warning">
        You have not highlighted any probes
    </div>
    <% } else {%>
    <div class="no_data">
        You have filtered out all of the probes
    </div>
    <% }
    } else {
    %>
    
    <% if (databaseID != null) {%>
    <p class="table_summary">
        There are <%= dataSize%> probes in this database.
    </p>
    <% } else {

     int tableWidth = 408;
     int checkWidth = 22;
     int starWidth = 18;
     int idWidth = 75;

    %>
    
    <table class="window-large_quant_data" style="width: <%= tableWidth%>px; table-layout: fixed;">
        <thead>
            <tr>
                <% if (databaseID == null) {%>
                <th style="width: <%= checkWidth%>px;"></th>
                <th style="width: <%= starWidth%>px;"></th>
                <% }%>
                <th style="text-align: left; width: <%= idWidth%>px;"><span style="text-decoration: underline;">ID:</span></th>
                <%
     if (heatmapRequest) {
         int heatMapWidth = 180;
         int remaningWidth = (tableWidth - checkWidth - starWidth - idWidth - heatMapWidth - 12);
                %>
                <th style="width: <%= heatMapWidth%>px;">Expression Profile Heatmap</th>
                <th style="width: <%= Math.floor(remaningWidth / 2)%>px;">Vol'</th>
                <th style="width: <%= Math.ceil(remaningWidth / 2)%>px;">Trend</th>
                <% } else if (labelsRequest) {%>
                <th>Labels</th>
                <% } else if (kDB != null) {
                    ProbeDataTypes probeDataTypes = kDB.getProbeDataTypes();
                    // probes.setKnowledgeLibrary(kDB);
                    if (probeDataTypes != null && probeDataTypes.size() > 0) {
                        Iterator<Integer> it = probeDataTypes.keySet().iterator();
                        while (it.hasNext()) {
                            int attributeID = it.next();
                            ProbeDataType dataType = probeDataTypes.get(attributeID);
                            boolean inUse = true;
                            //if (attributeUsage.containsKey(dataType.getAttribute())) {
                            //    inUse = (Boolean) attributeUsage.get(dataType.getAttribute());
                            //}
                            if (inUse) {
                %>
                <th>
                    <div style="overflow: hidden; white-space: nowrap;">
                        <%
                            String attHead = dataType.getAttribute();
                            //if (attHead.length() > maxStringSize) {
                            //    attHead = attHead.substring(0, maxStringSize - abbreviator.length());
                            //}
%>
                        <span id="attri_head_<%= dataType.getID()%>"><%= attHead.replace("_", " ")%></span>
                    </div>
                </th>
                <%
                        }
                    }
                } else {

                %>
                <th>
                </th>
                <%                        }
                }
                %>
            </tr>
        </thead>
        <tbody>
            <%
     FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));
     for (int i = firstProbeIndex; i < lastProbeIndex && i < dataSize; i++) {
         Probe probe = probes.getProbeAt(i);
         String rowClass = "odd";
         if (i % 2 == 0) {
             rowClass = "even";
         }
         String bandingClass = rowClass;
         String imgUrl = "unhigh.png";
         if (highIDs != null && highIDs.contains(probe.getID())) {
             rowClass = "highlighted";
             imgUrl = "high.png";
         }
         String coloredCell = "";
         if (SessionAttributeManager.isProfileVisualization(request)) {
             coloredCell = " base_color_" + (i % PreciseViz.colors.length);
         }
         String disabled = "";
         if (filterList.getProbeFilterIDs().contains(probe.getID())) {
             disabled = "disabled checked";
         }

         int rowspan = 3;
         if (!displayAttributes) {
             rowspan--;
         }
         if (!pcadRelevant) {
             rowspan--;
         }
            %>
            <tr class="<%= rowClass%>" id="probe_row_<%= probe.getID()%>">
                <%
                if (databaseID == null) {
                %>
                <td rowspan="<%= rowspan%>" class="button_star <%= coloredCell%>"><INPUT TYPE="CHECKBOX" name="probe_checkbox_<%= probe.getID()%>" value="<%= probe.getID()%>" id="probe_checkbox_<%= i%>" <%= disabled%>></td>
                <td rowspan="<%= rowspan%>" class="button_star">
                    <img src="../resources/images/<%= imgUrl%>" name="probe_highlight_<%= probe.getID()%>" id="probe_button_<%= i%>" onclick="return highlightProbe('<%= probe.getID()%>', <%= i%>, '<%= bandingClass%>');" alt="Highlight Probe" title="Highlight Probe">
                </td>
                <%
                }
                %>
                <td rowspan="<%= rowspan%>">
                    <div style="overflow: hidden; white-space: nowrap; font-size: 11px;">
                        <%= probe.getID()%>
                    </div>
                </td>
                
                <%
                //////////////////////////////////////////////
                // PRIMARY DATA ACCOMPANYING PROBE ID. One of:
                //  - Heat map
                //  - Labels
                //  - Attributes
                //////////////////////////////////////////////
                if (heatmapRequest) {

                    //////////////////////////////////////////////
                    // HEATMAP
                    //////////////////////////////////////////////
%>
                <td cellspacing="0" cellpadding="0"><img src="../SProbeHeatMap?probe_id=<%= probe.getID()%>&height=25&width=175" style="width: 175px; height:25px; border: 0; padding: 0; margin: 0;"></td>
                <td style="text-align: right;"><%= probe.getProfileTimePeriodBasedVolatility()%></td>
                <td style="text-align: right;"><%= probe.getProfileTimePeriodBasedLinearTrend()%></td>
                
                <% } else if (labelsRequest && labels != null) {

                    //////////////////////////////////////////////
                    // Labels
                    //////////////////////////////////////////////
                    ArrayList<Label> probeLabels = labels.getLabelsForProbe(probe.getID());
                    Iterator<Label> it = probeLabels.iterator();
                %>
                <td>
                    <table>
                        <%
                    while (it.hasNext()) {
                        Label lab = it.next();
                        %>
                        <tr>
                            <td>
                                <div style="border: 1px solid black; background-color: #FFCC80; padding: 1px 2px; font-size: 9px;">
                                    <%= lab.getName()%> | 
                                    <a href="javascript: void(0);" onclick="return un_label_probe('<%= probe.getID()%>','<%= lab.getID()%>');">X</a>
                                </div>
                            </td>
                            <td></td>
                        </tr>
                        <%
                    }
                        %>
                    </table>
                </td>
                <%
                } else if (displayAttributes) {
                    ProbeDataTypeValues allProbeDataValues = kDB.getDataForProbes();
                    ProbeDataTypes probeDataTypes = kDB.getProbeDataTypes();
                    //HashMap<String, Boolean> attributeUsage = adb.getAttributeUsage();
                    //ProbeAttributeHeaderList headers = probes.getProbeAttributes();

                    if (probeDataTypes.size() <= 0) {
                %>
                <td colspan="<%= Math.max(probeDataTypes.size(), 1)%>"></td>
                <%
                    } else if (allProbeDataValues != null && allProbeDataValues.containsKey(probe.getID())) {
                        HashMap<Integer, String> probeDataValues = allProbeDataValues.get(probe.getID());
                        Iterator<Integer> it = probeDataTypes.keySet().iterator();
                        while (it.hasNext()) {
                            int attributeID = it.next();
                            ProbeDataType dataType = probeDataTypes.get(attributeID);
                            String value = "No Entry";
                            if (probeDataValues.containsKey(dataType.getID())) {
                                value = probeDataValues.get(dataType.getID());
                            }
                %>
                <td>
                    <div style="overflow: hidden; white-space: nowrap; font-size: 11px;" alt="<%= value%>" title="<%= value%>">
                        <%
                        if (dataType.isLinked() && !value.equals("---")) {
                        %>
                        <a href="<%= dataType.getLink(value)%>" target="_blank" id="p_att_<%= dataType.getID()%>_<%= probe.getID()%>"><%= value%></a>
                        <%
                        } else {
                        %>
                        <%= value%>
                        <%
                        }
                        %>
                    </div>
                </td>
                <%
                        }
                    }
                }
                %>
            </tr>
            <%
                //////////////////////////////////////////////
                // Probe Attributes
                //////////////////////////////////////////////
                if (displayAttributes) {

                    // Get any existing notes:
                    String noteStyle = "";
                    String probeNotePrefix = "";
                    String probeNote = kDB.getProbeUserNote(probe.getID());
                    String defaultProbeNote = probeNote;
                    if (probeNote == null) {
                        noteStyle = "color: gray;";
                        probeNote = "Click to edit...";
                        defaultProbeNote = "Add your probe note here";
                    }
                    if (!probeNote.equals("")) {
                        probeNotePrefix = "<i>USER</i>: ";
                    }
            %>
            <tr class="<%= rowClass%>" valign="top" id="probe_row_<%= probe.getID()%>_sub">
                <td colspan="<%= Math.max(kDB.getProbeDataTypes().size(), 1)%>" style="padding: 3px;" class="note user_note">
                    <%= probeNotePrefix%><a href="javascript: void(0);" onclick="return get_probe_note_box('<%= probe.getID()%>');" class="note_toggle" id="note_toggle_<%= probe.getID()%>" style="<%= noteStyle%>"><%= probeNote%></a>
                    <div id="probe_note_<%= probe.getID()%>" style="display: none;">
                        <FORM action="../SAddProbeNote?probe_id=<%= probe.getID()%>">
                            <textarea id="probe_note_text_<%= probe.getID()%>" name="probe_note_text_<%= probe.getID()%>" wrap="virtual" rows="2" cols="30"><%= defaultProbeNote%></textarea>
                            <br>
                            <input type="button" value="Save" onclick="return save_probe_note_box('<%= probe.getID()%>');"> OR 
                            <input type="button" value="Cancel" onclick="return cancel_probe_note_box('<%= probe.getID()%>');">
                        </FORM>
                    </div>
                </td>
            </tr>
            <%
                }

                //////////////////////////////////////////////
                // PCAD
                //////////////////////////////////////////////
                if (pcadRelevant) {
                    /*
                    String url = "http://array.sfsu.edu/cgi-bin/SMD/pcad_search.pl?EST_CAYC_CAYF_CCAG=" + probe.getID() + "&EST_CAYC_CAYF_CCAG=" + probe.getID() + "&submit_query=Do%20Search";
                    String pcadPrefix = "";
                    String pcadNote = pcadDAO.makePCADProbeQuery(probe.getID()).toString();
                    if (!pcadNote.equals("")) {
                    pcadPrefix = "<i>PCAD</i>: ";
                    }
                     */
            %>
            <tr class="<%= rowClass%>" valign="top" id="probe_row_<%= probe.getID()%>_sub_pcad">
                <td colspan="<%= Math.max(kDB.getProbeDataTypes().size(), 1)%>" id="probe_cell_<%= probe.getID()%>_sub_pcad" style="padding: 3px;" class="note"></td>
            </tr>
            <%
         }
     }
            %>
        </tbody>
    </table>
    <%
                }
            }
    %>
    
    <div class="corners_bar">
        <div class="corners_inner_bottom_left"></div>
        <div class="corners_inner_bottom_right"></div>
        <div class="corners_inner_connector_bar"></div>
    </div>
</div>


<% if (databaseID != null) {%>

<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_bottom_line">
        
    </div>
</div>
<% } else if (highlightRequest) {%>

<% } else {%>
<div class="sidebar_tool_area">
    <div class="sidebar_tool_area_bottom_line">
        <div>
            <%
     if (pageNumber - 1 > 0) {
            %>
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?<%= urlParams%>', '<%= scriptDivID%>');">&lt; First</a>
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?page=<%= pageNumber - 1%><% if (urlParams.length() > 0) {%>&<%}%><%= urlParams%>', '<%= scriptDivID%>');">&lt; Previous</a>
            <%
     }
            %>
            Probes <b><%= Math.min(firstProbeIndex + 1, dataSize)%> - <%= lastProbeIndex%></b> of <b><%= dataSize%></b>
            <%
     if (pageNumber + 1 <= maxPageCount) {

            %>
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?page=<%= pageNumber + 1%><% if (urlParams.length() > 0) {%>&<%}%><%= urlParams%>', '<%= scriptDivID%>');">Next &gt;</a>
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?page=<%= maxPageCount%><% if (urlParams.length() > 0) {%>&<%}%><%= urlParams%>', '<%= scriptDivID%>');">Last &gt;</a>
            <%
     }
            %> 
        </div>
        <div>
            <%
     if (dataSize > 0) {
         if (dataSize > smallerList) {
            %>
            <b>View</b>: 
            <%
                    if (!all && dataSize <= 1000) {
            %>
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?view_all<% if (urlParams.length() > 0) {%>&<%}%><%= urlParams%>', '<%= scriptDivID%>');">All</a> (<%= dataSize%>), 
            <%= smallerList%> per page
            <%
            } else {
            %>
            All (<%= dataSize%>), 
            <a href="javascript: void(0);" onclick="probe_refresh('<%= basePageNavURL%>?<%= urlParams%>', '<%= scriptDivID%>');"><%= smallerList%> per page</a>
            <%
             }
         }
     }
            %>
        </div>
    </div>
</div>

<% }%>

<div class="corners_bar">
    <div class="corners_bottom_left"></div>
    <div class="corners_bottom_right"></div>
    <div class="corners_connector_bar"></div>
</div>