package annotations;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;

public class SAssignAnnotation extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String annotationID = request.getParameter("annotationID").trim();

        System.out.println(annotationID);

        ArrayList<String> probeArray = new ArrayList<String>();

        Map parameterMap = request.getParameterMap();
        Set keys = parameterMap.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (key.startsWith("probe_checkbox_")) {
                String value = (String) request.getParameter(key);
//                int probe_id = Integer.parseInt(value);
                probeArray.add(value);
            }
        }

        String[] probes = new String[probeArray.size()];
        int probeIndex = 0;
        Iterator probeIt = probeArray.iterator();
        while (probeIt.hasNext()) {
            probes[probeIndex] = (String) probeIt.next();
            probeIndex++;
        }

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
        
        KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(kDB.getID());
        ada.assignLabelToProbes(annotationID, probes);

        response.sendRedirect("visualization/visualization.jsp");

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
