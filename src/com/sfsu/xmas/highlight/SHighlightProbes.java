/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.highlight;

import com.sfsu.xmas.highlight.HighlightManager;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SHighlightProbes extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("cache-control", "max-age=0, must-revalidate, no-cache, no-store, private"); // HTTP 1.1

        response.setHeader("pragma", "no-cache"); // HTTP 1.0

        response.setDateHeader("expires", -1); // Stop proxy caching

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            String probeID = (String) request.getParameter("probe_id");

            String sessionID = SessionAttributeManager.getSessionID(request);
            if (sessionID == null) {
                response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
            }
            HighlightManager hm = HighlightManager.getUniqueInstance();
            if (hm.getHighlighted(sessionID).contains(probeID)) {
                hm.stopHighlightingProbe(sessionID, probeID);
                out.append("0");
            } else {
                hm.highlightProbe(sessionID, probeID);
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
