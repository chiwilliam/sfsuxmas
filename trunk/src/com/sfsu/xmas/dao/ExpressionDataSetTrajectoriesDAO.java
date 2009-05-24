package com.sfsu.xmas.dao;

import com.sfsu.xmas.dao.ExpressionDataSetDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.globals.FileGlobals;
import java.sql.ResultSet;

public class ExpressionDataSetTrajectoriesDAO extends ExpressionDataSetDAO {

    protected int numberOfTimePeriods;

    public ExpressionDataSetTrajectoriesDAO(int databaseID, int numberOfTimePeriod) {
        super(databaseID);
        this.numberOfTimePeriods = numberOfTimePeriod;
    }

    public ExpressionDataSetTrajectoriesDAO(ExpressionDataSet eDB) {
        super(eDB.getID());
        numberOfTimePeriods = eDB.getNumberOfTimePeriods();
    }

    public ResultSet getCollapsedTrajectories() {
        StringBuffer colHeaders = new StringBuffer();
        for (int i = 0; i < numberOfTimePeriods; i++) {
            colHeaders.append("TP");
            colHeaders.append(String.valueOf(i + 1));
            colHeaders.append("bin");
            if ((i + 1) < numberOfTimePeriods) {
                colHeaders.append(", ");
            }
        }
        String sql = "SELECT COUNT(probe_id), %_SAMPLE_COLUMN_HEADERS_% " +
            " AS Genes FROM " + FileGlobals.EXPRESSION_DATABASE + ".collapsed_trajectories GROUP BY %_SAMPLE_COLUMN_HEADERS_%;";
        ResultSet rs = getResultSet(sql.replace("%_SAMPLE_COLUMN_HEADERS_%", colHeaders.toString()));
        return rs;
    }

    public ResultSet getTrajectories(int timePeriodCount) {
        StringBuffer colHeaders = new StringBuffer();
        for (int i = 0; i < numberOfTimePeriods; i++) {
            colHeaders.append("TP");
            colHeaders.append(String.valueOf(i + 1));
            colHeaders.append("bin");
            if ((i + 1) < numberOfTimePeriods) {
                colHeaders.append(", ");
            }
        }
        String sql = "SELECT COUNT(probe_id), %_SAMPLE_COLUMN_HEADERS_% " +
            " AS Genes FROM " + FileGlobals.EXPRESSION_DATABASE + ".trajectories GROUP BY %_SAMPLE_COLUMN_HEADERS_%;";
        ResultSet rs = getResultSet(sql.replace("%_SAMPLE_COLUMN_HEADERS_%", colHeaders.toString()));
        return rs;
    }

    public void createCustomBinView(String fileName, double binUnit) {
        String sql = "CREATE ALGORITHM = MERGE VIEW " + FileGlobals.EXPRESSION_DATABASE + ".binnedexpression AS " +
                "(SELECT probe_id, time_period_id, expression, FLOOR( expression / %_BIN_UNIT_% ) AS bin " +
                "FROM " + FileGlobals.EXPRESSION_DATABASE + ".timeperiod_expression WHERE set_id = " + expressionDataSetKey + ");";
        executeSQL(sql.replace("%_BIN_UNIT_%", String.valueOf(binUnit)));
    }

    public void removeTrajectoryBinViews(int tpCount) {
        String sql = "DROP VIEW " + FileGlobals.EXPRESSION_DATABASE + ".binnedexpression;";
        executeSQL(sql);
        sql = "DROP VIEW " + FileGlobals.EXPRESSION_DATABASE + ".trajectories;";
        executeSQL(sql);
        sql = "DROP VIEW " + FileGlobals.EXPRESSION_DATABASE + ".collapsed_trajectories;";
        executeSQL(sql);
        for (int i = 0; i < numberOfTimePeriods; i++) {
            sql = "DROP VIEW " + FileGlobals.EXPRESSION_DATABASE + ".timeperiodbin" + String.valueOf(i + 1) + ";";
            executeSQL(sql);
        }
    }

