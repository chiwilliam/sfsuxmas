package com.sfsu.xmas.data_structures.expression;

public class Sample {

    private int id;
    private String description;
    private TimePeriod parentTimePeriod;
    private int parentTimePeriodID;

    public Sample(int id, String description, TimePeriod timePeriod) {
        this.id = id;
        this.description = description;
        this.parentTimePeriod = timePeriod;
        if (timePeriod != null) {
            parentTimePeriodID = timePeriod.getID();
        }
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TimePeriod getTimePeriod() {
        return parentTimePeriod;
    }

    public int getTimePeriodID() {
        return parentTimePeriodID;
    }
}
