package com.sfsu.xmas.data_files.expression;

import com.sfsu.xmas.dao.ExpressionDataFileDAO;
import com.sfsu.xmas.data_structures.expression.Sample;
import com.sfsu.xmas.data_structures.expression.Samples;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SGetSamples extends HttpServlet {

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
            String fileName = request.getParameter("file_name");

            // TODO: Make response = JSON
            
            if (fileName != null && !fileName.equals("")) {
                ExpressionDataFileDAO fr = new ExpressionDataFileDAO(fileName, null);

                Samples samples = fr.getSamples();

                for (int i = 0; i < samples.size(); i++) {
                    if (i > 0) {
                        out.write("|");
                    }
                    Sample samp = samples.get(i);
                    out.write(samp.getDescription().trim());
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
