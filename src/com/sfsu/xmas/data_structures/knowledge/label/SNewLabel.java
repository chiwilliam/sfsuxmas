/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.data_structures.knowledge.label;

import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SNewLabel extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String annotationName = request.getParameter("new_annotation_input");

        System.out.println("ADDING ANNOTATION: " + annotationName);

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
        KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(kDB.getID());

        int newLabelID = -1;
        
        if (annotationName != null && annotationName.length() != 0 && !annotationName.equals("null")) {
            newLabelID = ada.insertAnnotation(annotationName.trim());
        }
        if (newLabelID > 0) {
            out.write(String.valueOf(newLabelID));
            kDB.makeStale();
        } else {
            out.write("0");
        }
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
