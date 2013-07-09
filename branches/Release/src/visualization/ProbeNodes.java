/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author bdalziel
 */
public class ProbeNodes extends Vector<ProbeNode> {

    public ProbeNodes(NodeList probes) {
        if (probes != null && probes.getLength() > 0) {
            for (int i = 0; i < probes.getLength(); i++) {
                Node probe = probes.item(i);
                String nodeID = probe.getAttributes().getNamedItem("ID").getTextContent();
                add(new ProbeNode(
                        probe,
                        Integer.parseInt(nodeID)
                        ));
            }
        }
    }
}
