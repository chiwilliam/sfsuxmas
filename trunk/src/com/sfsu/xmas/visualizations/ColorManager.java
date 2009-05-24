/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.visualizations;

import java.awt.Color;

/**
 *
 * @author bdalziel
 */
public class ColorManager {

    public static synchronized Color getColor(double exp, double minimum, double maximum) {
        int red = 0;
        int green = 0;
        int blue = 0;

        double range = maximum - minimum;

        if (true) {
            blue = 0 + Math.max((int) (255 - Math.round((exp - minimum) * (255.0 / range))), 0);
            red = 255 - Math.max((int) (255 - Math.round((exp - minimum) * (255.0 / range))), 0);
        }
        if (red < 0 || red > 255) {
            System.err.println("BAD Color: " + red);
        }
        if (green < 0 || green > 255) {
            System.err.println("BAD Color: " + red);
        }
        if (blue < 0 || blue > 255) {
            System.err.println("BAD Color: " + red);
        }

        Color c = null;
        try {
            c = new Color(red, green, blue);
        } catch (IllegalArgumentException ex) {
            c = Color.BLACK;
        }

        return c;
    }

    public static synchronized String getHexColor(double exp, int minimum, int maximum) {
        Color color = getColor(exp, minimum, maximum);

        String rgb = Integer.toHexString(color.getRGB());
        rgb = rgb.substring(2, rgb.length());

        return rgb;
    }
}
