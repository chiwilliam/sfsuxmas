package com.sfsu.xmas.data_install;

import com.sfsu.xmas.data_files.expression.FileProbes;
import com.sfsu.xmas.dao.ExpressionDataFileDAO;
import com.sfsu.xmas.dao.ProbeInserterDAO;
import com.sfsu.xmas.dao.DAOFactoryFactory;
import com.sfsu.xmas.dao.DataSetDAO;
import com.sfsu.xmas.dao.ExpressionDataSetDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_structures.expression.Samples;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.servlet.ServletUtil;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.util.StringUtils;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class SUploadData extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("file_name").trim();

        String databaseName = StringUtils.clean(request.getParameter("database_name"));
        databaseName = databaseName.replace(' ', '_');

        String databaseDescription = StringUtils.clean(request.getParameter("database_description"));
        
        String reductionStrategy = request.getParameter(DataUploadGlobals.DATA_REDUCTION_STRATEGY_KEY);
        
        // Create Database
        DataSetDAO databasesDA = new DataSetDAO();
        int dataSetID = databasesDA.insertNewExpressionDataSet(databaseName, databaseDescription);
        Cookie cookie = ServletUtil.getCookieFromKey(request, SessionAttributes.PRIMARY_EXPRESSION_DATABASE);
        if (cookie != null) {
            String oldValue = cookie.getValue();
            if (FileGlobals.SINGLE_DATASET) {
                // unload old dataset
                ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(Integer.parseInt(oldValue), false);
                eDB.makeStale();
            }
        }
        response.addCookie(new YearLongCookie(SessionAttributes.PRIMARY_EXPRESSION_DATABASE, String.valueOf(dataSetID)));


        ExpressionDataSetDAO expDAO = DAOFactoryFactory.getUniqueInstance().getDatabaseDAOFactory().getExpressionDataSetDAO(dataSetID);
        
        // Time Periods
        TimePeriods tps = new TimePeriods();
        int numberOfTimePeriods = Integer.parseInt(request.getParameter("timePeriodCount"));
        for (int i = 0; i < numberOfTimePeriods; i++) {
            // get description and quantity of samples
            String description = request.getParameter("timePeriodDescription" + i);
            int numberOfSamplesInTimePeriod = Integer.parseInt(request.getParameter("timePeriodSampleCount" + i));
            tps.add(new TimePeriod(i+1, description, numberOfSamplesInTimePeriod));
        }
        expDAO.insertTimePeriods(tps);
        
        // Samples
        ExpressionDataFileDAO fr = new ExpressionDataFileDAO(fileName, tps);
        Samples samples = fr.getSamples();
        expDAO.insertSamples(samples);

        // Probes
        for (int i = 0; true; i++) {
            FileProbes probes = fr.getProbes(i);
            if (probes != null && probes.size() > 0) {
                new ProbeInserterDAO(dataSetID, probes, fr.getSamples().size(), tps, reductionStrategy, i);
            } else {
                break;
            }
        }

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
    }
    // </editor-fold>
}
