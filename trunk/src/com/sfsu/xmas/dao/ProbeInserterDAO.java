package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_files.expression.FileProbes;
import com.sfsu.xmas.data_files.expression.FileProbe;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.globals.FileGlobals;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import com.sfsu.xmas.util.NumberUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbeInserterDAO extends ExpressionDataSetDAO {

    private FileProbes probesToInsert;
    private int expressionCount;
    private TimePeriods tps;
    private static String _InsertProbe = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".probes (" +
            "probe_id, " +
            "set_id) " +
            "VALUES " +
            "%_VALUES_%;";
//            "(NULL , \"%_probe_id_ID_%\" );";
    private static String _InsertProbeTimePeriodExpression =
            "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression (" +
            "probe_id, " +
            "time_period_id, " +
            "expression, " +
            "set_id) " +
            "VALUES %_VALUES_%;";
    private static String _InsertProbeExpression = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".expression ( " +
            "probe_id , " +
            "sample_id, " +
            "expression, " +
            "set_id) " +
            "VALUES %_VALUES_%;";
    private static String _GetBinnedExpressionForProbe =
            "SELECT * FROM " + FileGlobals.EXPRESSION_DATABASE + ".binnedExpression WHERE probe_id = \"%_ID_%\" ORDER BY time_period_id;";
    private static String _GetInternalProbeID =
            "SELECT * FROM " + FileGlobals.EXPRESSION_DATABASE + ".probes WHERE probe_id = \"%_probe_id_ID_%\";";
//    private static String _InsertTrajectory = "INSERT INTO " + expressionDatabaseSQLKey + ".trajectories " +
//            "VALUES %_VALUES_%;";
    /** Finds numPrimes prime numbers, each of which is
     *  numDigits long or longer. You can set it to return
     *  only when done, or have it return immediately,
     *  and you can later poll it to see how far it
     *  has gotten.
     */
    public ProbeInserterDAO(int dataSetID, FileProbes probes, int expressionCount, TimePeriods tps, int offset) {
        super(dataSetID);
        this.probesToInsert = probes;
        this.expressionCount = expressionCount;
        this.tps = tps;

        ExecutionTimer et = new ExecutionTimer();
        et.start();
        insertProbes();
        et.end();
        System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName() + ", INSERTING PROBES");
        et.reset();
        
        et.start();
        insertProbeExpression();
        et.end();
        System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName() + ", INSERTING PROBE EXPRESSION");
        et.reset();

    }
    StringBuffer probeIDSQL = new StringBuffer();
    StringBuffer timePeriodExpressionSQL = new StringBuffer();
    StringBuffer sampleExpressionSQL = new StringBuffer();

    public synchronized void insertProbeExpression() {
        for (int i = 0; i < probesToInsert.size(); i++) {
            FileProbe probe = (FileProbe) probesToInsert.get(i);

            double[] expression = probe.getExpression();
            int sampleIndex = 0;
            // For each time period
            StringBuffer tpExpressionSQL = new StringBuffer();
            StringBuffer sampleExp = new StringBuffer();
            for (int timePeriodIndex = 0; timePeriodIndex < tps.size(); timePeriodIndex++) {
                int samples = tps.get(timePeriodIndex).getSampleCount();

                double[] samplesFromTP = new double[samples];

                // For each sample in time period
                for (int sampleID = sampleIndex; sampleID < sampleIndex + samples; sampleID++) {
                    // Add to sample
                    double expressionValue = expression[sampleID];
                    if (sampleID != sampleIndex || timePeriodIndex != 0) {
                        sampleExp.append(", ");
                    } else {
                        sampleExp.append(" ");
                    }
                    sampleExp.append("(\"");
                    sampleExp.append(probe.getID());
                    sampleExp.append("\",");
                    sampleExp.append(String.valueOf(sampleID + 1));
                    sampleExp.append(",");
                    sampleExp.append(String.valueOf(expressionValue));
                    sampleExp.append(", " + dataSetID + ")");
                    samplesFromTP[sampleID - sampleIndex] = expressionValue;
                }
                sampleIndex += samples;
                java.util.Arrays.sort(samplesFromTP);

                double medianValue = median(samplesFromTP);
                if (timePeriodIndex != 0) {
                    tpExpressionSQL.append(",");
                } else {
                    tpExpressionSQL.append(" ");
                }
                tpExpressionSQL.append("(\"");
                tpExpressionSQL.append(probe.getID());
                tpExpressionSQL.append("\",");
                tpExpressionSQL.append(String.valueOf(timePeriodIndex + 1));
                tpExpressionSQL.append(",");
                tpExpressionSQL.append(String.valueOf(NumberUtils.getDoubleToFourDecimalPlaces(medianValue)));
                tpExpressionSQL.append(", " + dataSetID + ")");
            }

            // Time period expression
            if (tpExpressionSQL.length() > 0) {
                if (timePeriodExpressionSQL.length() > 0) {
                    timePeriodExpressionSQL.append(",");
                }
                timePeriodExpressionSQL.append(tpExpressionSQL);
            }
            // Sample Expression
            if (sampleExp.length() > 0) {
                if (sampleExpressionSQL.length() > 0) {
                    sampleExpressionSQL.append(",");
                }
                sampleExpressionSQL.append(sampleExp);
            }


            if (timePeriodExpressionSQL.length() > maxSQLLength) {
                executeSQL(_InsertProbeTimePeriodExpression.replace("%_VALUES_%", timePeriodExpressionSQL.toString()));
                timePeriodExpressionSQL = new StringBuffer();
            }
            if (sampleExpressionSQL.length() > maxSQLLength) {
                executeSQL(_InsertProbeExpression.replace("%_VALUES_%", sampleExpressionSQL.toString()));
                sampleExpressionSQL = new StringBuffer();
            }
        }
        if (timePeriodExpressionSQL.length() > 0) {
            executeSQL(_InsertProbeTimePeriodExpression.replace("%_VALUES_%", timePeriodExpressionSQL.toString()));
        }
        if (sampleExpressionSQL.length() > 0) {
            executeSQL(_InsertProbeExpression.replace("%_VALUES_%", sampleExpressionSQL.toString()));
        }
    }

    public synchronized void insertProbes() {
        for (int i = 0; i < probesToInsert.size(); i++) {
            // Probe IDs
            FileProbe probe = (FileProbe) probesToInsert.get(i);
            if (probeIDSQL.length() > 0) {
                probeIDSQL.append(", ");
            }
            probeIDSQL.append("(\"" + probe.getID() + "\", " + dataSetID + ")");

            if (probeIDSQL.length() > maxSQLLength) {
                executeSQL(_InsertProbe.replace("%_VALUES_%", probeIDSQL.toString()));
                probeIDSQL = new StringBuffer();
            }
        }
        if (probeIDSQL.length() > 0) {
            executeSQL(_InsertProbe.replace("%_VALUES_%", probeIDSQL.toString()));
        }
    }

    public synchronized void insertTrajectories(FileProbe probe) {
        try {
            double[] expression = new double[expressionCount];
            ResultSet trajectory = getProbeTrajectory(probe.getID());
            int exIndex = 0;
            while (trajectory.next()) {
                String binnedExpression = trajectory.getString("bin");
                expression[exIndex] = Double.parseDouble(binnedExpression);
                exIndex++;
            }


//            if (trajectoryInsertionSQL.length() > 0) {
//                trajectoryInsertionSQL.append(", ");
//            }
//            trajectoryInsertionSQL.append("( " + internalProbeID + ", ");
//            for (int i = 0; i < expression.length; i++) {
//                trajectoryInsertionSQL.append(expression[i]);
//                if ((i + 1) < expression.length) {
//                    trajectoryInsertionSQL.append(", ");
//                }
//            }
//            trajectoryInsertionSQL.append(")");
        } catch (SQLException ex) {
            Logger.getLogger(ProbeInserterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized ResultSet getProbeTrajectory(String probeID) {
        String sql = _GetBinnedExpressionForProbe.replace("%_ID_%", probeID);
        ResultSet rs = getResultSet(sql);
        return rs;
    }

//   Precondition: Array must be sorted
    public static double median(double[] m) {
        int middle = m.length / 2;  // subscript of middle element
        if (m.length % 2 == 1) {
            // Odd number of elements -- return the middle one.
            return m[middle];
        } else {
            // Even number -- return average of middle two
            // Must cast the numbers to double before dividing.
            return (m[middle - 1] + m[middle]) / 2.0;
        }
    }
}
