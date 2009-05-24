package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_structures.expression.Samples;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.globals.FileGlobals;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProbeDAO extends ExpressionDataSetDAO {

    public ProbeDAO(int dataSetID) {
        super(dataSetID);
    }

    public ResultSet getAllProbesTimePeriodsExpression() {
        String sql =
                "SELECT probe_id, group_concat(timeperiods.time_period_id ORDER BY timeperiods.time_period_id SEPARATOR '|') as time_periods, " +
                "group_concat(timeperiod_expression.expression  ORDER BY timeperiods.time_period_id SEPARATOR '|') as expression " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression JOIN " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods " +
                "ON " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression.time_period_id = " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods.time_period_id " +
                "AND " +
                "timeperiods.set_id = timeperiod_expression.set_id " +
                "WHERE " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods.set_id = " + expressionDataSetKey + " " +
                "GROUP BY probe_id;";
        return getResultSet(sql);
    }

    public double[] getProbeTimePeriodExpression(String id) {
        String GET_PROBE_TIME_PERIOD_EXPRESSION_PROFILE =
                "SELECT probe_id, timeperiods.time_period_id, timeperiod_expression.expression as value " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression JOIN " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods ON " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression.time_period_id = " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods.time_period_id " +
                "AND " +
                "timeperiod_expression.set_id = timeperiods.set_id " +
                "WHERE " +
                "probe_id %_ID_% AND " +
                FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression.set_id = " + expressionDataSetKey + " " +
                "ORDER BY probe_id, time_period_id;";
        ResultSet rs = getResultSet(GET_PROBE_TIME_PERIOD_EXPRESSION_PROFILE.replace("%_ID_%", getEq(id)));

        double[] timePeriodExpressionProfile = new double[0];

        if (rs != null) {
            try {
                ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
                TimePeriods timePeriods = eDB.getTimePeriods();
                // For each time period
                timePeriodExpressionProfile = new double[timePeriods.size()];
                for (int tpIndex = 0; tpIndex < timePeriods.size(); tpIndex++) {

                    if (rs.next()) {
                        Double exp = Double.valueOf(rs.getString("value"));
                        timePeriodExpressionProfile[tpIndex] = exp;
                    } else {
                        // Fuck
                        System.err.println(this.getClass().getSimpleName() + ": Not enough data for probe, result set now empty");
                    }
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Samples.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return timePeriodExpressionProfile;
    }

    public double[] getProbeSamplesExpression(String id) {
        String GET_PROBE_SAMPLE_EXPRESSION_PROFILE =
                "SELECT probe_id, samples.time_period_id, samples.sample_id, expression.expression as value " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".expression JOIN " + FileGlobals.EXPRESSION_DATABASE + ".samples ON " + FileGlobals.EXPRESSION_DATABASE + ".expression.sample_id = " + FileGlobals.EXPRESSION_DATABASE + ".samples.sample_id " +
                "AND " +
                "expression.set_id = samples.set_id " +
                "WHERE " +
                "probe_id %_ID_% AND " +
                FileGlobals.EXPRESSION_DATABASE + ".expression.set_id = " + expressionDataSetKey + " " +
                "ORDER BY probe_id, time_period_id, sample_id;";
        ResultSet rs = getResultSet(GET_PROBE_SAMPLE_EXPRESSION_PROFILE.replace("%_ID_%", getEq(id)));

        double[] sampleExpressionProfile = new double[0];

        if (rs != null) {
            try {
                ExpressionDataSet eDB = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
                TimePeriods timePeriods = eDB.getTimePeriods();
                Samples samples = eDB.getSamples();
                // For each time period
                sampleExpressionProfile = new double[samples.size()];
                int sampleCount = 0;
                for (int tpIndex = 0; tpIndex < timePeriods.size(); tpIndex++) {
                    TimePeriod timePeriod = timePeriods.get(tpIndex);
                    // For each sample in a time period
                    for (int sIndex = 0; sIndex < timePeriod.getSampleCount(); sIndex++) {
                        if (rs.next()) {
                            Double exp = Double.valueOf(rs.getString("value"));
                            sampleExpressionProfile[sampleCount + sIndex] = exp;
                        } else {
                            // Fuck
                            System.err.println(this.getClass().getSimpleName() + ": Not enough data for probe, result set now empty");
                        }
                    }
                    sampleCount += timePeriod.getSampleCount();
                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Samples.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sampleExpressionProfile;
    }
}
