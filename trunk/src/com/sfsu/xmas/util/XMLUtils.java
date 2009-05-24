package com.sfsu.xmas.util;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class XMLUtils {

    public static void writeXmlFile(Document doc, String filename) {
        try {
            // Prepare the DOM document for writing
            DOMSource source = new DOMSource(doc);
    
            // Prepare the output file
            File file = new File(filename);
            final StreamResult sr = new StreamResult(file.toURI().getPath());
    
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, sr);
        } catch (TransformerConfigurationException ex) {
            System.err.println(XMLUtils.class.getName() + ": Unable to make XML file with path = " + filename + " " + ex);
        } catch (TransformerException ex) {
            System.err.println(XMLUtils.class.getName() + ": Unable to make XML file with path = " + filename + " " + ex);
        }
    }
    
}
