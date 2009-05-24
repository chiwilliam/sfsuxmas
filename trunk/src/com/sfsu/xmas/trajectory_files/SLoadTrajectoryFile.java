package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SLoadTrajectoryFile extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String fileName = request.getParameter("file_name");
        try {

            TrajectoryFile trajectoryFile = TrajectoryFileFactory.getUniqueInstance().getFile(SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getID(), fileName);

            if (trajectoryFile != null) {
                // Set cookie
                response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, fileName));
                
                // Determine if it's preserved or not.
                boolean preserved = !fileName.endsWith(FileGlobals.COLLAPSED_POSTFIX);
                response.addCookie(new YearLongCookie(SessionAttributes.PRESERVED, String.valueOf(preserved)));
                out.write(MsgTemplates.getSuccess("Trajectory file (\"" + fileName + "\") loaded"));
            } else {
                out.write(MsgTemplates.getError("Trajectory file (\"" + fileName + "\") does not exist"));
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
