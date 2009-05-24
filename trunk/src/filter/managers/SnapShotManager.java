package filter.managers;

public class SnapShotManager {

    private String rootFolder;

    public SnapShotManager(String rootFold) {
        this.rootFolder = rootFold;

    }

    public String renderSnapShots() {
        StringBuffer sb = new StringBuffer();
        String[] l = new java.io.File(rootFolder).list();
        // For each file (not folder)
        if (l != null && l.length > 0) {
            for (int i = 0; i < l.length; i++) {
                int dotLocation = l[i].indexOf('.');
                if (dotLocation > 0) {
                    String fileName = l[i].substring(0, dotLocation);

                    String fileLocation = rootFolder + fileName + ".xml";
//                    Document doc = XMLTrajectoryFactory.parseFile(fileLocation);
//                    SnapShotDocument ssDoc = new SnapShotDocument(doc);
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
