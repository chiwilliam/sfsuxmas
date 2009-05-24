package com.sfsu.xmas.dao;

import com.sfsu.xmas.capture.*;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.highlight.HighlightManager;
import com.sfsu.xmas.servlet.MsgTemplates;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.session.SessionAttributes;
import com.sfsu.xmas.session.YearLongCookie;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import com.sfsu.xmas.data_sets.AbstractDataSet;
import com.sfsu.xmas.trajectory_files.TrajectoryFile;
import filter.TrajectoryShapeFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CapturedAnalysisDAO extends XMLFileDAO {

    protected int knowledgeSetID;
    protected String tDName;
    protected String vizType;
    protected String description;
    public static final String XML_KEY_FILTERS = "filters";
    public static final String XML_KEY_FILTER = "filter";
    public static final String XML_KEY_HIGHLIGHTS = "highlights";
    public static final String XML_KEY_HIGHLIGHT = "highlight";
    public static final String XML_KEY_PROBES = "probes";
    public static final String XML_KEY_LABELS = "labels";
    public static final String XML_KEY_PATHWAYS = "pathways";
    public static final String XML_KEY_GO_TERMS = "go_terms";

    public CapturedAnalysisDAO(String name, int parentDB) {
        super(name, parentDB);
        description = getXpathString("//basic_data/analysis_description/@value");
        knowledgeSetID = getXpathInteger("//file_summary/knowledge_data/@value");
        tDName = getXpathString("//file_summary/trajectory_file/@value");
        vizType = getXpathString("//file_summary/visualization_type/@value");
    }

    public int getExpressionDatabaseName() {
        return parentDataSetID;
    }

    public int getKnowledgeDatabaseName() {
        return knowledgeSetID;
    }

    public String getVisualizationType() {
        return vizType;
    }

    public String getTrajectoryFileName() {
        return tDName;
    }

    public String getDescription() {
        return description;
    }

    public boolean expressionDBRequired() {
        boolean required = false;

        NodeList probeFilters = getNodeList(".//" + XML_KEY_FILTERS);
        if (probeFilters.getLength() > 0) {
            required = true;
        }
        NodeList probeHighlights = getNodeList(".//" + XML_KEY_HIGHLIGHTS);
        if (probeHighlights.getLength() > 0) {
            required = true;
        }

        return required;
    }

    public boolean knowledgeDBRequired() {
        // Any filters?
        NodeList filters = getNodeList(".//" + XML_KEY_FILTERS + "/pathways");
        if (filters.getLength() > 0) {
            return true;
        }
        filters = getNodeList(".//" + XML_KEY_FILTERS + "/" + XML_KEY_LABELS);
        if (filters.getLength() > 0) {
            return true;
        }
        return false;
    }

    public boolean trajectoryDocRequired() {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_FILTERS + "/shape");
        if (probeIDs.getLength() <= 0) {
            return false;
        }
        return true;
    }

    public void loadFilters(String id, StringBuffer sb) {
        loadProbeFilters(id, sb);
        loadLabelFilters(id, sb);
        loadPathwayFilters(id, sb);
        loadShapeFilters(id, sb);
    }

    protected void loadProbeFilters(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_FILTERS + "/" + XML_KEY_PROBES + "/filter");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                String id = (String) probeF.getAttributes().getNamedItem("value").getTextContent();
                String exc = (String) probeF.getAttributes().getNamedItem("excluded").getTextContent();
                boolean excluded = false;
                if (Boolean.valueOf(exc)) {
                    excluded = true;
                }
                FilterManager.getUniqueInstance().addFilter(sessionID, FilterManager.makeProbeIDFilter(id, excluded));
            }
        }
    }

    protected void loadLabelFilters(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_FILTERS + "/" + XML_KEY_LABELS + "/filter");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                int id = Integer.valueOf(probeF.getAttributes().getNamedItem("id").getTextContent());
                String exc = (String) probeF.getAttributes().getNamedItem("excluded").getTextContent();
                boolean excluded = false;
                if (Boolean.valueOf(exc)) {
                    excluded = true;
                }
                KnowledgeDataSet kDB = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(knowledgeSetID, true);
                Label label = kDB.getLabel(id);
                if (label != null) {
                    FilterManager.getUniqueInstance().filterLabel(sessionID, label, excluded);
                }
            }
        }
    }

    protected void loadPathwayFilters(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_FILTERS + "/" + XML_KEY_PATHWAYS + "/filter");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                int id = Integer.valueOf(probeF.getAttributes().getNamedItem("id").getTextContent());
                String exc = (String) probeF.getAttributes().getNamedItem("excluded").getTextContent();
                boolean excluded = false;
                if (Boolean.valueOf(exc)) {
                    excluded = true;
                }
                KnowledgeDataSet kDB = KnowledgeDataSetFactory.getUniqueInstance().getDataSet(knowledgeSetID, true);
                Pathway pathway = kDB.getPathway(id);
                if (pathway != null) {
                    FilterManager.getUniqueInstance().filterPathway(sessionID, pathway, excluded);
                }
            }
        }
    }

    protected void loadShapeFilters(String sessionID, StringBuffer sb) {
        NodeList shapeFilters = getNodeList(".//" + XML_KEY_FILTERS + "/shape/filter");
        // Should only be one shape filter
        for (int i = 0; i < shapeFilters.getLength(); i++) {
            Node shapeFilter = shapeFilters.item(i);

            TrajectoryFile td = TrajectoryFileFactory.getUniqueInstance().getFile(parentDataSetID, tDName);
            int subtractiveDegreeMin = td.getMinimumSubtractiveDegree();

            int[][] shape = td.getEmptyShape();

            for (int timePeriod = 1; timePeriod <= shape.length; timePeriod++) {
                String timePeriodConditions = shapeFilter.getAttributes().getNamedItem("time_period_" + timePeriod).getTextContent();
                if (timePeriodConditions != null && !timePeriodConditions.equals("")) {
                    String[] conditions = timePeriodConditions.split("\\|");
                    for (int conditionIndex = 0; conditionIndex < conditions.length; conditionIndex++) {
                        String condition = conditions[conditionIndex];
                        if (condition != null && !condition.equals("") && !condition.equals("|")) {
                            int value = Integer.parseInt(condition);
                            shape[timePeriod - 1][value - subtractiveDegreeMin] = 1;
                        }
                    }
                }
            }
            TrajectoryShapeFilter trajFilter = new TrajectoryShapeFilter(shape, td.getBinUnit(), td.getMaximumSubtractiveDegree(), td.getMinimumSubtractiveDegree());
            FilterManager.getUniqueInstance().addFilter(sessionID, trajFilter);
        }
    }

    public void loadHighlights(String id, StringBuffer sb) {
        loadProbeHighlights(id, sb);
        loadLabelHighlights(id, sb);
        loadPathwayHighlights(id, sb);
    }

    protected void loadProbeHighlights(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_HIGHLIGHTS + "/" + XML_KEY_PROBES + "/highlight");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                String id = (String) probeF.getAttributes().getNamedItem("value").getTextContent();
                HighlightManager.getUniqueInstance().highlightProbe(sessionID, id);
            }
        }
    }

    protected void loadLabelHighlights(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_HIGHLIGHTS + "/" + XML_KEY_LABELS + "/highlight");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                String id = (String) probeF.getAttributes().getNamedItem("value").getTextContent();
                HighlightManager.getUniqueInstance().highlightLabel(sessionID, KnowledgeDataSetFactory.getUniqueInstance().getDataSet(knowledgeSetID, true).getLabel(Integer.valueOf(id)));
            }
        }
    }

    protected void loadPathwayHighlights(String sessionID, StringBuffer sb) {
        NodeList probeIDs = getNodeList(".//" + XML_KEY_HIGHLIGHTS + "/" + XML_KEY_PATHWAYS + "/highlight");
        if (probeIDs != null) {
            for (int i = 0; i < probeIDs.getLength(); i++) {
                Node probeF = probeIDs.item(i);
                String id = (String) probeF.getAttributes().getNamedItem("value").getTextContent();
                HighlightManager.getUniqueInstance().highlightPathway(sessionID, KnowledgeDataSetFactory.getUniqueInstance().getDataSet(knowledgeSetID, true).getPathway(Integer.valueOf(id)));
            }
        }
    }

    public ArrayList<Cookie> loadRequiredData(HttpServletRequest request, StringBuffer sb) {
        ArrayList<Cookie> modifications = new ArrayList<Cookie>();

        Cookie eDBCookie = loadDatabase(SessionAttributeManager.getActivePrimaryExpressionDatabase(request), parentDataSetID, sb);
        if (eDBCookie != null) {
            modifications.add(eDBCookie);
        }

        Cookie kDBCookie = loadDatabase(SessionAttributeManager.getActiveKnowledgeLibrary(request), knowledgeSetID, sb);
        if (kDBCookie != null) {
            modifications.add(kDBCookie);
        }

        TrajectoryFile td = SessionAttributeManager.getActiveTrajectoryFile(request);
        if (td == null || trajectoryDocRequired()) {
            if (td == null || (td != null && !tDName.equals(td.getFileName()))) {
                // Need to load this DB
                if (!tDName.equals("")) {
                    Cookie cookie = new YearLongCookie(SessionAttributes.TRAJECTORY_FILE_NAME, tDName);
                    sb.append(MsgTemplates.getSuccess("Loaded Trajectory File \"" + tDName + "\""));
                    modifications.add(cookie);
                } else {
                    sb.append(MsgTemplates.getError("No Trajectory File specified"));
                }
            } else {
                sb.append(MsgTemplates.getSuccess("Correct Trajectory File active \"" + tDName + "\""));
            }
        } else {
            sb.append(MsgTemplates.getError("Trajectory Document not required to load this analysis"));
        }

        return modifications;
    }

    protected Cookie loadDatabase(AbstractDataSet db, int dataSetID, StringBuffer sb) {
        if (dataSetID > 0) {
            String dataType = "data";
            boolean required = false;
            if (db instanceof ExpressionDataSet) {
                dataType = "Expression Data Set";
                required = expressionDBRequired();
            } else if (db instanceof KnowledgeDataSet) {
                dataType = "Knowledge Library";
                required = knowledgeDBRequired();
            }
            if (db == null || required) {
                if (db == null || (db != null && dataSetID != db.getID())) {
                    Cookie cookie = new YearLongCookie(SessionAttributes.PRIMARY_EXPRESSION_DATABASE, String.valueOf(dataSetID));
                    sb.append(MsgTemplates.getSuccess("Loaded " + dataType + " \"" + dataSetID + "\""));
                    return cookie;
                } else {
                    sb.append(MsgTemplates.getSuccess("Correct " + dataType + " active \"" + dataSetID + "\""));
                }
            } else {
                sb.append(MsgTemplates.getError("" + dataType + " not required to load this analysis"));
            }
        } else {
            sb.append(MsgTemplates.getSuccess("NULL data set ID specified"));
        }
        return null;
    }

    protected Document getDocument() {
        DOMParser parser = new DOMParser();
        try {
            String filePath = FileGlobals.getRoot() + ExpressionDataSetMultiton.getUniqueInstance().getDataSet(parentDataSetID, false).getName() + File.separatorChar + fileName;
            if (!filePath.endsWith(FileGlobals.XML_FILE_EXTENSION)) {
                filePath = filePath + FileGlobals.XML_FILE_EXTENSION;
            }
            if (new File(filePath).exists()) {
                parser.parse(filePath);
            } else {
                System.err.println(CapturedAnalysisFileFactory.class.getName() + ": File not found at path = " + filePath);
                return null;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CapturedAnalysisFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(CapturedAnalysisFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CapturedAnalysisFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parser.getDocument();
    }
}
