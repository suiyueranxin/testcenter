package com.sap.testcenter.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IJiraIssueExtDAO {
    public List<Object[]> countBy(List<String> groupBy, List<String> filters);
    public List<Object[]> list(List<String> filters);
    public List<Object[]> list(List<String> fields, List<String> filters);
    public void testSucc();
}
