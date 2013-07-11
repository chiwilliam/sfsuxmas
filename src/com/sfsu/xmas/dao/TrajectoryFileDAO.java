package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.filter.FilterList;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.filter.IFilter;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.trajectory_files.LeafNodes;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import com.sfsu.xmas.trajectory_files.TrajectoryNode;
import filter.TrajNodeFilter;
import filter.TrajectoryShapeFilter;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrajectoryFileDAO extends XMLFileDAO {

    private String fakeIdentifier = "blank";
    private int numberOfTimePeriods;
    private int subtractiveDegreeMin;
    private int subtractiveDegreeMax;
    private double binUnit;
    private int k;
    private int[][] maxTrajectories;
//    private Document doc;
    public TrajectoryFileDAO(String name, int parentDB) {
        super(name, parentDB);
        numberOfTimePeriods = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(parentDB, false).getNumberOfTimePeriods();
        subtractiveDegreeMax = getXpathInteger("//FileSummary/SubtractiveDegree/@Max");
        subtractiveDegreeMin = getXpathInteger("//FileSummary/SubtractiveDegree/@Min");
        binUnit = getXpathDouble("//FileSummary/BinUnit/@Value");
        k = getXpathInteger("//FileSummary/K/@Value");
    }

    public String getDisplayName() {
        if (fileName.startsWith(FileGlobals.TRAJECTORY_DATA_FILE_PREFIX)) {
            return fileName.substring(FileGlobals.TRAJECTORY_DATA_FILE_PREFIX.length(), fileName.length());
        }
        return fileName;
    }

    public int getMaximumSubtractiveDegree() {
        return subtractiveDegreeMax;
    }

    public int getMinimumSubtractiveDegree() {
        return subtractiveDegreeMin;
    }

    public double getBinUnit() {
        return binUnit;
    }

    public int getK() {
        return k;
    }

    public boolean isPreserved() {
        return !getFileName().endsWith(FileGlobals.COLLAPSED_POSTFIX);
    }

    public LeafNodes getLeafNodes(String identifier, int nodeID, int timePeriod) {
        return new LeafNodes(identifier, getNodeTrajectories(identifier, nodeID, timePeriod), numberOfTimePeriods);
    }

    public NodeList getNodeTrajectories(String identifier, int nodeID, int timePeriod) {
        String leafFilter = getLeafFilter(identifier, String.valueOf(timePeriod), String.valueOf(nodeID));
        NodeList leaves = getNodeListForXpath(leafFilter);
        return leaves;
    }

    public LeafNodes getMultiNodeTrajectories(String identifier, int[] node_ids) {
        String leafFilter = getLeafFilter(identifier, numberOfTimePeriods, node_ids);
        NodeList leaves = getNodeListForXpath(leafFilter);
        return new LeafNodes(identifier, leaves, numberOfTimePeriods);
    }
    
    protected HashMap<String, NodeList> xPathResultCache = new HashMap<String, NodeList>();

    protected synchronized NodeList getNodeListForXpath(String xPath) {
        NodeList leaves = null;
//        if (xPathResultCache.containsKey(xPath)) {
//            System.out.println(this.getClass().getName() + ", XPATH cache kicked in for: " + xPath);
//            return xPathResultCache.get(xPath);
//        } else {
            try {
                javax.xml.xpath.XPathFactory xf = XPathFactory.newInstance();
                XPath xp = xf.newXPath();
                ExecutionTimer et = new ExecutionTimer();
                et.start();
                leaves = (NodeList) xp.evaluate(xPath, getRootNode(), XPathConstants.NODESET);
                et.end();
                System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName() + ", XPATH: " + xPath);
            } catch (XPathExpressionException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
//            if (xPathResultCache.size() > 1) {
//                // Don't want to blow out memory saving these things
//                System.out.println(this.getClass().getName() + ": Cache reset");
//                xPathResultCache = new HashMap<String, NodeList>();
//            }
//            xPathResultCache.put(xPath, leaves);
//        }
        return leaves;
    }

    public synchronized  TreeMap<Integer, HashMap<Integer, TrajectoryNode>> getTrajectoriesRankedByVolatility(String identifier) {
        LeafNodes trajectories = getLeafNodes(identifier);
        HashMap<Integer, HashMap<Integer, TrajectoryNode>> ordered = new HashMap<Integer, HashMap<Integer, TrajectoryNode>>();
        for (int i = 0; i < trajectories.size(); i++) {
            TrajectoryNode tn = trajectories.get(i);
            int vol = 1000 - tn.getVolatility();
            if (ordered.containsKey(vol)) {
                HashMap<Integer, TrajectoryNode> otherWithSameVol = ordered.get(vol);
                otherWithSameVol.put(tn.getID(), tn);
            } else {
                HashMap<Integer, TrajectoryNode> otherWithSameVol = new HashMap<Integer, TrajectoryNode>();
                otherWithSameVol.put(tn.getID(), tn);
                ordered.put(vol, otherWithSameVol);
            }
        }
        TreeMap<Integer, HashMap<Integer, TrajectoryNode>> tm = new TreeMap<Integer, HashMap<Integer, TrajectoryNode>>(ordered);
        return tm;
    }

    public synchronized  TreeMap<Integer, HashMap<Integer, TrajectoryNode>> getTrajectoriesRankedByTrend(String idendtifier) {
        LeafNodes trajectories = getLeafNodes(idendtifier);

        HashMap<Integer, HashMap<Integer, TrajectoryNode>> ordered = new HashMap<Integer, HashMap<Integer, TrajectoryNode>>();

        for (int i = 0; i < trajectories.size(); i++) {
            TrajectoryNode tn = trajectories.get(i);
            int vol = 1000 - Math.abs(tn.getTrend());
            if (ordered.containsKey(vol)) {
                HashMap<Integer, TrajectoryNode> otherWithSameVol = ordered.get(vol);
                otherWithSameVol.put(tn.getID(), tn);
            } else {
                HashMap<Integer, TrajectoryNode> otherWithSameVol = new HashMap<Integer, TrajectoryNode>();
                otherWithSameVol.put(tn.getID(), tn);
                ordered.put(vol, otherWithSameVol);
            }

        }

        TreeMap<Integer, HashMap<Integer, TrajectoryNode>> tm = new TreeMap<Integer, HashMap<Integer, TrajectoryNode>>(ordered);

        return tm;
    }

    public synchronized  TreeMap<Integer, HashMap<Integer, TrajectoryNode>> getTrajectoriesByProbeIds(String identifier, int[] probeIds) {
        //TODO figure this out!
        LeafNodes trajectories = getLeafNodes(identifier);

        HashMap<Integer, HashMap<Integer, TrajectoryNode>> ordered = new HashMap<Integer, HashMap<Integer, TrajectoryNode>>();

        for (int i = 0; i < trajectories.size(); i++) {
            TrajectoryNode tn = trajectories.get(i);
            int vol = 0;
            if (ordered.containsKey(vol)) {
                HashMap<Integer, TrajectoryNode> nodeMap = ordered.get(vol);
                nodeMap.put(tn.getID(), tn);
            } else {
                HashMap<Integer, TrajectoryNode> nodeMap = new HashMap<Integer, TrajectoryNode>();
                nodeMap.put(tn.getID(), tn);
                ordered.put(vol, nodeMap);
            }

        }

        TreeMap<Integer, HashMap<Integer, TrajectoryNode>> tm = new TreeMap<Integer, HashMap<Integer, TrajectoryNode>>(ordered);

        return tm;
    }

    public synchronized NodeList getTrajectories(String identifier) {
        FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);
        String xPath = fl.getTrajectoryXPathSnippet(numberOfTimePeriods);
        NodeList leaves = getNodeListForXpath(xPath);
        return leaves;
    }

    public synchronized String[] getProbes(String identifier) {
        NodeList leaves = getTrajectories(identifier);
        LeafNodes lns = new LeafNodes(identifier, leaves, numberOfTimePeriods);
        String[] probeIDs = lns.getMatchingProbes();
        return probeIDs;
    }

    public String getLeafFilter(String identifier, String timePeriod, String nodeID) {
        FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);
        TrajNodeFilter tnf = FilterManager.makeNodeFilter(Integer.valueOf(nodeID), Integer.valueOf(timePeriod), false);
        fl.add(tnf);
        String xPath = fl.getTrajectoryXPathSnippet(numberOfTimePeriods);
        fl.remove(tnf);
        return xPath;
    }

    public String getLeafFilter(String identifier, int timePeriod, int[] nodeID) {
        ArrayList<TrajNodeFilter> filters = new ArrayList<TrajNodeFilter>();

        FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);
        for (int i = 0; i < nodeID.length; i++) {
            if (nodeID[i] > 0) {
                TrajNodeFilter tnf = FilterManager.makeNodeFilter(nodeID[i], timePeriod, false);
                fl.add(tnf);
                filters.add(tnf);
            }
        }
        String xPath = fl.getTrajectoryXPathSnippet(numberOfTimePeriods);
        for (int i = 0; i < nodeID.length; i++) {
            fl.remove(filters.get(i));
        }
        return xPath;
    }

    public synchronized LeafNodes getLeafNodes(String identifier) {
        NodeList leaves = getTrajectories(identifier);
        LeafNodes lns = new LeafNodes(identifier, leaves, numberOfTimePeriods);
        return lns;
    }

    private void initializeMaxTrajectories() {
        int maxBin = getMaximumSubtractiveDegree();
        int minBin = getMinimumSubtractiveDegree();

        int[][] blankShape = new int[numberOfTimePeriods][maxBin - minBin + 1];

        for (int tp = 0; tp < blankShape.length; tp++) {
            for (int exp = 0; exp < maxBin - minBin + 1; exp++) {
                blankShape[tp][exp] = FilterManager.blankShapeValue;
            }
        }

        int[][] trajCount = new int[numberOfTimePeriods][maxBin - minBin + 1];

//        FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(name);
//        fl.add(tsf);
        LeafNodes leaves = getLeafNodes(fakeIdentifier);
//        fl.remove(tsf);

        for (int leafIndex = 0; leafIndex < leaves.size(); leafIndex++) {
            TrajectoryNode leaf = (TrajectoryNode) leaves.get(leafIndex);

            int[] traj = leaf.getSubtractiveTrajectory();
            for (int i = 1; i < traj.length; i++) {
                trajCount[i][(traj[i] - traj[i - 1]) - minBin]++;
            }
            trajCount[0][0 - minBin]++;
        }
        this.maxTrajectories = trajCount;
    }

    public synchronized int[][] getMaxPossibleTrajectorys() {
        if (maxTrajectories == null) {
            initializeMaxTrajectories();
        }
        return maxTrajectories;
    }

    public synchronized int[][] getPossibleTrajectorys(HttpServletRequest request) {
        String sessionID = SessionAttributeManager.getSessionID(request);
        if (maxTrajectories == null) {
            initializeMaxTrajectories();
        }
        int maxBin = getMaximumSubtractiveDegree();
        int minBin = getMinimumSubtractiveDegree();

        int[][] shape = getShapeFromRequest(request);

        TrajectoryShapeFilter tsf = new TrajectoryShapeFilter(shape, binUnit, maxBin, minBin);
        int[][] trajCount = new int[numberOfTimePeriods][maxBin - minBin + 1];

        FilterList fl = FilterManager.getUniqueInstance().getFilterListForIdentifier(sessionID);

        TrajectoryShapeFilter originalTSF = fl.getCurrentTrajectoryFilter();

        if (originalTSF != null) {
            FilterManager.getUniqueInstance().removeTrajectoryShapeFilter(sessionID);
        }
        FilterManager.getUniqueInstance().addFilter(sessionID, tsf);
        LeafNodes leaves = getLeafNodes(sessionID);
        FilterManager.getUniqueInstance().removeTrajectoryShapeFilter(sessionID);
        if (originalTSF != null) {
            FilterManager.getUniqueInstance().addFilter(sessionID, originalTSF);
        }


        for (int leafIndex = 0; leafIndex < leaves.size(); leafIndex++) {
            TrajectoryNode leaf = (TrajectoryNode) leaves.get(leafIndex);

            int[] traj = leaf.getSubtractiveTrajectory();
            for (int i = 1; i < traj.length; i++) {
                trajCount[i][(traj[i] - traj[i - 1]) - minBin]++;
            }
            trajCount[0][0 - minBin]++;
        }

        // For each TP now, if there is a check, should recalculate for fellow TP values
        for (int tpIndex = 1; tpIndex < shape.length; tpIndex++) {
            int[] tp = shape[tpIndex];
            boolean checkInTP = false;

            boolean[] checked = new boolean[tp.length];

            for (int i = 0; i < tp.length; i++) {
                checked[i] = false;
                if (tp[i] != FilterManager.blankShapeValue) {
                    checked[i] = true;
                    checkInTP = true;
                }
            }
            if (checkInTP) {
                int[][] adjustedShape = new int[numberOfTimePeriods][maxBin - minBin + 1];
                for (int z = 0; z < shape.length; z++) {
                    adjustedShape[z] = shape[z].clone();
                }
                for (int j = 0; j < tp.length; j++) {
                    adjustedShape[tpIndex][j] = FilterManager.blankShapeValue;
                }

                TrajectoryShapeFilter tsfAdjusted = new TrajectoryShapeFilter(adjustedShape, binUnit, maxBin, minBin);

                if (originalTSF != null) {
                    FilterManager.getUniqueInstance().removeTrajectoryShapeFilter(sessionID);
                }

                FilterManager.getUniqueInstance().addFilter(sessionID, tsfAdjusted);
                LeafNodes adjustedLeaves = getLeafNodes(sessionID);
                FilterManager.getUniqueInstance().removeTrajectoryShapeFilter(sessionID);

                if (originalTSF != null) {
                    FilterManager.getUniqueInstance().addFilter(sessionID, originalTSF);
                }

                for (int leafIndex = 0; leafIndex < adjustedLeaves.size(); leafIndex++) {
                    TrajectoryNode leaf = (TrajectoryNode) adjustedLeaves.get(leafIndex);

                    int[] traj = leaf.getSubtractiveTrajectory();
                    int binIndex = (traj[tpIndex] - traj[tpIndex - 1]) - minBin;
                    if (!checked[binIndex]) {
                        trajCount[tpIndex][binIndex]++;
                    }
                }
            }
        }

        return trajCount;
    }

    public int[][] getShapeFromRequest(HttpServletRequest request) {
        int[][] shape = getEmptyShape();
        for (int i = 1; i <= numberOfTimePeriods; i++) {
            for (int j = getMinimumSubtractiveDegree(); j <= getMaximumSubtractiveDegree(); j++) {
                String checkboxName = "traj_check_" + j + "_" + i;
                boolean checked = !(request.getParameter(checkboxName) == null);
                if (checked) {
                    int tp = i;
                    int value = j;
                    shape[tp - 1][value - getMinimumSubtractiveDegree()] = 1;
                }
            }
        }
        return shape;
    }

    public int[][] getEmptyShape() {
        int[][] shape = new int[numberOfTimePeriods][getMaximumSubtractiveDegree() - getMinimumSubtractiveDegree() + 1];
        for (int i = 1; i <= numberOfTimePeriods; i++) {
            for (int j = getMinimumSubtractiveDegree(); j <= getMaximumSubtractiveDegree(); j++) {
                shape[i - 1][j - getMinimumSubtractiveDegree()] = FilterManager.blankShapeValue;
            }
        }
        return shape;
    }

    public void trajectoryPopulated(FilterList fl) {
    }

    public int getTrajectoryCount(String identifier, IFilter filter) {
        FilterList trajShapeFilters = FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier);
        // Add it to the existing filters
        if (filter != null) {
            trajShapeFilters.add(filter);
        }
        String xPath = trajShapeFilters.getTrajectoryXPathSnippet(numberOfTimePeriods);
        NodeList leaves = getTrajectories(xPath);
        LeafNodes lns = new LeafNodes(identifier, leaves, numberOfTimePeriods);
        if (filter != null) {
            trajShapeFilters.remove(filter);
        }
        return lns.size();
    }

    protected synchronized Document getDocument() {
        ExecutionTimer et = new ExecutionTimer();
        et.start();
        Document doc = null;
        DOMParser parser = new DOMParser();
        try {
            String filePath = FileGlobals.getRoot() + ExpressionDataSetMultiton.getUniqueInstance().getDataSet(parentDataSetID, false).getName() + File.separatorChar + fileName + FileGlobals.XML_FILE_EXTENSION;
            if (new File(filePath).exists()) {
                parser.parse(filePath);
            } else {
                System.err.println(TrajectoryFileFactory.class.getName() + ": File not found at path = " + filePath);
                return null;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrajectoryFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TrajectoryFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrajectoryFileFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc = parser.getDocument();
        
        et.end();
        System.out.println(this.getClass().getSimpleName() + ": DURATION " + et.duration() + " Getting Trajectory Document");
        
        return doc;
    }
}
