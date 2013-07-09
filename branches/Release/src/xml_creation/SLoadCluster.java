/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_creation;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SLoadCluster extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String fileName = request.getParameter("fileName");
        System.out.println("Loading Cluster: " + fileName);
        try {
//            XMLClusterFactory.getUniqueInstance().setFileName(fileName);
////            ClusterDocument cd = new ClusterDocument(XMLClusterFactory.getUniqueInstance().getDocument());
////            XMLSnapShotFactory.getUniqueInstance().setFileName(cd.getParentFileName());
//            ClusterDocument clustD = new ClusterDocument(XMLClusterFactory.getUniqueInstance().getDocument());
//            out.write(clustD.getKValue());
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
    }
    // </editor-fold>
}
