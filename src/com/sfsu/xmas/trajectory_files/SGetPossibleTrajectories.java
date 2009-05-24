/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author bdalziel
 */
public class SGetPossibleTrajectories extends HttpServlet {

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
            TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
            
            int[][] trajCount = null;

            if (!(request.getParameter("max") == null)) {
                trajCount = td.getMaxPossibleTrajectorys();
            } else {
                ExecutionTimer eTime = new ExecutionTimer();
                eTime.start();
                trajCount = td.getPossibleTrajectorys(request);
                eTime.end();
                System.out.println("Time to traj: " + eTime.duration());
            }
            
            // TODO: Move this jazz to JSON
            StringBuffer result = new StringBuffer();
            for (int i = 0; i < trajCount.length; i++) {
                if (i != 0) {
                    result.append("|");
                }
                int[] tp = trajCount[i];
                for (int j = 0; j < tp.length; j++) {
                    if (j != 0) {
                        result.append(",");
                    }
                    result.append(tp[j]);
                }
            }

            out.println(result);
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
    }
    // </editor-fold>
}
