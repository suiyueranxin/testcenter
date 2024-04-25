package com.sap.testcenter.model;

import java.util.List;

public class CoverageInfo {
    private String sourcePath;
    private String targetPath;
    private String htmlPath;
    private String cssPath;
    private List<CoverSummary> summaries;
    
    public String getSourcePath() {
        return sourcePath;
    }
    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }
    public String getTargetPath() {
        return targetPath;
    }
    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
    public String getHtmlPath() {
        return htmlPath;
    }
    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }
    public String getCssPath() {
        return cssPath;
    }
    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }
    public List<CoverSummary> getSummaries() {
        return summaries;
    }
    public void setSummaries(List<CoverSummary> summaries) {
        this.summaries = summaries;
    }
    
}
