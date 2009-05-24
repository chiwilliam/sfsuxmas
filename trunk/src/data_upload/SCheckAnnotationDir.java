/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data_upload;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SCheckAnnotationDir extends HttpServlet {
   
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

            String rootDir = (String) request.getParameter("rootName");
            if (rootDir != null && rootDir.length() > 0) {
                File[] l = new java.io.File(rootDir).listFiles();
                if (l == null) {
                    // Error
                    out.write("0");
                } else {
                    // Ok
                    out.write("1");
                }
            } else {
                out.write("0");
            }

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
