package com.sap.testcenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.testcenter.pojo.TestLab;

public interface ITestLabDAO extends JpaRepository<TestLab,String>{

}
