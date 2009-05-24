package com.sfsu.xmas.dao;

import com.sfsu.xmas.globals.FileGlobals;

public abstract class BaseDataSetDAO extends BaseMySQLDAO {

    protected int dataSetID;

    protected static String dataSetTable;
    protected static String dataSetIDKey;
    
    protected static final String DATA_SET_TABLE = "%_DATA_SET_TABLE_%";
    protected static final String DATA_SET_ID = "%_DATA_SET_ID_%";

    public BaseDataSetDAO(int dataSetID) {
        this.dataSetID = dataSetID;
    }
    
    public String getName() {
        String GET_DATABASE_NAME = "SELECT name FROM " + FileGlobals.KNOWLEDGE_DATABASE + "." + DATA_SET_TABLE + " WHERE " + DATA_SET_ID + " = " + genericDataSetKey + ";";
        return getString(getResultSet(GET_DATABASE_NAME), "name");
    }

    public String getDescription() {
        String GET_DATABASE_DESCRIPTION = "SELECT description FROM " + FileGlobals.KNOWLEDGE_DATABASE + "." + DATA_SET_TABLE + " WHERE " + DATA_SET_ID + " = " + genericDataSetKey + ";";
        return getString(getResultSet(GET_DATABASE_DESCRIPTION), "description");
    }
    
    public int getCreationDate() {
        String GET_DATABASE_CREATION_DATE = "SELECT UNIX_TIMESTAMP(creation_date) AS creation_date FROM " + FileGlobals.KNOWLEDGE_DATABASE + "." + DATA_SET_TABLE + " WHERE " + DATA_SET_ID + " = " + genericDataSetKey + ";";
        return getInt(getResultSet(GET_DATABASE_CREATION_DATE), "creation_date");
    }
    
    @Override
    protected String useActiveDB(String sql) {
        if (sql.contains(genericDataSetKey)) {
            sql = sql.replace(genericDataSetKey, String.valueOf(dataSetID));
        }
        if (sql.contains(DATA_SET_TABLE)) {
            sql = sql.replace(DATA_SET_TABLE, dataSetTable);
        }
        if (sql.contains(DATA_SET_ID)) {
            sql = sql.replace(DATA_SET_ID, dataSetIDKey);
        }
        return sql;
    }
}
