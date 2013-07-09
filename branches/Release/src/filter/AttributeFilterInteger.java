/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

/**
 *
 * @author bdalziel
 */
public class AttributeFilterInteger extends AttributeFilterAbstract {

    protected int attributeID;
    
    public AttributeFilterInteger(String attributeType, int attributeID) {
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
    
    /**
     * Accessor method for the id of the attribute central to the filter
     * @return the identifier required by the filter
     */
    public int getAttributeID() {
        return attributeID;
    }
}
