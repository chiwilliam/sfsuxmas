package com.sfsu.xmas.data_sets;

import com.sfsu.xmas.dao.DataSetDAO;
import java.util.HashMap;
import java.util.Iterator;

public class KnowledgeDataSetFactory implements IDataSetFactory {

    private static KnowledgeDataSetFactory uniqueInstance;
    private HashMap<Integer, KnowledgeDataSet> dataSets;

    private KnowledgeDataSetFactory() {
        dataSets = new HashMap<Integer, KnowledgeDataSet>();
    }

    public static KnowledgeDataSetFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new KnowledgeDataSetFactory();
        }
        return uniqueInstance;
    }

    public static void refreshUniqueInstance() {
        uniqueInstance = new KnowledgeDataSetFactory();
    }

    @Override
    public synchronized KnowledgeDataSet getDataSet(int dataSetID, boolean populateFully) {
        if (!dataSets.keySet().contains(dataSetID) || !dataSets.get(dataSetID).isFresh()) {
            dataSets.put(dataSetID, new KnowledgeDataSet(dataSetID, populateFully));
        }
        KnowledgeDataSet kDB = dataSets.get(dataSetID);
        if (populateFully) {
            kDB.populateFully();
        }
        return dataSets.get(dataSetID);
    }

    public synchronized void refreshDatabase(int dataSetID) {
        if (dataSets.keySet().contains(dataSetID)) {
            dataSets.remove(dataSetID);
        }
    }

    public boolean isDatabase(int dataSetID) {
        DataSetDAO dDA = new DataSetDAO();
        DataSetList eDBs = dDA.getKnowledgeDataSets();
        Iterator<IDataSet> it = eDBs.iterator();
        while (it.hasNext()) {
            IDataSet eDB = it.next();
            if (eDB.getID() == dataSetID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNumberOfDataSets() {
        return dataSets.size();
    }

    @Override
    public HashMap<Integer, KnowledgeDataSet> getDataSets() {
        return dataSets;
    }
}
