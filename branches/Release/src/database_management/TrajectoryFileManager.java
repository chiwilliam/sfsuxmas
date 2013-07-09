package database_management;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import com.sfsu.xmas.data_sets.ExpressionDataSetMultiton;

public class TrajectoryFileManager {

    private static ExpressionDataSet database;

    public static void setActiveDataBase(int dataSetID) {
//        activeDatabase = db;
        database = ExpressionDataSetMultiton.getUniqueInstance().getDataSet(dataSetID, false);
    }

    public static ExpressionDataSet getActiveDataBase() {
        return database;
    }

    public static String cleanDatabaseName(String databaseName) {
        return databaseName.trim().replace(' ', '_');
    }

    public static boolean isNameOriginal(String proposedDBName) {
//        if (proposedDBName == null ||
//                proposedDBName.length() <= 0 ||
//                proposedDBName.contains(Databases.getPrefix()) ||
//                proposedDBName.contains(AnnotationDatabases.getPrefix())) {
//            return false;
//        } else {
//            DatabaseDataAccess dda = new DatabaseDataAccess();
//            if (dda.checkConnection()) {
//                Databases databases = dda.getDatabases();
//                Iterator it = databases.iterator();
//                boolean nameUnique = true;
//                while (it.hasNext()) {
//                    Database db = (Database) it.next();
//                    String dbName = db.getName();
//                    if (dbName.equals(proposedDBName)) {
//                        nameUnique = false;
//                    }
//                }
//                if (nameUnique) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
                return false;
//            }
//        }
    }
}
