package visualization;

import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.session.EatenCookie;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.visualizations.ImageCache;
import com.sfsu.xmas.visualizations.ImageMapQueue;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SImageMap extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String key = SessionAttributeManager.getImageMapKey(request);

        ExecutionTimer et = new ExecutionTimer();
        et.start();
        while (!ImageMapQueue.getInstance().isImageMapReady(key) && et.duration() < 500) {
            
        }
        et.end();
        System.out.println(this.getClass().getSimpleName() + ": DURATION = " + et.duration());
        // Clear image key
        response.addCookie(new EatenCookie(SessionAttributes.IMAGE_MAP_KEY, ""));
        
        ImageCache ic = ImageCache.getInstance();

        String imageMap = "";
        try {
            if (key == null || key.length() <= 0) {
                out.write("NULL KEY");
            } else {
//                out.write("<p>Key: \"" + key + "\"</p>");
                imageMap = ic.getImageMap(key);
                out.write(imageMap);
            }
        } finally {
            out.close();
            imageMap = null;
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
