package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_structures.expression.Sample;
import com.sfsu.xmas.data_structures.expression.Samples;
import com.sfsu.xmas.data_structures.expression.TimePeriod;
import com.sfsu.xmas.data_structures.expression.TimePeriods;
import com.sfsu.xmas.globals.FileGlobals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpressionDataSetDAO extends BaseDataSetDAO {

    public ExpressionDataSetDAO(int dataSetID) {
        super(dataSetID);
        dataSetTable = "data_sets";
        dataSetIDKey = "set_id";
    }

    public double getMaximumExpression() {
        String sql = "SELECT MAX(expression) AS mExpression FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression WHERE set_id = " + expressionDataSetKey + ";";
        return getDouble(getResultSet(sql), "mExpression");
    }

    public double getMinimumExpression() {
        String sql = "SELECT MIN(expression) AS mExpression FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression WHERE set_id = " + expressionDataSetKey + ";";
        return getDouble(getResultSet(sql), "mExpression");
    }

    public double getMeanExpression() {
        String sql = "SELECT AVG(expression) AS mExpression FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression WHERE set_id = " + expressionDataSetKey + ";";
        return getDouble(getResultSet(sql), "mExpression");
    }

    public int getNumberOfTimePeriodsInDatabase() {
        String sql = "SELECT COUNT(*) AS numberOfTimePeriods FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods WHERE set_id = " + expressionDataSetKey + ";";
        return getInt(getResultSet(sql), "numberOfTimePeriods");
    }

    public int getNumberOfSamplesInDatabase() {
        String sql = "SELECT COUNT(*) AS numberOfSamples FROM " + FileGlobals.EXPRESSION_DATABASE + ".samples WHERE set_id = " + expressionDataSetKey + ";";
        return getInt(getResultSet(sql), "numberOfSamples");
    }

    public int getNumberOfProbes() {
        String sql = "SELECT COUNT(*) AS numberOfProbes FROM " + FileGlobals.EXPRESSION_DATABASE + ".probes WHERE set_id = " + expressionDataSetKey + ";";
        return getInt(getResultSet(sql), "numberOfProbes");
    }

//    public ResultSet getDataForSampleIDs(int[] ids) {
//        String sql =
//            "SELECT s.sample_id as ID, s.description as description, tp.time_period_id as tp_id, tp.description as tp_description " +
//            "FROM " + FileGlobals.EXPRESSION_DATABASE + ".samples s JOIN " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods tp ON s.time_period_id = tp.time_period_id " +
//            "WHERE sample_id IN ( %_IDS_% ) AND set_id = " + expressionDataSetKey + ";";
//        return getResultSet(sql.replace("%_IDS_%", getIdentifierList(ids)));
//    }
    public String[] getProbes() {
        String sql = "SELECT probe_id FROM " + FileGlobals.EXPRESSION_DATABASE + ".probes WHERE set_id = " + expressionDataSetKey + ";";
        return getStringArray(getResultSet(sql), "probe_id");
    }

    public TimePeriods getTimePeriods() {
        String sql =
                "SELECT timeperiods.time_period_id, timeperiods.description as timeperiod_description, COUNT(sample_id) as sample_count " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods " +
                "JOIN " + FileGlobals.EXPRESSION_DATABASE + ".samples " +
                "ON timeperiods.time_period_id = samples.time_period_id AND timeperiods.set_id = samples.set_id " +
                "WHERE " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods.set_id = " + expressionDataSetKey + " " +
                "GROUP BY timeperiods.time_period_id " +
                "ORDER BY timeperiods.time_period_id;";
        TimePeriods timePeriods = new TimePeriods();
        ResultSet rs = getResultSet(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int timeperiodID = Integer.valueOf(rs.getString("time_period_id"));
                    String timeperiodDescription = rs.getString("timeperiod_description");
                    int sampleCount = Integer.valueOf(rs.getString("sample_count"));
                    timePeriods.add(new TimePeriod(timeperiodID, timeperiodDescription, sampleCount));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpressionDataSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return timePeriods;
    }

    public Samples getSamples() {
        String sql =
                "SELECT samples.sample_id, samples.description as sample_description, timeperiods.time_period_id, timeperiods.description as timeperiod_description " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".samples " +
                "JOIN " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods " +
                "ON samples.time_period_id = timeperiods.time_period_id AND samples.set_id = timeperiods.set_id " +
                "WHERE " + FileGlobals.EXPRESSION_DATABASE + ".samples.set_id = " + expressionDataSetKey + " " +
                "ORDER BY time_period_id, sample_id;";
        Samples samples = new Samples();
        ResultSet rs = getResultSet(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int sampleID = Integer.valueOf(rs.getString("sample_id"));
                    String sampleDescription = rs.getString("sample_description");
                    int timeperiodID = Integer.valueOf(rs.getString("time_period_id"));
                    String timeperiodDescription = rs.getString("timeperiod_description");
                    samples.add(new Sample(sampleID, sampleDescription, new TimePeriod(timeperiodID, timeperiodDescription)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExpressionDataSet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return samples;
    }

    public void insertSamples(Samples samples) {
        String sql = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".samples ( sample_id , time_period_id , description, set_id ) VALUES %_VALUES_%;";
        StringBuffer values = new StringBuffer();

        int index = 1;

        Iterator it = samples.iterator();
        while (it.hasNext()) {
            Sample sample = (Sample) it.next();

            if (values.length() > 0) {
                values.append(", ");
            }
            values.append("( " + index + ", ");
            values.append(sample.getTimePeriodID());
            values.append(", \"");
            values.append("si_" + index + "_" + sample.getDescription());
            values.append("\", " + expressionDataSetKey + ") ");
            index++;
        }
        executeSQL(sql.replace("%_VALUES_%", values.toString()));
    }

    public void insertTimePeriods(TimePeriods tps) {
        String sql = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".timeperiods ( time_period_id , description, set_id ) VALUES %_VALUES_%;";
        StringBuffer values = new StringBuffer();
        int index = 1;
        Iterator it = tps.iterator();
        while (it.hasNext()) {
            TimePeriod timePeriod = (TimePeriod) it.next();
            if (values.length() > 0) {
                values.append(", ");
            }
            values.append("( " + index + ", \"");
            values.append(timePeriod.getDescription());
            values.append("\", " + expressionDataSetKey + ") ");
            index++;
        }
        executeSQL(sql.replace("%_VALUES_%", values.toString()));
    }

    @Override
    protected String useActiveDB(String sql) {
        if (sql.contains(expressionDataSetKey)) {
            sql = sql.replace(expressionDataSetKey, String.valueOf(dataSetID));
        }
        return super.useActiveDB(sql);
    }
}
