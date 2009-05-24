/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.util.ArrayList;

/**
 *
 * @author bdalziel
 */
public class MatchingProbesForFilterSet extends ArrayList<ArrayList<String>> {

    public void addProbeArray(ArrayList<String> probeIDs) {
        add(probeIDs);
    }

    // guessing this gets the intersection...
    public boolean isProbeIsolated(String probeID) {
        // See if probe is in all inclusion filters
        for (int filterIndex = 0; filterIndex < size(); filterIndex++) {
            ArrayList<String> probesIsolatedByFilter = get(filterIndex);
            if (probesIsolatedByFilter.size() > 0 && !probesIsolatedByFilter.contains(probeID)) {
                // Probe isn't isolated by filter
                return false;
            }
        }
        // All of the filters include this probe
        return true;
    }

    public boolean isProbeExcluded(String probeID) {
        // See if probe is in all inclusion filters
        for (int v = 0; v < size(); v++) {
            ArrayList<String> probesExcludedByFilter = get(v);
            if (probesExcludedByFilter.contains(probeID)) {
                // Probe doesn't pass filter
                return true;
            }
        }
        // Probe isn't explicitly excluded
        return false;
    }
}
