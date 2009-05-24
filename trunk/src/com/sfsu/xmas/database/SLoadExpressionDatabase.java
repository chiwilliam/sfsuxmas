package com.sfsu.xmas.database;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SLoadExpressionDatabase extends HttpServlet {

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
        boolean isSecondary = !(request.getParameter(SessionAttributes.SECONDARY) == null);
        try {
            if (ExpressionDataSetMultiton.getUniqueInstance().isDatabase(dataSetID)) {
                // Set cookie
                if (isSecondary) {
                    Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.SECONDARY_EXPRESSION_DATABASE);
                    if (cookie != null) {
                        String oldValue = cookie.getValue();
                        if (FileGlobals.SINGLE_DATASET) {
                            // unload old dataset
                            ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(Integer.parseInt(oldValue), false);
                            eDB.makeStale();
                        }
                    }
                    response.addCookie(new YearLongCookie(SessionAttributes.SECONDARY_EXPRESSION_DATABASE, String.valueOf(dataSetID)));
                    out.write(MsgTemplates.getSuccess("Secondary database (\"" + dataSetID + "\") loaded"));
                } else {
                    Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
                    if (cookie != null) {
                        String oldValue = cookie.getValue();
                        if (FileGlobals.SINGLE_DATASET) {
                            // unload old dataset
                            ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(Integer.parseInt(oldValue), false);
                            eDB.makeStale();
                        }
                    }
                    if (cookie == null || (!cookie.getValue().equals(String.valueOf(dataSetID)))) {
                        // Changing data sets - unload traj file
                        Cookie trajCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.TRAJECTORY_FILE_NAME);
                        if (trajCookie != null) {
                            ServletUtil.emptyCookie(trajCookie);
                            response.addCookie(trajCookie);
                        }
                    }
                    response.addCookie(new YearLongCookie(SessionAttributes.PRIMARY_EXPRESSION_DATABASE, String.valueOf(dataSetID)));

                    String sessionID = SessionAttributeManager.getSessionID(request);
                    FilterManager.getUniqueInstance().removeFilters(sessionID);

                    out.write(MsgTemplates.getSuccess("Primary data set with ID=\"" + dataSetID + "\" loaded"));
                }
            } else {
                out.write(MsgTemplates.getError("Data set with ID=\"" + dataSetID + "\" does not exist"));
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
