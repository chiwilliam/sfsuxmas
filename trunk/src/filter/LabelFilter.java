package filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.filter.MultiProbeFilter;
import com.sfsu.xmas.data_structures.knowledge.Label;
import com.sfsu.xmas.filter.ProbeFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class LabelFilter extends MultiProbeFilter {

    protected Label label;

    public LabelFilter(Label label) {
        this.label = label;
        // Create a filter for each
        String[] probeIDs = label.getMemberProbes();

        for (int i = 0; i < probeIDs.length; i++) {
            ProbeFilter pf = new ProbeFilter(probeIDs[i]);
            fl.add(pf);
        }
    }
    
    @Override
    public void exclude() {
        super.exclude();
        fl.excludeAll();
    }
    
    public Label getLabel () {
        return label;
    }

    @Override
    public String getXPathToNode() {
        // Escape parent Probe to TP5
        return getXPath() + "/../..";
    }

    @Override
    public String getType() {
        return "Annotation";
    }

    public String getSimpleDescription() {
        return label.getName();
    }

    @Override
    public Node toXML(Document doc) {
        Element filter = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTER);

        filter.setAttribute("id", String.valueOf(label.getID()));
        filter.setAttribute("value", label.getName());
        filter.setAttribute("description", label.getDescription());
        filter.setAttribute("excluded", String.valueOf(isExcluded()));
        filter.setAttribute("active", String.valueOf(isActive()));
        filter.setAttribute("type", LabelFilter.class.getName());

        return filter;
    }
}
