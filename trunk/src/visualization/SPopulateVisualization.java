package visualization;

import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.visualizations.ImageCache;
import com.sfsu.xmas.visualizations.ImageMapQueue;
import com.sfsu.xmas.visualizations.VisualizationFactory;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @brief  Visualizations and image maps are unquestionably linked. This servlet
 *         populates the session with the the most recent visualization and 
 *         associated imagemap
 * 
 *          Scrap that - it was fucking up memory allocation
 *          - Saving enormous amounts of text to the session, somehow java is cacheing everything
 * 
 * @author bdalziel
 */
public class SPopulateVisualization extends AbstractVisualizationLoader {

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();

//        response.setHeader("cache-control", "max-age=0, must-revalidate, no-cache, no-store, private"); // HTTP 1.1
//        response.setHeader("pragma", "no-cache"); // HTTP 1.0
//        response.setDateHeader("expires", -1); // Stop proxy caching

        response.setContentType("image/png");

        String sessionID = SessionAttributeManager.getSessionID(request);
        if (sessionID == null) {
            response.addCookie(new YearLongCookie(SessionAttributes.SESSION_IDENTIFIER, ServletUtil.getNewSessionID(request)));
        }

        VisualizationFactory vizFact = VisualizationFactory.getUniqueInstance();
        IVisualization cg = vizFact.getVisualization(sessionID, request);
        BufferedImage image = cg.generateGraph();

        if (image == null) {
            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        }

        ImageCache ic = ImageCache.getInstance();
        
        // Store key as cookie...
        String key = ic.storeImageMap(cg.getImageMap(), SessionAttributeManager.getImageMapKey(request));
        
        ImageMapQueue.getInstance().imageMapIsReady(key);
        
//        HttpSession session = request.getSession(true);
//        session.setAttribute(SessionAttributes.IMAGE_PNG, image);
//        session.setAttribute(SessionAttributes.IMAGEMAP, cg.getImageMap());

        ServletOutputStream os = response.getOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } finally {
            os.close();
            image.flush();
            vizFact = null;
            cg = null;
            image = null;
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>
}
