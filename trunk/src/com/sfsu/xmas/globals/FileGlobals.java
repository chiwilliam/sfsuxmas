package com.sfsu.xmas.globals;

import com.sfsu.xmas.data_sets.ExpressionDataSet;
import java.io.File;

public class FileGlobals {

    public static final boolean PATHWAYS_ENABLED = false;
    private static String EXPRESSION_DATA_SUB_DIR = "xd_expression" + File.separatorChar;
    private static String KNOWLEDGE_SUB_DIR = "xd_knowledge" + File.separatorChar;
    public static final String COLLAPSED_POSTFIX = "_collapsed";
//    public static final String DATABASE_PREFIX = "xmas_";
    public static final String KNOWLEDGE_DATABASE_PREFIX = "xmas_lib_";
    public static final String ANALYSIS_CAPTURE_PREFIX = "acap_";
    public static final String XML_FILE_EXTENSION = ".xml";
    public static final String DATA_FILE_EXTENSION = "txt";
    public static final String EXPRESSION_DATA_FILE_PREFIX = "ex_";
    public static final String PROBE_DATA_FILE_PREFIX = "pr_";
    public static final String PATHWAY_DATA_FILE_PREFIX = "pa_";
    public static final String GO_DATA_FILE_PREFIX = "go_";
    public static final String LABEL_DATA_FILE_PREFIX = "la_";
    public static final String TRAJECTORY_DATA_FILE_PREFIX = "traj_";
    
    
    public static final String EXPRESSION_DATABASE = "xmas";
    public static final String KNOWLEDGE_DATABASE = "xmas";
    
    // Prod
//    public static String DB_PROFILE = "xmas";
//    private static String FILE_ROOT = "undefined";
//    public static final String PASS = "xmas9thfloor";
//    public static final boolean SINGLE_DATASET = false;
    
//    Dev
//    public static final String DB_PROFILE = "xmas_dev";
//    private static String FILE_ROOT = "/Users/bdalziel/Documents/xmas_files/";
//    public static final String PASS = "xmas9thfloor";
//    public static final boolean SINGLE_DATASET = true;
    
    // Local
    public static String DB_PROFILE = "xmas_da";
    private static String FILE_ROOT = "/home/da/Documents/IDEAProjects/XMAS/xmas_files/";
    public static final String PASS = "";
    public static final boolean SINGLE_DATASET = true;
    
    public static String getActiveRoot() {

        if(FILE_ROOT.contains("undefined") ==true){
            String OS = java.lang.System.getProperty("os.name");
            if(OS.contains("Windows") == true){
                FILE_ROOT = "C:\\xmas\\xmas_files\\";
            }
            else{
                FILE_ROOT = "/home/bdalziel/xmas_files/";
            }
        }        
        return FILE_ROOT;
    }

    public static boolean setRoot(String root) {
        if (!root.endsWith(String.valueOf(File.separatorChar))) {
            root = root + File.separatorChar;
        }
        FILE_ROOT = root;
        return true;
    }

    public static boolean setDataRoot(String root) {
        if (!root.endsWith(String.valueOf(File.separatorChar))) {
            root = root + File.separatorChar;
        }
        EXPRESSION_DATA_SUB_DIR = root;
        return true;
    }

    public static boolean setAnnotationsRoot(String root) {
        if (!root.endsWith(String.valueOf(File.separatorChar))) {
            root = root + File.separatorChar;
        }
        KNOWLEDGE_SUB_DIR = root;
        return true;
    }

    public static String getRoot() {
        return getActiveRoot();
    }

    public static String getJustDataRoot() {
        return EXPRESSION_DATA_SUB_DIR;
    }

    public static String getExpressionDataRoot() {
        return getActiveRoot() + EXPRESSION_DATA_SUB_DIR;
    }

    public static String getJustAnnotationsRoot() {
        return KNOWLEDGE_SUB_DIR;
    }

    public static String getKnowledgeDataRoot() {
        return getActiveRoot() + KNOWLEDGE_SUB_DIR;
    }

    public static String getActiveDatabaseRoot(ExpressionDataSet activeDB) {
//        Database activeDB = DatabaseManager.getActiveDataBase();
//        ExpressionDatabase activeDB = sam.getActiveDatabase();
        if (activeDB != null) {
            return getActiveRoot() + activeDB.getName() + File.separatorChar;
        }
        return getRoot();
    }//    public static String getActiveSnapshotDir(ExpressionDatabase activeDB) {
//        String root = getActiveDatabaseRoot(activeDB);
//        String activeTrajFile = sam.getActiveTrajectoryFile().getName();
//        if (activeTrajFile != null && activeTrajFile.length() > 0) {
//            root += activeTrajFile + File.separatorChar;
//        }
//        return root;
//    }
}
