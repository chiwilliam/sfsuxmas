package com.sfsu.xmas.dao;

public class DatabaseDAOFactory {

    public DataSetDAO getDataSetDAO() {
        return new DataSetDAO();
    }

    public ExpressionDataSetDAO getExpressionDataSetDAO(int dataSetID) {
        return new ExpressionDataSetDAO(dataSetID);
    }

    public KnowledgeDataSetDAO getKnowledgeDataSetDAO(int dataSetID) {
        return new KnowledgeDataSetDAO(dataSetID);
    }

}
