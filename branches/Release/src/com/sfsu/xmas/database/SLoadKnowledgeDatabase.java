/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.database;

import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SLoadKnowledgeDatabase extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int dataSetID = Integer.parseInt(request.getParameter(SessionAttributes.DATABASE_ID));
        try {
            if (KnowledgeDataSetFactory.getUniqueInstance().isDatabase(dataSetID)) {
                // Set cookie
                response.addCookie(new YearLongCookie(SessionAttributes.KNOWLEDGE_LIBRARY, String.valueOf(dataSetID)));
                out.write(MsgTemplates.getSuccess("Knowledge library (\"" + dataSetID + "\") loaded"));
            } else {
                out.write(MsgTemplates.getError("Database (\"" + dataSetID + "\") does not exist"));
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
