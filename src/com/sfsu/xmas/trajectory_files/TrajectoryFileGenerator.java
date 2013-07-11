package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.dao.ExpressionDataSetTrajectoriesDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.util.XMLUtils;
import k_means.ClusterService;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xml_creation.TrajectoryComparator;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrajectoryFileGenerator {

    public static final String collapsedSuffix = "_collapsed";
    public static final String clusteredSuffix = "_kmeans";
    protected ExpressionDataSet expressionDataSet;
    protected ExpressionDataSetTrajectoriesDAO dataAccess;
    protected double binUnit;
    protected int k;
    protected ClusterService clusterService;

    public enum TrajectoryType { PRESERVED, COLLAPSED, CLUSTERED }

    public TrajectoryFileGenerator(ExpressionDataSet expressionDatabase, double binUnit, int k) {
        expressionDataSet = expressionDatabase;
        dataAccess = new ExpressionDataSetTrajectoriesDAO(expressionDatabase);
        clusterService = new ClusterService(expressionDataSet);
        this.binUnit = binUnit;
        this.k = k;
    }

    public synchronized void generateFiles(String fileName) {
        dataAccess.createCustomBinView(fileName, binUnit);
        dataAccess.createTrajectoryViews(expressionDataSet.getNumberOfTimePeriods(), true);
        dataAccess.createCollapsedTrajectoryViews(expressionDataSet.getNumberOfTimePeriods(), true);

        generateFile(fileName, TrajectoryType.COLLAPSED);
        generateFile(fileName, TrajectoryType.PRESERVED);

        clusterService.kmeans(k);
        generateClusterFile(fileName);

        dataAccess.removeTrajectoryBinViews(expressionDataSet.getNumberOfTimePeriods());
    }

    private synchronized boolean generateFile(String fileName, TrajectoryType collapsed) {
        DOMImplementation domImpl = new DOMImplementationImpl();
        Document doc = domImpl.createDocument(null, "TrajectoryFile", null);
        Element root = doc.getDocumentElement();

        Element parent;
        Element child;

        int nodeCount = 0;
        int trajCount = 0;
        int probeCount = 0;

        int maxBinValue = -100;
        int minBinValue = 100;

        ResultSet trajRS = null;
        ResultSet genesRS = null;

        // Create custom view

        switch (collapsed) {
            case COLLAPSED:
                trajRS = dataAccess.getCollapsedTrajectories();
                genesRS = dataAccess.getAllProbesInCollapsedTrajectoryOrder(expressionDataSet.getNumberOfTimePeriods());
                break;
            case PRESERVED:
                trajRS = dataAccess.getTrajectories(expressionDataSet.getNumberOfTimePeriods());
                genesRS = dataAccess.getAllProbesInTrajectoryOrder(expressionDataSet.getNumberOfTimePeriods());
                break;
        }

        int numberOfTimePeriods = expressionDataSet.getNumberOfTimePeriods();

        int maxShift = -100;
        int minShift = 100;

        int maxVolatility = 0;
        int maxTrend = 0;

        TreeMap<Integer[], Element> existingPaths = new TreeMap<Integer[], Element>((TrajectoryComparator) new TrajectoryComparator());

        try {
            // For each trajectory
            while (trajRS.next()) {
                parent = root;

                int volatility = 0;
                int trend = 0;

                // Keeps track of the bins which define the trajectory
                Integer[] trajectory = new Integer[numberOfTimePeriods];
                // Row attributes are numbered from 1 -->
                int numberOfProbesInTrajectory = Integer.parseInt(trajRS.getString(1));
                // For each time period:
                for (int timePeriodIndex = 0; timePeriodIndex < numberOfTimePeriods; timePeriodIndex++) {
                    // See what bin the trajectory is in at the current time point
                    int trajectoryExpressionValue = (int) Math.floor(Double.parseDouble(trajRS.getString(timePeriodIndex + 2)));
                    // Keep track of the bin at this time point
                    trajectory[timePeriodIndex] = trajectoryExpressionValue;

                    if (timePeriodIndex > 0) {
                        int subtractiveLast = trajectoryExpressionValue - trajectory[timePeriodIndex - 1];
                        volatility += subtractiveLast * subtractiveLast;
                        trend += subtractiveLast;
                        if (volatility > maxVolatility) {
                            maxVolatility = volatility;
                        }
                        if (Math.abs(trend) > Math.abs(maxTrend)) {
                            maxTrend = trend;
                        }
                    }

                    // Okay, see if the trajectory up to this point has already been constructed
                    // if so, focus on the existing  node and move to the next time period
                    if (existingPaths.size() > 0) {
                        child = (Element) existingPaths.get(trajectory);
                    } else {
                        child = null;
                    }
                    if (child == null) {
                        nodeCount++;
                        child = doc.createElement("TP" + (timePeriodIndex + 1));
                        child.setAttribute("Value", String.valueOf(trajectoryExpressionValue));
                        if (trajectoryExpressionValue > maxBinValue) {
                            maxBinValue = trajectoryExpressionValue;
                        }
                        if (trajectoryExpressionValue < minBinValue) {
                            minBinValue = trajectoryExpressionValue;
                        }
                        child.setAttribute("NodeID", "" + nodeCount);
                        child.setAttribute("Depth", "" + (timePeriodIndex + 1));
                        child.setAttribute("Volatility", String.valueOf(volatility));
                        child.setAttribute("Trend", String.valueOf(trend));
                        parent.appendChild(child);

                        existingPaths.put(trajectory.clone(), child);
                    } else {
                        if ((timePeriodIndex + 1) == numberOfTimePeriods) {
                            System.out.println("ERROR: Non unique trajectory");
                        }
                    }
                    parent = child;
                }
                for (int probeIndex = 0; probeIndex < numberOfProbesInTrajectory && genesRS.next(); probeIndex++) {
                    child = doc.createElement("Probe");
                    child.setAttribute("ID", genesRS.getString("Probe_id"));
                    parent.appendChild(child);
                    probeCount++;
                }

                for (int i = 1; i < trajectory.length; i++) {
                    int tpiValue = trajectory[i];
                    int tp0Value = trajectory[0];
                    // Work out jump from previous TP
                    int subtractive = tpiValue - tp0Value;
                    if (subtractive > maxShift) {
                        // New max shift
                        System.out.println("NEW MAX JUMP: " + subtractive);
                        maxShift = subtractive;
                    }
                    if (subtractive < minShift) {
                        // New max neg shift
                        System.out.println("NEW MIN JUMP: " + subtractive);
                        minShift = subtractive;
                    }
                    int tpiM1Value = trajectory[i - 1];
                    int subtractiveLast = tpiValue - tpiM1Value;
                    if (subtractiveLast > maxShift) {
                        // New max shift
                        System.out.println("NEW MAX JUMP: " + subtractiveLast);
                        maxShift = subtractiveLast;
                    }
                    if (subtractiveLast < minShift) {
                        // New max neg shift
                        System.out.println("NEW MIN JUMP: " + subtractiveLast);
                        minShift = subtractiveLast;
                    }
                }

                trajCount++;
            }
            genesRS.close();
            trajRS.close();
        } catch (SQLException ex) {
            Logger.getLogger(TrajectoryFileGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element summary = doc.createElement("FileSummary");
        // Bin Unit
        Element bUnit = doc.createElement("BinUnit");
        bUnit.setAttribute("Value", String.valueOf(binUnit));
        summary.appendChild(bUnit);
        // K-Means K
        Element kmeansk = doc.createElement("K");
        kmeansk.setAttribute("Value", String.valueOf(k));
        summary.appendChild(kmeansk);
        // Probe count
        Element probeCountTotal = doc.createElement("Probes");
        probeCountTotal.setAttribute("Value", String.valueOf(probeCount));
        summary.appendChild(probeCountTotal);
        // Trajectory Count
        Element trajectoryCountTotal = doc.createElement("Trajectories");
        trajectoryCountTotal.setAttribute("Value", String.valueOf(trajCount));
        summary.appendChild(trajectoryCountTotal);
        // Node count
        Element nodeCountTotal = doc.createElement("Nodes");
        nodeCountTotal.setAttribute("Value", String.valueOf(nodeCount));
        summary.appendChild(nodeCountTotal);
        // Time Period Count
        Element tpCount = doc.createElement("TimePeriods");
        tpCount.setAttribute("Value", String.valueOf(expressionDataSet.getNumberOfTimePeriods()));
        summary.appendChild(tpCount);
        // Sample count
        Element sampleCount = doc.createElement("Samples");
        sampleCount.setAttribute("Value", String.valueOf(expressionDataSet.getNumberOfSamples()));
        summary.appendChild(sampleCount);
        // Max Bin
        Element maximumBinValue = doc.createElement("MaximumBinValue");
        maximumBinValue.setAttribute("Value", String.valueOf(maxBinValue));
        summary.appendChild(maximumBinValue);
        // Min Bin
        Element minimumBinValue = doc.createElement("MinimumBinValue");
        minimumBinValue.setAttribute("Value", String.valueOf(minBinValue));
        summary.appendChild(minimumBinValue);
        // Relative movement
        Element subtractiveDeg = doc.createElement("SubtractiveDegree");
        subtractiveDeg.setAttribute("Max", String.valueOf(maxShift));
        subtractiveDeg.setAttribute("Min", String.valueOf(minShift));
        summary.appendChild(subtractiveDeg);
        // Trajectory volatility and trends
        Element maxTraj = doc.createElement("TrajectoryStats");
        maxTraj.setAttribute("Volatility", String.valueOf(maxVolatility));
        maxTraj.setAttribute("Trend", String.valueOf(maxTrend));
        summary.appendChild(maxTraj);

        root.appendChild(summary);

        outputXML(fileName, doc, collapsed);
        return true;
    }

    private boolean generateClusterFile(String fileName) {
        DOMImplementation domImpl = new DOMImplementationImpl();
        Document doc = domImpl.createDocument(null, "TrajectoryFile", null);
        Element root = doc.getDocumentElement();

        Element parent;
        Element child;

        int nodeCount = 0;
        int trajCount = 0;
        int probeCount = 0;

        int maxBinValue = -100;
        int minBinValue = 100;

        int numberOfTimePeriods = expressionDataSet.getNumberOfTimePeriods();

        int maxShift = -100;
        int minShift = 100;

        int maxVolatility = 0;
        int maxTrend = 0;

        TreeMap<Integer[], Element> existingPaths = new TreeMap<Integer[], Element>((TrajectoryComparator) new TrajectoryComparator());

        for (Map.Entry<Integer,List<String>> entry : clusterService.getClusters().entrySet()) {
            List<String> probeIds = entry.getValue();

            parent = root;

            int volatility = 0;
            int trend = 0;

            // Keeps track of the bins which define the trajectory
            // For clustered output, this is just the discretized centroid.
            Integer[] trajectory = new Integer[expressionDataSet.getNumberOfTimePeriods()];
            // Row attributes are numbered from 1 -->
//            int numberOfProbesInTrajectory = probeIds.size();
            // For each time period:
            for (int timePeriodIndex = 0; timePeriodIndex < expressionDataSet.getNumberOfTimePeriods(); timePeriodIndex++) {
                // See what bin the trajectory is in at the current time point
                double trajectoryExpressionRawValue = clusterService.getCentroids().get(entry.getKey())[timePeriodIndex];
                int trajectoryExpressionValue = (int)Math.floor(trajectoryExpressionRawValue / binUnit);
                // Keep track of the bin at this time point
                trajectory[timePeriodIndex] = trajectoryExpressionValue;

                if (timePeriodIndex > 0) {
                    double subtractiveLast = trajectoryExpressionValue - trajectory[timePeriodIndex - 1];
                    volatility += subtractiveLast * subtractiveLast;
                    trend += subtractiveLast;
                    if (volatility > maxVolatility) {
                        maxVolatility = volatility;
                    }
                    if (Math.abs(trend) > Math.abs(maxTrend)) {
                        maxTrend = trend;
                    }
                }

                // Okay, see if the trajectory up to this point has already been constructed
                // if so, focus on the existing  node and move to the next time period
                if (existingPaths.size() > 0) {
                    child = (Element) existingPaths.get(trajectory);
                } else {
                    child = null;
                }
                if (child == null) {
                    nodeCount++;
                    child = doc.createElement("TP" + (timePeriodIndex + 1));
                    child.setAttribute("Value", String.valueOf(trajectoryExpressionValue));
                    if (trajectoryExpressionValue > maxBinValue) {
                        maxBinValue = trajectoryExpressionValue;
                    }
                    if (trajectoryExpressionValue < minBinValue) {
                        minBinValue = trajectoryExpressionValue;
                    }
                    child.setAttribute("NodeID", "" + nodeCount);
                    child.setAttribute("Depth", "" + (timePeriodIndex + 1));
                    child.setAttribute("Volatility", String.valueOf(volatility));
                    child.setAttribute("Trend", String.valueOf(trend));
                    parent.appendChild(child);

                    existingPaths.put(trajectory.clone(), child);
                } else {
                    if ((timePeriodIndex + 1) == numberOfTimePeriods) {
                        System.out.println("ERROR: Non unique trajectory");
                    }
                }
                parent = child;
            }
            for (int probeIndex = 0; probeIndex < probeIds.size(); probeIndex++) {
                child = doc.createElement("Probe");
                child.setAttribute("ID", probeIds.get(probeIndex));
                parent.appendChild(child);
                probeCount++;
            }

            for (int i = 1; i < trajectory.length; i++) {
                int tpiValue = trajectory[i];
                int tp0Value = trajectory[0];
                // Work out jump from previous TP
                int subtractive = tpiValue - tp0Value;
                if (subtractive > maxShift) {
                    // New max shift
                    System.out.println("NEW MAX JUMP: " + subtractive);
                    maxShift = subtractive;
                }
                if (subtractive < minShift) {
                    // New max neg shift
                    System.out.println("NEW MIN JUMP: " + subtractive);
                    minShift = subtractive;
                }
                int tpiM1Value = trajectory[i - 1];
                int subtractiveLast = tpiValue - tpiM1Value;
                if (subtractiveLast > maxShift) {
                    // New max shift
                    System.out.println("NEW MAX JUMP: " + subtractiveLast);
                    maxShift = subtractiveLast;
                }
                if (subtractiveLast < minShift) {
                    // New max neg shift
                    System.out.println("NEW MIN JUMP: " + subtractiveLast);
                    minShift = subtractiveLast;
                }
            }

            trajCount++;
        }
        Element summary = doc.createElement("FileSummary");
        // Bin Unit
        Element bUnit = doc.createElement("BinUnit");
        bUnit.setAttribute("Value", String.valueOf(binUnit));
        summary.appendChild(bUnit);
        // K-Means K
        Element kmeansk = doc.createElement("K");
        kmeansk.setAttribute("Value", String.valueOf(k));
        summary.appendChild(kmeansk);
        // Probe count
        Element probeCountTotal = doc.createElement("Probes");
        probeCountTotal.setAttribute("Value", String.valueOf(probeCount));
        summary.appendChild(probeCountTotal);
        // Trajectory Count
        Element trajectoryCountTotal = doc.createElement("Trajectories");
        trajectoryCountTotal.setAttribute("Value", String.valueOf(trajCount));
        summary.appendChild(trajectoryCountTotal);
        // Node count
        Element nodeCountTotal = doc.createElement("Nodes");
        nodeCountTotal.setAttribute("Value", String.valueOf(nodeCount));
        summary.appendChild(nodeCountTotal);
        // Time Period Count
        Element tpCount = doc.createElement("TimePeriods");
        tpCount.setAttribute("Value", String.valueOf(expressionDataSet.getNumberOfTimePeriods()));
        summary.appendChild(tpCount);
        // Sample count
        Element sampleCount = doc.createElement("Samples");
        sampleCount.setAttribute("Value", String.valueOf(expressionDataSet.getNumberOfSamples()));
        summary.appendChild(sampleCount);
        // Max Bin
        Element maximumBinValue = doc.createElement("MaximumBinValue");
        maximumBinValue.setAttribute("Value", String.valueOf(maxBinValue));
        summary.appendChild(maximumBinValue);
        // Min Bin
        Element minimumBinValue = doc.createElement("MinimumBinValue");
        minimumBinValue.setAttribute("Value", String.valueOf(minBinValue));
        summary.appendChild(minimumBinValue);
        // Relative movement
        Element subtractiveDeg = doc.createElement("SubtractiveDegree");
        subtractiveDeg.setAttribute("Max", String.valueOf(maxShift));
        subtractiveDeg.setAttribute("Min", String.valueOf(minShift));
        summary.appendChild(subtractiveDeg);
        // Trajectory volatility and trends
        Element maxTraj = doc.createElement("TrajectoryStats");
        maxTraj.setAttribute("Volatility", String.valueOf(maxVolatility));
        maxTraj.setAttribute("Trend", String.valueOf(maxTrend));
        summary.appendChild(maxTraj);

        root.appendChild(summary);

        outputXML(fileName, doc, TrajectoryType.CLUSTERED);
        return true;
    }


    private void outputXML(String fileName, Document doc, TrajectoryType collapsed) {

        StringBuffer filePath = new StringBuffer();
        filePath.append(com.sfsu.xmas.globals.FileGlobals.getRoot());
        filePath.append(expressionDataSet.getName());

        File f = new File(filePath.toString());
        if (!f.exists()) {
            boolean successfulDirCreation = f.mkdir();
            if (!successfulDirCreation) {
                System.err.println(TrajectoryFileGenerator.class.getSimpleName() + ": File creation failed for " + filePath.toString());
            }
        }

        filePath.append(File.separatorChar);
        filePath.append(FileGlobals.TRAJECTORY_DATA_FILE_PREFIX);
        filePath.append(fileName);
        switch (collapsed) {
            case PRESERVED:
                break;
            case COLLAPSED:
                filePath.append(collapsedSuffix);
                break;
            case CLUSTERED:
                filePath.append(clusteredSuffix).append(k);
                break;
        }
        filePath.append(FileGlobals.XML_FILE_EXTENSION);

        XMLUtils.writeXmlFile(doc, filePath.toString());
    }
}
