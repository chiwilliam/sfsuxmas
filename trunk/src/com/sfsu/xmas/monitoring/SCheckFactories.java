/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.monitoring;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_structures.knowledge.Labels;
import com.sfsu.xmas.data_structures.knowledge.Pathways;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypes;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Little bit of a monitoring hack to assess memory usage within factories
 * @author bdalziel
 */
public class SCheckFactories extends HttpServlet {

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
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SCheckFactories</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SCheckFactories at " + request.getContextPath() + "</h1>");



            ExpressionDataSetMultiton eDBF = ExpressionDataSetMultiton.getUniqueInstance();
            out.println("<h2>Expression Data Sets</h2>");
            HashMap<Integer, ExpressionDataSet> dataSets = eDBF.getDataSets();

            Set<Entry<Integer, ExpressionDataSet>> entrySet = dataSets.entrySet();
            Iterator<Entry<Integer, ExpressionDataSet>> it = entrySet.iterator();
            while (it.hasNext()) {
                Entry<Integer, ExpressionDataSet> entry = it.next();
                ExpressionDataSet eDB = entry.getValue();
                out.println("<p>");
                out.println("Name, ID: <b>");
                out.println(eDB.getName());
                out.println("</b>, <b>");
                out.println(eDB.getID());
                out.println("</b><br />");
                out.println("Is fresh: <b>");
                out.println(eDB.isFresh());
                out.println("</b><br />");

                Probes dbProbes = eDB.getProbes();
                int pIndex = 0;

                out.println("Probes: ");
                if (dbProbes == null) {
                    out.println("NULL");
                } else {
                    out.println("<blockquote>");
                    Set<Entry<String, Probe>> probeEntries = dbProbes.entrySet();
                    Iterator<Entry<String, Probe>> pit = probeEntries.iterator();
                    while (pit.hasNext() && pIndex < 5) {
                        Entry<String, Probe> probeEntry = pit.next();
                        Probe p = probeEntry.getValue();
                        out.println("<br />");
                        out.println("Probe: " + p.getID());
                        out.println("<br />");
                        double[] expression = p.getTimePeriodExpression();
                        out.println("     Expression: ");
                        if (expression == null) {
                            out.println("NULL");
                        } else {
                            for (int i = 0; i < expression.length; i++) {
                                out.println(String.valueOf(expression[i]));
                                out.println(", ");
                            }
                        }
                        pIndex++;
                    }
                    out.println("</blockquote>");
                }

                out.println();
                out.println("</p>");
            }









            KnowledgeDataSetFactory kDBF = KnowledgeDataSetFactory.getUniqueInstance();
            out.println("<h2>Knowledge Data Sets</h2>");
            HashMap<Integer, KnowledgeDataSet> knowledgeDataSets = kDBF.getDataSets();

            Set<Entry<Integer, KnowledgeDataSet>> knowledgeEntrySet = knowledgeDataSets.entrySet();
            Iterator<Entry<Integer, KnowledgeDataSet>> kit = knowledgeEntrySet.iterator();
            while (kit.hasNext()) {
                Entry<Integer, KnowledgeDataSet> entry = kit.next();
                KnowledgeDataSet kDB = entry.getValue();
                out.println("<p>");
                out.println("Name, ID: <b>");
                out.println(kDB.getName());
                out.println("</b>, <b>");
                out.println(kDB.getID());
                out.println("</b><br />");
                out.println("Is fresh: <b>");
                out.println(kDB.isFresh());
                out.println("</b><br />");

                Pathways dbPathways = kDB.getPathways();
                out.println("Pathways: ");
                if (dbPathways == null) {
                    out.println("NULL");
                } else {
                    out.println("SIZE: " + dbPathways.size());
                }
                
                
                Labels dbLabels = kDB.getLabels();
                out.println("Labels: ");
                if (dbLabels == null) {
                    out.println("NULL");
                } else {
                    out.println("SIZE: " + dbLabels.size());
                }
                
                
                ProbeDataTypes probeDataTypes = kDB.getProbeDataTypes();
                out.println("Probe Attributes: ");
                if (probeDataTypes == null) {
                    out.println("NULL");
                } else {
                    out.println("SIZE: " + probeDataTypes.size());
                }
                out.println("</p>");
            }






            out.println("<h2>Trajectory Files</h2>");

            HashMap<Integer, HashMap<String, TrajectoryFile>> files = TrajectoryFileFactory.getUniqueInstance().getFiles();
            Set<Entry<Integer, HashMap<String, TrajectoryFile>>> fileEntries = files.entrySet();
            Iterator<Entry<Integer, HashMap<String, TrajectoryFile>>> dataToFiles = fileEntries.iterator();
            while (dataToFiles.hasNext()) {
                Entry<Integer, HashMap<String, TrajectoryFile>> cat = dataToFiles.next();
                int dataID = cat.getKey();

                out.println("<h3>Data set ID: " + dataID + "</h3>");
                HashMap<String, TrajectoryFile> fileHash = cat.getValue();

                Collection<TrajectoryFile> trajDocsCol = fileHash.values();

                Iterator<TrajectoryFile> trajDocsIt = trajDocsCol.iterator();
                while (trajDocsIt.hasNext()) {
                    TrajectoryFile td = trajDocsIt.next();
                    out.println("<p>");
                    out.println("Name, ID: <b>");
                    out.println(td.getFileName());
                    out.println("</b>");
                    out.println("</p>");
                }

            }

            out.println("</body>");
            out.println("</html>");
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
