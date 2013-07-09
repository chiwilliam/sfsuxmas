package com.sfsu.xmas.filter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class AbstractFilter implements IFilter {

    protected int listIndex;
    protected boolean isActive = true;
    protected boolean excluded = false;
    protected static final String mStringAnd = " and ";
    protected static final String mStringOr = " or ";

    public boolean isActive() {
        return isActive;
    }

    public void activate() {
        isActive = true;
    }

    public void deActivate() {
        isActive = false;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void exclude() {
        this.excluded = true;
    }

    public void include() {
        this.excluded = false;
    }

    public void setListIndex(int index) {
        this.listIndex = index;
    }

    public int getListIndex() {
        return listIndex;
    }

    public Node toXML(Document doc) {
        Element filter = doc.createElement("filter");
        
        Element typeElement = doc.createElement("filter_type");
        typeElement.setAttribute("value", AbstractFilter.class.getName());
        filter.appendChild(typeElement);
        
        Element excludedElement = doc.createElement("excluded");
        excludedElement.setAttribute("value", String.valueOf(isExcluded()));
        filter.appendChild(excludedElement);
        
        return filter;
    }
}
