package filter;

import com.sfsu.xmas.filter.MultiProbeFilter;
import org.w3c.dom.Element;
import xml.ClusterDocument;

public class ClusterFilter extends MultiProbeFilter {

    ClusterDocument clusterDoc;

    public ClusterFilter(int clustID) {
        this.id = clustID;

//        this.clusterDoc = XMLClusterFactory.getUniqueInstance().getClusterDocument();
//        Probes probes = clusterDoc.getProbesForCluster(id);
//        for (int i = 0; i < probes.size(); i++) {
//            Probe p = (Probe) probes.get(i);
//            ProbeFilter pf = new ProbeFilter(p.getID());
//            fl.add(pf);
//        }
    }

    @Override
    public String getXPathToNode() {
        // Escape parent Probe to TP5
        return getXPath() + "/../..";
    }

    @Override
    public String getType() {
        return "Cluster";
    }
    
    public String getSimpleDescription() {
//        return pway.getDescription();
        return "TODO: Simple Desc";
    }

    public ClusterDocument getClusterDocument() {
        return clusterDoc;
    }

    @Override
    public void appendFilterSpecificAttributes(Element filterElement) {
        filterElement.setAttribute("clustering", getClusterDocument().getFileName());
    }
}
