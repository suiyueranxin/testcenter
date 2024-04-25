package com.sap.testcenter.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tc_report_jql")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportJql {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String reportName;
    public String getReportName() {
        return reportName;
    }
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    public String getJql() {
        return jql;
    }
    public void setJql(String jql) {
        this.jql = jql;
    }
    private String jql;
}
