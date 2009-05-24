/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.highlight;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SHighlightGOTerm extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String goTermID = (String) request.getParameter("go_term_id");

            String sessionID = SessionAttributeManager.getSessionID(request);
            if (sessionID == null) {
                response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
            }
            HighlightManager hm = HighlightManager.getUniqueInstance();
            if (hm.getHighlightedGOTerms(sessionID).contains(goTermID)) {
                hm.stopHighlightingGOTerm(sessionID, goTermID);
                out.append("0");
            } else {
                KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
                if (kDB != null) {
                    hm.highlightGOTerm(sessionID, kDB.getGOTerm(goTermID));
                }
                out.append("1");
            }
        } finally {
            out.close();
        }
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
