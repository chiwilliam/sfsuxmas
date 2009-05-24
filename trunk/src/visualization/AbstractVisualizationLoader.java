/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualization;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ben Dalziel
 */
public abstract class AbstractVisualizationLoader extends HttpServlet {

    public void cleanSession(HttpSession session) {
        //remove large session variables
//        session.removeAttribute("ImageMap");
//        session.removeAttribute("Image");
//        session.removeAttribute("XMLUnique");
    }
    
}
