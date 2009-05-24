/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.filter;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.servlet.ServletUtil;
import filter.*;
import java.io.*;

import java.util.StringTokenizer;
import javax.servlet.*;
import javax.servlet.http.*;
import com.sfsu.xmas.trajectory_files.LeafNodes;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;

/**
 *
 * @author bdalziel
 */
public class SFilterManipulator extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean removal = !(request.getParameter("removeFilter") == null);
        boolean removeAll = !(request.getParameter("removeAll") == null);


        String sessionID = SessionAttributeManager.getSessionID(request);
        if (sessionID == null) {
            response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
        }

        if (removal) {
            try {
                int filterID = Integer.parseInt(request.getParameter("removeFilter"));
                removeFilter(sessionID, filterID);
            } catch (NumberFormatException ex) {
                System.err.print(SFilterManipulator.class.getSimpleName() +
                        ": Invalid (non integer) filter ID provided = " + request.getParameter("removeFilter"));
            }
        } else if (removeAll) {
            FilterManager.getUniqueInstance().removeFilters(sessionID);
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_TRAJECTORY));
        } else {
            // Adding a filter
            try {
                int filterType = Integer.parseInt(request.getParameter("filterType"));
                boolean excluded = !(request.getParameter("exclude") == null);
                addFilter(SessionAttributeManager.getSessionID(request), request, filterType, excluded);
            } catch (NumberFormatException ex) {
                System.err.print(SFilterManipulator.class.getSimpleName() +
                        ": Invalid (non integer) filter type provided = " + request.getParameter("removeFilter"));
            }
        }
        response.sendRedirect("visualization/visualization.jsp");
    }

    private void removeFilter(String identifier, int filterID) {
        boolean successfulRemoval = FilterManager.getUniqueInstance().removeFilter(identifier, filterID);
        if (!successfulRemoval) {
            System.err.print(SFilterManipulator.class.getSimpleName() + ": No matching filter at index = " + filterID);
        }
    }

    public static void addFilter(String identifier, HttpServletRequest request, int filterType, boolean excluded) {
        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
        System.out.println(SFilterManipulator.class.getSimpleName() + ": FILTER ADDITION: " + filterType + ", EXCLUDED?: " + excluded);
        switch (filterType) {
            case 0:
                int[][] shape = td.getShapeFromRequest(request);
                FilterManager.getUniqueInstance().removeTrajectoryShapeFilter(identifier);
                TrajectoryShapeFilter trajFilter = new TrajectoryShapeFilter(shape, td.getBinUnit(), td.getMaximumSubtractiveDegree(), td.getMinimumSubtractiveDegree());
                FilterManager.getUniqueInstance().addFilter(identifier, trajFilter);
                break;
            case 1:
                String timePeriod = request.getParameter("timePeriod");
                String nodeID = request.getParameter("nodeID");
                if (nodeID != null && timePeriod != null) {
                    TrajNodeFilter trajNodeFilter = FilterManager.makeNodeFilter(Integer.parseInt(nodeID), Integer.parseInt(timePeriod), excluded);
                    FilterManager.getUniqueInstance().addFilter(identifier, trajNodeFilter);
                }
                break;
            case 2:
                String probeID = request.getParameter("geneID");
                ProbeFilter probeFilter = FilterManager.makeProbeIDFilter(probeID, excluded);
                FilterManager.getUniqueInstance().addFilter(identifier, probeFilter);
                break;
            case 3:
                String annotationID = request.getParameter("annotationID");
                if (annotationID != null) {
                    Label labs = kDB.getLabel(Integer.parseInt(annotationID));

                    LabelFilter filter = new LabelFilter(labs);
                    FilterManager.getUniqueInstance().addFilter(identifier, filter);
                }
                break;
            case 4:
                // Will eventually be an Anno propper
//                BasicDataAccess expDAO = new BasicDataAccess();
                String pathwayID = request.getParameter("pathwayID");
                String affyID = request.getParameter("affyID");
                String genID = request.getParameter("geneID");
                String annoID = request.getParameter("annotationID");
                if (pathwayID != null && !pathwayID.equals("-1") && pathwayID.length() != 0 && !pathwayID.equals(" ")) {
                    MultiProbeFilter pathwayFilter;
//                    pathwayFilter = new PathwayFilter(Integer.parseInt(pathwayID));
//                    FilterManager.addFilter(pathwayFilter);
                } else if (affyID != null && affyID.length() > 0) {
                    StringTokenizer strTok = new StringTokenizer(affyID, ",");
                    while (strTok.hasMoreTokens()) {
//                        ResultSet rs = expDAO.getAffyProbeInfo(new String[]{strTok.nextToken().trim()});
//                        try {
//                            while (rs.next()) {
//                                String psID = rs.getString("probeset_id");
                        ProbeFilter pf = FilterManager.makeProbeIDFilter(strTok.nextToken().trim(), excluded);
                        FilterManager.getUniqueInstance().addFilter(identifier, pf);
//                            }
//                        } catch (SQLException ex) {
//                            ex.printStackTrace();
//                        } finally {
//                            if (expDAO != null) {
//                                expDAO = null;
//                            }
//                            if (rs != null) {
//                                try {
//                                    rs.close();
//                                } catch (SQLException ex) {
//                                    Logger.getLogger(FilterManager.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
//                        }
                    }
                } else if (annoID != null && !annoID.equals("-1") && annoID.length() != 0 && !annoID.equals(" ")) {
                    MultiProbeFilter filter;
                    Label labs = kDB.getLabel(Integer.parseInt(annoID));
                    filter = new LabelFilter(labs);
                    FilterManager.getUniqueInstance().addFilter(identifier, filter);
                } else if (genID != null && !genID.equals("-1") && genID.length() != 0 && !genID.equals(" ")) {
                    StringTokenizer strTok = new StringTokenizer(genID, ",");
                    while (strTok.hasMoreTokens()) {
                        ProbeFilter filter = new ProbeFilter(strTok.nextToken().trim());
                        if (excluded) {
                            // Exclude
                            filter.exclude();
                        }
                        FilterManager.getUniqueInstance().addFilter(identifier, filter);
                    }
//                    } else if (true) {
//                        StringTokenizer strTok = new StringTokenizer(genID, ",");
//                        while (strTok.hasMoreTokens()) {
//                            ProbeFilter filter = new ProbeFilter(Integer.parseInt(strTok.nextToken().trim()));
//                            addFilter(filter);
//                        }
                }
                break;
            case 5:
                String clusterID = request.getParameter("clusterID");
                ClusterFilter clustFilt = new ClusterFilter(Integer.parseInt(clusterID));
                FilterManager.getUniqueInstance().addFilter(identifier, clustFilt);
                break;
            case 6:
                try {
                    int tp = Integer.parseInt(request.getParameter("timePeriod"));
                    int nID = Integer.parseInt(request.getParameter("nodeID"));

                    LeafNodes lns = td.getLeafNodes(identifier, nID, tp);
                    int[][] shp = lns.getFilterShape(eDB.getNumberOfTimePeriods(), td.getMaximumSubtractiveDegree() - td.getMinimumSubtractiveDegree() + 1, td.getMinimumSubtractiveDegree());

                    TrajectoryShapeFilter tFilter = new TrajectoryShapeFilter(shp, td.getBinUnit(), td.getMaximumSubtractiveDegree(), td.getMinimumSubtractiveDegree());
                    FilterManager.getUniqueInstance().addFilter(identifier, tFilter);
                    break;
                } catch (NumberFormatException ex) {
                    System.err.print(SFilterManipulator.class.getSimpleName() +
                            ": Invalid (non integer) time period or node id provided. TP = " +
                            request.getParameter("timePeriod") + ", Node ID = " + request.getParameter("nodeID"));
                }
                break;
            case 7:
                try {
                    int traj_count = Integer.parseInt(request.getParameter("traj_count"));
                    int check_count = Integer.parseInt(request.getParameter("check_count"));
                    int[] node_ids = new int[check_count];

                    int checkedNodeIndex = 0;
                    for (int m = 0; m < traj_count; m++) {
                        String x = request.getParameter("node_" + m);
                        if (x != null) {
                            node_ids[checkedNodeIndex] = Integer.parseInt(x);
                        }
                        checkedNodeIndex++;
                    }

                    // Assume TP5 throughout

                    LeafNodes lns = td.getMultiNodeTrajectories(identifier, node_ids);
                    int[][] shp = lns.getFilterShape(eDB.getNumberOfTimePeriods(), td.getMaximumSubtractiveDegree() - td.getMinimumSubtractiveDegree() + 1, td.getMinimumSubtractiveDegree());

                    TrajectoryShapeFilter tFilter = new TrajectoryShapeFilter(shp, td.getBinUnit(), td.getMaximumSubtractiveDegree(), td.getMinimumSubtractiveDegree());
                    FilterManager.getUniqueInstance().addFilter(identifier, tFilter);
                    break;
                } catch (NumberFormatException ex) {
                    System.err.print(SFilterManipulator.class.getSimpleName() +
                            ": Invalid (non integer) time period or node id provided. TP = " +
                            request.getParameter("timePeriod") + ", Node ID = " + request.getParameter("nodeID"));
                }
                break;
            default:
                System.out.println(SFilterManipulator.class.getSimpleName() + "FILTER TYPE: " + filterType + " UNKNOWN");
                break;
        }
    // Un-load any loaded snap shot file
//        XMLSnapShotFactory snapFact = XMLSnapShotFactory.getUniqueInstance();
//        snapFact.unload();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>
}
