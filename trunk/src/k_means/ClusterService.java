package k_means;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_structures.Probe;
import com.sfsu.xmas.data_structures.Probes;
import com.sfsu.xmas.filter.FilterManager;
import com.sfsu.xmas.session.SessionAttributeManager;
import com.sfsu.xmas.trajectory_files.TrajectoryFileFactory;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Daniel Asarnow
 */
public class ClusterService {
    int eDBID;
    String trajFN;
    ExpressionDataSet eDB, eDBSecond;
    List<double[]> expressionValueRows;
    Map<Integer,String> probeIndexId;
    int[] labels;

    public static final int SEED = 314159265;

    public ClusterService(String identifier, HttpServletRequest request) {
        eDB = SessionAttributeManager.getActivePrimaryExpressionDatabase(request);
        eDBID = eDB.getID();
        trajFN = SessionAttributeManager.getActiveTrajectoryFile(request).getFileName();
        expressionValueRows = new ArrayList<double[]>(eDB.getNumberOfProbes());
        probeIndexId = new LinkedHashMap<Integer, String>(eDB.getNumberOfProbes());
        populateExpressionValues(identifier);
    }

    public synchronized Probes getProbes(String identifier) {
        Probes probes;
        if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && FilterManager.getUniqueInstance().getFilterListForIdentifier(identifier).hasTrajFileBasedFilters()) {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getProbes(identifier), true);
        } else {
            probes = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(eDBID, false).getProbes(identifier, true);
        }
        return probes;
    }

    public void populateExpressionValues(String identifier) {
        Probes probes = getProbes(identifier);
        int idx = 0;
        for (Map.Entry<String, Probe> entry : probes.entrySet()) {
            double[] timePeriodExpression = entry.getValue().getTimePeriodExpression();

            double[] expressionValueRow = new double[timePeriodExpression.length];

            double profileShift = 0.0;
            if (TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN) != null && !TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).isPreserved()) {
                profileShift = TrajectoryFileFactory.getUniqueInstance().getFile(eDBID, trajFN).getCollapsedExpressionShiftAmount(timePeriodExpression);
                for (int i = 0; i < timePeriodExpression.length; i++) {
                    expressionValueRow[i] = timePeriodExpression[i] - profileShift;
                }
            } else {
                System.arraycopy(timePeriodExpression, 0, expressionValueRow, 0, timePeriodExpression.length);
            }
            expressionValueRows.add(expressionValueRow);
            probeIndexId.put(idx, entry.getKey());
            idx++;
        }
    }

    public Map<Integer,List<String>> probeIndicesToProbeIds(int[] labels) {
        Map<Integer,List<String>> clusters = new HashMap<Integer, List<String>>();
        for (int i=0; i<labels.length; i++) {
            if ( clusters.containsKey( labels[i] ) ) {
                clusters.get( labels[i] ).add(probeIndexId.get(i));
            } else {
                clusters.put( labels[i], new ArrayList<String>() );
            }
        }
        return clusters;
    }

    public void randomLabels(int[] labels, int k) {
        Random random = new Random(SEED);
        for(int i=0; i<labels.length; i++) {
            labels[i] = random.nextInt(k);
        }
    }

    public Map<Integer,List<String>> kmeans(int k) {
        double[][] d = new double[expressionValueRows.size()][k];
        int[] labels = new int[expressionValueRows.size()];
        double[][] means = new double[k][expressionValueRows.get(0).length];
        randomLabels(labels, k);
        kmeans_kernel(labels, means, d, new EuclideanDistance(), 0, 0);
        return probeIndicesToProbeIds(labels);
    }

    public Map<Integer,List<String>> kmeans(int k, Distance distance) {
        double[][] d = new double[expressionValueRows.size()][k];
        labels = new int[expressionValueRows.size()];
        double[][] means = new double[k][expressionValueRows.get(0).length];
        randomLabels(labels, k);
        kmeans_kernel(labels, means, d, distance, 0, 0);
        return probeIndicesToProbeIds(labels);
    }

    public void kmeans_kernel(int[] labels, double[][] means, double[][] d, Distance distance, int iter, int timeout) {
//        double eps = Math.pow(10.0D, -threshold);
        long time = System.nanoTime();
        if (iter == 0)
            iter = 1000000;
        for (int c = 0; c < iter; c++) {
            updateDistances(means, d, distance);
            updateLabels(labels, d);
//            discardUnusedLabels();
            updateMeans(labels, means);

            long timediff = (System.nanoTime() - time) / 1000000L;

            if ((timeout > 0) && (timediff > timeout)) {
                break;
            }
            /*if ((threshold > 0) && (change < eps)) {
                break;
            }*/
            if ((iter > 0) && (c >= iter - 1)) {
                break;
            }
        }
    }

    public void updateDistances(double[][] means, double[][] d, Distance distance) {
        for (int i=0; i<expressionValueRows.size(); i++) {
            for (int j=0; j<means.length; j++) {
                d[i][j] = distance.compute(expressionValueRows.get(i), means[j]);
            }
        }
    }

    public void updateLabels(int[] labels, double[][] d) {
        for (int i=0; i<labels.length; i++) {
            double curmin = Double.MAX_VALUE;
            int idx = 0;
            for (int j=0; j<d[0].length; j++) {
                if (d[i][j] < curmin) {
                    curmin = d[i][j];
                    idx = j;
                }
            }
            labels[i] = idx;
        }
    }

    public void updateMeans(int[] labels, double[][] means) {
        double[] n = new double[means.length];
        for (int label : labels) {
            n[label]++;
        }
        for (int i=0; i<labels.length; i++) {
            for (int j=0; j<means[0].length; j++) {
                means[ labels[i] ][j] += expressionValueRows.get( i )[j] / n[ labels[i] ];
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
