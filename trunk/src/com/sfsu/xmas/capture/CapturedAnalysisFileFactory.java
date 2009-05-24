package com.sfsu.xmas.capture;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;
import com.sfsu.xmas.globals.FileGlobals;
import java.util.HashMap;

public class CapturedAnalysisFileFactory {

    private static CapturedAnalysisFileFactory uniqueInstance;
    private HashMap<Integer, HashMap<String, CapturedAnalysisDAO>> databaseToAnalysisFileMap;

    public static CapturedAnalysisFileFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CapturedAnalysisFileFactory();
        }
        return uniqueInstance;
    }

    public static void refreshUniqueInstance() {
        uniqueInstance = new CapturedAnalysisFileFactory();
    }

    private CapturedAnalysisFileFactory() {
        databaseToAnalysisFileMap = new HashMap<Integer, HashMap<String, CapturedAnalysisDAO>>();
    }

    public synchronized CapturedAnalysisDAO getFile(int dataSetID, String fileName) {
        if (!fileName.startsWith(FileGlobals.ANALYSIS_CAPTURE_PREFIX)) {
            fileName = FileGlobals.ANALYSIS_CAPTURE_PREFIX + fileName;
        }
        if (!databaseToAnalysisFileMap.keySet().contains(dataSetID) || !databaseToAnalysisFileMap.get(dataSetID).keySet().contains(fileName)) {
            HashMap<String, CapturedAnalysisDAO> fileMap = new HashMap<String, CapturedAnalysisDAO>();

            CapturedAnalysisDAO caDoc = new CapturedAnalysisDAO(fileName, ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false).getID());

            fileMap.put(fileName, caDoc);
            databaseToAnalysisFileMap.put(dataSetID, fileMap);
        }

        return databaseToAnalysisFileMap.get(dataSetID).get(fileName);
    }

    public int getNumberOfDatabases() {
        return databaseToAnalysisFileMap.size();
    }
}
