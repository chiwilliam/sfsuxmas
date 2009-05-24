/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SGetSampleVisualization extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("image/png");
        // Write image to the output stream
        ServletOutputStream os = response.getOutputStream();

        String probeID = request.getParameter("probeID");
        int timePeriod = Integer.parseInt(request.getParameter("timePeriod"));

//        SamplesViz cg = null;
//        BufferedImage image = null;
//
//        System.out.println("LOADING: Exact Visualization");
//        cg = new SamplesViz(sam, probeID, timePeriod);
//
//        image = cg.generateGraph();
//
//
//        if (image == null) {
//            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
//        }
//
//        ImageIO.write(image, "png", os);
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
