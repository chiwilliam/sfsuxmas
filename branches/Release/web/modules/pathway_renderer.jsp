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


<div id="sidebar_pathway_area">
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
//ExpressionDatabase eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);

            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

            String sessionID = SessionAttributeManager.getSessionID(request);

//String basePageNavURL = "../modules/annotation_renderer.jsp";

            boolean summary = !(request.getParameter("summary") == null);

            int dataSize = 0;

            Pathways pathways = null;

            String urlParams = "";

            if (kDB != null) {
                if (!(request.getParameter("highlighted") == null)) {
                    ArrayList<Integer> high = HighlightManager.getUniqueInstance().getHighlightedPathways(sessionID);

                    int[] pathIDs = new int[high.size()];
                    for (int i = 0; i < high.size(); i++) {
                        pathIDs[i] = high.get(i);
                    }
                    pathways = kDB.getPathways(pathIDs);
                } else {
                    if (td != null && eDB != null) {
                        LeafNodes nodes = td.getLeafNodes(SessionAttributeManager.getSessionID(request));
                        Probes probes = eDB.getProbes(nodes.getMatchingProbes(), false);
                        pathways = kDB.getPathwaysForProbes(probes);
                    }
                }
                if (pathways != null) {
                    dataSize = pathways.size();
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

            int numPerPage = Math.min(dataSize, 20);
            if (summary) {
                numPerPage = 3;
            }


            int firstLabelIndex = 0;
            int lastLabelIndex = dataSize;


            boolean primaryPrecision = (request.getParameter("sort") == null || request.getParameter("sort").equals("precision"));

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
                <a href="../help/dv_pathways.jsp" 
                   class="help_button" 
                   alt="Help"
                   title="Help">
                    <span>?</span>
                </a>
                <% if (dataSize > 0) {%>
                <input type="button" 
                       onclick="submit_pathway_list_action('../SPathwayListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_isolate');" class="button"
                       alt="Focus analysis on probes that are members of the selected labels"
                       title="Focus analysis on probes that are members of the selected labels" value="Isolate">
                <input type="button" 
                       onclick="submit_pathway_list_action('../SPathwayListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_exclude');" class="button"
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
            No pathways for these probes
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
                    <th>
                        <%
            if (primaryPrecision) {
                        %>
                        Precision
                        <%                            } else {
                        %>
                        <a title="View Pathways that are represented in the current analysis ordered by Precision" 
                           alt="View Pathways that are represented in the current analysis ordered by Precision" 
                           onclick="make_nav_request('../modules/pathway_list.jsp?sort=precision');" href="javascript: void(0);">Precision</a>
                        <%                }
                        %>
                    </th>
                    <th>
                        <%
            if (primaryPrecision) {
                        %>
                        <a title="View Pathways that are represented in the current analysis ordered by Recall" 
                           alt="View Pathways that are represented in the current analysis ordered by Recall" 
                           onclick="make_nav_request('../modules/pathway_list.jsp?sort=recall');" href="javascript: void(0);">Recall</a>
                        <%                            } else {
                        %>
                        <u>Recall</u>
                        <%                }
                        %>
                    </th>
                    <th>p-value</th>
                </tr>
            </thead>
            <tbody>
                <%

            FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));

            ArrayList<Integer> highIDs = HighlightManager.getUniqueInstance().getHighlightedPathways(sessionID);

            int labelIndex = 0;

            // Label degLabel = SessionAttributeManager.getDEGLabel(request);

            int maxProbeWidth = 0;




            // Primary stat, Secondary stat, GOTerms
            TreeMap<Double, TreeMap<Double, ArrayList<Pathway>>> entiesSortedByPrimaryAndSecondaryStats = new TreeMap<Double, TreeMap<Double, ArrayList<Pathway>>>();

            Set<Map.Entry<Integer, Pathway>> goTermEntrySet = pathways.entrySet();
            Iterator<Map.Entry<Integer, Pathway>> goTermEntrySetIT = goTermEntrySet.iterator();
            while (goTermEntrySetIT.hasNext()) {
                Map.Entry<Integer, Pathway> entry = goTermEntrySetIT.next();
                Pathway pathway = entry.getValue();

                // Label P size = x
                int sizeOfEntity = pathway.getNumberOfMembers();

                // Analysis, size = y
                //int sizeOfAnalysis = probes.size();

                // Intersection, size = z
                int sizeOfIntersection = pathway.getNumberOfIntersectingMembers();

                int probeWidth = (sizeOfAnalysis + sizeOfEntity) - sizeOfIntersection;
                if (probeWidth > maxProbeWidth) {
                    maxProbeWidth = probeWidth;
                }


                double precision = 0;
                if (sizeOfAnalysis > 0) {
                    precision = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfAnalysis);
                }
                double recall = 0;
                if (sizeOfAnalysis > 0) {
                    recall = NumberUtils.getDoubleToTwoDecimalPlaces((double) sizeOfIntersection / (double) sizeOfEntity);
                }

                double primaryIndexValue = 0;
                double secondaryIndexValue = 0;

                if (primaryPrecision) {
                    primaryIndexValue = 1 - precision;
                    secondaryIndexValue = 1 - recall;
                } else {
                    primaryIndexValue = 1 - recall;
                    secondaryIndexValue = 1 - precision;
                }

                if (false && sizeOfAnalysis <= 30) {
                    double pValue = 0;
                    if (sizeOfAnalysis > 0) {
                        double N = (double) 44000;
                        int k = sizeOfAnalysis;
                        double x = (double) sizeOfIntersection;
                        double M = (double) sizeOfEntity;
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
                }

                TreeMap<Double, ArrayList<Pathway>> entitiesBySecondary = new TreeMap<Double, ArrayList<Pathway>>();
                if (entiesSortedByPrimaryAndSecondaryStats.containsKey((double) primaryIndexValue)) {
                    entitiesBySecondary = entiesSortedByPrimaryAndSecondaryStats.get((double) primaryIndexValue);
                }

                ArrayList<Pathway> entities = new ArrayList<Pathway>();
                if (entitiesBySecondary.containsKey((double) secondaryIndexValue)) {
                    entities = entitiesBySecondary.get((double) secondaryIndexValue);
                }

                entities.add(pathway);

                entitiesBySecondary.put((double) secondaryIndexValue, entities);

                entiesSortedByPrimaryAndSecondaryStats.put((double) primaryIndexValue, entitiesBySecondary);
            }

            boolean pathwaysRemain = false;

            Set<Map.Entry<Double, TreeMap<Double, ArrayList<Pathway>>>> entitiesOrderedByBothStats = entiesSortedByPrimaryAndSecondaryStats.entrySet();
            Iterator<Map.Entry<Double, TreeMap<Double, ArrayList<Pathway>>>> entitiesOrderedByBothStatsIT = entitiesOrderedByBothStats.iterator();

            // For each primary stat value
            while (entitiesOrderedByBothStatsIT.hasNext() && labelIndex < numPerPage) {
                Map.Entry<Double, TreeMap<Double, ArrayList<Pathway>>> entitiesForPrimaryStat = entitiesOrderedByBothStatsIT.next();
                TreeMap<Double, ArrayList<Pathway>> entitiesAtPrimaryStatValue = entitiesForPrimaryStat.getValue();
                Set<Map.Entry<Double, ArrayList<Pathway>>> entitiesBySecondaryStat = entitiesAtPrimaryStatValue.entrySet();
                Iterator<Map.Entry<Double, ArrayList<Pathway>>> entitiesBySecondaryStatIT = entitiesBySecondaryStat.iterator();

                // For each secondary stat value
                while (entitiesBySecondaryStatIT.hasNext()) {
                    Map.Entry<Double, ArrayList<Pathway>> precToGOTermEntry = entitiesBySecondaryStatIT.next();
                    ArrayList<Pathway> entities = precToGOTermEntry.getValue();
                    Iterator<Pathway> sameValueLabelIt = entities.iterator();

                    // For each Entity
                    while (sameValueLabelIt.hasNext() && labelIndex < numPerPage) {
                        Pathway pathway = sameValueLabelIt.next();
                        // Label P size = x
                        int sizeOfLabel = pathway.getNumberOfMembers();
                        // Analysis, size = y
                        //int sizeOfAnalysis = probes.size();
                        // Intersection, size = z
                        int sizeOfIntersection = pathway.getNumberOfIntersectingMembers();

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
                            double N = (double) eDB.getNumberOfProbes();
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
                        if (highIDs != null && highIDs.contains(pathway.getID())) {
                            rowClass = "highlighted";
                            imgUrl = "high.png";
                        }


                        String disabled = "";
                        if (filterList.getFilteredGOTerms().contains(pathway.getID())) {
                            disabled = "disabled checked";
                        }

                %>
                <tr class="<%= rowClass%>" id="pathway_row_<%= pathway.getID()%>">
                    <td rowspan="2" class="button_star"><input TYPE="CHECKBOX" name="pathway_checkbox_<%= pathway.getID()%>" value="<%= pathway.getID()%>" id="pathway_checkbox_<%= labelIndex%>" <%= disabled%>></td>
                    <td rowspan="2" class="button_star"><img src="../resources/images/<%= imgUrl%>" name="pathway_button_<%= pathway.getID()%>" id="pathway_button_<%= labelIndex%>" onclick="return highlightPathway(<%= pathway.getID()%>, <%= labelIndex%>, '<%= rowBandingClass%>');" alt="Highlight Pathway" title="Highlight Pathway"></td>
                    <td>
                        <b><%= pathway.getName()%></b>
                    </td>
                    <td style="white-space: nowrap;"><%= precision%> </td>
                    <td style="white-space: nowrap;">| <%= recall%> </td>
                    <td style="white-space: nowrap;">| <%= pVal%></td>
                </tr>
                <tr class="<%= rowClass%>" id="pathway_row_<%= pathway.getID()%>_sub">
                    <td colspan="4" style="padding-bottom: 2px;">
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
                                        <td style="padding-right: 2px;">Pathway</td>
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
                    <!-- <td><a href="../SFilterManipulator?filterType=4&annotationID=<%= pathway.getID()%>&exclude" target="_parent" style="text-decoration: none;">X</a></td> -->
                </tr>
                <%
                        labelIndex++;
                    }
                    if (sameValueLabelIt.hasNext()) {
                        pathwaysRemain = true;
                    }
                }
                if (entitiesBySecondaryStatIT.hasNext()) {
                    pathwaysRemain = true;
                }
            }
            if (entitiesOrderedByBothStatsIT.hasNext() || pathwaysRemain) {
                %>
                <tr>
                    <td colspan="7" style="text-align: right;">
                        <a href="javascript: void(0);" onclick="make_nav_request('../modules/pathway_list.jsp');">View All <%= dataSize%> Pathways</a>
                    </td>
                </tr>
                <%

            }
                %>
            </tbody>
        </table>
        <%

            }
        %>
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