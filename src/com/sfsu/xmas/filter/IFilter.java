/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.filter;

/**
 *
 * @author bdalziel
 */
public interface IFilter {

    boolean isActive();
    
    void activate();
    
    void deActivate();
    
    String getXPath();
    
    String getXPathToNode();
    
    String getSimpleDescription();
    
    String getType();
    
    int getListIndex();
}
