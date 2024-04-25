package com.sap.testcenter.model;

import java.io.Serializable;
import java.util.List;

public class JiraQuery implements Serializable {
    private String name;
    private List<String> func;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getFunc() {
        return func;
    }
    public void setFunc(List<String> func) {
        this.func = func;
    }
}
