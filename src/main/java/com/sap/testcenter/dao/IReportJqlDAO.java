package com.sap.testcenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sap.testcenter.pojo.ReportJql;

public interface IReportJqlDAO extends JpaRepository<ReportJql,String>{
    @Query("select j from ReportJql j where j.reportName=:reportName")
    ReportJql getOne(@Param("reportName") String name);
}