    ResultSet getProbesInTrajectory(int[] trajectory) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT probe_id FROM " + FileGlobals.EXPRESSION_DATABASE + ".trajectories WHERE ");
        for (int i = 0; i < trajectory.length; i++) {
            // TODO: Pull out
            sb.append("TP" + String.valueOf(i + 1) + "bin = " + trajectory[i] + " ");
            if ((i + 1) < trajectory.length) {
                sb.append(" AND ");
            }
        }
        sb.append(";");
        ResultSet rs = getResultSet(sb.toString());
        return rs;
    }

    public ResultSet getAllProbesInCollapsedTrajectoryOrder(int timePeriodCount) {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT probe_id FROM " + FileGlobals.EXPRESSION_DATABASE + ".collapsed_trajectories ORDER BY ");
        for (int i = 0; i < numberOfTimePeriods; i++) {
            if (i != 0) {
                sb.append(" , ");
            }
            sb.append("TP");
            sb.append(String.valueOf(i + 1));
            sb.append("bin ");
        }
        sb.append(";");
        ResultSet rs = getResultSet(sb.toString());
        return rs;
    }

    public ResultSet getAllProbesInTrajectoryOrder(int timePeriodCount) {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT probe_id FROM " + FileGlobals.EXPRESSION_DATABASE + ".trajectories ORDER BY ");
        for (int i = 0; i < numberOfTimePeriods; i++) {
            if (i != 0) {
                sb.append(" , ");
            }
            sb.append("TP");
            sb.append(String.valueOf(i + 1));
            sb.append("bin ");
        }
        sb.append(";");
        ResultSet rs = getResultSet(sb.toString());
        return rs;
    }

    public void createTrajectoryViews(int numberOfTimePeriods, boolean create) {
        String makeString = "CREATE";
        for (int i = 0; i < numberOfTimePeriods; i++) {
            StringBuffer viewSQL = new StringBuffer();
            viewSQL.append(makeString + " ALGORITHM = MERGE VIEW " + FileGlobals.EXPRESSION_DATABASE + ".timeperiodbin");
            viewSQL.append(String.valueOf(i + 1));
            viewSQL.append(" AS (SELECT probe_id, bin FROM " + FileGlobals.EXPRESSION_DATABASE + ".binnedexpression WHERE time_period_id = ");
            viewSQL.append(String.valueOf(i + 1));
            viewSQL.append(");");
            executeSQL(viewSQL.toString());
        }

        StringBuffer sql = new StringBuffer();

        sql.append(makeString + " ALGORITHM = MERGE VIEW " + FileGlobals.EXPRESSION_DATABASE + ".trajectories AS (");

        sql.append("SELECT TP1.probe_id ");
        for (int i = 0; i < numberOfTimePeriods; i++) {
            sql.append(", TP");
            sql.append(String.valueOf(i + 1));
            sql.append(".bin AS TP");
            sql.append(String.valueOf(i + 1));
            sql.append("bin ");
        }
        sql.append(" FROM ");


        for (int i = 0; i < numberOfTimePeriods; i++) {
            sql.append(" " + FileGlobals.EXPRESSION_DATABASE + ".timeperiodbin");
            sql.append(String.valueOf(i + 1));
            sql.append(" AS TP");
            sql.append(String.valueOf(i + 1));
            if (i > 0) {
                sql.append(" ON TP");
                sql.append(String.valueOf(i));
                sql.append(".probe_id = TP");
                sql.append(String.valueOf(i + 1));
                sql.append(".probe_id ");
            }
            if (i + 1 < numberOfTimePeriods) {
                sql.append(" INNER JOIN ");
            } else {
                // ORDER BY:
            }
        }
        sql.append(");");
        executeSQL(sql.toString());
    }

    public void createCollapsedTrajectoryViews(int numberOfTimePeriods, boolean create) {
        String makeString = "CREATE";
        StringBuffer sql = new StringBuffer();

        sql.append(makeString + " ALGORITHM = MERGE VIEW " + FileGlobals.EXPRESSION_DATABASE + ".collapsed_trajectories AS (");

        sql.append("SELECT TP1.probe_id ");
        for (int i = 0; i < numberOfTimePeriods; i++) {
            sql.append(", (TP");
            sql.append(String.valueOf(i + 1));
            sql.append(".bin - TP1.bin ) AS TP");
            sql.append(String.valueOf(i + 1));
            sql.append("bin ");
        }
        sql.append(" FROM ");

        for (int i = 0; i < numberOfTimePeriods; i++) {
            sql.append(" " + FileGlobals.EXPRESSION_DATABASE + ".timeperiodbin");
            sql.append(String.valueOf(i + 1));
            sql.append(" AS TP");
            sql.append(String.valueOf(i + 1));
            if (i > 0) {
                sql.append(" ON TP");
                sql.append(String.valueOf(i));
                sql.append(".probe_id = TP");
                sql.append(String.valueOf(i + 1));
                sql.append(".probe_id ");
            }
            if (i + 1 < numberOfTimePeriods) {
                sql.append(" INNER JOIN ");
            } else {
                // ORDER BY:
            }
        }
        sql.append(");");
        executeSQL(sql.toString());
    }
    
}
