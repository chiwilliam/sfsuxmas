/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

/**
 *
 * @author bdalziel
 */
public class TrajNodeFilter extends AttributeFilterInteger {

    private int timePeriod;

    public TrajNodeFilter(int nodeID, int timePeriod) {
        super("NodeID", nodeID);
        this.timePeriod = timePeriod;
    }

    @Override
    public String getXPathToNode() {
        if (timePeriod == 5) {
            return ".//TP" + timePeriod + "[@" + attributeType + getOperator() + "\"" + attributeID + "\"]";
        }
        return ".//TP" + timePeriod + "[@" + attributeType + getOperator() + "\"" + attributeID + "\"]//TP5";
    }

    @Override
    public String getType() {
        String type = "Isolation";
        if (isExcluded()) {
            type = "Exclusion";
        }
        return "Node " + type;
    }
    
    @Override
    public String getSimpleDescription() {
         return "TP" + getTimePeriod();
    }

    public String getConcatenator() {
        String includeExclude = " | ";
        if (isExcluded()) {
            includeExclude = " | ";
        }
        return includeExclude;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    @Override
    public String getXPath() {
        StringBuffer nodeAttributeConditions = new StringBuffer();
        nodeAttributeConditions.append("@" + getAttributeType() + getOperator() + getAttributeID());
        return nodeAttributeConditions.toString();
    }
}
