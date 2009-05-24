package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.util.StringUtils;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SMakeTrajectoryFile extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = StringUtils.clean(request.getParameter("file_name"));

        if (fileName.length() > 0) {
            double binUnit = Double.parseDouble(request.getParameter("bin_unit"));

            TrajectoryFileGenerator gX = new TrajectoryFileGenerator(SessionAttributeManager.getActivePrimaryExpressionDatabase(request), binUnit);

            gX.generateFiles(fileName);

            String urlString = "data/your_data.jsp";
            response.sendRedirect(urlString);
        } else {

            String urlString = "data/your_data.jsp";
            response.sendRedirect(urlString);
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
