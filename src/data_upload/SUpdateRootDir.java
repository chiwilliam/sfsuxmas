package data_upload;

import com.sfsu.xmas.globals.FileGlobals;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SUpdateRootDir extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rootDir = (String) request.getParameter("root_dir");
        if (rootDir != null && rootDir.length() > 0) {
            if (rootDir.lastIndexOf('.') > rootDir.lastIndexOf(File.separatorChar)) {
                rootDir = rootDir.substring(0, rootDir.lastIndexOf(File.separatorChar));
            }
            System.out.println(SUpdateRootDir.class.getSimpleName() + ": Setting root DIR = " + rootDir);
            FileGlobals.setRoot(rootDir);
        }
        response.sendRedirect("data/system_setup.jsp");
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
