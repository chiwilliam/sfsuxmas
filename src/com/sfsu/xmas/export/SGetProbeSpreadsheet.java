package com.sfsu.xmas.export;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataType;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypeValues;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypes;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGetProbeSpreadsheet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/octet-stream");

//        boolean textOutput = !(request.getAttribute("txt") == null);
//        boolean attributesAsWell = !(request.getAttribute("attributes") == null);

//        if (textOutput) {
//            response.addHeader("Content-Disposition", "attachment;filename=\"probe_list.txt\"");
//        } else {
        response.addHeader("Content-Disposition", "attachment;filename=\"probes_in_the_current_analysis.txt\"");
//        }
        PrintWriter out = response.getWriter();

        String delim = "\t";

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
        ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);

        Probes probes = null;
        if (eDB != null && td != null) {
            probes = eDB.getProbes(td.getProbes(SessionAttributeManager.getSessionID(request)), false);
        }

        // Header Line


        ProbeDataTypeValues pdtvs = null;
        ProbeDataTypes pdts = null;
        if (kDB != null) {
            pdts = kDB.getProbeDataTypes();
            pdtvs = kDB.getDataForProbes();
        }

        StringBuffer headerLine = new StringBuffer();
        headerLine.append("ID");
        headerLine.append(delim);
        if (pdts != null) {
            Set<Entry<Integer, ProbeDataType>> entrySet = pdts.entrySet();
            Iterator<Entry<Integer, ProbeDataType>> it = entrySet.iterator();
            while (it.hasNext()) {
                Entry<Integer, ProbeDataType> entry = it.next();
                headerLine.append(entry.getValue().getAttribute());
                headerLine.append(delim);
            }
        }
        out.write(headerLine.toString());
        out.println();

        try {
            if (probes != null) {
                Set<Map.Entry<String, Probe>> probeSet = probes.entrySet();
                Iterator<Map.Entry<String, Probe>> pit = probeSet.iterator();
                while (pit.hasNext()) {
                    StringBuffer probeLine = new StringBuffer();
                    Map.Entry<String, Probe> probeToValue = pit.next();

                    Probe p = probeToValue.getValue();
                    probeLine.append(p.getID());
                    probeLine.append(delim);
                    if (pdts != null && pdtvs != null && pdtvs.containsKey(p.getID())) {

                        HashMap<Integer, String> values = pdtvs.get(p.getID());

                        if (pdts != null) {
                            Set<Entry<Integer, ProbeDataType>> entrySet = pdts.entrySet();
                            Iterator<Entry<Integer, ProbeDataType>> it = entrySet.iterator();
                            while (it.hasNext()) {
                                Entry<Integer, ProbeDataType> entry = it.next();
                                int typeValue = entry.getValue().getID();
                                if (values.containsKey(typeValue)) {
                                    probeLine.append(values.get(typeValue));
                                    probeLine.append(delim);
                                }
                            }
                        }
                    }
                    out.write(probeLine.toString());
                    out.println();
                }
            }
            out.close();
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
    }// </editor-fold>
}
