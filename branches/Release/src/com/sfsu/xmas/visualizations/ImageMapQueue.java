/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sfsu.xmas.visualizations;

import java.util.ArrayList;

/**
 *
 * @author bdalziel
 */
public class ImageMapQueue {

    private static ImageMapQueue instance;
    
    private ArrayList<String> readyImageMapKeys;
    
    private ImageMapQueue() {
        readyImageMapKeys = new ArrayList<String>();
    }
    
    public static ImageMapQueue getInstance() {
        if (instance == null) {
            instance = new ImageMapQueue();
        }
        return instance;
    }
    
    public synchronized boolean isImageMapReady (String key) {
        return readyImageMapKeys.contains(key);
    }
    
    public synchronized void imageMapIsReady(String key) {
        readyImageMapKeys.add(key);
    }
    
}
