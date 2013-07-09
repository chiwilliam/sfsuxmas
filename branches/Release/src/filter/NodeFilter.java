/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;

/**
 *
 * @author bdalziel
 */
public class NodeFilter extends AttributeFilterString {
    
    public NodeFilter(String nodeType, String attributeType, String attributeID) {
        super(attributeType, attributeID);
    }

    @Override
    public String getXPath() {
        return "@" + getAttributeType() + getOperator() + getAttributeID();
    }
    
    @Override
    public String getType() {
        return "XML Node";
    }
    
    
    
}
