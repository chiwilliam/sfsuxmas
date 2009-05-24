/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import com.sfsu.xmas.data_structures.knowledge.MultiProbeDataStructure;
import java.util.ArrayList;

/**
 *
 * @author bdalziel
 */
public interface ISearchResult {

    // TOdo, get this to work - can't be bothered with generics right now
    public ArrayList<? extends MultiProbeDataStructure> getResult();
    
}
