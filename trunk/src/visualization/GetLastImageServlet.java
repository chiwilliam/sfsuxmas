package visualization;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class GetLastImageServlet extends HttpServlet {

    public void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

////        response.setHeader("cache-control", "max-age=0, must-revalidate, no-cache, no-store, private"); // HTTP 1.1
////        response.setHeader("pragma", "no-cache"); // HTTP 1.0
////        response.setDateHeader("expires", -1); // Stop proxy caching
//        response.setContentType("image/png");
//
//        String key = (String) request.getParameter("key");
//        
//        ImageCache ic = ImageCache.getInstance();
////        BufferedImage image = (BufferedImage) ic.getImage(key);
//        
//        //
////        HttpSession session = request.getSession(true);
////
////        BufferedImage image = (BufferedImage) session.getAttribute(SessionAttributes.IMAGE_PNG);
////
//        ServletOutputStream os = response.getOutputStream();
//        try {
//            ImageIO.write(image, "png", os);
//        } finally {
//            image.flush();
//            os.close();
//        }
        
        
    }

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
