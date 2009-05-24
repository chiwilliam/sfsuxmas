package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGetTrajectoryFiles extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        StringBuffer sb = new StringBuffer();
        sb.append("<ul id=\"fileTree\">");
        try {
            Cookie databaseCookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
            if (databaseCookie != null && !databaseCookie.getValue().equals("")) {
                String dataSetID = databaseCookie.getValue();
                ExpressionDataSet edb = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(Integer.parseInt(dataSetID), false);
                int fileCount = 0;

//                File[] l = edb.getTrajectoryDataFiles();
//                if (l != null && l.length > 0) {
//                    TrajectoryFile trajFile = SessionAttributeManager.getActiveTrajectoryFile(request);
//                    for (int i = 0; i < l.length; i++) {
//                        int dotLocation = l[i].getName().indexOf('.');
//                        if (dotLocation > 0) {
//                            String fileName = l[i].getName().substring(0, dotLocation);
//                            String highlightedRow = "";
//                            if (trajFile != null && fileName.equals(trajFile.getFileName())) {
//                                highlightedRow = " <span style=\"color: #66CC00; font-weight: bold;\">(Active)</span> ";
//                            }
//
//                            //SnapShotManager ssMan = new SnapShotManager(FileGlobals.getActiveDatabaseRoot(edb) + fileName + File.separatorChar);
//                            sb.append(
//                                    "<li><b class=\"fileNode\">FILE</b>: <a href=\"javascript:void(0)\" onclick=\"return load_file('" + fileName + "')\">Load</a> | \"" + fileName + highlightedRow + "\"</li>");
//                            fileCount++;
//                        } else {
//                            // Folder
//                        }
//                    }
//                } else {
//                    sb.append(MsgTemplates.getError("No files for active database (\"" + databaseCookie.getValue() + "\")"));
//                }
            } else {
                sb.append(MsgTemplates.getError("No active database found"));
            }
        } finally {
            sb.append("</ul>");
            out.write(sb.toString());
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
