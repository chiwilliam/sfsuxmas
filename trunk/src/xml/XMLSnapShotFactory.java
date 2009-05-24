package xml;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLSnapShotFactory {

//    protected String fileName = "";
//    protected String fileExtension = ".xml";
//    
//    private static XMLSnapShotFactory uniqueInstance;
//    protected Document mDoc = null;
//
//    public static synchronized XMLSnapShotFactory getUniqueInstance() {
//        if (uniqueInstance == null) {
//            uniqueInstance = new XMLSnapShotFactory();
//        }
//        return uniqueInstance;
//    }
//
//    private XMLSnapShotFactory() {
//        this.parentFile = XMLTrajectoryFactory.getUniqueInstance();
//        parseFile();
//    }
//
//    public Document getDocument() {
//        return mDoc;
//    }
//
//    private boolean parseFile() {
//        if (!fileName.equals("")) {
//            try {
//                DOMParser parser = new DOMParser();
//                parser.parse(parentFile.getChildFileRoot() + fileName + fileExtension);
//                mDoc = parser.getDocument();
//            } catch (SAXException ex) {
//                Logger.getLogger(XMLSnapShotFactory.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            } catch (IOException ex) {
//                Logger.getLogger(XMLSnapShotFactory.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public String getCurrentFileLocation() {
//        return parentFile.getChildFileRoot() + fileName + File.separatorChar;
//    }
//
//    public boolean setFileName(String file) {
//        if (!file.equals(fileName)) {
//            fileName = file;
//            return parseFile();
//        }
//        return true;
//    }
    
}
