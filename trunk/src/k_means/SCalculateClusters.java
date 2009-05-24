package k_means;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SCalculateClusters extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        XMLTrajectoryFactory xmlFact = XMLTrajectoryFactory.getUniqueInstance();
//        TrajectoryDocument trajDoc = xmlFact.getTrajectoryDocument();
//
//        Node rootNode = trajDoc.getRootNode();
//        Probes nl = TrajectoryDocument.getProbes(rootNode);
//        int k = 4;
//        if (nl.size() <= 0 || nl.size() > 1000) {
//            response.setContentType("text/html;charset=UTF-8");
//            PrintWriter out = response.getWriter();
//            out.print("Empty or too large node list");
//        } else {
//            String fileName = request.getParameter("cluster_name");
//
//            Document snapShot = XMLSnapShotFactory.getUniqueInstance().getDocument();
//            if (snapShot == null) {
//                // Make the snapshot
//
//                FilterManager.getXML(fileName);
//                XMLSnapShotFactory xmlSSFact = XMLSnapShotFactory.getUniqueInstance();
//                xmlSSFact.setFileName(fileName);
//
//                String snapShotClusterDirectory = xmlSSFact.getCurrentFileLocation();
//                File f = new File(snapShotClusterDirectory);
//
//
//                boolean successfulDirCreation = f.mkdir();
//                if (!successfulDirCreation) {
//                    System.err.println(SCalculateClusters.class.getSimpleName() + ": File creation failed for " + snapShotClusterDirectory);
//                }
//            }
//
//            // Cluster probes
//            // See if a value of K was passed into the request
//            String kStr = request.getParameter("kValue");
//            if (kStr != null) {
//                k = Integer.parseInt(kStr);
//            }
//
//            KMeans km = new KMeans(k);
//            km.useDistance(request.getParameter("metric"));
//
////            if (fileName != null && fileName.length() > 0) {
////                km.cluster(nl, fileName);
////            } else {
////                km.cluster(nl, "NO_FILE_NAME");
////            }
//            response.sendRedirect("visualization/visualization_cluster.jsp");
//        }
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
