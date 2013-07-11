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

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

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
        
        // Clear image key
        response.addCookie(new EatenCookie(SessionAttributes.IMAGE_MAP_KEY, ""));

        /*
         * Data collapse operator
         */
        boolean propSet = !(request.getParameter(SessionAttributes.PRESERVED) == null);
        boolean clustSet = !(request.getParameter(SessionAttributes.CLUSTERED) == null);

        if (propSet && clustSet) {
            boolean clustered = Boolean.valueOf(request.getParameter(SessionAttributes.CLUSTERED));
            boolean preserved = Boolean.valueOf(request.getParameter(SessionAttributes.PRESERVED));
            response.addCookie(new YearLongCookie(SessionAttributes.PRESERVED, String.valueOf(preserved)));
            response.addCookie(new YearLongCookie(SessionAttributes.CLUSTERED, String.valueOf(clustered)));
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
            if (td != null) {
                // File active, switch to the correct version:
                String currentFileName = td.getFileName();
                String bareFileName = null;
                if (td.isPreserved()) bareFileName = currentFileName;
                if (td.isCollapsed()) bareFileName = currentFileName.substring(0, currentFileName.length() - FileGlobals.COLLAPSED_POSTFIX.length());
                if (td.isClustered()) bareFileName = currentFileName.substring(0, currentFileName.length() - (FileGlobals.CLUSTERED_INFIX + td.getK()).length());


                if (!td.isClustered() && clustered) {
                    // currently preserved or collapsed - clustered
                    response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, bareFileName + FileGlobals.CLUSTERED_INFIX + td.getK()));
                }
                if (!td.isPreserved() && preserved) {
                    // Currently preserved or clustered - collapse
                    response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, bareFileName));
                }
                if (!td.isCollapsed() && !preserved && !clustered) {
                    // Currently collapsed or clustered - preserve
                    response.addCookie(new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, bareFileName + FileGlobals.COLLAPSED_POSTFIX));
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
