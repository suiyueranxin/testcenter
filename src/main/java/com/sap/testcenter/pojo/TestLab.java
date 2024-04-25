package com.sap.testcenter.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sap.testcenter.model.CoverageInfo;
import com.sap.testcenter.model.GitInfo;
import com.sap.testcenter.model.RunInfo;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

@Entity
@Table(name = "tc_testlab")
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDefs({@TypeDef(name="json", typeClass=JsonStringType.class)})
public class TestLab {
    private int id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @Type(type = "json")
    private GitInfo gitInfo;
    @Type(type = "json")
    private RunInfo runInfo;
    @Type(type = "json")
    private CoverageInfo coverageInfo;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public GitInfo getGitInfo() {
        return gitInfo;
    }
    public void setGitInfo(GitInfo gitInfo) {
        this.gitInfo = gitInfo;
    }
    public RunInfo getRunInfo() {
        return runInfo;
    }
    public void setRunInfo(RunInfo runInfo) {
        this.runInfo = runInfo;
    }
    public CoverageInfo getCoverageInfo() {
        return coverageInfo;
    }
    public void setCoverageInfo(CoverageInfo coverageInfo) {
        this.coverageInfo = coverageInfo;
    }
    
    
}
