package com.sfsu.xmas.util;

import java.text.DecimalFormat;

public class NumberUtils {

    public static double getDoubleToTwoDecimalPlaces(double value) {
        DecimalFormat twoDForm = new DecimalFormat("0.000");
        return Double.valueOf(twoDForm.format(value));
    }

    public static double getDoubleToFourDecimalPlaces(double value) {
        DecimalFormat twoDForm = new DecimalFormat("0.0000");
        return Double.valueOf(twoDForm.format(value));
    }

    public static synchronized  int binom(int n, int m) {
        int[] b = new int[n+1];
        b[0] = 1;
        for (int i = 1; i <= n; ++i) {
            b[i] = 1;
            for (int j = i-1; j> 0; --j) {
                b[j] += b[j-1];
            }
        }
        return b[m];
    }
}
