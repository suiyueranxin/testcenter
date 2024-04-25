package com.sap.testcenter.model;

import java.io.Serializable;

public class JiraView implements Serializable{
    private String dims;
    private String measures;
    private String group;
    
    public String getDims() {
        return dims;
    }
    public void setDims(String dims) {
        this.dims = dims;
    }
    public String getMeasures() {
        return measures;
    }
    public void setMeasures(String measures) {
        this.measures = measures;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    
}
