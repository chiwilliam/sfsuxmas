/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.data_install;

import com.sfsu.xmas.data_files.readers.ProbeAttributeFileReader;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SIntegrateProbeAttributes extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // Add probe attributes
        KnowledgeDataSet kDB =SessionAttributeManager.getActiveKnowledgeLibrary(request);
        
        String fileName = request.getParameter("file_name").trim();
        ProbeAttributeFileReader fr = new ProbeAttributeFileReader(fileName);
        
        kDB.addProbeAttributes(fr);
                
        KnowledgeDataSetFactory.getUniqueInstance().refreshDatabase(SessionAttributeManager.getActiveKnowledgeLibrary(request).getID());

        String urlString = "data/your_data.jsp";
        response.sendRedirect(urlString);
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
