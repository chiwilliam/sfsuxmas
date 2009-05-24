/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import com.sfsu.xmas.data_structures.Probes;

/**
 *
 * @author bdalziel
 */
public class ProbeSearchResults {

    protected String searchTerm = "";
    protected String searchAttribute = "";
    protected Probes result;

//    ProbeSearchResults(String term, String attribute, String[] ids) {
//        this.searchTerm = term;
//        this.searchAttribute = attribute;
//        Probes probes = new Probes();
//        if (ids.length > 0) {
//            probes.setProbeIDs(ids);
//        }
//        this.result = probes;
//    }

    public Probes getResults() {
        return result;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getSearchAttribute() {
        return searchAttribute;
    }
}
