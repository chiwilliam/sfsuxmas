/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.data_structures.knowledge.probe_data;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.servlet.MsgTemplates;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SAddProbeDataTypeLink extends HttpServlet {

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
            String dataSetID = request.getParameter("data_id").trim();
            String attributeID = request.getParameter("attribute_id").trim();
            String link = request.getParameter("attribute_link").trim();

            if (!link.contains(ProbeDataTypes.PROBE_DATA_TYPE_VALUE_IDENTIFIER)) {
                out.write(MsgTemplates.getError("Link \"" + link + "\" does not contain wildcard"));
            } else {
                KnowledgeDataSet kDB = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(Integer.valueOf(dataSetID), false);

                if (kDB != null) {
                    kDB.addProbeAttributeLink(attributeID, link);
                    kDB.makeStale();
                    out.write(MsgTemplates.getSuccess("Link added to attribute"));
                }
            }
            out.close();
        } finally {
            out.write(MsgTemplates.getError("Invalid request"));
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
