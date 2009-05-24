package filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.filter.MultiProbeFilter;
import com.sfsu.xmas.data_structures.knowledge.Pathway;
import com.sfsu.xmas.filter.ProbeFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PathwayFilter extends MultiProbeFilter {

    protected Pathway pway;

    public PathwayFilter(Pathway pathway) {
        this.pway = pathway;
        // Create a filter for each
        String[] probeIDs = pathway.getMemberProbes();

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
    
    public Pathway getLabel () {
        return pway;
    }

    @Override
    public String getXPathToNode() {
        // Escape parent Probe to TP5
        return getXPath() + "/../..";
    }

    @Override
    public String getType() {
        return "Pathway";
    }

    public String getSimpleDescription() {
        return pway.getName();
    }

    @Override
    public Node toXML(Document doc) {
        Element filter = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTER);

        filter.setAttribute("id", String.valueOf(pway.getID()));
        filter.setAttribute("value", pway.getName());
        filter.setAttribute("excluded", String.valueOf(isExcluded()));
        filter.setAttribute("active", String.valueOf(isActive()));
        filter.setAttribute("type", LabelFilter.class.getName());

        return filter;
    }
}
