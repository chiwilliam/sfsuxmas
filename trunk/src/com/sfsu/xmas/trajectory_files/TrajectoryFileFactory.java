package com.sfsu.xmas.trajectory_files;

import com.sfsu.xmas.globals.FileGlobals;
import java.util.HashMap;

public class TrajectoryFileFactory {

    private static TrajectoryFileFactory uniqueInstance;
    private HashMap<Integer, HashMap<String, TrajectoryFile>> databaseTrajectoryFileMap;

    public static TrajectoryFileFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TrajectoryFileFactory();
        }
        return uniqueInstance;
    }

    public static void refreshUniqueInstance() {
        uniqueInstance = new TrajectoryFileFactory();
    }

    private TrajectoryFileFactory() {
        databaseTrajectoryFileMap = new HashMap<Integer, HashMap<String, TrajectoryFile>>();
    }

    public synchronized TrajectoryFile getFile(int dataSetID, String fileName) {
        if (!fileName.startsWith(FileGlobals.TRAJECTORY_DATA_FILE_PREFIX)) {
            fileName = FileGlobals.TRAJECTORY_DATA_FILE_PREFIX + fileName;
        }
        if (fileName.endsWith(FileGlobals.XML_FILE_EXTENSION)) {
            fileName = fileName.substring(0, fileName.length() - (FileGlobals.XML_FILE_EXTENSION.length()));
        }
        if (!databaseTrajectoryFileMap.keySet().contains(dataSetID) || !databaseTrajectoryFileMap.get(dataSetID).keySet().contains(fileName)) {
            HashMap<String, TrajectoryFile> fileMap;
            if (databaseTrajectoryFileMap.keySet().contains(dataSetID)) {
                fileMap = databaseTrajectoryFileMap.get(dataSetID);
            } else {
                fileMap = new HashMap<String, TrajectoryFile>();
            }
            TrajectoryFile trajectoryDocument = new TrajectoryFile(fileName, dataSetID);

            fileMap.put(fileName, trajectoryDocument);
            databaseTrajectoryFileMap.put(dataSetID, fileMap);
        }

        return databaseTrajectoryFileMap.get(dataSetID).get(fileName);
    }

    public int getNumberOfDatabases() {
        return databaseTrajectoryFileMap.size();
    }

    public HashMap<Integer, HashMap<String, TrajectoryFile>> getFiles() {
        return databaseTrajectoryFileMap;
    }
}
