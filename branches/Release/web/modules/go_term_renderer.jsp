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


<div id="sidebar_go_term_area">
    <%
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
//ExpressionDatabase eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);

            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

            String sessionID = SessionAttributeManager.getSessionID(request);

//String basePageNavURL = "../modules/annotation_renderer.jsp";

            boolean summary = !(request.getParameter("summary") == null);

            int dataSize = 0;

            GOTerms goTerms = null;

            String urlParams = "";

            if (kDB != null) {
                if (!(request.getParameter("highlighted") == null)) {
                    ArrayList<String> high = HighlightManager.getUniqueInstance().getHighlightedGOTerms(sessionID);

                    String[] goTermIDs = new String[high.size()];
                    for (int i = 0; i < high.size(); i++) {
                        goTermIDs[i] = high.get(i);
                    }
                    goTerms = kDB.getGOTerms(goTermIDs);
                } else {
                    if (td != null && eDB != null) {
                        LeafNodes nodes = td.getLeafNodes(SessionAttributeManager.getSessionID(request));
                        Probes probes = eDB.getProbes(nodes.getMatchingProbes(), false);
                        //if (FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request)).size() > 0) {
                        goTerms = kDB.getGOTermsForProbes(probes);
                    //} else {
                    //    goTerms = kDB.getGOTerms();
                    //}
                    }
                }
                if (goTerms != null) {
                    dataSize = goTerms.size();
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
    
    <form>
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
                           onclick="submit_go_term_list_action('../SGOTermListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_isolate');" class="button"
                           alt="Focus analysis on probes that are members of the selected GO Terms"
                           title="Focus analysis on probes that are members of the selected GO Terms" value="Isolate" />
                    <input type="button" 
                           onclick="submit_go_term_list_action('../SGOTermListActions', <%= firstLabelIndex%>, <%= numPerPage%>, 'filter_exclude');" class="button"
                           alt="Remove from analysis, probes that are members of the selected GO Terms from analysis"
                           title="Remove from analysis, probes that are members of the selected GO Terms from analysis" value="Exclude" />
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
        <div class="sidebar_content_holder">
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
                        <th />
                        <th />
                        <th>Name</th>
                        <th>
                            <%
                            if (primaryPrecision) {
                            %>
                            Precision
                            <%
                            } else {
                    %>
                            <a title="View GO Terms that are represented in the current analysis ordered by Precision" 
                               alt="View GO Terms that are represented in the current analysis ordered by Precision" 
                               onclick="make_nav_request('../modules/go_term_list.jsp?sort=precision');" href="javascript: void(0);">Precision</a>
                    <%
                            }
                            %>
                        </th>
                        <th>
                            <%
                            if (primaryPrecision) {
                            %>
                            <a title="View GO Terms that are represented in the current analysis ordered by Recall" 
                               alt="View GO Terms that are represented in the current analysis ordered by Recall" 
                               onclick="make_nav_request('../modules/go_term_list.jsp?sort=recall');" href="javascript: void(0);">Recall</a>
                            <%
                            } else {
                    %>
                            <u>Recall</u>
                    <%
                            }
                            %>
                        </th>
                        <th>p-value</th>
                    </tr>
                </thead>
                <tbody>
                    <%

                FilterList filterList = FilterManager.getUniqueInstance().getFilterListForIdentifier(SessionAttributeManager.getSessionID(request));

                ArrayList<String> highIDs = HighlightManager.getUniqueInstance().getHighlightedGOTerms(sessionID);

                int labelIndex = 0;

                // Label degLabel = SessionAttributeManager.getDEGLabel(request);

                int maxProbeWidth = 0;


                // Primary stat, Secondary stat, GOTerms
                TreeMap<Double, TreeMap<Double, ArrayList<GOTerm>>> entiesSortedByPrimaryAndSecondaryStats = new TreeMap<Double, TreeMap<Double, ArrayList<GOTerm>>>();

                Set<Map.Entry<String, GOTerm>> goTermEntrySet = goTerms.entrySet();
                Iterator<Map.Entry<String, GOTerm>> goTermEntrySetIT = goTermEntrySet.iterator();
                while (goTermEntrySetIT.hasNext()) {
                    Map.Entry<String, GOTerm> entry = goTermEntrySetIT.next();
                    GOTerm goTerm = entry.getValue();

                    // Label P size = x
                    int sizeOfEntity = goTerm.getNumberOfMembers();

                    // Analysis, size = y
                    //int sizeOfAnalysis = probes.size();

                    // Intersection, size = z
                    int sizeOfIntersection = goTerm.getNumberOfIntersectingMembers();

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

                    TreeMap<Double, ArrayList<GOTerm>> entitiesBySecondary = new TreeMap<Double, ArrayList<GOTerm>>();
                    if (entiesSortedByPrimaryAndSecondaryStats.containsKey((double) primaryIndexValue)) {
                        entitiesBySecondary = entiesSortedByPrimaryAndSecondaryStats.get((double) primaryIndexValue);
                    }

                    ArrayList<GOTerm> entities = new ArrayList<GOTerm>();
                    if (entitiesBySecondary.containsKey((double) secondaryIndexValue)) {
                        entities = entitiesBySecondary.get((double) secondaryIndexValue);
                    }

                    entities.add(goTerm);

                    entitiesBySecondary.put((double) secondaryIndexValue, entities);

                    entiesSortedByPrimaryAndSecondaryStats.put((double) primaryIndexValue, entitiesBySecondary);
                }

                boolean pathwaysRemain = false;


                Set<Map.Entry<Double, TreeMap<Double, ArrayList<GOTerm>>>> entitiesOrderedByBothStats = entiesSortedByPrimaryAndSecondaryStats.entrySet();
                Iterator<Map.Entry<Double, TreeMap<Double, ArrayList<GOTerm>>>> entitiesOrderedByBothStatsIT = entitiesOrderedByBothStats.iterator();

                // For each primary stat value
                while (entitiesOrderedByBothStatsIT.hasNext() && labelIndex < numPerPage) {
                    Map.Entry<Double, TreeMap<Double, ArrayList<GOTerm>>> entitiesForPrimaryStat = entitiesOrderedByBothStatsIT.next();
                    TreeMap<Double, ArrayList<GOTerm>> entitiesAtPrimaryStatValue = entitiesForPrimaryStat.getValue();
                    Set<Map.Entry<Double, ArrayList<GOTerm>>> entitiesBySecondaryStat = entitiesAtPrimaryStatValue.entrySet();
                    Iterator<Map.Entry<Double, ArrayList<GOTerm>>> entitiesBySecondaryStatIT = entitiesBySecondaryStat.iterator();

                    // For each secondary stat value
                    while (entitiesBySecondaryStatIT.hasNext()) {
                        Map.Entry<Double, ArrayList<GOTerm>> precToGOTermEntry = entitiesBySecondaryStatIT.next();
                        ArrayList<GOTerm> entities = precToGOTermEntry.getValue();
                        Iterator<GOTerm> sameValueLabelIt = entities.iterator();

                        // For each Entity
                        while (sameValueLabelIt.hasNext() && labelIndex < numPerPage) {
                            GOTerm goTerm = sameValueLabelIt.next();
                            // Label P size = x
                            int sizeOfLabel = goTerm.getNumberOfMembers();
                            // Analysis, size = y
                            //int sizeOfAnalysis = probes.size();
                            // Intersection, size = z
                            int sizeOfIntersection = goTerm.getNumberOfIntersectingMembers();

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
                            if (highIDs != null && highIDs.contains(goTerm.getStringID())) {
                                rowClass = "highlighted";
                                imgUrl = "high.png";
                            }


                            String disabled = "";
                            if (filterList.getFilteredGOTerms().contains(goTerm.getStringID())) {
                                disabled = "disabled=\"true\" checked=\"true\"";
                            }

                    %>
                    <tr class="<%= rowClass%>" id="go_term_row_<%= goTerm.getStringID()%>">
                        <td rowspan="2" class="button_star"><input TYPE="CHECKBOX" name="go_term_checkbox_<%= goTerm.getStringID()%>" value="<%= goTerm.getStringID()%>" id="go_term_checkbox_<%= labelIndex%>" <%= disabled%> /></td>
                        <td rowspan="2" class="button_star"><img src="../resources/images/<%= imgUrl%>" name="go_term_button_<%= goTerm.getStringID()%>" id="go_term_button_<%= labelIndex%>" onclick="return highlightGOTerm('<%= goTerm.getStringID()%>', <%= labelIndex%>, '<%= rowBandingClass%>');" /></td>
                        <td>
                            <b><%= goTerm.getName()%></b>
                        </td>
                        <td style="white-space: nowrap;"><%= precision%> </td>
                        <td style="white-space: nowrap;">| <%= recall%> </td>
                        <td style="white-space: nowrap;">| <%= pVal%></td>
                    </tr>
                    <tr class="<%= rowClass%>" id="go_term_row_<%= goTerm.getStringID()%>_sub">
                        <td colspan="4" style="padding-bottom: 2px;">
                            <style>
                                .precision_recall {
                                    padding: 0;
                                    margin: 0;
                                    border-collapse: collapse;
                                    border-right: 1px solid gray;
                                }
                                .precision_recall td, .precision_recall th {
                                    border: 0;
                                    padding: 0;
                                    margin: 0;
                                    font-size: 10px;
                                }
                                .bar {
                                    height: 10px;
                                    margin: 2px 0;
                                }
                                .bar_thin {
                                    height: 2px;
                                }
                                .bar_probes {       background-color: #0033CC;  }
                                .bar_intersection { background-color: #FF6600; }
                                .bar_label {        background-color: #FFCC00;   }
                            </style>
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
                                            <td />
                                            <td />
                                            <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar_thin bar_probes" style="width: <%= probesWidth%>px;"></div></td>
                                            <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar_thin bar_intersection" style="width: <%= intersectionWidth%>px;"></div></td>
                                            <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"><div class="bar_thin bar_label" style="width: <%= labelWidth%>px;"></div></td>
                                        </tr>
                                        <tr>
                                            <td style="padding-right: 2px;">GO Term</td>
                                            <td style="padding-right: 4px;"><%= sizeOfLabel%></td>
                                            <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;" />
                                            <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_label" style="width: <%= intersectionWidth%>px;"></div></td>
                                            <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;"><div class="bar bar_label" style="width: <%= labelWidth%>px;"></div></td>
                                        </tr>
                                        <tr>
                                            <td style="padding-right: 2px;">Intersection:</td>
                                            <td style="padding-right: 4px;"><%= sizeOfIntersection%></td>
                                            <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;" />
                                            <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar bar_intersection" style="width: <%= intersectionWidth%>px;"></div></td>
                                            <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;" />
                                        </tr>
                                        <tr>
                                            <td style="padding-right: 2px;">Probes:</td>
                                            <td style="padding-right: 4px;"><%= sizeOfAnalysis%></td>
                                            <td style="background-color: #809FFF; border-left: 1px solid gray; width: <%= probesWidth%>px;"><div class="bar bar_probes" style="width: <%= probesWidth%>px;"></div></td>
                                            <td style="background-color: #FFB380; border-left: 1px solid gray; width: <%= intersectionWidth%>px;"><div class="bar  bar_probes" style="width: <%= intersectionWidth%>px;"></div></td>
                                            <td style="background-color: #FFE680; border-left: 1px solid gray; width: <%= labelWidth%>px;" />
                                        </tr>
                                        <tr>
                                            <td />
                                            <td />
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
                        <!-- <td><a href="../SFilterManipulator?filterType=4&annotationID=<%= goTerm.getStringID()%>&exclude" target="_parent" style="text-decoration: none;">X</a></td> -->
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
                            <a href="javascript: void(0);" onclick="make_nav_request('../modules/go_term_list.jsp');">View All <%= dataSize%> GO Terms</a>
                        </td>
                    </tr>
                    <%

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
        <div class="corners_bar">
            <div class="corners_bottom_left"></div>
            <div class="corners_bottom_right"></div>
            <div class="corners_connector_bar"></div>
        </div>
    </form>
    
</div>