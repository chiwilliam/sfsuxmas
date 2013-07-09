package com.sfsu.xmas.highlight;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class HighlightList {

    private ArrayList<String> probeIDs = new ArrayList<String>();
    private ArrayList<Integer> pathwayIDs = new ArrayList<Integer>();
    private ArrayList<String> goTermIDs = new ArrayList<String>();
    private HashMap<Integer, Pathway> pathways = new HashMap<Integer, Pathway>();
    private HashMap<String, GOTerm> goTerms = new HashMap<String, GOTerm>();
    private ArrayList<Integer> labelIDs = new ArrayList<Integer>();
    private HashMap<Integer, Label> labels = new HashMap<Integer, Label>();

    public void highlightProbes(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            highlightProbe(ids[i]);
        }
    }

    public void highlightProbe(String id) {
        if (!probeIDs.contains(id)) {
            probeIDs.add(id);
        }
    }

    public void stopHighlightingProbe(String id) {
        if (probeIDs.contains(id)) {
            for (int i = 0; i < probeIDs.size(); i++) {
                if (probeIDs.get(i).equals(id)) {
                    // We know the index of the probe
                    probeIDs.remove(i);
                }
            }
        }
    }

    public void highlightPathways(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            pathwayIDs.add(ids[i]);
        }
    }

    public void highlightGOTerms(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            goTermIDs.add(ids[i]);
        }
    }


    public void highlightPathway(Pathway pathway) {
        if (!pathwayIDs.contains(pathway.getID())) {
            pathwayIDs.add(pathway.getID());
            pathways.put(pathway.getID(), pathway);
        }
    }
    public void highlightGOTerm(GOTerm goTerm) {
        if (!goTermIDs.contains(goTerm.getStringID())) {
            goTermIDs.add(goTerm.getStringID());
            goTerms.put(goTerm.getStringID(), goTerm);
        }
    }

    public void stopHighlightingPathway(int id) {
        if (pathwayIDs.contains(id)) {
            for (int i = 0; i < pathwayIDs.size(); i++) {
                if (pathwayIDs.get(i).equals(id)) {
                    // We know the index of the pathway
                    pathwayIDs.remove(i);
                }
            }
            pathways.remove(id);
        }
    }

    public void stopHighlightingGOTerm(String id) {
        if (goTermIDs.contains(id)) {
            for (int i = 0; i < goTermIDs.size(); i++) {
                if (goTermIDs.get(i).equals(id)) {
                    // We know the index of the pathway
                    goTermIDs.remove(i);
                }
            }
            goTerms.remove(id);
        }
    }

    public void highlightLabels(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            labelIDs.add(ids[i]);
        }
    }

    public void highlightLabel(Label label) {
        if (!labelIDs.contains(label.getID())) {
            labelIDs.add(label.getID());
            labels.put(label.getID(), label);
        }
    }

    public void stopHighlightingLabel(int id) {
        if (labelIDs.contains(id)) {
            for (int i = 0; i < labelIDs.size(); i++) {
                if (labelIDs.get(i).equals(id)) {
                    // We know the index of the pathway
                    labelIDs.remove(i);
                }
            }
            labels.remove(id);
        }
    }

    public ArrayList<String> getHighlightedProbes() {
        return probeIDs;
    }

    public ArrayList<Integer> getHighlightedPathways() {
        return pathwayIDs;
    }

    public ArrayList<String> getHighlightedGOTerms() {
        return goTermIDs;
    }

    public ArrayList<Integer> getHighlightedLabels() {
        return labelIDs;
    }

    /**
     * An array of all probes highlighted, either directly or through membership to a highlighted pathway
     * @return
     */
    public ArrayList<String> getHighlighted() {
        ArrayList<String> highlighted = (ArrayList<String>) probeIDs.clone();

        // Pathways
        for (int i = 0; i < pathwayIDs.size(); i++) {
            Pathway pway = pathways.get(pathwayIDs.get(i));
            String[] probeIDsFromPathway = pway.getMemberProbes();
            for (int j = 0; j < probeIDsFromPathway.length; j++) {
                String id = probeIDsFromPathway[j];
                if (!highlighted.contains(id)) {
                    highlighted.add(id);
                }
            }
        }
        // Labels
        for (int i = 0; i < labelIDs.size(); i++) {
            int labID = labelIDs.get(i);
            if (labels.containsKey(labID)) {
                Label label = labels.get(labID);
                String[] probeIDsFromAnnotation = label.getMemberProbes();
                for (int j = 0; j < probeIDsFromAnnotation.length; j++) {
                    String id = probeIDsFromAnnotation[j];
                    if (!highlighted.contains(id)) {
                        highlighted.add(id);
                    }
                }
            }
        }
        // GO Terms
        for (int i = 0; i < goTermIDs.size(); i++) {
            String labID = goTermIDs.get(i);
            if (goTerms.containsKey(labID)) {
                GOTerm goTerm = goTerms.get(labID);
                String[] probeIDsFromGOTerm = goTerm.getMemberProbes();
                for (int j = 0; j < probeIDsFromGOTerm.length; j++) {
                    String id = probeIDsFromGOTerm[j];
                    if (!highlighted.contains(id)) {
                        highlighted.add(id);
                    }
                }
            }
        }
        if (highlighted == null) {
            highlighted = new ArrayList<String>();
        }
        return highlighted;
    }

    public void stopHighlighting() {
        probeIDs = new ArrayList<String>();
        pathwayIDs = new ArrayList<Integer>();
        labelIDs = new ArrayList<Integer>();
        goTermIDs = new ArrayList<String>();
    }

    public Node toXML(Document doc) {
        Element filters = doc.createElement(CapturedAnalysisDAO.XML_KEY_HIGHLIGHTS);

        ArrayList<String> hl = getHighlightedProbes();
        Element probes = doc.createElement(CapturedAnalysisDAO.XML_KEY_PROBES);
        boolean probesActive = false;
        for (int i = 0; i < hl.size(); i++) {
            probesActive = true;
            Element highlight = doc.createElement(CapturedAnalysisDAO.XML_KEY_HIGHLIGHT);
            highlight.setAttribute("value", hl.get(i));
            probes.appendChild(highlight);
        }
        if (probesActive) {
            filters.appendChild(probes);
        }

        ArrayList<Integer> labelHl = getHighlightedLabels();
        Element highLabels = doc.createElement(CapturedAnalysisDAO.XML_KEY_LABELS);
        boolean labelsActive = false;
        for (int i = 0; i < labelHl.size(); i++) {
            labelsActive = true;
            Element highlight = doc.createElement(CapturedAnalysisDAO.XML_KEY_HIGHLIGHT);
            highlight.setAttribute("value", String.valueOf(labelHl.get(i)));
            highLabels.appendChild(highlight);
        }
        if (labelsActive) {
            filters.appendChild(highLabels);
        }

        ArrayList<Integer> pathwayHl = getHighlightedPathways();
        Element highPathways = doc.createElement(CapturedAnalysisDAO.XML_KEY_PATHWAYS);
        boolean pathwaysActive = false;
        for (int i = 0; i < pathwayHl.size(); i++) {
            pathwaysActive = true;
            Element highlight = doc.createElement(CapturedAnalysisDAO.XML_KEY_HIGHLIGHT);
            highlight.setAttribute("value", String.valueOf(pathwayHl.get(i)));
            highPathways.appendChild(highlight);
        }
        if (pathwaysActive) {
            filters.appendChild(highPathways);
        }

        ArrayList<String> goTermHl = getHighlightedGOTerms();
        Element highGOTerms = doc.createElement(CapturedAnalysisDAO.XML_KEY_GO_TERMS);
        boolean goTermsActive = false;
        for (int i = 0; i < goTermHl.size(); i++) {
            goTermsActive = true;
            Element highlight = doc.createElement(CapturedAnalysisDAO.XML_KEY_HIGHLIGHT);
            highlight.setAttribute("value", goTermHl.get(i));
            highGOTerms.appendChild(highlight);
        }
        if (goTermsActive) {
            filters.appendChild(highGOTerms);
        }

        return filters;
    }
}
