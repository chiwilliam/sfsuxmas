package com.sfsu.xmas.data_structures.knowledge;

import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_structures.Probes;
import java.util.ArrayList;

public class Label extends MultiProbeDataStructure {

    protected int parentDatabaseID;
    protected String[] probeIDs;
    protected String[] intersection;

    public Label(int parentDatabase, int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentDatabaseID = parentDatabase;
        populate();
    }

    public void intersectWithProbes(Probes probes) {
        ArrayList<String> intersectionArray = new ArrayList<String>();
//        if (probes.size() > probeIDs.length) {
        // Iterate over probeIDs
        for (int i = 0; i < probeIDs.length; i++) {
            String memberProbeID = probeIDs[i];
            if (probes.containsKey(memberProbeID)) {
                intersectionArray.add(memberProbeID);
            }
        }
//        } else {
//            probes.
//        }
        intersection = intersectionArray.toArray(new String[intersectionArray.size()]);
    }

    public void populate() {
        KnowledgeDataSetDAO aDA = new KnowledgeDataSetDAO(parentDatabaseID);
        probeIDs = aDA.getProbesForLabel(id);
//        aDA.
//        description = dataAccess.getAnnotationDescription(id);
    }

    public int getNumberOfMembers() {
        return probeIDs.length;
    }

    public int getNumberOfIntersectingMembers() {
        if (intersection != null) {
            return intersection.length;
        }
        return 0;
    }
    
    public void refreshMemberProbes() {
        populate();
    }

    protected void populateMembers() {
//        KnowledgeDatabaseDA aDA = new KnowledgeDatabaseDA(sam.getActiveKnowledgeLibrary().getName());
//        setMembers(dataAccess.getProbesForAnnotation(id));
    }

    public boolean probeRepresented(String probeID) {
        for (int i = 0; i < probeIDs.length; i++) {
            if (probeIDs[i].equals(probeID)) {
                return true;
            }
        }
        return false;
    }
    
    public String[] getMemberProbes() {
        return probeIDs;
    }
    
    
    //    protected void setMembers(String[] ids) {
//        this.probes = ids;
//        totalCardinality = probes.length;
//    }//    public String[] getProbesForAnnotation() {
//        if (memberProbes == null) {
//            memberProbes = annotationDAO.getProbesForAnnotation(id);
//        }
//        return memberProbes;
//    }

//    protected void populateCardinalities() {
//        try {
//            AnnotationDataAccess ada = new AnnotationDataAccess();
//            int totalCard = totalCardinality;
//            ResultSet rs = ada.getTotalCardinalityForAnnotation(id);
//            if (rs != null) {
//                if (rs.next()) {
//                    totalCard = Integer.parseInt(rs.getString("totalCardinality"));
//                }
//            }
//            setTotalCardinality(totalCard);
//        } catch (SQLException ex) {
//            Logger.getLogger(Pathway.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    protected void populateData() {
//        try {
//            ResultSet rsProbesetInfo = ada.getAnnotationData(id);
//            if (rsProbesetInfo != null && rsProbesetInfo.next()) {
//                this.description = rsProbesetInfo.getString("description");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Probe.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        dataPopulated = true;
//    }
}
