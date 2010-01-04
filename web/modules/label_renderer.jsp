<%@page import="com.sfsu.xmas.dao.*"%>
<%@page import="com.sfsu.xmas.data_sets.*"%>
<%@page import="com.sfsu.xmas.data_structures.*"%>
<%@page import="com.sfsu.xmas.data_structures.knowledge.*"%>
<%@page import="com.sfsu.xmas.session.SessionAttributeManager"%>
<%@page import="com.sfsu.xmas.filter.*"%>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.highlight.*"%>
<%@page import="java.util.*" %>
<%@page import="com.sfsu.xmas.trajectory_files.*"%>
<%@page import="com.sfsu.xmas.util.NumberUtils"%>


<div id="sidebar_label_area">
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);

            String sessionID = SessionAttributeManager.getSessionID(request);

//String basePageNavURL = "../modules/annotation_renderer.jsp";


            boolean summary = !(request.getParameter("summary") == null);

            int dataSize = 0;

            Labels labels = null;

            String urlParams = "";

            if (kDB != null) {
                if (!(request.getParameter("highlighted") == null)) {
                    ArrayList<Integer> high = HighlightManager.getUniqueInstance().getHighlightedAnnotations(sessionID);

                    int[] labelIDs = new int[high.size()];
                    for (int i = 0; i < high.size(); i++) {
                        labelIDs[i] = high.get(i);
                    }
                    labels = kDB.getLabels(labelIDs);
                } else {
                    if (td != null && eDB != null) {
                        LeafNodes nodes = td.getLeafNodes(SessionAttributeManager.getSessionID(request));
                        Probes probes = eDB.getProbes(nodes.getMatchingProbes(), false);
                        labels = kDB.getLabelsForProbes(probes);
                    }
                }
                if (labels != null) {
                    dataSize = labels.size();
                }
            }
            String ess = "";
            if (dataSize != 1) {
                ess = "s";
            }

            Probes probes = null;
            if (eDB != null && td != null) {
                probes = eDB.getProbes(td.getProbes(SessionAttributeManager.getSessionID(request)), false);
            }

            int numPerPage = dataSize;
            if (summary) {
                numPerPage = Math.min(3, dataSize);
            }


            int firstLabelIndex = 0;
            int lastLabelIndex = dataSize;

    %>
    <div class="corners_bar">
        <div class="corners_top_left"></div>
        <div class="corners_top_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
    <div class="sidebar_tool_area">
        <div class="sidebar_tool_area_content">
            <div class="corners_bar">
                <div class="corners_inner_top_left"></div>
                <div class="corners_inner_top_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
            <div class="sidebar_tool_buttons">
                <a href="../help/dv_labels.jsp" 
                   class="help_button" 
                   alt="Help"
                   title="Help">
                    <span>?</span>
                </a>
                <% if (dataSize > 0) {%>
                <input type="button" 
                       onclick="submit_label_list_action('../SLabelListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_isolate');" class="button"
                       alt="Focus analysis on probes that are members of the selected labels"
                       title="Focus analysis on probes that are members of the selected labels" value="Isolate">
                <input type="button" 
                       onclick="submit_label_list_action('../SLabelListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_exclude');" class="button"
                       alt="Remove from analysis, probes that are members of the selected labels from analysis"
                       title="Remove from analysis, probes that are members of the selected labels from analysis" value="Exclude">
                <% }%>
            </div>
            <div class="corners_bar">
                <div class="corners_inner_bottom_left"></div>
                <div class="corners_inner_bottom_right"></div>
                <div class="corners_inner_connector_bar"></div>
            </div>
        </div>
        <!--
            <div class="sidebar_tool_area_bottom_line">
                <% if (dataSize > 0) {%>
                <b>Select</b>: 
                <a href="javascript: void(0);" onclick="return check(<%= firstLabelIndex%>, <%= lastLabelIndex%>, 'annotation_checkbox_');" class="mock_button">ALL</a> (<%= dataSize%>), 
                <a href="javascript: void(0);" onclick="return un_check(<%= firstLabelIndex%>, <%= lastLabelIndex%>, 'annotation_checkbox_');" class="mock_button">NONE</a>
                <% }%>
            </div>
        -->
    </div>
    <div class="corners_bar">
        <div class="corners_connector_bar_full"></div>
    </div>
    <div class="sidebar_content_holder" style="overflow-x: scroll;">
        <div class="corners_bar">
            <div class="corners_inner_top_left"></div>
            <div class="corners_inner_top_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
        <%
            if (dataSize <= 0) {
        %>
        <div class="no_data">
            No annotations for these probes
        </div>
        <%    } else {

            // Analysis, size = y
            int sizeOfAnalysis = probes.size();
        %>
        <table class="window-large_quant_data">
            <thead>
                <tr>
                    <th></th>
                    <th></th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Precision</th>
                    <th><% if (sizeOfAnalysis >= 30) {%><u>Recall</u><% } else {%>Recall<% }%></th>
                    <th><% if (sizeOfAnalysis < 30) {%><u>p-value</u><% } else {%>p-value<% }%></th>
                </tr>
            </thead>
            <tbody>
                <%

            FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));

            ArrayList<Integer> highIDs = HighlightManager.getUniqueInstance().getHighlightedAnnotations(sessionID);

            int labelIndex = 0;

            // Label degLabel = SessionAttributeManager.getDEGLabel(request);

            int maxProbeWidth = 0;

            TreeMap<Double, ArrayList<Label>> tm = new TreeMap<Double, ArrayList<Label>>();

            Set<Map.Entry<Integer, Label>> labelESet = labels.entrySet();
            Iterator<Map.Entry<Integer, Label>> it = labelESet.iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Label> entry = it.next();
                Label label = entry.getValue();

                // Label P size = x
                int sizeOfLabel = label.getNumberOfMembers();

                // Intersection, size = z
                int sizeOfIntersection = label.getNumberOfIntersectingMembers();

                int probeWidth = (sizeOfAnalysis + sizeOfLabel) - sizeOfIntersection;
                if (probeWidth > maxProbeWidth) {
                    maxProbeWidth = probeWidth;
                }

                double indexValue = 0;
                if (sizeOfAnalysis >= 30) {
                    //double precision = 0;
                    //if (sizeOfAnalysis > 0) {
                    //    precision = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfAnalysis);
                    //}
                    double recall = 0;
                    if (sizeOfAnalysis > 0) {
                        recall = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfLabel);
                    }
                    indexValue = 1 - recall;
                } else {
                    double pValue = 0;
                    if (sizeOfAnalysis > 0) {
                        double N = (double) 44000;
                        int k = sizeOfAnalysis;
                        double x = (double) sizeOfIntersection;
                        double M = (double) sizeOfLabel;
                        if (k < 30) {
                            double MbyN = M / N;
                            double sum = (double) 0;
                            for (int i = 0; i < x; i++) {
                                double iterVal = (double) NumberUtils.binom(k, i) *
                                        (double) Math.pow((double) MbyN, i) *
                                        (double) Math.pow((double) (1 - MbyN), k - i);
                                sum += iterVal;
                            }
                            pValue = (double) 1 - (double) sum;
                        }
                    }
                    indexValue = pValue;
                }

                ArrayList<Label> existingLabels = new ArrayList<Label>();
                if (tm.containsKey((double) indexValue)) {
                    existingLabels = tm.get((double) indexValue);
                }
                existingLabels.add(label);
                tm.put((double) indexValue, existingLabels);
            }


            Set<Map.Entry<Double, ArrayList<Label>>> orderedLabels = tm.entrySet();
            Iterator<Map.Entry<Double, ArrayList<Label>>> orderedIt = orderedLabels.iterator();
            while (orderedIt.hasNext() && labelIndex < numPerPage) {
                Map.Entry<Double, ArrayList<Label>> entry = orderedIt.next();
                ArrayList<Label> labs = entry.getValue();

                Iterator<Label> sameValueLabelIt = labs.iterator();
                while (sameValueLabelIt.hasNext()) {

                    Label label = sameValueLabelIt.next();

                    // Label P size = x
                    int sizeOfLabel = label.getNumberOfMembers();

                    // Analysis, size = y
                    //int sizeOfAnalysis = probes.size();

                    // Intersection, size = z
                    int sizeOfIntersection = label.getNumberOfIntersectingMembers();

                    double unit = (double) 230 / (double) maxProbeWidth;

                    int labelWidth = Math.max((int) Math.round((sizeOfLabel - sizeOfIntersection) * unit), 0);
                    if (labelWidth == 0) {
                        labelWidth = (int) Math.ceil((sizeOfLabel - sizeOfIntersection) * unit);
                    }
                    int intersectionWidth = Math.max((int) Math.round(sizeOfIntersection * unit), 0);
                    if (intersectionWidth == 0) {
                        intersectionWidth = (int) Math.ceil(sizeOfIntersection * unit);
                    }
                    int probesWidth = Math.max((int) Math.round((sizeOfAnalysis - sizeOfIntersection) * unit), 0);
                    if (probesWidth == 0) {
                        probesWidth = (int) Math.ceil((sizeOfAnalysis - sizeOfIntersection) * unit);
                    }

                    double precision = 0;
                    if (sizeOfAnalysis > 0) {
                        precision = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfAnalysis);
                    }
                    double recall = 0;
                    if (sizeOfAnalysis > 0) {
                        recall = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfLabel);
                    }

                    String pVal = "---";

                    double pValue = 0;
                    if (sizeOfAnalysis > 0) {
                        double N = (double) 44000;
                        int k = sizeOfAnalysis;
                        double x = (double) sizeOfIntersection;
                        double M = (double) sizeOfLabel;
                        if (k < 30) {
                            double MbyN = M / N;
                            double sum = (double) 0;
                            for (int i = 0; i < x; i++) {
                                double iterVal = (double) NumberUtils.binom(k, i) *
                                        (double) Math.pow((double) MbyN, i) *
                                        (double) Math.pow((double) (1 - MbyN), k - i);
                                sum += iterVal;
                            }
                            pValue = (double) 1 - (double) sum;
                            pVal = String.valueOf(NumberUtils.getDoubleToTwoDecimalPlaces(pValue));
                        }
                    }

                    String rowClass = "odd";
                    if (labelIndex % 2 == 0) {
                        rowClass = "even";
                    }
                    String rowBandingClass = rowClass;
                    String imgUrl = "unhigh.png";
                    if (highIDs != null && highIDs.contains(label.getID())) {
                        rowClass = "highlighted";
                        imgUrl = "high.png";
                    }


                    String disabled = "";
                    if (filterList.getFilteredLabels().contains(label.getID())) {
                        disabled = "disabled checked";
                    }

                %>
                <tr class="<%= rowClass%>" id="annotation_row_<%= label.getID()%>">
                    <td rowspan="2" class="button_star"><input TYPE="CHECKBOX" name="label_checkbox_<%= label.getID()%>" value="<%= label.getID()%>" id="label_checkbox_<%= labelIndex%>" <%= disabled%>></td>
                    <td rowspan="2" class="button_star"><img src="../resources/images/<%= imgUrl%>" name="annotation_button_<%= label.getID()%>" id="annotation_button_<%= labelIndex%>" onclick="return highlightAnnotation(<%= label.getID()%>, <%= labelIndex%>, '<%= rowBandingClass%>');" alt="Highlight Label" title="Highlight Label"></td>
                    <td>
                        <b><%= label.getName()%></b>
                    </td>
                    <td>
                        <%= label.getDescription()%>
                    </td>
                    <td style="white-space: nowrap;"><%= precision%> </td>
                    <td style="white-space: nowrap;">| <%= recall%> </td>
                    <td style="white-space: nowrap;">| <%= pVal%></td>
                </tr>
                <tr class="<%= rowClass%>" id="annotation_row_<%= label.getID()%>_sub">
                    <td colspan="5" style="padding-bottom: 2px;">
                        
                        <div class="corners_bar">
                            <div class="corners_top_left"></div>
                            <div class="corners_top_right"></div>
                            <div class="corners_connector_bar"></div>
                        </div>
                        <div class="sidebar_content_holder" style="background-color: #FFF;">
                            <div class="corners_bar">
                                <div class="corners_inner_top_left"></div>
                                <div class="corners_inner_top_right"></div>
                                <div class="corners_inner_connector_bar"></div>
                            </div>
                            <div style="padding: 0 2px;">
                                <table class="precision_recall">
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar_thin bar_probes" style="width: <%= probesWidth%>px;"></div></td>
                                        <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar_thin bar_intersection" style="width: <%= intersectionWidth%>px;"></div></td>
                                        <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"><div class="bar_thin bar_label" style="width: <%= labelWidth%>px;"></div></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 2px;">Label:</td>
                                        <td style="padding-right: 4px;"><%= sizeOfLabel%></td>
                                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"></td>
                                        <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_label" style="width: <%= intersectionWidth%>px;"></div></td>
                                        <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"><div class="bar bar_label" style="width: <%= labelWidth%>px;"></div></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 2px;">Intersection:</td>
                                        <td style="padding-right: 4px;"><%= sizeOfIntersection%></td>
                                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"></td>
                                        <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_intersection" style="width: <%= intersectionWidth%>px;"></div></td>
                                        <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"></td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 2px;">Probes:</td>
                                        <td style="padding-right: 4px;"><%= sizeOfAnalysis%></td>
                                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar bar_probes" style="width: <%= probesWidth%>px;"></div></td>
                                        <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar  bar_probes" style="width: <%= intersectionWidth%>px;"></div></td>
                                        <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar_thin bar_probes" style="width: <%= probesWidth%>px;"></div></td>
                                        <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar_thin bar_intersection" style="width: <%= intersectionWidth%>px;"></div></td>
                                        <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"><div class="bar_thin bar_label" style="width: <%= labelWidth%>px;"></div></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="corners_bar">
                                <div class="corners_inner_bottom_left"></div>
                                <div class="corners_inner_bottom_right"></div>
                                <div class="corners_inner_connector_bar"></div>
                            </div>
                        </div>
                        <div class="corners_bar">
                            <div class="corners_bottom_left"></div>
                            <div class="corners_bottom_right"></div>
                            <div class="corners_connector_bar"></div>
                        </div>
                    </td>
                    <!-- <td><a href="../SFilterManipulator?filterType=4&annotationID=<%= label.getID()%>&exclude" target="_parent" style="text-decoration: none;">X</a></td> -->
                </tr>
                <%
                    labelIndex++;
                }

            }
            if (orderedIt.hasNext()) {
                %>
                <tr>
                    <td colspan="7" style="text-align: right;">
                        <a href="javascript: void(0);" onclick="make_nav_request('../modules/label_list.jsp');">View All <%= dataSize%> Labels</a>
                    </td>
                </tr>
                <%
            }


                %>
            </tbody>
        </table>
        <% }%>
        <div class="corners_bar">
            <div class="corners_inner_bottom_left"></div>
            <div class="corners_inner_bottom_right"></div>
            <div class="corners_inner_connector_bar"></div>
        </div>
    </div>
    <div class="corners_bar">
        <div class="corners_bottom_left"></div>
        <div class="corners_bottom_right"></div>
        <div class="corners_connector_bar"></div>
    </div>
</div>