/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package k_means;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author bdalziel
 */
public class Cluster {

    private HashMap points;

    public Cluster(HashMap points) {
        this.points = points;
    }

    public HashMap getPoints() {
        return points;
    }

    public HashMap getClusterDiameter() {
        // Iterate over points, find greatest distance
        double maxEuclideanDistance = 0;
        double maxCorrelationDistance = 0;

        Set probeIDSet = points.keySet();
        Iterator probeIt = probeIDSet.iterator();
        
        int comparisonIndex = 0;
        
        while (probeIt.hasNext()) {
            int probeID = (Integer) probeIt.next();
            double[] pointA = (double[]) points.get(probeID);

            Set probeIDSet2 = points.keySet();
            Iterator probeIt2 = probeIDSet2.iterator();
            while (probeIt2.hasNext()) {
                int probeID2 = (Integer) probeIt2.next();
                double[] pointB = (double[]) points.get(probeID2);
                
                HashMap distances = KMeans.getDistances(pointA, pointB);
                double eucDist = (Double) distances.get("euclidean");
                double correlDist = (Double) distances.get("correlation");
                
                if (comparisonIndex == 0) { // First distance
                    maxEuclideanDistance = eucDist;
                    maxCorrelationDistance = correlDist;
                } else if (eucDist > maxEuclideanDistance) {
                    maxEuclideanDistance = eucDist;
                } else if ( Math.abs(correlDist) < maxCorrelationDistance) {
                    maxCorrelationDistance = correlDist;
                }
                comparisonIndex++;
            }
        }
        
        HashMap<String, Double> distances = new HashMap<String, Double>();
        distances.put("euclidean", maxEuclideanDistance);
        distances.put("correlation", maxCorrelationDistance);
        return distances;
    }
}
