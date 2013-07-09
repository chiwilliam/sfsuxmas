package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributes;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SUnloadTrajectoryFile extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.TRAJECTORY_FILE_NAME);
            if (cookie != null) {
                String oldValue = cookie.getValue();
                ServletUtil.emptyCookie(cookie);
                response.addCookie(cookie);
                out.write(MsgTemplates.getSuccess("Trajectory file (\"" + oldValue + "\") unloaded"));
            } else {
                out.write(MsgTemplates.getError("A trajectory file was not found to unload"));
            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
