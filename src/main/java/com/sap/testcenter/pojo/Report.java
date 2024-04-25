package com.sap.testcenter.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonStringType;

import com.alibaba.fastjson.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sap.testcenter.model.JiraQuery;

@Entity
@Table(name = "tc_report")
//@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDefs({@TypeDef(name="json", typeClass=JsonStringType.class)})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "status")
    private String status;
    
    @Type(type = "json")
    @Column(name="jiraQueries", columnDefinition="json")
    private List<JiraQuery> jiraQueries;
    
    @Column(name = "createdTime")
    private Date createdTime;
    
    @Column(name = "tags")
    private String tags;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<JiraQuery> getJiraQueries() {
        return jiraQueries;
    }

    public void setJiraQueries(List<JiraQuery> jiraQueries) {
        this.jiraQueries = jiraQueries;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
//        return "Report [id=" + id + ", title=" + title + "]";
        return JSONObject.toJSONString(this);
    }
}
