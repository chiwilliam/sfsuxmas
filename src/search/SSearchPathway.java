/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import java.io.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SSearchPathway extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String attributeToSearchWithin = "description";

        String searchString = request.getParameter("search_term");

//        KnowledgeDatabase adb = AnnotationDatabaseManager.getActiveDataBase();
//        if (adb != null) {
////            AnnotationDatabaseDataAccess dda = new AnnotationDatabaseDataAccess(adb);
////            ResultSet rs = dda.getPathwaySearchResults(attributeToSearchWithin, searchString);
////            int resultsCount = dda.getPathwaySearchResultsCount(attributeToSearchWithin, searchString);
////            if (rs != null) {
////                HttpSession session = request.getSession(true);
////                session.setAttribute("PathwaySearchResults", getPathwayIDs(rs, resultsCount));
////                session.setAttribute("PathwaySearchQuery", searchString);
////                session.setAttribute("PathwaySearchAttribute", attributeToSearchWithin);
////            }
//        }
        out.write("1");
        out.close();
    }

    private int[] getPathwayIDs(ResultSet rs, int resultSetSize) {
        int[] ids = new int[resultSetSize];
        if (rs != null) {
            try {
                int index = 0;
                while (rs.next()) {
                    ids[index] = Integer.parseInt(rs.getString("pathway_id"));
                    index++;
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(SSearchProbe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ids;
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
