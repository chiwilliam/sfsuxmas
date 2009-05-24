package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_files.readers.GOFileReader;
import com.sfsu.xmas.data_files.readers.PathwayFileReader;
import com.sfsu.xmas.data_files.readers.ProbeAttributeFileReader;
import com.sfsu.xmas.data_sets.*;
import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.data_structures.knowledge.GOTerms;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Labels;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import com.sfsu.xmas.data_structures.knowledge.Pathways;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataType;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypes;
import com.sfsu.xmas.globals.FileGlobals;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnowledgeDataSetDAO extends BaseDataSetDAO {

    private int maxSQLLength = 25000;

    public KnowledgeDataSetDAO(int dataSetID) {
        super(dataSetID);
        dataSetTable = "k_data_sets";
        dataSetIDKey = "k_set_id";
    }

    public int getNumberOfLabels() {
        String sql = "SELECT COUNT(*) AS NumberOfLabels FROM " + FileGlobals.EXPRESSION_DATABASE + ".k_annotations WHERE k_set_id = " + knowledgeDataSetKey + ";";
        return getInt(getResultSet(sql), "NumberOfLabels");
    }

    public int getNumberOfProbeAttributes() {
        String sql = "SELECT COUNT(*) AS NumberOfProbeAttributes FROM " + FileGlobals.EXPRESSION_DATABASE + ".k_probe_attributes WHERE k_set_id = " + knowledgeDataSetKey + ";";
        return getInt(getResultSet(sql), "NumberOfProbeAttributes");
    }

    public int getNumberOfProbesWithAttributes() {
        return getInt(getResultSet("SELECT COUNT(DISTINCT probe_id) as number_of_probes FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attribute_values WHERE k_set_id = " + knowledgeDataSetKey + ";"), "number_of_probes");
    }

    public ResultSet getProbeDataValues(KnowledgeDataSet knowledgeDatabase) {
        return getResultSet("SELECT * FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attribute_values WHERE k_set_id = " + knowledgeDataSetKey + ";");
    }

    public int getNumberOfPathways() {
        String sql = "SELECT COUNT(*) AS NumberOfPathways FROM " + FileGlobals.EXPRESSION_DATABASE + ".k_pathways WHERE k_set_id = " + knowledgeDataSetKey + ";";
        return getInt(getResultSet(sql), "NumberOfPathways");
    }

    public int getNumberOfGOTerms() {
        String sql = "SELECT COUNT(*) AS NumberOfGOTerms FROM " + FileGlobals.EXPRESSION_DATABASE + ".k_go_terms WHERE k_set_id = " + knowledgeDataSetKey + ";";
        return getInt(getResultSet(sql), "NumberOfGOTerms");
    }

    public String[] getProbesForLabel(int id) {
        String GET_PROBES_FOR_LABELS = "SELECT k_annotation_id, probe_id FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotation_assignment WHERE k_annotation_id %_IDS_% AND k_set_id = " + knowledgeDataSetKey + " ORDER BY k_annotation_id;";
        return getStringArray(getResultSet(GET_PROBES_FOR_LABELS.replace("%_IDS_%", getEq(String.valueOf(id)))), "probe_id");
    }

    public String[] getProbesForPathway(int id) {
        String GET_PROBES_FOR_PATHWAYS = "SELECT k_pathway_id, probe_id FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_pathway_assignment WHERE k_pathway_id %_IDS_% ORDER BY k_pathway_id;";
        return getStringArray(getResultSet(GET_PROBES_FOR_PATHWAYS.replace("%_IDS_%", getEq(String.valueOf(id)))), "probe_id");
    }

    public String[] getProbesForGOTerm(String id) {
        String GET_PROBES_FOR_GO_TERM = "SELECT k_go_term_id, probe_id FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_go_term_assignment WHERE k_go_term_id %_IDS_% ORDER BY k_go_term_id;";
        return getStringArray(getResultSet(GET_PROBES_FOR_GO_TERM.replace("%_IDS_%", getEq(id))), "probe_id");
    }

    public Labels getLabels() {
        String sql = "SELECT k_annotation_id, name, description FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotations WHERE k_set_id = " + knowledgeDataSetKey + ";";
        ResultSet rs = getResultSet(sql);
        HashMap<Integer, Label> labels = new HashMap<Integer, Label>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("k_annotation_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    labels.put(id, new Label(dataSetID, id, name, description));
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Labels(labels);
    }

    private ResultSet getLabelIDFromName(String name) {
        String ANNOTATION_ID_FROM_NAME = "SELECT k_annotation_id FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotations WHERE name = \"%_LABEL_NAME_%\";";
        return getResultSet(ANNOTATION_ID_FROM_NAME.replace("%_LABEL_NAME_%", name));
    }

    public Pathways getPathways() {
        String sql = "SELECT k_pathway_id, name FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_pathways WHERE k_set_id = " + knowledgeDataSetKey + ";";
        ResultSet rs = getResultSet(sql);
        HashMap<Integer, Pathway> pathways = new HashMap<Integer, Pathway>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("k_pathway_id");
                    String name = rs.getString("name");
                    pathways.put(id, new Pathway(dataSetID, id, name, true));
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Pathways(pathways);
    }

    public GOTerms getGOTerms() {
        String sql = "SELECT k_go_term_id, name, description FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_go_terms WHERE k_set_id = " + knowledgeDataSetKey + ";";
        ResultSet rs = getResultSet(sql);
        HashMap<String, GOTerm> goTerms = new HashMap<String, GOTerm>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    String id = rs.getString("k_go_term_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    goTerms.put(id, new GOTerm(dataSetID, id, name, description, true));
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new GOTerms(goTerms);
    }

    public ProbeDataTypes getProbeAttributes() {
        String sql = "SELECT * FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attributes WHERE k_set_id = " + knowledgeDataSetKey + "; ";
        ProbeDataTypes pdt = new ProbeDataTypes();
        ResultSet rs = getResultSet(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("k_attribute_id");
                    String name = rs.getString("name");
                    String link = rs.getString("link");
                    pdt.put(id, new ProbeDataType(id, name, link));
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pdt;
    }

    public void assignProbeDataTypeLink(ProbeDataType pDT, String link) {
        executeSQL("REPLACE INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attributes " +
                "(k_attribute_id, k_set_id, name, link) " +
                "VALUES (" + pDT.getID() + ", " + knowledgeDataSetKey + ", \"" + pDT.getAttribute() + "\", \"" + link + "\");");
    }

    public int insertAnnotation(String name) {
        String INSERT_LABEL = "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotations ( k_set_id, name, description ) VALUES (" + knowledgeDataSetKey + ", \"%_ANNOTATION_DESCRIPTION_%\", NULL);";
        String sql = INSERT_LABEL.replace("%_ANNOTATION_DESCRIPTION_%", name);
        return executeSQLGetInsertID(sql);
    }

    public int getLabelID(String name) {
        ResultSet rs = getLabelIDFromName(name);
        try {
            if (rs.next()) {
                return rs.getInt("user_annotation_id");
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int makeLabel(String labelName, String labelDescription) {
        String MAKE_LABEL = "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotations (k_set_id, name, description) VALUES (%_VALUES_%);";
        StringBuffer values = new StringBuffer();
        values.append(dataSetID);
        values.append(", ");
        values.append("\"");
        values.append(labelName);
        values.append("\", \"");
        values.append(labelDescription);
        values.append("\"");
        String sql = MAKE_LABEL.replace("%_VALUES_%", values.toString());
        return executeSQLGetInsertID(sql);
    }

    public void removeLabel(int id) {
        String REMOVE_LABEL = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".user_annotations WHERE user_annotation_id = \"%_ID_%\";";
        String sql = REMOVE_LABEL;
        sql = sql.replace("%_ID_%", "" + id);
        executeSQL(sql);
    }

    public void assignLabelToProbes(String annotationID, String[] probeIDs) {
        String ASSIGN_LABEL = "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotation_assignment (probe_id, k_set_id, k_annotation_id) VALUES (%VALUES%);";
        for (int i = 0; i < probeIDs.length; i++) {
            String sql = ASSIGN_LABEL;
            StringBuffer sb = new StringBuffer();
            sb.append("\"");
            sb.append(probeIDs[i]);
            sb.append("\", ");
            sb.append(knowledgeDataSetKey);
            sb.append(", ");
            sb.append(annotationID);
            sql = sql.replace("%VALUES%", sb.toString());
            executeSQL(sql);
        }
    }

    public boolean unLabelProbe(String probeID, int labelID) {
        String UN_LABEL_PROBE = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_annotation_assignment WHERE probe_id = \"" + probeID + "\" AND k_set_id = \"" + knowledgeDataSetKey + "\" AND k_annotation_id = \"" + labelID + "\";";
        return executeSQL(UN_LABEL_PROBE);
    }

    public void deAssignProbesFromLabel(String annotationID, String[] probeIDs) {
        if (annotationID.equals("X")) {
            String UNLABEL_PROBE = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".user_annotations_assignment WHERE probe_id = \"%_PROBE_ID_%\";";
            for (int i = 0; i < probeIDs.length; i++) {
                String sql = UNLABEL_PROBE;
                sql = sql.replace("%_PROBE_ID_%", probeIDs[i]);
                executeSQL(sql);
            }
        } else {
            for (int i = 0; i < probeIDs.length; i++) {
                String sql = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".user_annotations_assignment WHERE user_annotation_id = %_ANNO_ID_%;";
                StringBuffer sb = new StringBuffer();
                sb.append("(" + annotationID + ", \"" + probeIDs[i] + "\")");
                sql = sql.replace("%VALUES%", sb.toString());
                executeSQL(sql);
            }
        }
    }

    public void deAssignLabel(String annotationID, String probeID) {
        String sql = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".user_annotations_assignment WHERE user_annotation_id = %_ANNO_ID_% AND probe_id = \"%_PROBE_ID_%\";";
        sql = sql.replace("%_ANNO_ID_%", annotationID);
        sql = sql.replace("%_PROBE_ID_%", probeID);
        executeSQL(sql);
    }

    public void emptyLabel(String annotationID) {
        String sql = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".user_annotations_assignment WHERE user_annotation_id = %_ANNO_ID_%;";
        sql = sql.replace("%_ANNO_ID_%", annotationID);
        executeSQL(sql);
    }

    public void addProbeUserNote(String note, String probeID) {
        String sql = "REPLACE INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_user_probe_notes (probe_id, k_set_id, k_user_id, note) VALUES (\"%_PROBE_ID_%\", \"" + knowledgeDataSetKey + "\", NULL, \"%_NOTE_%\");";
        sql = sql.replace("%_NOTE_%", note);
        sql = sql.replace("%_PROBE_ID_%", probeID);
        executeSQL(sql);
    }

    public void removeProbeUserNote(String probeID) {
        String sql = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_user_probe_notes WHERE probe_id = \"" + probeID + "\" AND k_set_id = \"" + knowledgeDataSetKey + "\" LIMIT 1;";
        executeSQL(sql);
    }

    public String getProbeUserNote(String probeID) {
        String sql = "SELECT note FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_user_probe_notes WHERE probe_id = \"%_PROBE_ID_%\" AND k_set_id = \"" + knowledgeDataSetKey + "\";";
        sql = sql.replace("%_PROBE_ID_%", probeID);
        String note = getString(getResultSet(sql), "note");
        if (note.equals(BaseMySQLDAO.NULL_VALUE)) {
            return null;
        }
        return note;
    }

    public void addProbeAttributes(ProbeAttributeFileReader fr) {
        String INSERT_PROBE_ATTRIBUTES = "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attributes (k_set_id, name) VALUES (%_VALUES_%);";
        String[] headers = fr.getColumnHeaders();
        int[] attributeIDs = new int[headers.length];
        for (int i = 1; i < headers.length; i++) {
            String header = headers[i];
            StringBuffer sb = new StringBuffer();
            sb.append(String.valueOf(dataSetID));
            sb.append(", \"");
            sb.append(header);
            sb.append("\"");
            String sql = INSERT_PROBE_ATTRIBUTES.replace("%_VALUES_%", sb.toString());
            int attributeID = executeSQLGetInsertID(sql);
            attributeIDs[i] = attributeID;
        }
        HashMap<String, String[]> data = fr.getFileData();
        Set<String> probeIDs = data.keySet();
        Iterator<String> probeIDIt = probeIDs.iterator();
        populateProbeAttributesTable(data, probeIDIt, attributeIDs);
    }
    private static final int maxQuery = 1000;

    public void populateProbeAttributesTable(HashMap<String, String[]> data, Iterator<String> probeIDIt, int[] attributeIDs) {
        String INSERT_PROBE_ATTRIBUTE_VALUES = "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_probe_attribute_values (probe_id, k_set_id, k_attribute_id, value) VALUES %_VALUES_%;";
        int dataRowIndex = 0;
        StringBuffer probeData = new StringBuffer();
        while (probeIDIt.hasNext() && dataRowIndex < maxQuery) {
            String probeID = probeIDIt.next();
            if (!probeID.equals("")) {
                String[] attributes = data.get(probeID);
                for (int i = 1; i < attributes.length; i++) {
                    StringBuffer attributeType = new StringBuffer();
                    attributeType.append("(");
                    attributeType.append("\"");
                    attributeType.append(probeID);
                    attributeType.append("\", ");
                    attributeType.append(dataSetID);
                    attributeType.append(", ");
                    attributeType.append(attributeIDs[i]);
                    attributeType.append(", ");

                    attributeType.append("\"");
                    String value = attributes[i];
                    // Better safe than sorry
                    if (value != null) {
                        value = value.substring(0, Math.min(value.length(), 240));
                    } else {
                        value = "null";
                    }
                    attributeType.append(value.replace("\"", ""));
                    attributeType.append("\"");
                    attributeType.append(")");

                    if (probeData.length() > 0) {
                        probeData.append(", ");
                    }
                    probeData.append(attributeType);
                }
                dataRowIndex++;
            }
        }
        // Execute the code we have:
        executeSQL(INSERT_PROBE_ATTRIBUTE_VALUES.replace("%_VALUES_%", probeData.toString()));
        if (probeIDIt.hasNext()) {
            // Continue with the next row
            populateProbeAttributesTable(data, probeIDIt, attributeIDs);
        }
    }

    public boolean populatePathwaysTable(PathwayFileReader fr) {
        String INSERT_PATHWAYS =
                "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_pathways (k_set_id, name) " +
                "VALUES %_VALUES_%;";
        StringBuffer pathwayEntries = new StringBuffer();
        Vector<String> pathways = fr.getPathways();
        for (int i = 0; i < pathways.size(); i++) {
            String pathway = pathways.elementAt(i);
            if (pathwayEntries.length() > 0) {
                pathwayEntries.append(",");
            }
            pathwayEntries.append("(");
            pathwayEntries.append(knowledgeDataSetKey);
            pathwayEntries.append(",\"");
            pathwayEntries.append(pathway);
            pathwayEntries.append("\")");
        }
        if (pathwayEntries.length() > 0) {
            String sql = INSERT_PATHWAYS;
            sql = sql.replace("%_VALUES_%", pathwayEntries);
            return executeSQL(sql);
        }
        return false;
    }

    public boolean populatePathwayAssignmentTable(PathwayFileReader fr) {
        String INSERT_PATHWAY_ASSIGNMENTS =
                "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_pathway_assignment (probe_id, k_set_id, k_pathway_id) " +
                "VALUES %_VALUES_%;";
        HashMap<String, ArrayList<Integer>> pathways = fr.getProbePathwayMembership();
        Set<Entry<String, ArrayList<Integer>>> goTermEntrySet = pathways.entrySet();
        Iterator<Entry<String, ArrayList<Integer>>> it = goTermEntrySet.iterator();

        StringBuffer goTermAssignmentInsertionSQL = new StringBuffer();
        while (it.hasNext()) {
            Entry<String, ArrayList<Integer>> entry = it.next();

            String probeID = entry.getKey();

            ArrayList<Integer> pathwayIDs = entry.getValue();
            Iterator<Integer> pathwayIDsIT = pathwayIDs.iterator();

            while (pathwayIDsIT.hasNext()) {
                int pathwayID = pathwayIDsIT.next();

                if (goTermAssignmentInsertionSQL.length() > 0) {
                    goTermAssignmentInsertionSQL.append(",");
                }

                goTermAssignmentInsertionSQL.append("(\"");
                goTermAssignmentInsertionSQL.append(probeID);
                goTermAssignmentInsertionSQL.append("\", ");
                goTermAssignmentInsertionSQL.append(knowledgeDataSetKey);
                goTermAssignmentInsertionSQL.append(", ");
                goTermAssignmentInsertionSQL.append(pathwayID);
                goTermAssignmentInsertionSQL.append(")");
            }
            if (goTermAssignmentInsertionSQL.length() > maxSQLLength) {
                String sql = INSERT_PATHWAY_ASSIGNMENTS;
                sql = sql.replace("%_VALUES_%", goTermAssignmentInsertionSQL);
                executeSQL(sql);
                goTermAssignmentInsertionSQL = new StringBuffer();
            }
        }
        if (goTermAssignmentInsertionSQL.length() > 0) {
            String sql = INSERT_PATHWAY_ASSIGNMENTS;
            sql = sql.replace("%_VALUES_%", goTermAssignmentInsertionSQL);
            executeSQL(sql);
        }
        return false;
    }

    public boolean populateGoTermsTable(GOFileReader fr) {
        String INSERT_GO_TERMS =
                "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_go_terms (k_go_term_id, k_set_id, name, description) " +
                "VALUES %_VALUES_%;";
        StringBuffer goTermInsertionSQL = new StringBuffer();
        HashMap<String, String[]> pathways = fr.getGOTerms();

        Set<Entry<String, String[]>> goTermEntrySet = pathways.entrySet();

        Iterator<Entry<String, String[]>> it = goTermEntrySet.iterator();

        while (it.hasNext()) {
            Entry<String, String[]> entry = it.next();

            if (goTermInsertionSQL.length() > 0) {
                goTermInsertionSQL.append(",");
            }
            goTermInsertionSQL.append("(\"");
            goTermInsertionSQL.append(entry.getKey());
            goTermInsertionSQL.append("\", ");
            goTermInsertionSQL.append(knowledgeDataSetKey);
            goTermInsertionSQL.append(", \"");
            goTermInsertionSQL.append(entry.getValue()[1]);
            goTermInsertionSQL.append("\", \"");
            goTermInsertionSQL.append(entry.getValue()[2]);
            goTermInsertionSQL.append("\"");
            goTermInsertionSQL.append(")");
        }
        if (goTermInsertionSQL.length() > 0) {
            String sql = INSERT_GO_TERMS;
            sql = sql.replace("%_VALUES_%", goTermInsertionSQL);
            return executeSQL(sql);
        }
        return false;
    }

    public boolean populateGoTermAssignmentTable(GOFileReader fr) {
        String INSERT_GO_TERMS =
                "INSERT INTO " + FileGlobals.KNOWLEDGE_DATABASE + ".k_go_term_assignment (probe_id, k_set_id, k_go_term_id) " +
                "VALUES %_VALUES_%;";
        HashMap<String, ArrayList<String>> pathways = fr.getProbeToGOTerms();
        Set<Entry<String, ArrayList<String>>> goTermEntrySet = pathways.entrySet();
        Iterator<Entry<String, ArrayList<String>>> it = goTermEntrySet.iterator();

        StringBuffer goTermAssignmentInsertionSQL = new StringBuffer();
        while (it.hasNext()) {
            Entry<String, ArrayList<String>> entry = it.next();

            String probeID = entry.getKey();

            ArrayList<String> goTermIDs = entry.getValue();
            Iterator<String> goTermIDIT = goTermIDs.iterator();

            while (goTermIDIT.hasNext()) {
                String goTermID = goTermIDIT.next();

                if (goTermAssignmentInsertionSQL.length() > 0) {
                    goTermAssignmentInsertionSQL.append(",");
                }

                goTermAssignmentInsertionSQL.append("(\"");
                goTermAssignmentInsertionSQL.append(probeID);
                goTermAssignmentInsertionSQL.append("\", ");
                goTermAssignmentInsertionSQL.append(knowledgeDataSetKey);
                goTermAssignmentInsertionSQL.append(", \"");
                goTermAssignmentInsertionSQL.append(goTermID);
                goTermAssignmentInsertionSQL.append("\"");
                goTermAssignmentInsertionSQL.append(")");
            }
            if (goTermAssignmentInsertionSQL.length() > maxSQLLength) {
                String sql = INSERT_GO_TERMS;
                sql = sql.replace("%_VALUES_%", goTermAssignmentInsertionSQL);
                executeSQL(sql);
                goTermAssignmentInsertionSQL = new StringBuffer();
            }
        }
        if (goTermAssignmentInsertionSQL.length() > 0) {
            String sql = INSERT_GO_TERMS;
            sql = sql.replace("%_VALUES_%", goTermAssignmentInsertionSQL);
            executeSQL(sql);
        }
        return false;
    }

    @Override
    protected String useActiveDB(String sql) {
        if (sql.contains(knowledgeDataSetKey)) {
            sql = sql.replace(knowledgeDataSetKey, String.valueOf(dataSetID));
        }
        return super.useActiveDB(sql);
    }
}
