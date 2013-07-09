package com.sfsu.xmas.dao;

import com.sfsu.xmas.data_sets.DataSetList;
import com.sfsu.xmas.data_sets.KnowledgeDataSetFactory;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.data_sets.IDataSet;
import com.sfsu.xmas.data_sets.KnowledgeDataSet;
import com.sfsu.xmas.globals.FileGlobals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataSetDAO extends BaseMySQLDAO {

    public int insertNewExpressionDataSet(String name, String description) {
        String INSERT_NEW_DATA_SET = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".data_sets (name, description, creation_date) VALUES ('%_NAME_%', '%_DESCRIPTION_%', NOW() );";
        String sql = INSERT_NEW_DATA_SET.replace("%_NAME_%", name);
        sql = sql.replace("%_DESCRIPTION_%", description);
        return executeSQLGetInsertID(sql);
    }

    public int insertNewKnowledgeDataSet(String name, String description) {
        String INSERT_NEW_KNOWLEDGE_DATA_SET = "INSERT INTO " + FileGlobals.EXPRESSION_DATABASE + ".k_data_sets (name, description, creation_date) VALUES ('%_NAME_%', '%_DESCRIPTION_%', NOW() );";
        String sql = INSERT_NEW_KNOWLEDGE_DATA_SET.replace("%_NAME_%", name);
        sql = sql.replace("%_DESCRIPTION_%", description);
        return executeSQLGetInsertID(sql);
    }

    public DataSetList getExpressionDataSets() {
        String sql = "SELECT * FROM " + FileGlobals.EXPRESSION_DATABASE + ".data_sets ORDER BY creation_date DESC;";
        DataSetList dbs = new DataSetList();
        try {
            ResultSet rs = getResultSet(sql);
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("set_id");
                    dbs.add(ExpressionDataSetMultiton.getUniqueInstance().getDataSet(id, false));
                }
                rs.close();
            } else {
                System.out.println(DataSetDAO.class.getName() + ": Null result set");
                return new DataSetList();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbs;
    }

    public DataSetList getKnowledgeDataSets() {
        String sql = "SELECT * FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_data_sets ORDER BY creation_date DESC;";
        DataSetList dbs = new DataSetList();
        try {
            ResultSet rs = getResultSet(sql);
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("k_set_id");
                    dbs.add(KnowledgeDataSetFactory.getUniqueInstance().getDataSet(id, false));
                }
                rs.close();
            } else {
                System.out.println(DataSetDAO.class.getName() + ": Null result set");
                return new DataSetList();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbs;
    }

    public boolean isExistingExpressionDataSet(int databaseID) {
        String sql = "SELECT * FROM " + FileGlobals.EXPRESSION_DATABASE + ".data_sets WHERE set_id = " + databaseID + ";";
        ResultSet rs = getResultSet(sql);
        if (rs != null) {
            try {
                while (rs.next()) {
                    int id = rs.getInt("set_id");
                    return databaseID == id;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataSetDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public boolean isExistingKnowledgeDataSet(int databaseID) {
        DataSetList dbs = getKnowledgeDataSets();
        Iterator<IDataSet> it = dbs.iterator();
        while (it.hasNext()) {
            IDataSet ds = it.next();
            if (ds.getID() == databaseID && ds instanceof KnowledgeDataSet) {
                return true;
            }
        }
        return false;
    }

    public void dropDataSet(int dataSetID, boolean isLibrary) {
        String DELETE_EXPRESSION_DATASET = "DELETE FROM " + FileGlobals.EXPRESSION_DATABASE + ".data_sets where set_id = " + expressionDataSetKey + ";";
        String DELETE_KNOWLEDGE_DATASET = "DELETE FROM " + FileGlobals.KNOWLEDGE_DATABASE + ".k_data_sets where k_set_id = " + knowledgeDataSetKey + ";";
        String sql = "";
        if (isLibrary) {
            sql = DELETE_KNOWLEDGE_DATASET.replace(knowledgeDataSetKey, String.valueOf(dataSetID));
        } else {
            sql = DELETE_EXPRESSION_DATASET.replace(expressionDataSetKey, String.valueOf(dataSetID));
        }
        executeSQL(sql);
    }

    protected String cleanKnowledgeDatabaseName(String databaseName) {

        return databaseName;
    }

    @Override
    protected String useActiveDB(String sql) {
        return sql;
    }
}
