/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bdalziel
 */
public class SGetMultiMemberImage extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("cache-control", "max-age=0, must-revalidate, no-cache, no-store, private"); // HTTP 1.1

        response.setHeader("pragma", "no-cache"); // HTTP 1.0

        response.setDateHeader("expires", -1); // Stop proxy caching

        response.setContentType("image/png");

        int max = Integer.parseInt(request.getParameter("max"));
        int maxPoss = Integer.parseInt(request.getParameter("maxPoss"));
        int actual = Integer.parseInt(request.getParameter("actual"));

        MembershipRenderer dr = new MembershipRenderer(max, maxPoss, actual);

        BufferedImage image = dr.getVisualization();

        ServletOutputStream os = response.getOutputStream();

        ImageIO.write(image, "png", os);
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
    }// </editor-fold>
}
