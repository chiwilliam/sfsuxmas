/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package k_means;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SUpdateClusterOptions extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * 
         * Should be set as options in XML
         * 
         */
        String kStr = request.getParameter("resolution");
        if (kStr != null) {
            if (kStr.equals("timeperiods")) {
                // UPDATE XML
                System.out.println("Setting cluster option: use timeperiods resolution");
            } else if (kStr.equals("samples")) {
                // UPDATE XML
                System.out.println("Setting cluster option: use samples resolution");
            }
        }

        String normParam = request.getParameter("normalization");
        if (normParam != null) {
            if (normParam.equals("none")) {
                // UPDATE XML
                System.out.println("Setting cluster option: use no normalization");
            } else if (normParam.equals("mean")) {
                // UPDATE XML
                System.out.println("Setting cluster option: use mean normalization");
            }
        }

        // Now the XML has been updated, refresh:
//        XMLClusterFactory.getUniqueInstance().refreshFile();

        // Can go to viz page which will now render based on the updated options
        response.sendRedirect("visualization_cluster.jsp");
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
