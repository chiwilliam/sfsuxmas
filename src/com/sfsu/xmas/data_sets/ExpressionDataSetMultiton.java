package com.sfsu.xmas.data_sets;

import com.sfsu.xmas.dao.DataSetDAO;
import java.util.HashMap;
import java.util.Iterator;

public class ExpressionDataSetMultiton implements IDataSetFactory {

    private static ExpressionDataSetMultiton uniqueInstance;
    private HashMap<Integer, ExpressionDataSet> dataSets;

    private ExpressionDataSetMultiton() {
        dataSets = new HashMap<Integer, ExpressionDataSet>();
    }

    public static ExpressionDataSetMultiton getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ExpressionDataSetMultiton();
        }
        return uniqueInstance;
    }
    
    public static void refreshUniqueInstance() {
        uniqueInstance = new ExpressionDataSetMultiton();
    }

    public synchronized ExpressionDataSet getDataSet(int dataSetID, boolean populateFully) {
        if (!dataSets.keySet().contains(dataSetID) || !dataSets.get(dataSetID).isFresh()) {
            dataSets.put(dataSetID, new ExpressionDataSet(dataSetID, populateFully));
        }
        ExpressionDataSet eDB = dataSets.get(dataSetID);
        if (populateFully) {
            eDB.populateFully();
        }
        return eDB;
    }

    public boolean isDatabase(int dataSetID) {
        DataSetDAO dDA = new DataSetDAO();
        DataSetList eDBs = dDA.getExpressionDataSets();
        Iterator<IDataSet> it = eDBs.iterator();
        while (it.hasNext()) {
            IDataSet eDB = it.next();
            if (eDB.getID() == dataSetID) {
                return true;
            }
        }
        return false;
    }
    
    public int getNumberOfDataSets() {
        return dataSets.size();
    }
    
    public HashMap<Integer, ExpressionDataSet> getDataSets() {
        return dataSets;
    }
}
