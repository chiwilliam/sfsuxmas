package com.sfsu.xmas.highlight;

import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import java.util.ArrayList;
import java.util.HashMap;

public class HighlightManager extends HashMap<String, HighlightList> {

    private static HighlightManager uniqueInstance;

    public static HighlightManager getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new HighlightManager();
        }
        return uniqueInstance;
    }

    private HighlightManager() {
    }

    public synchronized HighlightList getHighlightListForIdentifier(String identifier) {
        if (containsKey(identifier)) {
            return get(identifier);
        }
        return new HighlightList();
    }

    public void highlightProbes(String identifier, String[] ids) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        for (int i = 0; i < ids.length; i++) {
            hl.highlightProbe(ids[i]);
        }
        put(identifier, hl);
    }

    public void highlightProbe(String identifier, String id) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightProbe(id);
        put(identifier, hl);
    }

    public void stopHighlightingProbe(String identifier, String id) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.stopHighlightingProbe(id);
        put(identifier, hl);
    }


    public void highlightPathways(String identifier, int[] ids) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightPathways(ids);
        put(identifier, hl);
    }

    public void highlightPathway(String identifier, Pathway pathway) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightPathway(pathway);
        put(identifier, hl);
    }

    public void highlightGOTerm(String identifier, GOTerm goTerm) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightGOTerm(goTerm);
        put(identifier, hl);
    }

    public void stopHighlightingPathway(String identifier, int id) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.stopHighlightingPathway(id);
        put(identifier, hl);
    }

    public void stopHighlightingGOTerm(String identifier, String id) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.stopHighlightingGOTerm(id);
        put(identifier, hl);
    }

    public void highlightLabels(String identifier, int[] ids) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightLabels(ids);
        put(identifier, hl);
    }

    public void highlightLabel(String identifier, Label label) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.highlightLabel(label);
        put(identifier, hl);
    }

    public void stopHighlightingAnnotation(String identifier, int id) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.stopHighlightingLabel(id);
        put(identifier, hl);
    }

    public ArrayList<String> getHighlightedProbes(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        return hl.getHighlightedProbes();
    }

    public ArrayList<Integer> getHighlightedPathways(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        return hl.getHighlightedPathways();
    }

    public ArrayList<String> getHighlightedGOTerms(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        return hl.getHighlightedGOTerms();
    }

    public ArrayList<Integer> getHighlightedAnnotations(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        return hl.getHighlightedLabels();
    }

    public ArrayList<String> getHighlighted(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        return hl.getHighlighted();
    }

    public void stopHighlighting(String identifier) {
        HighlightList hl = getHighlightListForIdentifier(identifier);
        hl.stopHighlighting();
        put(identifier, hl);
    }
}
