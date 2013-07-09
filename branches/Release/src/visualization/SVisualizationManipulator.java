    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.EatenCookie;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SVisualizationManipulator extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
//        response.setContentType("image/png");

        HttpSession session = request.getSession(true);

        boolean propSet;
        
        // Clear image key
        response.addCookie(new EatenCookie(SessionAttributes.IMAGE_MAP_KEY, ""));

        /*
         * Data collapse operator
         */
        propSet = !(request.getParameter(SessionAttributes.PRESERVED) == null);
        if (propSet) {
            boolean preserved = Boolean.valueOf(request.getParameter(SessionAttributes.PRESERVED));
            response.addCookie(new YearLongCookie(SessionAttributes.PRESERVED, String.valueOf(preserved)));
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
            if (td != null) {
                // File active, switch to the correct version:
                String currentFileName = td.getFileName();
                if (td.isPreserved() && !preserved) {
                    // Currently preserved - collapse
                    response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, currentFileName + FileGlobals.COLLAPSED_POSTFIX));
                } else if (!td.isPreserved() && preserved) {
                    // Currently collapsed - preserve
                    response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, currentFileName.substring(0, currentFileName.length() - FileGlobals.COLLAPSED_POSTFIX.length())));
                }
            }
        }

        /*
         * Data comparison operator
         * DEPRICATED?
         */
        propSet = !(request.getParameter(SessionAttributes.FOCAL_DATABASE) == null);
        if (propSet) {
            // Should be 'primary' or 'secondary'
            session.setAttribute(SessionAttributes.FOCAL_DATABASE, request.getParameter(SessionAttributes.FOCAL_DATABASE));
        }


        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_COMPARATIVE) == null);
        if (propSet) {
            boolean value = true;
            Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.IMAGE_TYPE_COMPARATIVE);
            if (cookie != null) {
                value = !Boolean.parseBoolean(cookie.getValue());
            }
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE_TYPE_COMPARATIVE, String.valueOf(value)));
        }

        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_SUBTRACTIVE) == null);
        if (propSet) {
            boolean value = true;
            Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.IMAGE_TYPE_SUBTRACTIVE);
            if (cookie != null) {
                value = !Boolean.parseBoolean(cookie.getValue());
            }
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE_TYPE_SUBTRACTIVE, String.valueOf(value)));
        }

        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_DATASELECTOR) == null);
        if (propSet) {
            boolean value = true;
            Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.IMAGE_TYPE_DATASELECTOR);
            if (cookie != null) {
                value = !Boolean.parseBoolean(cookie.getValue());
            }
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE_TYPE_DATASELECTOR, String.valueOf(value)));
        }

        /*
         * Image type
         */
        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_TRAJECTORY) == null);
        if (propSet) {
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_TRAJECTORY));
        }
        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_PROFILE) == null);
        if (propSet) {
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_PROFILE));
        }
        propSet = !(request.getParameter(SessionAttributes.IMAGE_TYPE_HYBRID) == null);
        if (propSet) {
            response.addCookie(new YearLongCookie(SessionAttributes.IMAGE, SessionAttributes.IMAGE_TYPE_HYBRID));
        }

        out.write("1");
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
