package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.dao.DAOFactoryFactory;
import com.sfsu.xmas.dao.TrajectoryFileDAO;
import com.sfsu.xmas.filter.IFilter;
import com.sfsu.xmas.globals.FileGlobals;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.TreeMap;

public class TrajectoryFile {

    private String fileName;
    private int nodeCount;
    private int trajectoryCount;
    private int minBinValue;
    private int maxBinValue;
    private TrajectoryFileDAO dataAccess;

    public TrajectoryFile(String name, int parentDB) {
        this.fileName = name;
        dataAccess = DAOFactoryFactory.getUniqueInstance().getFileSystemDAOFactory().getTrajectoryFileDAO(name, parentDB);
        populateBasicData();
    }

    protected void populateBasicData() {
        nodeCount = dataAccess.getXpathInteger("//FileSummary/Nodes/@Value");
        trajectoryCount = dataAccess.getXpathInteger("//FileSummary/Trajectories/@Value");
        minBinValue = dataAccess.getXpathInteger("//FileSummary/MinimumBinValue/@Value");
        maxBinValue = dataAccess.getXpathInteger("//FileSummary/MaximumBinValue/@Value");
    }

    public double getCollapsedExpressionShiftAmount(double[] timePeriodExpression) {
        return Math.floor(timePeriodExpression[0] / getBinUnit()) * getBinUnit();
    }

    public String getFileName() {
        return fileName;
    }

    public double getBinUnit() {
        return dataAccess.getBinUnit();
    }

    public int getK() {
        return dataAccess.getK();
    }

    public int getNumberOfNodes() {
        return nodeCount;
    }

    public int getNumberOfTrajectories() {
        return trajectoryCount;
    }

    public int getMinimumBinValue() {
        return minBinValue;
    }

    public int getMaximumBinValue() {
        return maxBinValue;
    }

    public int getMaximumSubtractiveDegree() {
        return dataAccess.getMaximumSubtractiveDegree();
    }

    public int getMinimumSubtractiveDegree() {
        return dataAccess.getMinimumSubtractiveDegree();
    }

    public boolean isPreserved() {
        return !getFileName().endsWith(FileGlobals.COLLAPSED_POSTFIX) && !isClustered();
    }

    public boolean isCollapsed() {
        return getFileName().endsWith(FileGlobals.COLLAPSED_POSTFIX) && !isClustered();
    }

    public boolean isClustered() {
        return getFileName().contains(FileGlobals.CLUSTERED_INFIX);
    }

    /**
     * Returns and array of probe identifiers which are not filtered out based on the user identifier
     * @param identifier
     * @return
     */
    public synchronized String[] getProbes(String identifier) {
        return dataAccess.getProbes(identifier);
    }

    public LeafNodes getLeafNodes(String identifier) {
        return dataAccess.getLeafNodes(identifier);
    }

    public LeafNodes getLeafNodes(String identifier, int nodeID, int timePeriod) {
        return dataAccess.getLeafNodes(identifier, nodeID, timePeriod);
    }

    public int[][] getMaxPossibleTrajectorys() {
        return dataAccess.getMaxPossibleTrajectorys();
    }

    public int[][] getPossibleTrajectorys(HttpServletRequest request) {
        return dataAccess.getPossibleTrajectorys(request);
    }

    public int[][] getShapeFromRequest(HttpServletRequest request) {
        return dataAccess.getShapeFromRequest(request);
    }
    
    public LeafNodes getMultiNodeTrajectories(String identifier, int[] node_ids) {
        return dataAccess.getMultiNodeTrajectories(identifier, node_ids);
    }

    public int getNumberOfTrajectories(String identifier, IFilter filter) {
        return dataAccess.getTrajectoryCount(identifier, filter);
    }
    
    public int[][] getEmptyShape() {
        return dataAccess.getEmptyShape();
    }

    public NodeList getNodeTrajectories(String identifier, int nodeID, int timePeriod) {
        return dataAccess.getNodeTrajectories(identifier, nodeID, timePeriod);
    }

    public TreeMap<Integer, HashMap<Integer, TrajectoryNode>> getTrajectoriesRankedByTrend(String identifier) {
        return dataAccess.getTrajectoriesRankedByTrend(identifier);
    }
    
    public TreeMap<Integer, HashMap<Integer, TrajectoryNode>> getTrajectoriesRankedByVolatility(String identifier) {
        return dataAccess.getTrajectoriesRankedByVolatility(identifier);
    }
}
