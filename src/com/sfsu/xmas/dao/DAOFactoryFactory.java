package com.sfsu.xmas.dao;

/**
 * Singleton Factory for other factories
 * @author bdalziel
 */     
public class DAOFactoryFactory {

    private static DAOFactoryFactory uniqueInstance;

    private DAOFactoryFactory() {
    }

    public static DAOFactoryFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DAOFactoryFactory();
        }
        return uniqueInstance;
    }

    public DatabaseDAOFactory getDatabaseDAOFactory() {
        return new DatabaseDAOFactory();
    }

    public FileSystemDAOFactory getFileSystemDAOFactory() {
        return new FileSystemDAOFactory();
    }
}
