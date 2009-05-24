package com.sfsu.xmas.data_structures.expression;

public class TimePeriod {

    private int id;
    private String description;
    private int sampleCount = 0;

    public TimePeriod(int internalID, String description, int sampleCount) {
        this.id = internalID;
        this.description = description;
        this.sampleCount = sampleCount;
    }

    /**
     * Only to be used during construction, when IDs have not been assigned
     * @param description
     * @param sampleCount
     */
//    public TimePeriod( String description, int sampleCount) {
//        this.id = 0;
//        this.description = description;
//        this.sampleCount = sampleCount;
//    }

    public TimePeriod(int internalID, String description) {
        this.id = internalID;
        this.description = description;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getSampleCount() {
        return sampleCount;
    }
}
