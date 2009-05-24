package filter;

import com.sfsu.xmas.filter.AbstractFilter;

public abstract class AttributeFilterAbstract extends AbstractFilter {

    protected String attributeType;
    
    public AttributeFilterAbstract(String attributeType) {
        this.attributeType = attributeType;
    }
    
    public String getType() {
        return "Attribute";
    }
    
    public String getOperator() {
        String includeExclude = "=";
        if (isExcluded()) {
            includeExclude = "!=";
        }
        return includeExclude;
    }
    
    public String getAttributeType() {
        return attributeType;
    }
    
}
