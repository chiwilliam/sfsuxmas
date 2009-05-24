package com.sfsu.xmas.data_structures.knowledge;

import com.sfsu.xmas.dao.KnowledgeDataSetDAO;
import com.sfsu.xmas.data_structures.Probes;
import java.util.ArrayList;

public class GOTerm extends MultiProbeDataStructure {

    protected String id; // Purposeful hiding
    protected int parentDatabaseID;
    protected String[] probeIDs;
    protected String[] intersection;


    public String getStringID() {
        return id;
    }
    
    public GOTerm(int parentDatabase, String id, String name, String description, boolean populateFully) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentDatabaseID = parentDatabase;
        if (populateFully) {
            populate();
        }
    }

    public void intersectWithProbes(Probes probes) {
        ArrayList<String> intersectionArray = new ArrayList<String>();
        for (int i = 0; i < probeIDs.length; i++) {
            String memberProbeID = probeIDs[i];
            if (probes.containsKey(memberProbeID)) {
                intersectionArray.add(memberProbeID);
            }
        }
        intersection = intersectionArray.toArray(new String[intersectionArray.size()]);
    }

    public void populate() {
        KnowledgeDataSetDAO aDA = new KnowledgeDataSetDAO(parentDatabaseID);
        probeIDs = aDA.getProbesForGOTerm(id);
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
}
