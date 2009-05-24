/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.filter;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SPathwayListActions extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean filterIsolation = !(request.getParameter("filter_isolate") == null);
        boolean filterExclusion = !(request.getParameter("filter_exclude") == null);

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);

        if (filterIsolation) {
            filterPathways(kDB, request, false);
            response.sendRedirect("visualization/visualization.jsp");
        } else if (filterExclusion) {
            filterPathways(kDB, request, true);
            response.sendRedirect("visualization/visualization.jsp");
        } else {
            System.err.println(SPathwayListActions.class.getSimpleName() + ": No action specified");
        }
    }

    private void filterPathways(KnowledgeDataSet kDB, HttpServletRequest request, boolean exclude) {
        ArrayList<Integer> pathArray = new ArrayList<Integer>();
        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("pathway_checkbox_")) {
                int value = (int) Integer.parseInt(request.getParameter(key));
                pathArray.add(value);
            }
        }

        Integer[] pwayIDs = (Integer[]) pathArray.toArray(new Integer[0]);
        int[] pathwayIDs = new int[pwayIDs.length];
        for (int i = 0; i < pwayIDs.length; i++) {
            pathwayIDs[i] = pwayIDs[i];
        }

        String sessionID = SessionAttributeManager.getSessionID(request);
        FilterManager.getUniqueInstance().filterPathways(sessionID, kDB.getPathways(pathwayIDs), exclude);
    }

//    private void filterPathways(HttpServletRequest request, boolean exclude) {
//        ArrayList<Integer> pathwayArray = new ArrayList<Integer>();
//        Map parameterMap = request.getParameterMap();
//        Set keys = parameterMap.keySet();
//        Iterator it = keys.iterator();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            if (key.startsWith("pathway_checkbox_")) {
//                int value = (int) Integer.parseInt(request.getParameter(key));
////                int probe_id = Integer.parseInt(value);
//                pathwayArray.add(value);
//            }
//        }
//
//        Integer[] pwayIDs = (Integer[]) pathwayArray.toArray(new Integer[0]);
//        int[] pathwayIDs = new int[pwayIDs.length];
//        for (int i = 0; i < pwayIDs.length; i++) {
//            pathwayIDs[i] = pwayIDs[i];
//        }
//        
//        Pathways pathways = new Pathways(SessionAttributeManager.getActiveKnowledgeLibrary(request), pathwayIDs);
//
//
//        String identifier = "test_identifier";
//        
//        FilterManager.getUniqueInstance().filterPathways(identifier, pathways, exclude);
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
