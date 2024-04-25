package com.sap.testcenter.model;

import java.util.List;

public class JiraData {
    private String name;
    private List<String> dims;
    private List<List<String>> measures;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getDims() {
        return dims;
    }
    public void setDims(List<String> dims) {
        this.dims = dims;
    }
    public List<List<String>> getMeasures() {
        return measures;
    }
    public void setMeasures(List<List<String>> measures) {
        this.measures = measures;
    }
    
    
}
