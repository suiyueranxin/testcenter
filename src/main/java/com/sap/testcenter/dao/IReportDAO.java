package com.sap.testcenter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sap.testcenter.pojo.Report;

public interface IReportDAO extends JpaRepository<Report,String> {
    @Query("select r from Report r where r.name=:name")
    Report getOne(@Param("name") String name);
}
