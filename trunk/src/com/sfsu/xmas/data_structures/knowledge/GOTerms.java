package com.sfsu.xmas.data_structures.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GOTerms extends HashMap<String, GOTerm> {

//    protected KnowledgeDatabase parentDatabase;

    public GOTerms(HashMap<String, GOTerm> goTerms) {
//        this.parentDatabase = parentDatabase;
        if (goTerms != null) {
            putAll(goTerms);
        }
    }

    private Set<String> getIDs() {
        return keySet();
    }

    public ArrayList<Pathway> getPathwaysForProbe(String probeID) {
        ArrayList<Pathway> matchingLabels = new ArrayList<Pathway>();
        Set<Map.Entry<String, GOTerm>> labelESet = entrySet();
        Iterator<Map.Entry<String, GOTerm>> it = labelESet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, GOTerm> entry = it.next();
            GOTerm pway = entry.getValue();
//            if (pway.probeRepresented(probeID)) {
//                matchingLabels.add(pway);
//            }
        }
        return matchingLabels;
    }

//    public void populateMemberProbes() {
//        ResultSet rs = dataAccess.getProbesForPathways(getIDs());
//
//        HashMap<Integer, ArrayList<String>> pathwaysToProbes = new HashMap<Integer, ArrayList<String>>();
//        if (rs != null) {
//            try {
//                while (rs.next()) {
//                    int pathwayID = Integer.valueOf(rs.getString("pathway_id"));
//                    ArrayList existingProbes = new ArrayList();
//                    if (pathwaysToProbes.containsKey(pathwayID)) {
//                        existingProbes = pathwaysToProbes.get(pathwayID);
//                    }
////                    existingProbes.add(rs.getString("probe_id"));
////                    pathwaysToProbes.put(pathwayID, existingProbes);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Pathways.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        for (int i = 0; i < size(); i++) {
//            Pathway pathway = get(i);
//            if (pathwaysToProbes.containsKey(pathway.getID())) {
//                ArrayList<String> probeIDs = pathwaysToProbes.get(pathway.getID());
//                pathway.setMembers(probeIDs.toArray(new String[probeIDs.size()]));
//            }
//        }
//
//    }    //
//    public Pathways() {
//        database = AnnotationDatabaseManager.getActiveDataBase();
//    }

//    public Pathways(ResultSet rs) {
//        AnnotationDatabase adb = AnnotationDatabaseManager.getActiveDataBase();
//        if (rs != null) {
//            HashMap<Integer, Integer> pathTotalCards = adb.getPathwayCardinalities();
//            HashMap<Integer, Integer> pathUnfiltCards = adb.getUnfilteredPathwayCardinalities();
//            try {
//                while (rs.next()) {
//                    String id = rs.getString("pathway_id");
////                    String description = rs.getString("description");
//                    int filtCardinality = Integer.parseInt(rs.getString("filteredCardinality"));
////                    int unfiltCardinality = Integer.parseInt(rs.getString("unfilteredCardinality"));
////                    int totalCardinality = Integer.parseInt(rs.getString("totalCardinality"));
//                    Pathway pway = new Pathway(Integer.parseInt(id));
////                    pway.setDatabase(database);
//                    pway.setFilteredCardinality(filtCardinality);
//                    pway.setUnfilteredCardinality(pathUnfiltCards.get(pway.getID()));
//                    pway.setTotalCardinality(pathTotalCards.get(pway.getID()));
//                    add(pway);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Pathways.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

//    public void populateCardinalities() {
//        AnnotationDatabase adb = AnnotationDatabaseManager.getActiveDataBase();
//        PathwayDataAccess pda = new PathwayDataAccess();
//
//        HashMap<Integer, Integer> pathTotalCards = adb.getPathwayCardinalities();
//        HashMap<Integer, Integer> pathUnFilteredCards = adb.getUnfilteredPathwayCardinalities();
//        HashMap<Integer, Integer> pathFilteredCards = pda.getFilteredCardinalitiesOfPathways(this);
//        for (int i = 0; i < size(); i++) {
//            Pathway pway = get(i);
//            pway.setTotalCardinality(pathTotalCards.get(pway.getID()));
//            pway.setFilteredCardinality(pathFilteredCards.get(pway.getID()));
//            pway.setUnfilteredCardinality(pathUnFilteredCards.get(pway.getID()));
//        }
//    }
//
//    public Pathways(AnnotationDatabase db) {
//        database = db;
//    }

//    public void setPathwayIDs(int[] ids) {
//        for (int j = 0; j < ids.length; j++) {
//            Pathway pathway = new Pathway(ids[j]);
//            add(pathway);
//        }
//    }
//
//    public void setPathwayIDs(Vector ids) {
//        for (int j = 0; j < ids.size(); j++) {
//            Pathway pathway = new Pathway((Integer) ids.get(j));
//            add(pathway);
//        }
//    }
}
