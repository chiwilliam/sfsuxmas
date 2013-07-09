package filter;

import com.sfsu.xmas.dao.CapturedAnalysisDAO;
import com.sfsu.xmas.data_structures.knowledge.GOTerm;
import com.sfsu.xmas.filter.MultiProbeFilter;
import com.sfsu.xmas.filter.ProbeFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GOTermFilter extends MultiProbeFilter {

    protected GOTerm goTerm;

    public GOTermFilter(GOTerm goTerm) {
        this.goTerm = goTerm;
        // Create a filter for each
        String[] probeIDs = goTerm.getMemberProbes();

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

    public GOTerm getLabel() {
        return goTerm;
    }

    @Override
    public String getXPathToNode() {
        // Escape parent Probe to TP5
        return getXPath() + "/../..";
    }

    @Override
    public String getType() {
        return "GO Term";
    }

    public String getSimpleDescription() {
        return goTerm.getName();
    }

    @Override
    public Node toXML(Document doc) {
        Element filter = doc.createElement(CapturedAnalysisDAO.XML_KEY_FILTER);

        filter.setAttribute("id", goTerm.getStringID());
        filter.setAttribute("value", goTerm.getName());
        filter.setAttribute("excluded", String.valueOf(isExcluded()));
        filter.setAttribute("active", String.valueOf(isActive()));
        filter.setAttribute("type", LabelFilter.class.getName());

        return filter;
    }
}
