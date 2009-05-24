package com.sfsu.xmas.data_structures.expression;

import java.util.Vector;

public class TimePeriods extends Vector<TimePeriod> {
    
    public TimePeriod getTimePeriodForSample(int sampleIndex) {
        
        int sampIndex = 0;
        
        for (int i = 0; i < this.size(); i++) {
            TimePeriod tp = (TimePeriod) get(i);
            sampIndex += tp.getSampleCount();
            if (sampleIndex <= sampIndex) {
                return tp;
            }
        }
        return null;
    }
}
