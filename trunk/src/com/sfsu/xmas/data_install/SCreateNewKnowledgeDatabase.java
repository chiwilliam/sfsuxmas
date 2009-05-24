package com.sfsu.xmas.data_install;

import com.sfsu.xmas.dao.DataSetDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.util.StringUtils;

public class SCreateNewKnowledgeDatabase extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String databaseName = StringUtils.clean(request.getParameter("database_name"));
        databaseName = databaseName.replace(' ', '_');
        
        String databaseDescription = StringUtils.clean(request.getParameter("database_description"));

        DataSetDAO databasesDA = new DataSetDAO();
        int dataSetID = databasesDA.insertNewKnowledgeDataSet(databaseName, databaseDescription);
        
        response.addCookie(new YearLongCookie(SessionAttributes.KNOWLEDGE_LIBRARY, String.valueOf(dataSetID)));

        
        String urlString = "data/your_data.jsp";
        response.sendRedirect(urlString);
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
    }// </editor-fold>
}
