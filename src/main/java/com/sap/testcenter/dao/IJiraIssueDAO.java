package com.sap.testcenter.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sap.testcenter.pojo.JiraIssue;

public interface IJiraIssueDAO extends JpaRepository<JiraIssue,String>, JpaSpecificationExecutor<JiraIssue>, IJiraIssueExtDAO {
    @Query("select count(i.jiraId), i.status from JiraIssue i where i.issueType=:issueType group by i.status")
    List<Object[]> countByStatus(@Param("issueType") String issueType);
    
    @Query("select count(i.jiraId), i.assignee, i.status from JiraIssue i where i.issueType=:issueType group by i.assignee, i.status")
    List<Object[]> countByAssigneeAndStatus(@Param("issueType") String issueType);
    
    @Query("select i from JiraIssue i where i.issueType=:issueType and i.priority in :priorities")
    List<Object[]> findByPriority(String issueType, List<String> priorities);
    
    @Query("select i from JiraIssue i where i.issueType=:issueType and i.status in :status")
    List<Object[]> findByStatus(String issueType, List<String> status);
    
    
}
