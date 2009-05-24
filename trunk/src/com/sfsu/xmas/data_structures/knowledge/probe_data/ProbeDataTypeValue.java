package com.sfsu.xmas.data_structures.knowledge.probe_data;


public class ProbeDataTypeValue {

    private String dataTypeValue;
    private ProbeDataType probeDataType;
    
    public ProbeDataTypeValue(String value, ProbeDataType dataType) {
        this.dataTypeValue = value;
        this.probeDataType = dataType;
    }
    
    public String getAttribute() {
        return dataTypeValue;
    }
    
    public ProbeDataType getHeader() {
        return probeDataType;
    }
    
    public String getLink() {
        return probeDataType.getLink(dataTypeValue);
    }
    
}
