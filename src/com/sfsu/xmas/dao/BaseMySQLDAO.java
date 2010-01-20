package com.sfsu.xmas.dao;

import com.sfsu.xmas.globals.FileGlobals;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sfsu.xmas.monitoring.ExecutionTimer;

public abstract class BaseMySQLDAO {
    
    protected int maxSQLLength = 50000;

    public static final String NULL_VALUE = "NOT FOUND";
    
    protected static final String expressionDataSetKey = "%_EXPRESSION_DATA_ID_%";
    protected static final String knowledgeDataSetKey = "%_KNOWLEDGE_DATA_ID_%";
    protected static final String genericDataSetKey = "%_DATA_ID_%";
    private static final int maxQueryDisplay = 300;
    private static final String START_IN = " IN ( ";
    private static final String END_IN = ") ";
    private static final String START_EQ = " = ( ";
    private static final String END_EQ = ") ";

    protected abstract String useActiveDB(String sql);

    protected boolean executeSQL(String sql) {
        sql = useActiveDB(sql);
        try {
            boolean resultSetRecieved = false;
            ExecutionTimer et = new ExecutionTimer();
            Connection conn = DBConnectionManager.getInstance().getConnection(FileGlobals.DB_PROFILE);
            Statement stmt = conn.createStatement();
            et.start();
            resultSetRecieved = stmt.execute(sql);
            et.end();
            stmt.close();
            DBConnectionManager.getInstance().freeConnection(FileGlobals.DB_PROFILE, conn);
            if (sql.length() > maxQueryDisplay) {
                sql = sql.substring(0, Math.min(maxQueryDisplay, sql.length())) + "...";
            }
            //System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName() + ", SQL: " + sql);
            if (resultSetRecieved) {
                //
            }
            return resultSetRecieved;
        } catch (SQLException ex) {
            System.out.println("TROUBLE SQL in class " + this.getClass().getName() + ", SQL: " + sql);
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    protected synchronized int executeSQLGetInsertID(String sql) {
        executeSQL(sql);
        int index = 0;
        for (int i = 0; i < 5; i++) {
            // Race condition
            String LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS last_id;";
            index = getInt(getResultSet(LAST_INSERT_ID), "last_id");
            if (index > 0) {
                break;
            }
        }
        return index;
    }

    protected String getIn(String ids) {
        return START_IN + ids + END_IN;
    }

    protected String getEq(String ids) {
        return START_EQ + "\"" + ids + "\"" + END_EQ;
    }

    protected ResultSet getResultSet(String sql) {
        sql = useActiveDB(sql);
        ResultSet rs = null;
        if (!sql.contains(expressionDataSetKey) && !sql.contains(knowledgeDataSetKey)) {
            try {
                ExecutionTimer et = new ExecutionTimer();
                Connection conn = DBConnectionManager.getInstance().getConnection(FileGlobals.DB_PROFILE);

                if (conn != null && !conn.isClosed()) {
                    Statement stmt = conn.createStatement();
                    et.start();
                    rs = stmt.executeQuery(sql);
                    et.end();
                    if (sql.length() > maxQueryDisplay) {
                        sql = sql.substring(0, Math.min(maxQueryDisplay, sql.length())) + "...";
                    }
                    DBConnectionManager.getInstance().freeConnection(FileGlobals.DB_PROFILE, conn);
                    //System.out.println("DURATION = " + et.duration() + " in class " + this.getClass().getName() + ", SQL: " + sql);
                } else {
                    System.err.println(this.getClass().getName() + ", null connection: " + sql);
                }
            } catch (SQLException ex) {
                System.out.println("TROUBLE SQL in class " + this.getClass().getName() + ", SQL: " + sql);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println(this.getClass().getName() + ", database name unset in SQL execution: " + sql);
        }
        return rs;
    }

    /**
     * 
     * @param ids integer identifiers
     * @return a comma seperated string of IDs
     */
    protected String getIdentifierList(int[] ids) {
        StringBuffer ret = new StringBuffer();
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                if (i > 0) {
                    ret.append(",");
                }
                ret.append(String.valueOf(ids[i]));
            }
        }
        return ret.toString();
    }

    /**
     * 
     * @param ids string identifiers
     * @return a comma seperated string of IDs
     */
    protected String getIdentifierList(String[] ids) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                ret.append(",");
            }
            ret.append(" \"");
            ret.append(ids[i]);
            ret.append("\"");
        }
        return ret.toString();
    }

    protected double getDouble(ResultSet rs, String key) {
        double value = 0.0;
        if (rs != null) {
            try {
                if (rs.next()) {
                    String v = rs.getString(key);
                    if (v != null) {
                        value = Double.parseDouble(v);
                        rs.close();
                        return value;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    protected int getInt(ResultSet rs, String key) {
        int value = -1;
        if (rs != null) {
            try {
                if (rs.next()) {
                    value = Integer.parseInt(rs.getString(key));
                    rs.close();
                    return value;
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    protected String getString(ResultSet rs, String key) {
        String value = NULL_VALUE;
        if (rs != null) {
            try {
                if (rs.next()) {
                    value = rs.getString(key);
                    rs.close();
                    return value;
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    protected int[] getIntArray(ResultSet rs, String key) {
        ArrayList<String> values = getArrayOfValues(rs, key);
        int[] ids = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            ids[i] = Integer.valueOf(values.get(i));
        }
        return ids;
    }

    protected String[] getStringArray(ResultSet rs, String key) {
        ArrayList<String> values = getArrayOfValues(rs, key);
        String[] ids = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            ids[i] = values.get(i);
        }
        return ids;
    }

    protected ArrayList<String> getArrayOfValues(ResultSet rs, String key) {
        ArrayList<String> values = new ArrayList<String>();
        if (rs != null) {
            try {
                while (rs.next()) {
                    values.add(rs.getString(key));
                }
            } catch (SQLException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }
}
