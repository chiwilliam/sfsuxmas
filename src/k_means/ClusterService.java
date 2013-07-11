package k_means;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Daniel Asarnow
 */
public class ClusterService {
    ExpressionDataSet eDB;

    double[][] expressionValues;
    double[][] means;
    String[] probeIndexId;
    int[] labels;
    int[] count;
    double[][] d;

    Map<Integer,List<String>> clusters;
    Map<Integer,double[]> centroids;

    public static final int SEED = 314159265;

    public ClusterService(ExpressionDataSet eDB) {
        this.eDB = eDB;
        expressionValues = new double[eDB.getNumberOfProbes()][eDB.getNumberOfTimePeriods()];
        probeIndexId = new String[eDB.getNumberOfProbes()];
        populateExpressionValues();
    }

    /*public synchronized Probes getProbes(String identifier) {
        Probes probes;
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier).hasTrajFileBasedFilters()) {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getProbes(identifier), true);
        } else {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(identifier, true);
        }
        return probes;
    }*/

    public double[][] getExpressionValues() {
        return expressionValues;
    }

    public double[][] getMeans() {
        return means;
    }

    public Map<Integer,List<String>> getClusters() {
        return clusters;
    }

    public Map<Integer,double[]> getCentroids() {
        return centroids;
    }

    public void populateExpressionValues() {
        Probes probes = eDB.getProbes();
        int idx = 0;
        for (Map.Entry<String, Probe> entry : probes.entrySet()) {
            double[] expressionValueRow = entry.getValue().getTimePeriodExpression();
            System.arraycopy(expressionValueRow, 0, expressionValues[idx], 0, expressionValueRow.length);
            probeIndexId[idx] = entry.getKey();
            idx++;
        }
    }

    public String getProbeIdByIndex(int i) {
        return probeIndexId[i];
    }

    public void finalClusters() {
        clusters = new HashMap<Integer, List<String>>();
        for (int i=0; i<labels.length; i++) {
            if ( clusters.containsKey( labels[i] ) ) {
                clusters.get( labels[i] ).add(probeIndexId[i]);
            } else {
                clusters.put( labels[i], new ArrayList<String>() );
            }
        }
    }

    public void finalCentroids() {
        centroids = new HashMap<Integer,double[]>();
        for (Integer i : clusters.keySet()) {
            centroids.put( i, means[i] );
        }
    }


    public void randomLabels(int[] labels, int k) {
        Random random = new Random(SEED);
        for(int i=0; i<labels.length; i++) {
            labels[i] = random.nextInt(k);
        }
    }

    public void randomSeeds() {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int [] idx = randomDataGenerator.nextPermutation(expressionValues.length,means.length);
        for (int i=0; i<idx.length; i++) {
            System.arraycopy(expressionValues[idx[i]], 0, means[i], 0, means[0].length);
        }
    }

    public void kmeans(int k) {
        kmeans(k, new EuclideanDistance());
    }

    public void kmeans(int k, Distance distance) {
        d = new double[k][expressionValues.length];
        labels = new int[expressionValues.length];
        means = new double[k][expressionValues[0].length];
        count = new int[k];
        Arrays.fill(count, 1); // initially each cluster has at least 1 member
        randomSeeds();
        kmeans_kernel(distance, 10000, 60000 * 10); // 10,000 iterations and 10 minutes
        finalClusters();
        finalCentroids();
    }

    public void kmeans_kernel(Distance distance, int iter, int timeout) {
//        double eps = Math.pow(10.0D, -threshold);
        long time = System.nanoTime();
        if (iter == 0)
            iter = 1000000;
        for (int c = 0; c < iter; c++) {
            updateDistances(distance);
            int numChanged = updateLabels();
            updateMeans();

            long timediff = (System.nanoTime() - time) / 1000000L;

            if ((timeout > 0) && (timediff > timeout)) {
                break;
            }
            if (numChanged == 0) {
                break;
            }
            if ((iter > 0) && (c >= iter - 1)) {
                break;
            }
        }
    }

    public void updateDistances(Distance distance) {
        for (int j=0; j<means.length; j++) {
            if (count[j] == 0) continue;
            for (int i=0; i< expressionValues.length; i++) {
                d[j][i] = distance.compute(expressionValues[i], means[j]);
            }
        }
    }

    public int updateLabels() {
        int numChanged = 0;
        for (int i=0; i<labels.length; i++) {
            double curmin = Double.MAX_VALUE;
            int idx = 0;
            for (int j=0; j<d.length; j++) {
                if (count[j]==0) continue; // skip empty clusters
                if (d[j][i] < curmin) { // found closer label
                    curmin = d[j][i];
                    idx = j;
                }
            }
            if ( labels[i] != idx ) {
                labels[i] = idx; // update label
                numChanged++;
            }
        }
        return numChanged;
    }

    public void updateMeans() {
        Arrays.fill(count, 0);
        for (int i=0; i<labels.length; i++) {
            count[labels[i]]++; // count members
            for (int j=0; j<means[0].length; j++) {
                means[ labels[i] ][j] += expressionValues[i][j]; // accumulate
            }
        }
        for (int i=0; i<means.length; i++) {
            if (count[i]==0) continue; // skip empty
            for (int j=0; j<means[0].length; j++) {
                means[i][j] /= count[i]; // average
            }
        }
    }

    public interface Distance {
        public double compute(double[] p, double[] q);
    }

    public class EuclideanDistance implements Distance {
        public double compute(double[] p, double[] q) {
            double d = 0;
            for(int i=0; i<p.length; i++) {
                d += Math.pow( p[i] - q[i], 2 );
            }
            return d;
        }
    }

    public class CorrelationDistance implements Distance {
        public double compute(double[] p, double[] q) {
            Covariance covariance = new Covariance();
            double cov = covariance.covariance(p,q);
            double sigmaP = Math.sqrt( StatUtils.variance(p) );
            double sigmaQ = Math.sqrt( StatUtils.variance(q) );
            double corr =  Math.abs( cov / (sigmaP * sigmaQ) );
            return 1 - corr;
        }
    }

}
