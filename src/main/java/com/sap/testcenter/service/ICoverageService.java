package com.sap.testcenter.service;

public interface ICoverageService {
    public String genCoverage(String name);
    public String getCoverage(String name);
    public String getHanaSchema(String schemaName);
}
