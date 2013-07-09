package com.sfsu.xmas.data_structures.knowledge.label;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.data_files.readers.ProbeIDFileReader;
import java.io.*;

import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class SLabelProbeBatch extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean validLabel = true;

        int labelID = 0;

        boolean existingLabel = false;

        String newOrExisting = request.getParameter("label_type");
        if (newOrExisting != null) {
            // Labels exist
            if (newOrExisting.equals("existing_label")) {
                existingLabel = true;
            }
        }

        if (existingLabel) {
            // Existing label
            String labelIDString = request.getParameter("label_id").trim();
            if (labelIDString != null && !labelIDString.equals("")) {
                labelID = Integer.parseInt(labelIDString);
            } else {
                validLabel = false;
            }
        } else {
            // new label
            String labelName = request.getParameter("label_name");
            String labelDescription = request.getParameter("label_description");
            // Create label
            if (labelName != null && !labelName.equals("")) {
                KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
                labelID = kDB.addLabel(labelName, labelDescription);
                validLabel = (labelID >= 0);
            } else {
                validLabel = false;
            }
        }

        if (validLabel) {
            String fileName = request.getParameter("probe_id_file_name");
            ProbeIDFileReader fr = new ProbeIDFileReader(fileName);
            ArrayList<String> probeIDs = fr.getRowEntries();
            String[] probes = probeIDs.toArray(new String[probeIDs.size()]);
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(kDB.getID());
            ada.assignLabelToProbes(String.valueOf(labelID), probes);
        }

        KnowledgeDataSetFactory.getUniqueInstance().refreshDatabase(SessionAttributeManager.getActiveKnowledgeLibrary(request).getID());
        
        response.sendRedirect("data/your_data.jsp");
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
