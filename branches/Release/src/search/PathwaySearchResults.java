/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import com.sfsu.xmas.data_structures.knowledge.Pathways;

/**
 *
 * @author bdalziel
 */
public class PathwaySearchResults {

    protected String searchTerm;
    protected String searchAttribute;
    protected Pathways result;

//    public PathwaySearchResults(String term, String attribute, int[] ids) {
//        this.searchTerm = term;
//        this.searchAttribute = attribute;
//        Pathways pathways = new Pathways(sam, ids);
//        if (ids.length > 0) {
////            pathways.setPathwayIDs(ids);
//        }
//        this.result = pathways;
//    }

    public Pathways getResults() {
        return result;
    }
}
