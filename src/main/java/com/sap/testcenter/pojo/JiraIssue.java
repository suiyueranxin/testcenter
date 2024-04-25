package com.sap.testcenter.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tc_report_issue")
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
    @Id
    @JSONField(ordinal=1, name = "Jira Id")
    private String jiraId;
    @JSONField(ordinal=2, name = "Issue Type")
    private String issueType;
    @JSONField(ordinal=3, name = "Summary")
    private String summary;
    @JSONField(ordinal=4, name = "Status")
    private String status;
    @JSONField(ordinal=5, name = "Assignee")
    private String assignee;
    @JSONField(ordinal=6, name = "Reporter")
    private String reporter;
    @JSONField(ordinal=7, name = "Priority")
    private String priority;
    private String linkedId;
    @JSONField(ordinal=8, name = "Blocked By")
    private String blockedLinks;
    
    public String getBlockedLinks() {
        return blockedLinks;
    }

    public void setBlockedLinks(String blockedLinks) {
        this.blockedLinks = blockedLinks;
    }

    public String getJiraId() {
        return jiraId;
    }

    public void setJiraId(String jiraId) {
        this.jiraId = jiraId;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
//    public JiraIssue(String jiraId, String summary, String assignee, String reporter, String status) {
//        this.jiraId = jiraId;
//        this.summary = summary;
//        this.assignee = assignee;
//        this.reporter = reporter;
//        this.status = status;
//    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String sprint) {
        this.priority = sprint;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    

}
