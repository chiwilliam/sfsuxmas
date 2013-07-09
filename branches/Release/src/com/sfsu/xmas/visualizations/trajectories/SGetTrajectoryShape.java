package com.sfsu.xmas.visualizations.trajectories;

import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGetTrajectoryShape extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("image/png");
        TrajectoryViz cg = null;
        BufferedImage image = null;

        String sessionID = SessionAttributeManager.getSessionID(request);
        if (sessionID == null) {
            response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
        }

        int nodeID = Integer.parseInt(request.getParameter("nodeID"));
        int timePeriod = Integer.parseInt(request.getParameter("timePeriod"));
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));
        boolean legend = !(request.getParameter("legend") == null) && Boolean.valueOf(request.getParameter("legend"));
        ServletOutputStream os = response.getOutputStream();
        try {
            cg = new TrajectoryViz(sessionID, SessionAttributeManager.getActivePrimaryExpressionDatabase(request).getID(), SessionAttributeManager.getActiveTrajectoryFile(request).getFileName(), nodeID, timePeriod, width, height, legend);
            image = cg.generateGraph();
            if (image == null) {
                image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            }
            ImageIO.write(image, "png", os);
        } finally {
            os.close();
            if (image != null) {
                image.flush();
            }
            os = null;
            image = null;
            cg = null;
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
    }// </editor-fold>
}
