package com.sfsu.xmas.capture;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SGetCapturedAnalyses extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
//        SessionAttributeManager sam = new SessionAttributeManager(session);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.write("<ul id=\"fileTree\">");

            int fileCount = 0;

            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);

            File[] files = eDB.getCapturedAnalyses();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    CapturedAnalysisDAO caD = CapturedAnalysisFileFactory.getUniqueInstance().getFile(eDB.getID(), files[i].getName());

                }
            }
            if (fileCount <= 0) {
                out.write("<li><b>NO FILES</b></li>");
            }
            out.write("</ul>");
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
