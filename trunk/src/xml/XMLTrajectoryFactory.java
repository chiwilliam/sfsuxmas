package xml;


public class XMLTrajectoryFactory {

//    private String fileName = "";
//    private String fileExtension = ".xml";
//    private static XMLTrajectoryFactory uniqueInstance;
//    private Document doc = null;
//    private TrajectoryDocument trajectoryDoc = null;
//
//    public static boolean isNameOriginal(ExpressionDatabase eDB, String proposedName) {
//        if (proposedName == null || proposedName.length() <= 0) {
//            return false;
//        } else {
//            File[] l = ExpressionDataFileManager.getFilesForActiveDatabase(eDB);
//
//            if (l != null && l.length > 0) {
//                for (int i = 0; i < l.length; i++) {
//                    int dotLocation = l[i].getName().indexOf('.');
//                    if (dotLocation > 0) {
//                        String tempFileName = l[i].getName().substring(0, dotLocation);
//                        String extension = l[i].getName().substring(dotLocation, l[i].getName().length());
//                        if (extension.equals(".xml")) {
//                            // Trajectory file
//                            if (tempFileName.toLowerCase().equals(proposedName.toLowerCase())) {
//                                return false;
//                            }
//                        } else {
//                            // Not a trajecotry XML file
//                        }
//                    } else {
//                        // Folder
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//    protected void getFilesForDataBase(ExpressionDatabase eDB) {
//        String root = fileRoot;
//        if (eDB != null) {
//            root += eDB.getName() + File.separatorChar;
//
//            System.out.println(XMLTrajectoryFactory.class.getName() + ": Getting file information for: " + root);
//            String[] l = new java.io.File(root).list();
//            if (l != null && l.length > 0) {
//                for (int i = 0; i < l.length; i++) {
//                    int dotLocation = l[i].indexOf('.');
//                    if (dotLocation > 0) {
//                        String tempFileName = l[i].substring(0, dotLocation);
//                        String extension = l[i].substring(dotLocation, l[i].length());
//                        if (extension.equals(".xml")) {
//                        }
//                    } else {
//                        // Folder
//                    }
//                }
//            }
//        } else {
//        }
//    }
//
//    public static synchronized XMLTrajectoryFactory getUniqueInstance() {
//        if (uniqueInstance == null) {
//            uniqueInstance = new XMLTrajectoryFactory();
//        }
//        return uniqueInstance;
//    }
//
//    private XMLTrajectoryFactory() {
//        
//    }
//
//    public Document getDocument() {
//        return doc;
//    }
//
//    public TrajectoryDocument getTrajectoryDocument() {
//        return trajectoryDoc;
//    }
//
//    private boolean parseFile(ExpressionDatabase eDB) {
//        if (eDB != null) {
//            if (!fileName.equals("")) {
//                try {
//                    DOMParser parser = new DOMParser();
//                    parser.parse(Globals.FileUpload.getRoot() + eDB.getName() + File.separatorChar + fileName + fileExtension);
//                    doc = parser.getDocument();
//                    trajectoryDoc = new TrajectoryDocument(doc);
//                } catch (SAXException ex) {
//                    Logger.getLogger(XMLTrajectoryFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    return false;
//                } catch (IOException ex) {
//                    Logger.getLogger(XMLTrajectoryFactory.class.getName()).log(Level.SEVERE, null, ex);
//                    return false;
//                }
//            }
//        } else {
//            return false;
//        }
//        return true;
//    }
//
//    public String getCurrentFile() {
//        return fileName;
//    }
//
//    public String getChildFileRoot(SessionAttributeManager sam) {
//        String childFileRoot = Globals.FileUpload.getRoot();
//        Database activeDB = sam.getActiveDatabase();
//        if (activeDB != null) {
//            if (fileName.endsWith(CreateXML.collapsedSuffix)) {
//                childFileRoot += activeDB.getName() + File.separatorChar + fileName.substring(0, fileName.length() - CreateXML.collapsedSuffix.length()) + File.separatorChar;
//            } else {
//                childFileRoot += activeDB.getName() + File.separatorChar + fileName + File.separatorChar;
//            }
//        }
//        return childFileRoot;
//    }
//
//    public String getCurrentFileLocation(SessionAttributeManager sam) {
//        return getChildFileRoot(sam);
//    }
////
//////    public boolean setDatabase(String db) {
//////        if (!database.equals(db)) {
//////            database = db;
//////            return parseFile();
//////        }
//////        return true;
//////    }
//    public boolean setFileName(SessionAttributeManager sam, String file) {
//        if (!file.equals(fileName)) {
//            fileName = file;
//            return parseFile(sam);
//        }
//        return true;
//    }
//
//    public static Document parseFile(String fileN) {
//        Document d = null;
//        if (!fileN.equals("")) {
//            try {
//                DOMParser parser = new DOMParser();
//                parser.parse(fileN);
//                d = parser.getDocument();
//            } catch (SAXException ex) {
//                Logger.getLogger(XMLTrajectoryFactory.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(XMLTrajectoryFactory.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return d;
//    }
}
