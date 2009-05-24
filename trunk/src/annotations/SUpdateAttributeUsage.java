/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package annotations;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SUpdateAttributeUsage extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dbName = request.getParameter("database_name").trim();
        
        
        String setID = request.getParameter("data_set_id").trim();

//        KnowledgeDatabase adb = new KnowledgeDatabase(Integer.valueOf(setID));

//        IntegratedProbeAnnotations probeAts = adb.getProbes();

//        HashMap<String, Boolean> headers = adb.getAttributeUsage(); //probeAts.getProbeAttributes();
//        String baseID = "attribute_checkbox_";
//        Set<String> keys = headers.keySet();
//        
//        Iterator<String> it = keys.iterator();
//        while (it.hasNext()) {
//            String head = it.next();
//            String checkValue = request.getParameter(baseID + head);
//            if (checkValue != null) {
//                adb.useAttribute(head);
//            } else {
//                adb.stopUsingAttribute(head);
//            }
//        }

        response.sendRedirect("data/integrated_probe_attributes.jsp?database_name=" + dbName);

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
