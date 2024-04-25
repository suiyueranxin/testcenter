package com.sap.testcenter.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sap.testcenter.dao.IJiraIssueDAO;
import com.sap.testcenter.dao.IReportDAO;
import com.sap.testcenter.dao.IReportJqlDAO;
import com.sap.testcenter.dao.spec.JiraIssueSpec;
import com.sap.testcenter.model.JiraData;
import com.sap.testcenter.model.JiraQuery;
import com.sap.testcenter.model.JiraQueryMethod;
import com.sap.testcenter.model.ReportWeek;
import com.sap.testcenter.pojo.JiraIssue;
import com.sap.testcenter.pojo.Report;
import com.sap.testcenter.pojo.ReportJql;
import com.sap.testcenter.service.IReportService;
import com.sap.testcenter.util.JiraCenter;

import org.apache.log4j.Logger;

@Service
public class ReportServiceImpl implements IReportService {
    static Logger logger = Logger.getLogger(ReportServiceImpl.class);
    
    @Autowired IReportDAO reportDAO;
    @Autowired IJiraIssueDAO jiraIssueDAO;
    @Autowired IReportJqlDAO reportJqlDAO;
    
    @Scheduled(cron="0 0 17 * * ?")
    public void updateJiraStatus() {
        jiraIssueDAO.deleteAll();
        initialData("TC_Analytics2");
    }
    
    public void initialData(String name) {
        ReportJql rjql = reportJqlDAO.getOne(name);
        String jql = rjql.getJql();
        List<JiraIssue> issues = JiraCenter.getInstance().queryIssues(jql);
        logger.info("amy " + JSON.toJSONString(issues, true));
        for (JiraIssue issue : issues) {
            jiraIssueDAO.save(issue);
        }
    }
    
//    public String queryReport(String name) {
//        Object o = null;
//        Report report = reportDAO.getOne(name);
//       
//        List<JiraQuery> jiraQueries = report.getJiraQueries();
//        for (JiraQuery query : jiraQueries) {
//           o = queryIssue(query);
//        }
//        return JSONObject.toJSONString(o);
//    }
    public String queryReport(String name) {
        ReportJql rjql = reportJqlDAO.getOne(name);
        String jql = rjql.getJql();
        List<JiraIssue> issues = JiraCenter.getInstance().queryIssues(jql);
        return JSONObject.toJSONString(issues);
    }
    
    public String countBy(List<String> by, List<String> filters) {
        List<Object[]> result = jiraIssueDAO.countBy(by, filters);
        return JSONObject.toJSONString(result);
    }
    
    public String list(List<String> fields, List<String> filters) {
        List<Object[]> result = jiraIssueDAO.list(fields, filters);
        return JSONObject.toJSONString(result);
    }
    
    public String calcReportStatus(List<String> calculators) {
        return "OnTrack";
    }
    
    public String calcReportWeek(String startDate, String endDate) {
        ReportWeek rw = new ReportWeek();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(1);
        try {
            int curWeek = cal.get(Calendar.WEEK_OF_YEAR);
            cal.setTime(df.parse(startDate));
            int startWeek = cal.get(Calendar.WEEK_OF_YEAR);
            cal.setTime(df.parse(endDate));
            int endWeek = cal.get(Calendar.WEEK_OF_YEAR);
            
            if (startWeek <= endWeek) {
                rw.setTotal(endWeek-startWeek+1);
            }
            if (curWeek>= startWeek && curWeek <= endWeek) {
                rw.setCurrent(curWeek-startWeek+1);
            } else {
                rw.setCurrent(0);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return JSONObject.toJSONString(rw);
    }
}
