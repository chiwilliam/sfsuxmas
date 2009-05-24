/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.util;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SRefreshFactories extends HttpServlet {
   
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
            
            // Expression Database
            out.write("ExpressionDatabaseFactory - Number of databases prior to refresh = <b>" + ExpressionDataSetMultiton.getUniqueInstance().getNumberOfDataSets() + "</b><br />");
            ExpressionDataSetMultiton.refreshUniqueInstance();
            
            // Knowledge Database
            out.write("KnowledgeDatabaseFactory - Number of databases prior to refresh = <b>" + KnowledgeDataSetFactory.getUniqueInstance().getNumberOfDataSets() + "</b><br />");
            KnowledgeDataSetFactory.refreshUniqueInstance();
            
            // Trajectory file
            out.write("TrajectoryFileFactory - Number of files prior to refresh = <b>" + TrajectoryFileFactory.getUniqueInstance().getNumberOfDatabases() + "</b><br />");
            TrajectoryFileFactory.refreshUniqueInstance();
            
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
