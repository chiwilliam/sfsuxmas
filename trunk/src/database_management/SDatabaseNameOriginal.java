package database_management;

import com.sfsu.xmas.util.StringUtils;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SDatabaseNameOriginal extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String proposedDBName = StringUtils.clean(request.getParameter("database_name"));
        if (proposedDBName != null) {
//            proposedDBName = proposedDBName.replace(' ', '_');
//
//
//            boolean integratedDatabase = !(request.getParameter("integrated") == null);
//
//            boolean isOriginal = false;
//            if (integratedDatabase) {
//                isOriginal = !KnowledgeDatabaseFactory.getUniqueInstance().isDatabase(proposedDBName);
//            } else {
//                isOriginal = !ExpressionDatabaseFactory.getUniqueInstance().isDatabase(proposedDBName);
//            }
//
//            if (isOriginal) {
                out.write("unique");
//            } else {
//                out.write("duplicate");
//            }
        } else {
            out.write("duplicate");
        }
        out.close();
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
