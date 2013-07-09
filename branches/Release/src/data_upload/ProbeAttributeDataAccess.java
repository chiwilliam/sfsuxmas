/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data_upload;

import com.sfsu.xmas.dao.BaseMySQLDAO;
import com.sfsu.xmas.data_files.readers.ProbeAttributeFileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bdalziel
 */
public class ProbeAttributeDataAccess extends BaseMySQLDAO {
    
    private String databaseName;

    private static final int maxQuery = 1000;
    private static String _PopulateTable = "INSERT INTO " + knowledgeDataSetKey + ".probes VALUES %_VALUES_%;";
    private static String _CountProbes = "SELECT COUNT(*) AS numberOfProbes FROM " + knowledgeDataSetKey + ".probes;";

    public ProbeAttributeDataAccess(String kDatabaseName) {
        this.databaseName = kDatabaseName;
    }

    public void populateTable(ProbeAttributeFileReader fr) {
        if (probeTableEmpty()) {
            HashMap<String, String[]> data = fr.getFileData();
            Set<String> probeIDs = data.keySet();
            Iterator<String> probeIDIt = probeIDs.iterator();
            populateTable(data, probeIDIt);
        } else {
            System.err.println(ProbeAttributeDataAccess.class.getSimpleName() + ": Probe table is not empty");
        }
    }

    public void populateTable(HashMap<String, String[]> data, Iterator<String> probeIDIt) {
        int dataRowIndex = 0;
        StringBuffer probeData = new StringBuffer();
        while (probeIDIt.hasNext() && dataRowIndex < maxQuery) {
            String probeID = probeIDIt.next();
            String[] attributes = data.get(probeID);

            if (probeData.length() > 0) {
                probeData.append(", ");
            }
            probeData.append("(");
            for (int i = 0; i < attributes.length; i++) {
                if (i > 0) {
                    probeData.append(", ");
                }
                String value = attributes[i];
                // Better safe than sorry
                if (value != null) {
                    value = value.substring(0, Math.min(value.length(), 240));
                    probeData.append("\"" + value.replace("\"", "") + "\"");
                } else {
                    value = "null";
                }
            }
            probeData.append(")");
            dataRowIndex++;
        }
        // Execute the code we have:
        executeSQL(_PopulateTable.replace("%_VALUES_%", probeData.toString()));
        if (probeIDIt.hasNext()) {
            // Continue with the next row
            populateTable(data, probeIDIt);
        }
    }

    /**
     * Determines the number of probes in the probe attribute table
     * @return - true if the table is empty, false if there are > 0 members
     */
    private boolean probeTableEmpty() {
        ResultSet rs = getResultSet(_CountProbes);
        if (rs != null) {
            try {
                if (rs.next()) {
                    String noProbes = (String) rs.getString("numberOfProbes");
                    if (noProbes != null && noProbes.length() > 0) {
                        int numberOfProbes = Integer.parseInt(noProbes);
                        if (numberOfProbes > 0) {
                            return false;
                        }
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(ProbeAttributeDataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    @Override
    protected String useActiveDB(String sql) {
        return sql.replace(knowledgeDataSetKey, databaseName);
    }
}
