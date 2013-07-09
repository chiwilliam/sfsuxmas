package k_means;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.http.HttpServlet;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class KMeans extends HttpServlet {

    private int k = 1;
    private HashMap<Integer, HashMap<String, double[]>> clusters;
    private boolean useDistance = true;
    // SHould be ENUM'ed
    private String correlation = "correlation";

    public KMeans() {
    }

    // Alternate constructor allowing user to define K
    public KMeans(int k) {
        this.k = k;
    }

    public void useCorrelation() {
        useDistance = false;
    }

    public void useEuclidean() {
        useDistance = true;
    }

    public void useDistance(String type) {
        if (type != null) {
            if (type.equals(correlation)) {
                useDistance = false;
            } else {
                useDistance = true;
            }
        }
    }

//    public Document cluster(Probeset probes, String fileName) {
//        Document doc = null;
//        if (probes.size() < k) {
//            // Not enough probes to populate clusters
//            System.out.println("Not enough probes to populate clusters");
//        }
//
//        // Removes individual calls:
//        probes.populateExpression(sam);
//
//        HashMap<String, double[]> probeExpressionPoints = new HashMap<String, double[]>();
//        for (int i = 0; i < probes.size(); i++) {
//            // for each probe, create an expression vector
//            Probe probe = (Probe) probes.get(i);
//            Expression exp = probe.getExpression();
//            double[] expression = exp.getExpression();
//            double[] normalizedExpression = getNormalizedExpression(expression, exp.getMeanExpression());
//            probeExpressionPoints.put(probe.getID(), normalizedExpression);
//        }
//
//        HashMap<Integer, HashMap<String, double[]>> initialCluster = new HashMap<Integer, HashMap<String, double[]>>();
//        initialCluster.put(0, probeExpressionPoints);
//
//        clusters = kMeansStep(initialCluster, 0);
//
//        doc = makeXMLDocument(fileName);
//
//        return doc;
//    }

    public int[] getProbeIDs(NodeList probeList) {
        int[] probeIDs = null;
        if (probeList != null) {
            probeIDs = new int[probeList.getLength()];
            for (int j = 0; j < probeList.getLength(); j++) {
                Node probe = probeList.item(j);
                String internalProbeID = probe.getAttributes().getNamedItem("ID").getTextContent();
                int probeID = Integer.parseInt(internalProbeID);
                probeIDs[j] = probeID;
            }
        }
        return probeIDs;
    }

    private boolean distanceIsBetter(double dist, double smallestDistance) {
        if (useDistance) {
            // Euclidean
            if (Math.abs(dist) < Math.abs(smallestDistance)) { // This is a fix for dealing with correlation scores in a range from -1 to 1
                return true;
            }
        } else {
            // Correl
            if (Math.abs(dist) > Math.abs(smallestDistance)) { // This is a fix for dealing with correlation scores in a range from -1 to 1
                return true;
            }
        }
        return false;
    }

    private double[] getNormalizedExpression(double[] expression, double meanExpression) {
        double[] normalizedExpression = new double[expression.length];
        for (int i = 0; i < expression.length; i++) {
            // Use the expression value minus the mean value
            normalizedExpression[i] = expression[i] - meanExpression;
        }
        return normalizedExpression;
    }

    private HashMap<Integer, double[]> getRandomCenterPoints(HashMap<Integer, HashMap<String, double[]>> cluster) {
        // Assumes all probes are in a single cluster indexed at 0
        HashMap<String, double[]> pointV = cluster.get(0);

        ArrayList<Integer> usedPoints = new ArrayList<Integer>();

        // Get k random indices
        for (int i = 0; i < k; i++) {
            int pointIndex = 0;
            boolean newPointFound = false;
            while (!newPointFound) {
                pointIndex = (int) Math.round(Math.random() * (pointV.size() - 1));
                newPointFound = true;
                if (usedPoints.contains(pointIndex)) {
                    newPointFound = false;
                    System.out.println("Repeated Center point");
                }
            }
            usedPoints.add(pointIndex);
        }

        HashMap<Integer, double[]> centers = new HashMap<Integer, double[]>();
        Set keys = pointV.keySet();

        int clusterIndex = 0;

        int iterationIndex = 0;
        Iterator probeIt = keys.iterator();
        while (probeIt.hasNext()) {
            int probeID = (Integer) probeIt.next();
            if (usedPoints.contains(iterationIndex)) {
                centers.put(clusterIndex, pointV.get(probeID));
                System.out.println("Initial center point: " + probeID);
                clusterIndex++;
            }
            iterationIndex++;
        }

        if (centers.size() != k) {
            System.err.println(KMeans.class.getSimpleName() + ": Not enough center points from initialization");
        }

        return centers;
    }

//    private Vector initializeClusters(Vector<HashMap> clusters) {
//
//        Vector<HashMap> tempClusters = new Vector<HashMap>();
//        // centers.length should == k;
//        for (int i = 0; i < k; i++) {
//            tempClusters.add(new HashMap());
//        }
//
//        // For each cluster
//        for (int i = 0; i < clusters.size(); i++) {
//            HashMap cluster = clusters.get(i);
//
//            // For each point in the cluster
//            Set keys = cluster.keySet();
//            Iterator probeIt = keys.iterator();
//            while (probeIt.hasNext()) {
//                int key = (Integer) probeIt.next();
//                int closestCenterIndex = 0;
//                double smallestDistance = 0;
//                double[] point = (double[]) cluster.get(key);
//
//                // For each cluster center
//                Set centerKeys = centers.keySet();
//                Iterator centerIt = centerKeys.iterator();
//                int iterationIndex = 0;
//                while (centerIt.hasNext()) {
//                    int centerID = (Integer) centerIt.next();
//                    double[] centerPoint = (double[]) centers.get(centerID);
//                    double dist = getEuclideanDistance(point, centerPoint);
//                    // Find closest center
//                    if (iterationIndex == 0) { // First comparison has to be smallest
//                        smallestDistance = dist;
//                        closestCenterIndex = centerID;
//                    } else {
//                        if (dist < smallestDistance) {
//                            smallestDistance = dist;
//                            closestCenterIndex = centerID;
//                        }
//                    }
//                    if (dist == 0) {
//                        System.out.println("INNIT: Center point distance with self");
//                    }
//                    iterationIndex++;
//                }
//                HashMap c = (HashMap) tempClusters.get(closestCenterIndex);
//                c.put(key, point);
//            }
//        }
//
//        for (int i = 0; i < tempClusters.size(); i++) {
//            HashMap c = (HashMap) tempClusters.get(i);
//            if (c.size() <= 0) {
//                // Should have a minimum of one probe in each cluster
//                System.out.println("INNIT: Empty Cluster at initialization stage");
//            }
//        }
//
//        return tempClusters;
//    }
    private HashMap<Integer, HashMap<String, double[]>> kMeansStep(HashMap<Integer, HashMap<String, double[]>> clusters, int iteration) {
        HashMap<Integer, double[]> centers = null;
        System.out.println("K Means step " + iteration);
        // Get Center points
        if (iteration <= 0 || clusters.size() != k) {
            // First pass - pick random points as cluster centers
            centers = getRandomCenterPoints(clusters);
        } else {
            centers = new HashMap<Integer, double[]>();
            for (int i = 0; i < clusters.size(); i++) {
                centers.put(i, getMeanPointInCluster(clusters.get(i))); // Mean point of cluster

            }
        }

        // Initialize new clusters:
        HashMap<Integer, HashMap<String, double[]>> newClusters = new HashMap<Integer, HashMap<String, double[]>>();
        for (int i = 0; i < k; i++) {
            newClusters.put(i, new HashMap<String, double[]>());
        }

        boolean pointChangedClusters = false;

        // For each cluster
        for (int clusterIndex = 0; clusterIndex < clusters.size(); clusterIndex++) {
            HashMap<String, double[]> cluster = clusters.get(clusterIndex);

            Set<String> probeIDs = cluster.keySet();
            Iterator<String> probeIt = probeIDs.iterator();
            // For each point
            while (probeIt.hasNext()) {
                String probeID = probeIt.next();
                int closestCenterIndex = 0;
                double smallestDistance = 0;
                double[] point = (double[]) cluster.get(probeID);

                Set centerIDs = centers.keySet();
                Iterator centerIt = centerIDs.iterator();
                int iterationIndex = 0;
                // Calculate the distance to each center
                while (centerIt.hasNext()) {
                    int centerID = (Integer) centerIt.next();
                    double[] centerPoint = (double[]) centers.get(centerID);
                    double dist = getDistance(point, centerPoint);
                    // Find closest center
                    if (iterationIndex == 0) { // First comparison has to be smallest

                        smallestDistance = dist;
                        closestCenterIndex = centerID;
                    } else if (distanceIsBetter(dist, smallestDistance)) {
                        smallestDistance = dist;
                        closestCenterIndex = centerID;
                        System.out.println("Moving to : " + centerID + ", From : " + clusterIndex);
                    }


                    if (dist == 1) {
                        System.out.println(iteration + ": Center point distance with self");
                    }
                    iterationIndex++;
                }

                // Move to closest cluster
                HashMap<String, double[]> c = newClusters.get(closestCenterIndex);
                c.put(probeID, point);

                // Should updte hashmap

//                newClusters.set(closestCenterIndex, c);
                if (closestCenterIndex != clusterIndex) {
                    pointChangedClusters = true;
                }
            }
        }


        for (int i = 0; i < newClusters.size(); i++) {
            HashMap cluster = newClusters.get(i);
            System.out.println("#############################");
            System.out.println("### Cluster:" + i + ", size = " + cluster.size());
            System.out.println("#############################");

            if (cluster.size() <= 0) {
                // Should have a minimum of one probe in each cluster
                System.out.println("Empty Cluster at step");
            }
        }

        if (pointChangedClusters && iteration < 10) {
            return kMeansStep(newClusters, iteration + 1);
        }
        return newClusters;
    }

    public double getDistance(double[] a, double[] b) {
        if (useDistance) {
            return getEuclideanDistance(a, b);

        } else {
            return getCorrelationScore(a, b);
        }
    }

    public static HashMap getDistances(double[] a, double[] b) {
        HashMap<String, Double> hm = new HashMap<String, Double>();
        hm.put("euclidean", getEuclideanDistance(a, b));
        hm.put("correlation", getCorrelationScore(a, b));
        return hm;
    }

    private static double getEuclideanDistance(double[] a, double[] b) {
        if (a.length != b.length) {
            // Shit
            return 0;
        }
        double aMinusB = 0;
        for (int i = 1; i < a.length; i++) {
            double pointWiseDifference = a[i] - b[i];
            aMinusB += pointWiseDifference * pointWiseDifference;
        }
        return (double) Math.sqrt(aMinusB);
    }

    public static double getCorrelationScore(double[] scores1, double[] scores2) {
        double result = 0;
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;
        if (scores1 == null || scores1.length <= 0 || scores2 == null || scores2.length <= 0) {
            System.out.println("Empy expression traj in correl calculation");
        } else {
            double mean_x = scores1[0];
            double mean_y = scores2[0];
            for (int i = 2; i < scores1.length + 1; i += 1) {
                double sweep = Double.valueOf(i - 1) / i;
                double delta_x = scores1[i - 1] - mean_x;
                double delta_y = scores2[i - 1] - mean_y;
                sum_sq_x += delta_x * delta_x * sweep;
                sum_sq_y += delta_y * delta_y * sweep;
                sum_coproduct += delta_x * delta_y * sweep;
                mean_x += delta_x / i;
                mean_y += delta_y / i;
            }
            double pop_sd_x = (double) Math.sqrt(sum_sq_x / scores1.length);
            double pop_sd_y = (double) Math.sqrt(sum_sq_y / scores1.length);
            double cov_x_y = sum_coproduct / scores1.length;
            result = cov_x_y / (pop_sd_x * pop_sd_y);
        }
        return result;
    }

//    public static double getCorrelationScore(double[] a, double[] b) {
//        double correlationScore = 0;
//
//        if (a[0] == b[0]) {
//            System.out.println("Maybe_same");
//        }
//        
//        if (a.length == b.length) {
//            
//            
//
//            double aDotb = getDotProduct(a, b);
//
//            double magA = (double) Math.sqrt(getSumOfSquares(a));
//            double magB = (double) Math.sqrt(getSumOfSquares(b));
//            if (magA > 0 && magB > 0) {
//                correlationScore = aDotb / (magA * magB);
//            }
//        }
//
//        return correlationScore;
//    }
    private static double[] centerExpression(double[] a) {
        double expressionSum = 0;
        if (a.length == 0) {
            for (int i = 0; i < a.length; i++) {
                expressionSum += a[i];
            }
            double mean = expressionSum / a.length;

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] - mean;
            }
        }
        return a;
    }

    public static double getDotProduct(double[] a, double[] b) {
        double dotProduct = 0;
        // Maybe check they are both the same size...
        for (int i = 0; i < a.length && i < b.length; i++) {
            double aValue = a[i];
            double bValue = b[i];
            dotProduct += (aValue * bValue);
        }
        return dotProduct;
    }

    public static double getSumOfSquares(double[] a) {
        double sumOfSquaresA = 0;
        if (a != null) {
            for (int i = 0; i < a.length; i++) {
                double entry = a[i];
                sumOfSquaresA += (entry * entry);
            }
        }
        return sumOfSquaresA;
    }

    private double[] getMeanPointInCluster(HashMap cluster) {
        double[] sumOfValues = new double[getNumberOfExpressionValues(cluster)];
        // Innit

        Set probeIDs = cluster.keySet();
        Iterator probeIt = probeIDs.iterator();
        // For each probe
        while (probeIt.hasNext()) {
            int probeIndex = (Integer) probeIt.next();
            double[] point = (double[]) cluster.get(probeIndex);
            // For each expression value
            for (int j = 0; j < point.length; j++) {
                double currentValue = sumOfValues[j];
                currentValue += point[j];
                // Add it to the group sum
                sumOfValues[j] = currentValue;
            }
        }

        for (int i = 0; i < sumOfValues.length; i++) {
            double value = sumOfValues[i];
            value = value / cluster.size();
            sumOfValues[i] = value;
        }
        return sumOfValues;
    }

    // Takes the first point and returns the size of its expression vector
    public int getNumberOfExpressionValues(HashMap cluster) {
        int numberOfExpressionValues = 0;
        if (cluster != null) {
            Set probeIDs = cluster.keySet();
            Iterator probeIt = probeIDs.iterator();
            while (probeIt.hasNext()) {
                int probeIndex = (Integer) probeIt.next();
                // Get each point
                double[] point = (double[]) cluster.get(probeIndex);
                if (point.length - 1 > 0) {
                    numberOfExpressionValues = point.length;
                }
                break;
            }
        }
        return numberOfExpressionValues;
    }

    public int getK() {
        return k;
    }

    public boolean setK(int k) {
        if (k > 0 && k < 50) {
            this.k = k;
            return true;
        }
        return false;
    }

    private Document makeXMLDocument(String fileName) {
        DOMImplementation domImpl = new DOMImplementationImpl();
        Document doc = domImpl.createDocument(null, "ClusterFile", null);
        OutputStream os = null;
//        XMLSnapShotFactory xmlFact = XMLSnapShotFactory.getUniqueInstance();
//        Element root = doc.getDocumentElement();
//        Element summary = doc.createElement("ClusterSummary");
//        // K Value
//        Element kValue = doc.createElement("k");
//        kValue.setAttribute("Value", String.valueOf(clusters.size()));
//        summary.appendChild(kValue);
//        // File Name
//        Element fileNameElement = doc.createElement("File");
//        fileNameElement.setAttribute("Value", fileName);
//        summary.appendChild(fileNameElement);
        // Parent vote
//        Element parentFileFull = doc.createElement("ParentFileFull");
//        parentFileFull.setAttribute("Value", xmlFact.getCurrentFileLocation());
//        summary.appendChild(parentFileFull);
//        // Parent vote
//        Element parentFile = doc.createElement("ParentFile");
//        parentFile.setAttribute("Value", xmlFact.getCurrentFile());
//        summary.appendChild(parentFile);
//        // Add summary to doc
//        root.appendChild(summary);
//
//        Element cluster;
//        for (int clusterIndex = 0; clusterIndex < clusters.size(); clusterIndex++) {
//            HashMap clu = clusters.get(clusterIndex);
//            Cluster clust = new Cluster(clu);
//            cluster = doc.createElement("Cluster");
//            cluster.setAttribute("ID", "" + clusterIndex);
//            cluster.setAttribute("Size", "" + clu.size());
//
//            HashMap distances = clust.getClusterDiameter();
//
//            Element diameters = doc.createElement("Diameters");
//
//            // Add each to node
//            Set keys = distances.keySet();
//            Iterator it = keys.iterator();
//            while (it.hasNext()) {
//                String key = (String) it.next();
//
//                Element dist = doc.createElement(key);
//                dist.setAttribute("Value", String.valueOf(distances.get(key)));
//
//                diameters.appendChild(dist);
//            }
//            cluster.appendChild(diameters);
//
//            Set probeIDSet = clu.keySet();
//            Iterator probeIt = probeIDSet.iterator();
//            while (probeIt.hasNext()) {
//                int probeID = (Integer) probeIt.next();
//
//                // Could include expression values in XML for speed...
////                    double[] probeVector = (double[]) clu.get(probeID);
//                Element probe = doc.createElement("Probe");
//
//                probe.setAttribute("ID", String.valueOf(probeID));
//                cluster.appendChild(probe);
//            }
//            root.appendChild(cluster);
//        }
//
//        String fullFilename = xmlFact.getCurrentFileLocation() + fileName;
//
//        XMLFileCreator.writeXmlFile(doc, fullFilename + ".xml");
//
//        XMLClusterFactory.getUniqueInstance().setFileName(fileName);

        return doc;
    }
}
