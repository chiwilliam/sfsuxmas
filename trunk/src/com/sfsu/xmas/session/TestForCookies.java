/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.session;

import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.servlet.ServletUtil;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestForCookies extends HttpServlet {

    private static final Cookie testCookie = new Cookie("hello", "world");
    private static final String paramName = "set";
    private static final String successURI = "/success.htm";
    private static final String failureURI = "/failure.htm";

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
            if (request.getParameter(paramName) == null) {
                response.addCookie(testCookie);
                response.sendRedirect(request.getRequestURI() + "?" + paramName + "=test");
            } else {
                Cookie[] cookies = request.getCookies();
                if (cookies == null || cookies.length <= 0) {
                    Cookie cookie = ServletUtil.getCookieFromKey(request, "");
                    if (cookie == null) {
                        out.write(MsgTemplates.getError("Cookies are not enabled. XMAS will not function correctly"));
                    }
                } else {
                    out.write(MsgTemplates.getSuccess("Cookies are enabled"));
                }
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
