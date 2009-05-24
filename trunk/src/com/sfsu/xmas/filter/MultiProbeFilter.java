package com.sfsu.xmas.filter;

import org.w3c.dom.Element;

public abstract class MultiProbeFilter extends AbstractFilter {

    protected FilterList fl = new FilterList();
    protected int id;

    public abstract String getType();

    public String getXPathToNode() {
        return getXPath() + "/../..";
    }

    public String getXPath() {
        return "";
    }

    public int getID() {
        return id;
    }

    public void appendFilterSpecificAttributes(Element filterElement) {
        
    }

    /**
     * 
     * @return A FilterList of ProbeFilters
     */ 
    public FilterList getFilterList() {
        return fl;
    }
}
