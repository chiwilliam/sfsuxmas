package com.sfsu.xmas.data_install;

import com.sfsu.xmas.dao.DAOFactoryFactory;
import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_files.readers.PathwayFileReader;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SIntegratePathwayData extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);

        String fileName = request.getParameter("pathway_file_name");

        PathwayFileReader fr = new PathwayFileReader(fileName);

        KnowledgeDataSetDAO kDBDAO = DAOFactoryFactory.getUniqueInstance().getDatabaseDAOFactory().getKnowledgeDataSetDAO(kDB.getID());

        boolean tablePopulationSuccessful = kDBDAO.populatePathwaysTable(fr);
        if (tablePopulationSuccessful) {
            System.out.println("");
        }
        kDBDAO.populatePathwayAssignmentTable(fr);
        
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
