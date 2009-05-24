package com.sfsu.xmas.capture;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.filter.FilterList;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.highlight.HighlightList;
import com.sfsu.xmas.highlight.HighlightManager;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import com.sfsu.xmas.util.StringUtils;
import com.sfsu.xmas.util.XMLUtils;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SCaptureAnalysis extends HttpServlet {

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
            ExpressionDataSet eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
            KnowledgeDataSet kDB = SessionAttributeManager.getActiveKnowledgeLibrary(request);
            TrajectoryFile tD = SessionAttributeManager.getActiveTrajectoryFile(request);



            String captureName = request.getParameter("capture_name");
            String captureDescription = request.getParameter("capture_description");

            String sessionID = SessionAttributeManager.getSessionID(request);



            DOMImplementation domImpl = new DOMImplementationImpl();
            Document doc = domImpl.createDocument(null, "captured_analysis", null);
            Element root = doc.getDocumentElement();

            Element basicData = doc.createElement("basic_data");
            Element captureNameE = doc.createElement("analysis_name");
            captureNameE.setAttribute("value", captureName);
            captureNameE.setNodeValue(captureName);
            basicData.appendChild(captureNameE);
            Element captureDescriptionE = doc.createElement("analysis_description");
            captureDescriptionE.setAttribute("value", captureDescription);
            captureDescriptionE.setNodeValue(captureDescription);
            basicData.appendChild(captureDescriptionE);
            root.appendChild(basicData);

            Element file_summary = doc.createElement("file_summary");
            if (eDB != null) {
                Element expression_data = doc.createElement("expression_data");
                expression_data.setAttribute("value", String.valueOf(eDB.getID()));
                file_summary.appendChild(expression_data);
            }
            if (kDB != null) {
                Element knowledge_data = doc.createElement("knowledge_data");
                knowledge_data.setAttribute("value", String.valueOf(kDB.getID()));
                file_summary.appendChild(knowledge_data);
            }
            if (tD != null) {
                Element trajectory_document = doc.createElement("trajectory_file");
                trajectory_document.setAttribute("value", tD.getFileName());
                file_summary.appendChild(trajectory_document);
            }

            Element viz_type = doc.createElement("visualization_type");
            if (SessionAttributeManager.isTrajectoryVisualization(request)) {
                viz_type.setAttribute("value", SessionAttributes.IMAGE_TYPE_TRAJECTORY);
            } else if (SessionAttributeManager.isProfileVisualization(request)) {
                viz_type.setAttribute("value", SessionAttributes.IMAGE_TYPE_PROFILE);
            } else if (SessionAttributeManager.isHybridVisualization(request)) {
                viz_type.setAttribute("value", SessionAttributes.IMAGE_TYPE_HYBRID);
            }
            file_summary.appendChild(viz_type);

            root.appendChild(file_summary);

            FilterManager fManager = FilterManager.getUniqueInstance();
            FilterList fList = fManager.getFilterListForIdentifier(sessionID);
            root.appendChild(fList.toXML(doc));
            
            HighlightManager hManager = HighlightManager.getUniqueInstance();
            HighlightList hList = hManager.getHighlightListForIdentifier(sessionID);
            root.appendChild(hList.toXML(doc));

            String path = FileGlobals.getActiveDatabaseRoot(eDB) + FileGlobals.ANALYSIS_CAPTURE_PREFIX + StringUtils.clean(captureName).replace(' ', '_') + ".xml";

            XMLUtils.writeXmlFile(doc, path);

//            String snapShotClusterDirectory = xmlSSFact.getCurrentFileLocation();
//            File f = new File(snapShotClusterDirectory);
//
//            boolean successfulDirCreation = f.mkdir();
//            if (!successfulDirCreation) {
//                System.err.println(SFilterSnapShot.class.getSimpleName() + ": File creation failed for " + snapShotClusterDirectory);
//            }
//            
            response.sendRedirect("visualization/visualization.jsp");

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
