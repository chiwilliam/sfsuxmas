/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.external_data;

import com.sfsu.xmas.dao.PCADDAO;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGetPCADData extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        StringBuffer sb = new StringBuffer();

        String probeID = (String) request.getParameter("probe_id");

        PCADDAO pcad = PCADDAO.getInstance();

        if (probeID != null) {
            sb = pcad.makePCADProbeQuery(probeID);
        }

        String url = "http://array.sfsu.edu/cgi-bin/SMD/pcad_search.pl?EST_CAYC_CAYF_CCAG=" + probeID + "&EST_CAYC_CAYF_CCAG=" + probeID + "&submit_query=Do%20Search";
        if (sb != null && !sb.toString().equals(PCADDAO.CONNECT_TIMEOUT) && !sb.toString().equals(PCADDAO.READ_TIMEOUT)) {
            String pcadNote = sb.toString();

            sb = new StringBuffer();
            sb.append("<div style=\"border-left: 2px solid gray; padding-left: 5px;\">");
            String pcadPrefix = "";
            if (!pcadNote.equals("") && sb.length() > 0) {
                pcadPrefix = "<i>PCAD</i>: ";
            } else {
                pcadNote = "PCAD Entry";
            }
            sb.append(pcadPrefix);
            sb.append("<a href=\"");
            sb.append(url);
            sb.append("\" title=\"View PCAD Entry\" target=\"_blank\">");
            sb.append(pcadNote);
            sb.append("</a>");
            sb.append("</div>");
        }

        out.write(sb.toString());

        out.close();
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
