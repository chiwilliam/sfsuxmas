package com.sfsu.xmas.filter;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

public class SProbeListActions extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sessionID = SessionAttributeManager.getSessionID(request);
        if (sessionID == null) {
            response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
        }

        boolean filterIsolation = !(request.getParameter("filter_isolate") == null);
        boolean filterExclusion = !(request.getParameter("filter_exclude") == null);
        boolean label = !(request.getParameter("label_probes") == null);
        boolean removeLabel = !(request.getParameter("filter_annotate_remove") == null);

        if (label) {
            int numberAnnotated = labelProbes(request);
            
            if (numberAnnotated > 0) {
                SessionAttributeManager.getActiveKnowledgeLibrary(request).makeStale();
            }
            
            response.sendRedirect("visualization/visualization.jsp?sb=probe_list&par=labels");
        } else if (removeLabel) {
            int numberAnnotated = unLabelProbe(request);
            response.sendRedirect("visualization/visualization.jsp?message_id=ANNOTATION_ADDED&message_value=" + numberAnnotated);
        } else if (filterIsolation) {
            filterProbes(sessionID, getProbeIDsFromRequest(request), false);
            response.sendRedirect("visualization/visualization.jsp");
        } else if (filterExclusion) {
            filterProbes(sessionID, getProbeIDsFromRequest(request), true);
            response.sendRedirect("visualization/visualization.jsp");
        } else {
            System.err.println(SProbeListActions.class.getSimpleName() + ": No action specified");
        }

    }

    private int labelProbes(HttpServletRequest request) {
        String labelID = request.getParameter("label_id").trim();
        System.out.println("Label ID: " + labelID);

        ArrayList<String> probeArray = new ArrayList<String>();
        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("probe_checkbox_")) {
                String value = (String) request.getParameter(key);
//                int probe_id = Integer.parseInt(value);
                probeArray.add(value);
            }
        }

        String[] probes = (String[]) probeArray.toArray(new String[0]);

        KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(SessionAttributeManager.getActiveKnowledgeLibrary(request).getID());
        ada.assignLabelToProbes(labelID, probes);
        return probes.length;
    }

    private int unLabelProbe(HttpServletRequest request) {
        String annotationID = request.getParameter("annotationID").trim();
        System.out.println(annotationID);

        ArrayList<String> probeArray = new ArrayList<String>();
        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("probe_checkbox_")) {
                String value = (String) request.getParameter(key);
                probeArray.add(value);
            }
        }

        String[] probes = (String[]) probeArray.toArray(new String[0]);

        KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(SessionAttributeManager.getActiveKnowledgeLibrary(request).getID());
        ada.deAssignProbesFromLabel(annotationID, probes);
        return probes.length;
    }

    private void filterProbes(String identifier, Probes probes, boolean exclude) {
        FilterManager.getUniqueInstance().filterProbes(identifier, probes, exclude);
    }

    protected Probes getProbeIDsFromRequest(HttpServletRequest request) {
        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        ArrayList<String> probeArray = new ArrayList<String>();
        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("probe_checkbox_")) {
                String value = (String) request.getParameter(key);
                probeArray.add(value);
            }
        }
        return eDB.getProbes(probeArray.toArray(new String[probeArray.size()]), false);
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
