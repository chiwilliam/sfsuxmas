/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

/**
 *
 * @author bdalziel
 */
public class AttributeFilterString extends AttributeFilterAbstract {

    protected String attributeID;
    
    public AttributeFilterString(String attributeType, String attributeID) {
        super(attributeType);
        this.attributeID = attributeID;
    }
    
    public String getXPath() {
        return ".//*[@" + attributeType + getOperator() + "\"" + attributeID + "\"]";
    }
    
    public String getXPathToNode() {
        return getXPath() + "/../..";
    }

    public String getSimpleDescription() {
        return "ID: " + attributeID;
    }
    
    public String getAttributeID() {
        return attributeID;
    }
    
}
