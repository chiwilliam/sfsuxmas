/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.data_structures.Probes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author bdalziel
 */
public class ClusterDocument {

    private Document doc;
    private XPathFactory factory;

    public ClusterDocument(Document doc) {
        this.doc = doc;
        factory = XPathFactory.newInstance();
    }

    public Node getRootNode() {
        Node node = null;
        try {
            node = doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }

    public String getFileName() {
        String parentFile = "";
        try {
            XPath xPath = factory.newXPath();
            Node rootNode = getRootNode();
            XPathExpression compiledXPath = xPath.compile(".//ClusterSummary/File/@Value");
            parentFile = (String) compiledXPath.evaluate(rootNode, XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ClusterDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parentFile;
    }

    public String getParentFileName() {
        String parentFile = "";
        try {
            XPath xPath = factory.newXPath();
            Node rootNode = getRootNode();
            XPathExpression compiledXPath = xPath.compile(".//ClusterSummary/ParentFile/@Value");
            parentFile = (String) compiledXPath.evaluate(rootNode, XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(CapturedAnalysisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parentFile;
    }

    public String getFileRoot() {
        String parentFile = "";
        try {
            XPath xPath = factory.newXPath();
            Node rootNode = getRootNode();
            XPathExpression compiledXPath = xPath.compile(".//ClusterSummary/ParentFileFull/@Value");
            parentFile = (String) compiledXPath.evaluate(rootNode, XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(CapturedAnalysisDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parentFile;
    }

    public String getKValue() {
        String parentFile = "";
        try {
            XPath xPath = factory.newXPath();
            Node rootNode = getRootNode();
            XPathExpression compiledXPath = xPath.compile(".//ClusterSummary/k/@Value");
            parentFile = (String) compiledXPath.evaluate(rootNode, XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ClusterDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parentFile;
    }

    public String render() {
        StringBuffer sb = new StringBuffer();
        String highlightedRow = "";
        String fileName = getFileRoot() + getFileName();
//        String activeFileName = XMLClusterFactory.getUniqueInstance().getCurrentFile();
//        String parentFile = getParentFileName();
//        String activeSnapShot = XMLSnapShotFactory.getUniqueInstance().getCurrentFile();
//        if (fileName.equals(activeFileName) && parentFile.equals(activeSnapShot)) {
//            highlightedRow = " <span style=\"color: #66CC00; font-weight: bold;\">(Active)</span> ";
//        }
//        sb.append("<li><b class=\"clusterNode\">Cluster</b>: <a href=\"javascript:void(0)\" onclick=\"return load_cluster('" + getFileName() + "')\">Load</a> | <b class=\"nodeAttribute\">(K=" + getKValue() + ")</b> \"" + getFileName() + highlightedRow + "\"" +
//                "<ul>" +
//                renderClusterSummary() +
//                "</ul>" +
//                "</li>");
        return sb.toString();
    }

    public String renderClusterSummary() {
        StringBuffer summary = new StringBuffer();
        summary.append(
                "<li>Summary" +
                "<ul>");

        for (int i = 0; i < Integer.parseInt(getKValue()); i++) {
            summary.append(
                    "<li>" +
                    "<b>Clust' Quality " + i + "</b>: " + getClusterQuality(i) +
                    "</li>");
        }

        summary.append(
                "</ul>" +
                "</li>");
        return summary.toString();
    }

    public String getClusterQuality(int clusterID) {
        Number correlation = null;
        Number euclidean = null;
        try {
            XPath xp = factory.newXPath();
            String correlationLeafFilter = "//Cluster[@ID=" + String.valueOf(clusterID) + "]/Diameters/correlation/@Value";
            System.out.println(correlationLeafFilter + ": cluster XPATH");
            correlation = (Number) xp.evaluate(correlationLeafFilter, doc, XPathConstants.NUMBER);


            String euclideanLeafFilter = "//Cluster[@ID=" + String.valueOf(clusterID) + "]/Diameters/euclidean/@Value";
            System.out.println(euclideanLeafFilter + ": cluster XPATH");
            euclidean = (Number) xp.evaluate(euclideanLeafFilter, doc, XPathConstants.NUMBER);

        } catch (XPathExpressionException ex) {
            Logger.getLogger(ClusterDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        double correl = 0;
        double euclid = 0;
        if (correlation != null && euclidean != null) {
            correl = correlation.doubleValue();
            euclid = euclidean.doubleValue();
        } else {
            System.err.println(ClusterDocument.class.getSimpleName() + ": null cluster qualities for cluster: " + clusterID);
        }

        return String.valueOf(euclid) + " | " + String.valueOf(correl);
    }

    public Probes getProbesForCluster(int clusterID) {
        Probes probes = null; //new Probes();
        try {

            NodeList nl = null;
            XPath xp = factory.newXPath();
            String leafFilter = "//Cluster[@ID=" + String.valueOf(clusterID) + "]/Probe";
            System.out.println(leafFilter + ": cluster XPATH");
            nl = (NodeList) xp.evaluate(leafFilter, doc, XPathConstants.NODESET);
            System.out.println(nl.getLength() + " nodes returned from cluster");

            String[] ids = getProbeIDArray(nl);

//            probes.setProbeIDs(ids);

        } catch (XPathExpressionException ex) {
            Logger.getLogger(ClusterDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return probes;
    }

    private static String[] getProbeIDArray(NodeList probeList) {
        String[] probeIDs = new String[probeList.getLength()];
        for (int j = 0; j < probeList.getLength(); j++) {
            Node probe = probeList.item(j);
            probeIDs[j] = probe.getAttributes().getNamedItem("ID").getTextContent();
        }
        return probeIDs;
    }
}
