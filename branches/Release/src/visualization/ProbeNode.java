/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualization;

import org.w3c.dom.Node;

/**
 *
 * @author bdalziel
 */
public class ProbeNode {

    public int id;
    Node actualNode;

    public ProbeNode(Node probe, int id) {
        this.actualNode = probe;
        this.id = id;
    }

    public ProbeNode(Node leaf) {
        this.actualNode = leaf;
        this.id = Integer.parseInt(leaf.getAttributes().getNamedItem("NodeID").getTextContent());
    }

    public int getID() {
        return id;
    }

    public Node getActualNode() {
        return actualNode;
    }
}
