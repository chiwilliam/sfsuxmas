package com.sfsu.xmas.data_sets;

import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_files.readers.ProbeAttributeFileReader;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.data_structures.knowledge.GOTerms;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Labels;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import com.sfsu.xmas.data_structures.knowledge.Pathways;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypeValues;
import com.sfsu.xmas.data_structures.knowledge.probe_data.ProbeDataTypes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnowledgeDataSet extends AbstractDataSet {

    private ProbeDataTypes probeDataTypes;
    private ProbeDataTypeValues probeDataValues;
    private KnowledgeDataSetDAO dataAccess;
    private Labels labels;
    private Pathways pathways;
    private GOTerms goTerms;
    private int numberOfLabels;
    private int numberOfPathways;
    private int numberOfGOTerms;
    private int numberOfProbeAttributes;

    public KnowledgeDataSet(int id, boolean populateFully) {
        this.id = id;
        dataAccess = new KnowledgeDataSetDAO(this.id);
        populateBasicData();
        if (populateFully) {
            populateFully();
        }
    }

    public int addLabel(String labelName, String labelDescription) {
        int newLabelID = dataAccess.makeLabel(labelName, labelDescription);
//        this.populateBasicData(false);
        return newLabelID;
    }
    
    public boolean unLabelProbe(String probeID, int labelID) {
        boolean execution = dataAccess.unLabelProbe(probeID, labelID);
        Label lab = labels.get(labelID);
        lab.refreshMemberProbes();
        return execution;
    }

    public void addProbeAttributes(ProbeAttributeFileReader fr) {
        dataAccess.addProbeAttributes(fr);
    }
    
    public String getProbeUserNote(String probeID) {
        return dataAccess.getProbeUserNote(probeID);
    }
    
    public void addProbeUserNote(String note, String probeID) {
        dataAccess.addProbeUserNote(note, probeID);
    }
    
    public void removeProbeUserNote(String probeID) {
        dataAccess.removeProbeUserNote(probeID);
    }

    public synchronized void populateBasicData() {
        this.name = dataAccess.getName();
        this.description = dataAccess.getDescription();
        this.creationDate = dataAccess.getCreationDate();
        this.numberOfProbeAttributes = dataAccess.getNumberOfProbeAttributes();
        this.numberOfLabels = dataAccess.getNumberOfLabels();
        this.numberOfPathways = dataAccess.getNumberOfPathways();
        this.numberOfGOTerms = dataAccess.getNumberOfGOTerms();
        isFresh = true;
    }

    public synchronized void populateFully() {
        if (probeDataTypes == null) {
            this.probeDataTypes = dataAccess.getProbeAttributes();
        }
        populateProbeDataTypeValues();
        if (labels == null) {
            labels = dataAccess.getLabels();
        }
        if (pathways == null) {
            pathways = dataAccess.getPathways();
        }
        if (goTerms == null) {
            goTerms = dataAccess.getGOTerms();
        }
    }

    private void populateProbeDataTypeValues() {
        if (probeDataValues == null) {
            probeDataValues = new ProbeDataTypeValues();
            if (probeDataTypes != null) {
                ResultSet rs = dataAccess.getProbeDataValues(this);
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            String probeID = rs.getString("probe_id");
                            int attributeID = rs.getInt("k_attribute_id");
                            String value = rs.getString("value");

                            HashMap<Integer, String> attributeToValuePairs = new HashMap<Integer, String>();
                            if (probeDataValues.containsKey(probeID)) {
                                attributeToValuePairs = probeDataValues.get(probeID);
                            }
                            attributeToValuePairs.put(attributeID, value);
                            probeDataValues.put(probeID, attributeToValuePairs);
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(KnowledgeDataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

//    public Pathways getPathwaysForProbes(Probes probes) {
//        return knowledgeDatabaseDA.getPathwaysForProbes(probes);
//    }
    public Labels getLabels() {
//        if (labels == null) {
//            labels = knowledgeDatabaseDA.getLabels(this);
//        }
        return labels;
    }

    public Labels getLabels(int[] ids) {
        //Subset
        HashMap<Integer, Label> subset = new HashMap<Integer, Label>();
        for (int i = 0; i < ids.length; i++) {
            subset.put(ids[i], labels.get(ids[i]));
        }
        Labels labs = new Labels(subset);
        return labs;
    }

    public Pathways getPathways() {
//        if (labels == null) {
//            labels = knowledgeDatabaseDA.getLabels(this);
//        }
        return pathways;
    }

    public GOTerms getGOTerms() {
        return goTerms;
    }

    public Pathways getPathways(int[] ids) {
        //Subset
        HashMap<Integer, Pathway> subset = new HashMap<Integer, Pathway>();
        for (int i = 0; i < ids.length; i++) {
            subset.put(ids[i], pathways.get(ids[i]));
        }
        Pathways labs = new Pathways(subset);
        return labs;
    }

    public GOTerms getGOTerms(String[] ids) {
        //Subset
        HashMap<String, GOTerm> subset = new HashMap<String, GOTerm>();
        for (int i = 0; i < ids.length; i++) {
            subset.put(ids[i], goTerms.get(ids[i]));
        }
        GOTerms labs = new GOTerms(subset);
        return labs;
    }

    public Pathways getPathwaysForProbes(Probes probes) {
        Pathways matchingPathways = new Pathways(null);

        Set<Map.Entry<Integer, Pathway>> pathESet = pathways.entrySet();
        Iterator<Map.Entry<Integer, Pathway>> it = pathESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Pathway> entry = it.next();
            Pathway pathway = entry.getValue();
            pathway.intersectWithProbes(probes);
            if (pathway.getNumberOfIntersectingMembers() > 0) {
                matchingPathways.put(pathway.getID(), pathway);
            }
        }
        return matchingPathways;
    }

    public GOTerms getGOTermsForProbes(Probes probes) {
        GOTerms matchingGOTerms = new GOTerms(null);

        Set<Map.Entry<String, GOTerm>> pathESet = goTerms.entrySet();
        Iterator<Map.Entry<String, GOTerm>> it = pathESet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, GOTerm> entry = it.next();
            GOTerm goTerm = entry.getValue();
            goTerm.intersectWithProbes(probes);
            if (goTerm.getNumberOfIntersectingMembers() > 0) {
                matchingGOTerms.put(goTerm.getStringID(), goTerm);
            }
        }
        return matchingGOTerms;
    }

    public Pathway getPathway(int id) {
        return pathways.get(id);
    }

    public GOTerm getGOTerm(String id) {
        return goTerms.get(id);
    }

    public Label getLabel(int id) {
        return labels.get(id);
    }

    public Labels getLabelsForProbes(Probes probes) {
        Labels matchingLabels = new Labels(null);

        Set<Map.Entry<Integer, Label>> labelESet = labels.entrySet();
        Iterator<Map.Entry<Integer, Label>> it = labelESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Label> entry = it.next();
            Label lab = entry.getValue();
            lab.intersectWithProbes(probes);
            if (lab.getNumberOfIntersectingMembers() > 0) {
                matchingLabels.put(lab.getID(), lab);
            }
        }
        return matchingLabels;
    }

    public ProbeDataTypes getProbeDataTypes() {
        return probeDataTypes;
    }

    public synchronized ProbeDataTypeValues getDataForProbes() {
        if (probeDataValues == null) {
            populateProbeDataTypeValues();
        }
        return probeDataValues;
    }

    public int getNumberOfLabels() {
        return numberOfLabels;
    }

    public int getNumberOfPathways() {
        return numberOfPathways;
    }

    public int getNumberOfGOTerms() {
        return numberOfGOTerms;
    }

    public int getNumberOfProbeAttributes() {
        return numberOfProbeAttributes;
    }

    public int getNumberOfProbesWithData() {
        int numberOfProbes = 0;
        if (probeDataValues != null) {
            numberOfProbes = probeDataValues.size();
        } else {
            numberOfProbes = dataAccess.getNumberOfProbesWithAttributes();
        }
        return numberOfProbes;
    }

    public void addProbeAttributeLink(String dataType, String link) {
        dataAccess.assignProbeDataTypeLink(probeDataTypes.get(Integer.parseInt(dataType)), link);
    }    //
//    public void deleteLink(String attribute) {
//        dda.deleteLink(attribute);
//    }
//
//    public HashMap<String, Boolean> getAttributeUsage() {
//        if (AnnotationDatabaseManager.attributeUsage == null) {
//            initializeAttributeUsage();
//        }
//        return AnnotationDatabaseManager.attributeUsage;
//    }
//
//    public boolean useAttribute(String attribute) {
//        if (AnnotationDatabaseManager.attributeUsage == null) {
//            initializeAttributeUsage();
//        }
//        if (AnnotationDatabaseManager.attributeUsage.containsKey(attribute)) {
//            AnnotationDatabaseManager.attributeUsage.put(attribute, true);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean stopUsingAttribute(String attribute) {
//        if (AnnotationDatabaseManager.attributeUsage == null) {
//            initializeAttributeUsage();
//        }
//        if (AnnotationDatabaseManager.attributeUsage.containsKey(attribute)) {
//            AnnotationDatabaseManager.attributeUsage.put(attribute, false);
//            return true;
//        }
//        return false;
//    }
//
//    private void initializeAttributeUsage() {
//        // If should 
//        HashMap<String, Boolean> attributesUsed = new HashMap<String, Boolean>();
//        if (getNumberOfProbes() > 0) {
//            // Probe attributes exist in this database
//            IntegratedProbeAnnotations probeAts = getProbes();
//            ProbeAttributeHeaderList headers = probeAts.getProbeAttributes();
//            for (int i = 0; i < headers.size(); i++) {
//                attributesUsed.put(headers.get(i).getAttribute(), true);
//            }
//        }
//        AnnotationDatabaseManager.attributeUsage = attributesUsed;
//    }
//
//    public HashMap<Integer, Integer> getPathwayCardinalities() {
//        if (pathwayCardinalities == null) {
//            this.pathwayCardinalities = dda.getPathwayCards();
//        }
//        return pathwayCardinalities;
//    }
//
//    public int getMaxPathwayCardinality() {
//        if (maxPathwayCardinality <= 0) {
//            this.maxPathwayCardinality = dda.getMaxPathwayCardinality();
//        }
//        return maxPathwayCardinality;
//    }
//
//    public HashMap<Integer, Integer> getUnfilteredPathwayCardinalities() {
//        if (unfiltPathwayCardinalities == null) {
//            this.unfiltPathwayCardinalities = dda.getUnfilteredPathwayCards();
//        }
//        return unfiltPathwayCardinalities;
//    }
//
//
//    public HashMap<String, String> getAttributeLinks() {
//        return dda.getAttributeLinks();
//    }
//
//    public int getNumberOfPathways() {
//        if (numberOfPathways > 0) {
//            // Already accessed, return
//        } else {
//            // Needs setting
//            this.numberOfPathways = dda.getNumberOfPathways();
//        }
//        return numberOfPathways;
//    }
//
//    public int getNumberOfUserAnnotations() {
//        if (numberOfUserAnnotations > 0) {
//            // Already accessed, return
//        } else {
//            // Needs setting
//            this.numberOfUserAnnotations = dda.getNumberOfUserAnnotations();
//        }
//        return numberOfUserAnnotations;
//    }
//
//    public Pathways getPathways() {
////        Pathways pathways = new Pathways(this);
//        try {
//            ResultSet pways = dda.getPathways();
//            if (pways != null) {
//                Vector<Integer> pathwayIDs = new Vector<Integer>();
//                while (pways.next()) {
//                    pathwayIDs.add(Integer.valueOf(pways.getString("pathway_id")));
//                }
//                pways.close();
////                pathways = new Pathways(this);
////                pathways.setPathwayIDs(pathwayIDs);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null; //pathways;
//    }
//
//    public IntegratedProbeAnnotations getProbes() {
//        IntegratedProbeAnnotations integratedProbes = new IntegratedProbeAnnotations();
//        try {
//            ResultSet probes = dda.getProbeAnnotations();
//            if (probes != null) {
//                String[] ids = new String[dda.getNumberOfProbes()];
//                int index = 0;
//                while (probes.next()) {
//                    ids[index] = probes.getString("probe_id");
//                    index++;
//
//                }
//                probes.close();
//                integratedProbes =
//                        new IntegratedProbeAnnotations(this);
//                integratedProbes.setIDs(ids);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return integratedProbes;
//    }
//
//    public IntegratedUserAnnotations getUserAnnotations() {
//        return dda.getUserAnnotations();
//    }
}
