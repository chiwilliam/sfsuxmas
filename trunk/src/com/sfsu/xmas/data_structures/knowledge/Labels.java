package com.sfsu.xmas.data_structures.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Labels extends HashMap<Integer, Label> {

//    protected KnowledgeDatabase parentDatabase;

    public Labels(HashMap<Integer, Label> annotations) {
//        this.parentDatabase = parentDatabase;
        if (annotations != null) {
            putAll(annotations);
        }
    }

    private Set<Integer> getIDs() {
        return keySet();
    }

    public ArrayList<Label> getLabelsForProbe(String probeID) {
        ArrayList<Label> matchingLabels = new ArrayList<Label>();
        Set<Map.Entry<Integer, Label>> labelESet = entrySet();
        Iterator<Map.Entry<Integer, Label>> it = labelESet.iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Label> entry = it.next();
            Label lab = entry.getValue();
            if (lab.probeRepresented(probeID)) {
                matchingLabels.add(lab);
            }
        }
        return matchingLabels;
    }
    
    
    //    public void populateMemberProbes() {
//        KnowledgeDatabaseDA pDA = new KnowledgeDatabaseDA(parentDatabase.getName());
//        ResultSet rs = pDA.getProbesForAnnotations(getIDs());
//
//        HashMap<Integer, ArrayList<String>> annotationsToProbes = new HashMap<Integer, ArrayList<String>>();
//        if (rs != null) {
//            try {
//                while (rs.next()) {
//                    int annotationID = Integer.valueOf(rs.getString("user_annotation_id"));
//                    ArrayList existingProbes = new ArrayList();
//                    if (annotationsToProbes.containsKey(annotationID)) {
//                        existingProbes = annotationsToProbes.get(annotationID);
//                    }
////                    existingProbes.add(rs.getString("probe_id"));
////                    annotationsToProbes.put(annotationID, existingProbes);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Pathways.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        for (int i = 0; i < size(); i++) {
//            Annotation annotation = get(i);
//            if (annotationsToProbes.containsKey(annotation.getID())) {
//                ArrayList<String> probeIDs = annotationsToProbes.get(annotation.getID());
//                annotation.setMembers(probeIDs.toArray(new String[probeIDs.size()]));
//            }
//        }
//
//    }
//    public Annotations(ResultSet rs) {
//        if (rs != null) {
//            try {
//                while (rs.next()) {
//                    String id = rs.getString("user_annotation_id");
//                    int filtCardinality = Integer.parseInt(rs.getString("filteredCardinality"));
//                    int unfiltCardinality = Integer.parseInt(rs.getString("unfilteredCardinality"));
//                    int totalCardinality = Integer.parseInt(rs.getString("totalCardinality"));
//                    Annotation anno = new Annotation(Integer.parseInt(id));
////                    anno.setDatabase(database);
//                    anno.setFilteredCardinality(filtCardinality);
//                    anno.setUnfilteredCardinality(unfiltCardinality);
//                    anno.setTotalCardinality(totalCardinality);
//                    add(anno);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Annotations.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

//    public Annotations(String probeID) {
//
//        if (AnnotationDatabaseManager.getActiveDataBase() == null) {
//            return;
//        }
//        try {
//            AnnotationDA annoDAO = new AnnotationDA();
//            ResultSet rsProbesetInfo = null;
//            rsProbesetInfo = annoDAO.getAnnotationsForProbe(probeID);
//            if (rsProbesetInfo != null) {
//                while (rsProbesetInfo.next()) {
//                    String id = rsProbesetInfo.getString("user_annotation_id");
//                    String description = rsProbesetInfo.getString("description");
//                    this.add(new Annotation(Integer.parseInt(id), description));
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Annotations.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public boolean containsAnnotation(int annotationID) {
//        Iterator it = this.iterator();
//        while (it.hasNext()) {
//            Annotation anno = (Annotation) it.next();
//            if (anno.getID() == annotationID) {
//                return true;
//            }
//        }
//        return false;
//    }//    public void setAnnotationIDs(int[] ids) {
//        for (int j = 0; j < ids.length; j++) {
//            Annotation annotation = new Annotation(ids[j]);
//            add(annotation);
//        }
//    }
}
