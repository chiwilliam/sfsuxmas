/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.database;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SUnloadExpressionDatabase extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        boolean isSecondary = !(request.getParameter("secondary") == null);

        try {
            Cookie cookie;
            if (isSecondary) {
                cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.SECONDARY_EXPRESSION_DATABASE);
            } else {
                cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
            }
            if (cookie != null) {
                String oldValue = cookie.getValue();
                ServletUtil.emptyCookie(cookie);
                response.addCookie(cookie);

                if (FileGlobals.SINGLE_DATASET) {
                    // not used any more, make stale
                    ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(Integer.parseInt(oldValue), false);
                    eDB.makeStale();
                }

                // Changing data sets - unload traj file
                if (!isSecondary) {
                    Cookie trajCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.TRAJECTORY_FILE_NAME);
                    if (trajCookie != null) {
                        ServletUtil.emptyCookie(trajCookie);
                        response.addCookie(trajCookie);
                    }
                    String sessionID = SessionAttributeManager.getSessionID(request);
                    FilterManager.getUniqueInstance().removeFilters(sessionID);
                }

                out.write(MsgTemplates.getSuccess("data set with ID=\"" + oldValue + "\" unloaded"));
            } else {
                out.write(MsgTemplates.getError("A database was not found to unload"));
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
