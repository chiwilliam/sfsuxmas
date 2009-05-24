package com.sfsu.xmas.capture;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.filter.FilterManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.session.EatenCookie;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.util.ArrayList;
import java.util.Iterator;

public class SLoadAnalysis extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

        String fileName = request.getParameter("file_name");
        System.out.println("Loading Analysis: " + fileName);
        try {

            CapturedAnalysisDAO cad = CapturedAnalysisFileFactory.getUniqueInstance().getFile(eDB.getID(), fileName);
            String id = SessionAttributeManager.getSessionID(request);
            FilterManager.getUniqueInstance().removeFilters(id);

//            XMLSnapShotFactory.getUniqueInstance().setFileName(fileName);
//            Document doc = XMLSnapShotFactory.getUniqueInstance().getDocument();
//            
            StringBuffer sb = new StringBuffer();
            
            ArrayList<Cookie> cookies = cad.loadRequiredData(request, sb);
            Iterator<Cookie> it = cookies.iterator();
            while (it.hasNext()) {
                Cookie c = it.next();
                if (c != null) {
                    response.addCookie(c);
                }
            }
            
            cad.loadFilters(id, sb);
            cad.loadHighlights(id, sb);
            
            // Load image type
            String imageType = cad.getVisualizationType();
            if (imageType != null && !imageType.equals("")) {
                response.addCookie(getCookieForImageType(imageType));
            } else {
                sb.append(MsgTemplates.getError("Null image type"));
            }

            
            
//            getProbeIDFiltersFromXML(doc);
//            getAnnotationIDFiltersFromXML(doc);
//            getPathwayIDFiltersFromXML(doc);
//            getNodeIDFiltersFromXML(doc);
//            getShapeFiltersFromXML(sam, doc);
            
            out.write(sb.toString());
            
        } finally {
            out.close();
        }
    }

    protected Cookie getCookieForImageType(String imageType) {
        if (imageType.equals(SessionAttributes.IMAGE_TYPE_TRAJECTORY)) {
            return new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_TRAJECTORY);
        }
        if (imageType.equals(SessionAttributes.IMAGE_TYPE_PROFILE)) {
            return new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_PROFILE);
        }
        if (imageType.equals(SessionAttributes.IMAGE_TYPE_HYBRID)) {
            return new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_HYBRID);
        }
        return new EatenCookie(SessionAttributes.IMAGE, "");
    }

//    public void getProbeIDFiltersFromXML(Document doc) {
//        NodeList probeIDs = getNodeList(doc, ".//ProbeIDs/Probe");
//        if (probeIDs != null) {
//            for (int i = 0; i < probeIDs.getLength(); i++) {
//                Node probeF = probeIDs.item(i);
//                String id = (String) probeF.getAttributes().getNamedItem("ID").getTextContent();
//                String exc = (String) probeF.getAttributes().getNamedItem("excluded").getTextContent();
//                boolean excluded = false;
//                if (exc.equals("true")) {
//                    excluded = true;
//                }
//                ProbeFilter filter = FilterManager.makeProbeIDFilter(id, excluded);
//                FilterManager.getUniqueInstance().addFilter("", filter);
//            }
//        }
//    }
//
//    public void getAnnotationIDFiltersFromXML(Document doc) {
//        NodeList probeIDs = getNodeList(doc, ".//GroupedIDs/Annotation");
//        if (probeIDs != null) {
//            for (int i = 0; i < probeIDs.getLength(); i++) {
//                Node probeF = probeIDs.item(i);
//                String id = (String) probeF.getAttributes().getNamedItem("ID").getTextContent();
////                LabelFilter filter = FilterManager.makeAnnotationIDFilter(Integer.parseInt(id));
////                FilterManager.getUniqueInstance().addFilter("", filter);
//            }
//        }
//    }
//
//    public void getPathwayIDFiltersFromXML(Document doc) {
//        NodeList probeIDs = getNodeList(doc, ".//GroupedIDs/Pathway");
//        for (int i = 0; i < probeIDs.getLength(); i++) {
//            Node probeF = probeIDs.item(i);
//            String id = (String) probeF.getAttributes().getNamedItem("ID").getTextContent();
////            PathwayFilter filter = FilterManager.makePathwayIDFilter(Integer.parseInt(id));
////            FilterManager.addFilter(filter);
//        }
//    }
//
//    public void getNodeIDFiltersFromXML(Document doc) {
//        NodeList nodeIDs = getNodeList(doc, ".//NodeIDs/Node");
//        for (int i = 0; i < nodeIDs.getLength(); i++) {
//            Node probeF = nodeIDs.item(i);
//            String id = (String) probeF.getAttributes().getNamedItem("ID").getTextContent();
//            String timePeriod = (String) probeF.getAttributes().getNamedItem("timePeriod").getTextContent();
//            TrajNodeFilter filter = FilterManager.makeNodeFilter(Integer.parseInt(id), Integer.parseInt(timePeriod), false);
//            FilterManager.getUniqueInstance().addFilter("", filter);
//        }
//    }
//
//    public void getShapeFiltersFromXML(HttpServletRequest request, Document doc) {
//        CapturedAnalysisDocument d = new CapturedAnalysisDocument(doc);
//        NodeList shapeFilters = d.getShapeFilters();
//
//        TrajectoryDocument trajD = SessionAttributeManager.getActiveTrajectoryFile(request);
//        int range = trajD.getMaximumSubtractiveDegree() - trajD.getMinimumSubtractiveDegree() + 1;
//
//        // For each shape filter
//        for (int i = 0; i < shapeFilters.getLength(); i++) {
//            Node shape = shapeFilters.item(i);
//
//            NodeList timePeriods = shape.getChildNodes();
//            if (timePeriods != null) {
//                int[][] shapeArray = new int[trajD.getNumberOfTimePeriods()][range];
//                // For each time period
//                boolean conditionAdded = false;
//
//                for (int tp = 0; tp < timePeriods.getLength(); tp++) {
//                    Node timePeriod = timePeriods.item(tp);
////                    String timePeriodID = timePeriod.getAttributes().getNamedItem("ID").getTextContent();
//
//                    NodeList conditions = timePeriod.getChildNodes();
//                    for (int cond = 0; cond < range; cond++) {
//                        shapeArray[tp][cond] = FilterManager.blankShapeValue;
//                    }
//                    // For each condition
//                    if (conditions != null) {
//                        for (int cond = 0; cond < conditions.getLength(); cond++) {
//                            Node condition = conditions.item(cond);
//                            String move = condition.getAttributes().getNamedItem("move").getTextContent();
//                            shapeArray[tp][range - (trajD.getMaximumSubtractiveDegree() - Integer.parseInt(move)) - 1] = 1;
//                            conditionAdded = true;
//                        }
//                    }
//                }
//                if (conditionAdded) {
//                    TrajectoryShapeFilter filter = new TrajectoryShapeFilter(shapeArray, trajD.getBinUnit(), trajD.getMaximumSubtractiveDegree(), trajD.getMinimumSubtractiveDegree());
//                    FilterManager.getUniqueInstance().addFilter("", filter);
//                }
//            }
//        }
//        trajD = null;
//    }

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
