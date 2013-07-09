package com.sfsu.xmas.dao;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import com.sfsu.xmas.monitoring.ExecutionTimer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sfsu.xmas.capture.SLoadAnalysis;

public abstract class XMLFileDAO {

//    protected String name;
    public String fileName;
    private XPathFactory factory;
    protected int parentDataSetID;

    public XMLFileDAO(String name, int parentDB) {
        this.fileName = name;
        this.parentDataSetID = parentDB;
        factory = XPathFactory.newInstance();
    }

    public String getFileName() {
        return fileName;
    }

//    public String getName() {
//        return name;
//    }
    public String getXpathString(String xPath) {
        String result = "";
        result = getXpathResult(xPath);
        System.out.println("Resultant value:- \"" + result + "\"");
        return result;
    }

    public int getXpathInteger(String xPath) {
        try {
            int result = Integer.parseInt(getXpathResult(xPath));
            System.out.println("Resultant value:- \"" + result + "\"");
            return result;
        } catch (NumberFormatException ex) {
            System.err.println(XMLFileDAO.class.getName() + ": Integer not returned, as expected, from: " + xPath);
        }
        return 0;
    }

    public double getXpathDouble(String xPath) {
        try {
            double result = Double.parseDouble(getXpathResult(xPath));
            System.out.println("Resultant value:- \"" + result + "\"");
            return result;
        } catch (NumberFormatException ex) {
            System.err.println(XMLFileDAO.class.getName() + ": Double not returned, as expected, from: " + xPath);
        }
        return 0;
    }

    private synchronized String getXpathResult(String xPath) {
        String result = "";
        XPath xp = factory.newXPath();
        try {
            ExecutionTimer et = new ExecutionTimer();
            et.start();
            result = xp.evaluate(xPath, getRootNode());
            et.end();
            System.out.println("DURATION = " + et.duration() + " in class " + XMLFileDAO.class.getName() + ", XPATH: " + xPath);
        } catch (XPathExpressionException ex) {
            System.err.println(XMLFileDAO.class.getName() + ": Trajectory document doesn't have requested attribute: " + xPath);
        }
        return result;
    }

    public synchronized NodeList getNodeList(String xPathString) {
        NodeList nodes = null;
        Node rootNode = getRootNode();
        try {
            XPath xPath = factory.newXPath();
            XPathExpression compiledXPath = xPath.compile(xPathString);

            ExecutionTimer et = new ExecutionTimer();
            et.start();
            nodes = (NodeList) compiledXPath.evaluate(rootNode, XPathConstants.NODESET);
            et.end();
            System.out.println("DURATION = " + et.duration() + " in class " + XMLFileDAO.class.getName() + ", XPATH: " + xPathString);
            if (nodes != null) {
                System.out.println("Resultant NodeList size:- \"" + nodes.getLength() + "\"");
            } else {
                System.out.println("Resultant NodeList is NULL");
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(SLoadAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rootNode = null;
        }
        return nodes;
    }

    public synchronized Node getRootNode() {
        Node node = null;
        try {
            node = getDocument().getDocumentElement();
        } catch (Exception e) {
            System.err.println(XMLFileDAO.class.getName() + ": no root node for doc");
        }
        return node;
    }

    protected abstract Document getDocument();
}
