package com.sfsu.xmas.filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import filter.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ProbeFilter extends NodeFilter {
    
    public ProbeFilter(String probeID) {
        super("Probe", "ID", probeID);
    }
    
    @Override
    public String getXPathToNode() {
        return getXPath() + "/..";
    }
    
    @Override
    public String getType() {
        String type = "Isolation";
        if (isExcluded()) {
            type = "Exclusion";
        }
        return "Probe " + type;
    }
    
    @Override
    public Node toXML(Document doc) {
        Element filter = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTER);
        
        filter.setAttribute("value", getAttributeID());
        filter.setAttribute("excluded", String.valueOf(isExcluded()));
        filter.setAttribute("active", String.valueOf(isActive()));
        filter.setAttribute("type", ProbeFilter.class.getName());
        
        return filter;
    }
    
}
