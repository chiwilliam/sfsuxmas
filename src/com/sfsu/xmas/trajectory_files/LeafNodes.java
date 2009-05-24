package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.filter.FilterList;
import com.sfsu.xmas.filter.FilterManager;
import filter.MatchingProbesForFilterSet;
import com.sfsu.xmas.highlight.HighlightManager;
import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeafNodes extends Vector<TrajectoryNode> {

    private int totalNumberOfMatchingProbes = 0;
    private int totalNumberOfProbes = 0;
    private int totalNumberOfMatchingHighProbes = 0;
    private int totalNumberOfHighProbes = 0;
//    private int minExpressionBin = 0;
//    private int maxExpressionBin = 0;
    private String[] matchingProbes = new String[0];

    /**
     * 
     * @param leaves
     * @param filters
     */
//    public LeafNodes(NodeList leaves, FilterList filters) {
//        initialize(leaves, filters);
//    }
    public LeafNodes(String identifier, NodeList leaves, int tpCount) {
        initialize(leaves, FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier), HighlightManager.getUniqueInstance().getHighlighted(identifier), tpCount);
    }

    public int[][] getFilterShape(int timePeriodCount, int range, int minExp) {
        int[][] shape = new int[timePeriodCount][range];
        for (int i = 0; i < timePeriodCount; i++) {
            for (int j = 0; j < range; j++) {
                shape[i][j] = FilterManager.blankShapeValue;
            }
        }
        for (int i = 0; i < size(); i++) {
            TrajectoryNode ln = elementAt(i);
            int[] traj = ln.getSubtractiveTrajectory();
            for (int j = 1; j < traj.length; j++) {
                shape[j][(traj[j] - traj[j - 1]) - minExp] = 1;
            }
        }
        return shape;
    }

    public synchronized void initialize(NodeList leaves, FilterList filters, ArrayList<String> highlighted, int tpCount) {
//        int tpCount = getNumberOfTimePeriods();

        int totNoOfMatchProbes = 0;
        int totNoOfProbes = 0;

        int totNoOfMatchHighProbes = 0;
        int totNoOfHighProbes = 0;

//        ArrayList<String> highlighted = HighlightManager.getUniqueInstance().getHighlighted(identifier);

        MatchingProbesForFilterSet includedPs = filters.getIncludedProbeFiltersByIDs();
        MatchingProbesForFilterSet excludedPs = filters.getExcludedProbeFiltersByIDs();

        ArrayList<String> totalMatchingPs = new ArrayList<String>();

        try {

            if (leaves != null && leaves.getLength() > 0) {

                // For each node
                for (int i = 0; i < leaves.getLength(); i++) {

                    Node leaf = leaves.item(i);
                    NodeList probes = leaf.getChildNodes();

//                    Node tpNode = leaf;
//                    for (int tp = 0; tp < tpCount; tp++) {
//                        int tpVal = Integer.parseInt(tpNode.getAttributes().getNamedItem("Value").getTextContent());
//                        if (i == 0 && tp == 0) {
//                            maxExpressionBin = tpVal;
//                            minExpressionBin = tpVal;
//                        } else if (tpVal > maxExpressionBin) {
//                            maxExpressionBin = tpVal;
//                        } else if (tpVal < minExpressionBin) {
//                            minExpressionBin = tpVal;
//                        }
//                        tpNode = tpNode.getParentNode();
//                    }

                    int numberOfFilteredProbesInNode = 0;
                    int numberOfProbesInNodeTotal = 0; // Unfiltered

                    int numberOfFilteredHighlightedProbesInNode = 0;
                    int numberOfHighlightedProbesInNodeTotal = 0;

                    ArrayList<String> matchingProbesInNode = new ArrayList<String>();

                    if (probes != null) {
                        
                        
                        numberOfProbesInNodeTotal = probes.getLength();
                        if (includedPs.size() > 0 || excludedPs.size() > 0) {
                            // At least one probe isolation

                            // For each probe in the node
                            for (int j = 0; j < probes.getLength(); j++) {
                                Node probe = probes.item(j);
                                String probeID = probe.getAttributes().getNamedItem("ID").getTextContent();
                                boolean isolated = includedPs.size() <= 0 || (includedPs.size() > 0 && includedPs.isProbeIsolated(probeID));
                                boolean excluded = excludedPs.size() > 0 && excludedPs.isProbeExcluded(probeID);

                                boolean probeStillInAnalysis = isolated && !excluded;

                                if (probeStillInAnalysis) {
                                    // Probe is ok
                                    matchingProbesInNode.add(probeID);
                                    numberOfFilteredProbesInNode++;
                                }

                                if (probeStillInAnalysis && highlighted.contains(probeID)) {
                                    numberOfFilteredHighlightedProbesInNode++;
                                    numberOfHighlightedProbesInNodeTotal++;
                                }
                            }
                        } else {
                            // No probe level filters
                            // Add all probes
                            for (int j = 0; j < numberOfProbesInNodeTotal; j++) {
                                Node probe = probes.item(j);
                                String probeID = probe.getAttributes().getNamedItem("ID").getTextContent();

                                matchingProbesInNode.add(probeID);
                                numberOfFilteredProbesInNode++;
                                if (highlighted.contains(probeID)) {
                                    numberOfFilteredHighlightedProbesInNode++;
                                    numberOfHighlightedProbesInNodeTotal++;
                                }
                            }
                        }
                    }

                    // This trajectory has >= 1 probe after filtering
                    if (numberOfFilteredProbesInNode > 0) {
                        TrajectoryNode tnode = new TrajectoryNode(leaf);
                        tnode.setMatchingQuantity(numberOfFilteredProbesInNode);
                        tnode.setTotalQuantity(numberOfProbesInNodeTotal);

                        tnode.setMatchingHighlightedQuantity(numberOfFilteredHighlightedProbesInNode);
                        tnode.setTotalHighlightedQuantity(numberOfHighlightedProbesInNodeTotal);

                        totNoOfMatchProbes += numberOfFilteredProbesInNode;
                        totNoOfProbes += numberOfProbesInNodeTotal;

                        totNoOfMatchHighProbes += numberOfFilteredHighlightedProbesInNode;
                        totNoOfHighProbes += numberOfHighlightedProbesInNodeTotal;

                        String[] ids = new String[matchingProbesInNode.size()];
                        for (int m = 0; m < matchingProbesInNode.size(); m++) {
                            ids[m] = matchingProbesInNode.get(m);
                            totalMatchingPs.add(matchingProbesInNode.get(m));
                        }

                        tnode.setMatchingProbes(ids);
                        add(tnode);
                    }
                }
            }

        } catch (NullPointerException ex) {
            System.out.println(LeafNodes.class.getSimpleName() + ": Null pointer exception thrown, retry");
            throw ex;
        //initialize(leaves, filters, highlighted, tpCount);
        }
        
        
        this.totalNumberOfMatchingProbes = totNoOfMatchProbes;
        this.totalNumberOfProbes = totNoOfProbes;
        this.totalNumberOfMatchingHighProbes = totNoOfMatchHighProbes;
        this.totalNumberOfHighProbes = totNoOfHighProbes;

        this.matchingProbes = totalMatchingPs.toArray(new String[totalMatchingPs.size()]);
    }

    public String[] getMatchingProbes() {
        return matchingProbes;
    }

    public int getTotalNumberOfMatchingProbes() {
        return totalNumberOfMatchingProbes;
    }

    public int getTotalNumberOfProbes() {
        return totalNumberOfProbes;
    }

    public int getTotalNumberOfMatchingHighlightedProbes() {
        return totalNumberOfMatchingHighProbes;
    }

    public int getTotalNumberOfHighlightedProbes() {
        return totalNumberOfHighProbes;
    }//    public int getMinExpressionBin() {
//        return minExpressionBin;
//    }
//
//    public int getMaxExpressionBin() {
//        return maxExpressionBin;
//    }
}
