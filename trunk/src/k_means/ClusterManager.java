/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package k_means;

/**
 *
 * @author bdalziel
 */
public class ClusterManager {

    private String rootFolder;

    public ClusterManager(String rootFold) {
        this.rootFolder = rootFold;
    }

    public String renderClusters() {
        StringBuffer sb = new StringBuffer();
        String[] l = new java.io.File(rootFolder).list();
        // For each file (not folder)
        if (l != null) {
            for (int i = 0; i < l.length; i++) {
                int dotLocation = l[i].indexOf('.');
                if (dotLocation > 0) {
                    String fileName = l[i].substring(0, dotLocation);

                    String fileLocation = rootFolder + fileName + ".xml";
//                    Document doc = XMLClusterFactory.parseFile(fileLocation);
//                    ClusterDocument ssDoc = new ClusterDocument(doc);
//                    sb.append(ssDoc.render());
                } else {
                // Folder
                }
            }
        } else {
            return "";
        }
        return sb.toString();
    }
}
