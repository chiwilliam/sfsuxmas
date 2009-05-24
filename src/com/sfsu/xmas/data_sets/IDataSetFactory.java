package com.sfsu.xmas.data_sets;

import java.util.HashMap;

public interface IDataSetFactory {

    public AbstractDataSet getDataSet(int dataSetID, boolean populateFully);
    
    public HashMap getDataSets();
    
    public int getNumberOfDataSets();
    
}
