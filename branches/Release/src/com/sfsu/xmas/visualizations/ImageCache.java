/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.visualizations;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author bdalziel
 */
public class ImageCache {

    private static ImageCache instance;
    private static JCS cache;

    private ImageCache() {
        try {
            cache = JCS.getInstance("imageCache");
        } catch (CacheException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
        }
        return instance;
    }

    public String storeImageMap(String imageMap, String key) {
        if (key == null || key.length() <= 0) {
            key = "ts_" + new Date().getTime() + "_rand_" + Math.random();
        }

        try {
//            cache.put("IMAGE_KEY_" + key, image);
            cache.put("IMAGE_MAP_KEY_" + key, imageMap);
        } catch (CacheException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }
//    public BufferedImage getImage(String key) {
//        BufferedImage image = null;
//        image = (BufferedImage) cache.get("IMAGE_KEY_" + key);
//        return image;
//    }
    public String getImageMap(String key) {
        String imageMap = "";
        imageMap = (String) cache.get("IMAGE_MAP_KEY_" + key);
        if (imageMap == null || imageMap.length() <= 0) {
            imageMap = "";
        }
        return imageMap;
    }
}
