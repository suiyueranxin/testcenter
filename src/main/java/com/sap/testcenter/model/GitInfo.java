package com.sap.testcenter.model;

public class GitInfo {
    private String remoteUrl;
    private String branch;
    private String localPath;
    public String getRemoteUrl() {
        return remoteUrl;
    }
    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getLocalPath() {
        return localPath;
    }
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
