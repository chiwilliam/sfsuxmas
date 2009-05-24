package com.sfsu.xmas.database;

import com.sfsu.xmas.dao.DataSetDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.data_sets.AbstractDataSet;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SDropDatabase extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int database_id = Integer.parseInt(request.getParameter("database_id"));

        boolean isLibrary = !(request.getParameter("library") == null);
//        boolean isSecondary = !(request.getParameter("secondary") == null);

        AbstractDataSet db;
        if (isLibrary) {
            db = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(database_id, false);
        } else {
            db = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(database_id, false);
        }

        try {
            if (db != null) {
                if (SessionAttributeManager.isAdmin(request)) {
                    db.makeStale();
                    // It exists, and it's admin, go ahead and delete
                    DataSetDAO dda = new DataSetDAO();
                    dda.dropDataSet(database_id, isLibrary);
                    out.write(MsgTemplates.getSuccess("Deleted data set ID=" + db.getID() + " Name=\"" + db.getName() + "\""));

                    if (isLibrary) {
                        Cookie libraryCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.KNOWLEDGE_LIBRARY);
                        if (libraryCookie != null && libraryCookie.getValue().equals(db.getID())) {
                            ServletUtil.emptyCookie(libraryCookie);
                            response.addCookie(libraryCookie);
                        }
                        KnowledgeDataSetFactory.getUniqueInstance().refreshDatabase(database_id);
                    } else {
                        Cookie secondaryCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.SECONDARY_EXPRESSION_DATABASE);
                        if (secondaryCookie != null && secondaryCookie.getValue().equals(db.getID())) {
                            ServletUtil.emptyCookie(secondaryCookie);
                            response.addCookie(secondaryCookie);
                        }

                        Cookie primaryCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
                        if (primaryCookie != null && primaryCookie.getValue().equals(db.getID())) {
                            ServletUtil.emptyCookie(primaryCookie);
                            response.addCookie(primaryCookie);
                            // Remove traj file
                            Cookie trajCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.TRAJECTORY_FILE_NAME);
                            if (trajCookie != null) {
                                ServletUtil.emptyCookie(trajCookie);
                                response.addCookie(trajCookie);
                            }
                            String sessionID = SessionAttributeManager.getSessionID(request);
                            FilterManager.getUniqueInstance().removeFilters(sessionID);
                        }
                    }


                }
            } else {
                out.write(MsgTemplates.getError("Trying to delete non-existent database"));
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
