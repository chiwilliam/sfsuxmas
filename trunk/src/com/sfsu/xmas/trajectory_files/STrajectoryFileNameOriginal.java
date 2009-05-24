package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.util.StringUtils;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class STrajectoryFileNameOriginal extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String proposedFileName = StringUtils.clean(request.getParameter("file_name"));

        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        
        boolean isOriginal = !eDB.isTrajectoryFile(proposedFileName);
        
        if (isOriginal) {
            out.write("unique");
        } else {
            out.write("duplicate");
        }
        out.close();
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
