/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_creation;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author bdalziel
 */
public class TrajectoryComparator implements Comparator<Integer[]>, Serializable {

    public int compare(Integer[] i1, Integer[] i2) {
        if (i1.length == i2.length) {
            for (int i = 0; i < i1.length && i < i2.length; i++) {
                if (i1[i] == null || i2[i] == null) {
                    if (i1[i] == null && i2[i] == null) {
                        return 0;
                    } else if (i1[i] == null) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    if (!i1[i].equals( i2[i])) {
                        return i1[i].compareTo(i2[i]);
                    }
                }
            }
            return 0;
        } else {
            return 1;
        }
    }

    public boolean equals(Object o1, Object o2) {
        Integer[] i1 = (Integer[]) o1;
        Integer[] i2 = (Integer[]) o2;

        if (i1.length == i2.length) {
            for (int i = 0; i < i1.length && i < i2.length; i++) {
                if (!i1[i].equals(i2[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
