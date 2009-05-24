package xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLClusterFactory {

//    XMLSnapShotFactory parentFile;
//
//    String fileName = "";
//    String fileExtension = ".xml";
//    
//    private static XMLClusterFactory uniqueInstance;
//    Document doc = null;
//    ClusterDocument clusterDoc = null;
//    
//    public static synchronized XMLClusterFactory getUniqueInstance() {
//        if (uniqueInstance == null) {
//            uniqueInstance = new XMLClusterFactory();
//        }
//        return uniqueInstance;
//    }
//
//    private XMLClusterFactory() {
//        this.parentFile = XMLSnapShotFactory.getUniqueInstance();
//        parseFile();
//    }
//
//    public Document getDocument() {
//        parseFile();
//        return doc;
//    }
//
//    public ClusterDocument getClusterDocument() {
//        parseFile();
//        return clusterDoc;
//    }
//
//    public void refreshFile() {
//        parseFile();
//    }
//
//    public void unload() {
//        fileName = "";
//        doc = null;
//        clusterDoc = null;
//    }
//
//    private boolean parseFile() {
//        if (!fileName.equals("")) {
//            try {
//                DOMParser parser = new DOMParser();
//                parser.parse(parentFile.getCurrentFileLocation() + fileName + fileExtension);
////                parser.parse(fileName + fileExtension);
//                doc = parser.getDocument();
//                clusterDoc = new ClusterDocument(doc);
//            } catch (SAXException ex) {
//                Logger.getLogger(XMLClusterFactory.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            } catch (IOException ex) {
//                Logger.getLogger(XMLClusterFactory.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public String getCurrentFile() {
//        return fileName;
//    }
//
//    public boolean setFileName(String file) {
//        if (!file.equals(fileName)) {
//            fileName = file;
//            return parseFile();
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
