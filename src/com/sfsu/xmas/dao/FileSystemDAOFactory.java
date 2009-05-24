package com.sfsu.xmas.dao;


public class FileSystemDAOFactory {

    public TrajectoryFileDAO getTrajectoryFileDAO(String name, int parentDB) {
        return new TrajectoryFileDAO(name, parentDB);
    }

    public CapturedAnalysisDAO getCapturedAnalysisDAO(String name, int parentDB) {
        return new CapturedAnalysisDAO(name, parentDB);
    }

    public DataFileDAO getDataFileDAO() {
        return new DataFileDAO();
    }
}
