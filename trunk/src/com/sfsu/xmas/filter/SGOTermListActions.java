/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.filter;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGOTermListActions extends HttpServlet {

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
            filterGOTerms(kDB, request, false);
            response.sendRedirect("visualization/visualization.jsp");
        } else if (filterExclusion) {
            filterGOTerms(kDB, request, true);
            response.sendRedirect("visualization/visualization.jsp");
        } else {
            System.err.println(SGOTermListActions.class.getSimpleName() + ": No action specified");
        }
    }

    private void filterGOTerms(KnowledgeDataSet kDB, HttpServletRequest request, boolean exclude) {
        ArrayList<String> goTermIDs = new ArrayList<String>();
        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("go_term_checkbox_")) {
                String value = request.getParameter(key);
                goTermIDs.add(value);
            }
        }

        String[] goTermIDStrings = (String[]) goTermIDs.toArray(new String[0]);

        String sessionID = SessionAttributeManager.getSessionID(request);
        FilterManager.getUniqueInstance().filterGOTerms(sessionID, kDB.getGOTerms(goTermIDStrings), exclude);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
