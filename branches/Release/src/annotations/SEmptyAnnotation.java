package annotations;

import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.session.SessionAttributeManager;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SEmptyAnnotation extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String annotationID = request.getParameter("id").trim();
        
        System.out.println("Deassigning annotation: " + annotationID);

        KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);

        KnowledgeDataSetDAO ada = new KnowledgeDataSetDAO(kDB.getID());
        ada.emptyLabel(annotationID);
        
        response.sendRedirect("visualization/visualization.jsp");
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
