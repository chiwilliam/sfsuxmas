/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

/**
 *
 * @author bdalziel
 */
public class SearchResultManager {

    protected ProbeSearchResults probeResult;
    protected PathwaySearchResults pathwayResult;
    
    private static SearchResultManager uniqueInstance;

    private SearchResultManager() {
        
    }
    
    public static SearchResultManager getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SearchResultManager();
        }
        return uniqueInstance;
    }

    public ProbeSearchResults getProbeSearchResult() {
        return this.probeResult;
    }
    
    public PathwaySearchResults getPathwaySearchResult() {
        return this.pathwayResult;
    }
    
    public void setProbeSearchResult(ProbeSearchResults result) {
        this.probeResult = result;
    }

    public void setPathwaySearchResult(PathwaySearchResults result) {
        this.pathwayResult = result;
    }
}
