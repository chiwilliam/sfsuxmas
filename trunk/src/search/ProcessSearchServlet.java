package search;

/*
 * CSearch.java
 *
 * Created on August 12, 2007, 2:58 PM
 */
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 * @version
 */
public class ProcessSearchServlet extends HttpServlet {

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String redirectURL = "SLoadViz?";
        boolean unique = !(request.getParameter("unique") == null);
        String affyID = request.getParameter("affyID");
        String pathwayID = request.getParameter("pathwayID");
        String annotationID = request.getParameter("annotationID");

        if (unique) {
            redirectURL += "unique&";
        }

        if (affyID != null && affyID.length() > 0) {
//            try {
////                ResultSet rs = getProbeIDForAffy(affyID);
////                if (rs != null && rs.next()) {
////                    int probeID = Integer.parseInt(rs.getString("probeset_id"));
////                    redirectURL += "geneID=" + probeID;
////                } else {
////                    redirectURL = "filter.jsp?error=2";
////                }
//            } catch (SQLException ex) {
//                Logger.getLogger(ProcessSearchServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } else if (pathwayID != null && pathwayID.length() > 0) {
            redirectURL += "pathwayID=" + pathwayID;
        } else if (annotationID != null && annotationID.length() > 0) {
            redirectURL += "annotationID=" + annotationID;
        } else {
            redirectURL = "filter.jsp?error=1";
        }

        response.sendRedirect(redirectURL);
    }

//    private ResultSet getProbeIDForAffy(String affyID) {
//        BasicDataAccess expDAO = new BasicDataAccess();
//        ResultSet rsProbesetInfo = null;
//        String[] affyIDs = new String[1];
//        affyIDs[0] = affyID;
//        rsProbesetInfo = expDAO.getAffyProbeInfo(affyIDs);
//        return rsProbesetInfo;
//    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
