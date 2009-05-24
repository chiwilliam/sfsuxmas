package com.sfsu.xmas.trajectory_files;

import org.w3c.dom.Node;

public class TrajectoryNode {

    public int value;
    public int depth;
    public int id;
    public int volatility;
    public int trend;
    public int numberOfMatchingProbes = -1;
    public int numberOfProbesTotal = -1;
    public int highlightedNumberOfMatchingProbes = -1;
    public int highlightedNumberOfProbesTotal = -1;
    Node actualNode;
    public String[] matchingProbeIDs;

    public TrajectoryNode(Node leaf) {
        this.actualNode = leaf;
        this.value = Integer.parseInt(leaf.getAttributes().getNamedItem("Value").getTextContent());
        this.depth = Integer.parseInt(leaf.getAttributes().getNamedItem("Depth").getTextContent());
        this.id = Integer.parseInt(leaf.getAttributes().getNamedItem("NodeID").getTextContent());
        this.volatility = Integer.parseInt(leaf.getAttributes().getNamedItem("Volatility").getTextContent());
        this.trend = Integer.parseInt(leaf.getAttributes().getNamedItem("Trend").getTextContent());
    }

    public int[] getSubtractiveTrajectory() {
        int[] traj = new int[depth];
        
        Node no = actualNode;
        
        for (int i = depth - 1; i >= 0; i--) {
            traj[i] = Integer.parseInt(no.getAttributes().getNamedItem("Value").getTextContent());
            no = no.getParentNode();
        }
        return traj;
    }

    public int getValue() {
        return value;
    }

    public int getDepth() {
        return depth;
    }

    public int getID() {
        return id;
    }

    public int getVolatility() {
        return volatility;
    }

    public int getTrend() {
        return trend;
    }

    public Node getActualNode() {
        return actualNode;
    }

    public Node getParentNode() {
        return actualNode.getParentNode();
    }

    public int getChildNodeCount() {
        return actualNode.getChildNodes().getLength();
    }

    public int getMatchingQuantity() {
        return numberOfMatchingProbes;
    }

    public int getTotalQuantity() {
        return numberOfProbesTotal;
    }

    public int getMatchingHighlighted() {
        return highlightedNumberOfMatchingProbes;
    }

    public int getHighlightedTotal() {
        return highlightedNumberOfProbesTotal;
    }
    
    

    public void setMatchingHighlightedQuantity(int numberOfFilteredHighlightedProbesInNode) {
        highlightedNumberOfMatchingProbes = numberOfFilteredHighlightedProbesInNode;
    }

    public void setTotalHighlightedQuantity(int numberOfHighlightedProbesInNodeTotal) {
        highlightedNumberOfProbesTotal = numberOfHighlightedProbesInNodeTotal;
    }

    public void setMatchingQuantity(int numberOfFilteredProbesInNode) {
        numberOfMatchingProbes = numberOfFilteredProbesInNode;
    }

    public void setTotalQuantity(int numberOfProbesInNodeTotal) {
        numberOfProbesTotal = numberOfProbesInNodeTotal;
    }

    public void setMatchingProbes(String[] probeIDs) {
        if (probeIDs.length == numberOfMatchingProbes) {
            this.matchingProbeIDs = probeIDs;
        }
    }

    public String[] getMatchingProbes() {
        return matchingProbeIDs;
    }
}
