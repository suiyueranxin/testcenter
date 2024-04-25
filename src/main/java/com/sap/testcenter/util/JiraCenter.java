package com.sap.testcenter.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueLink;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.sap.testcenter.model.JiraData;
import com.sap.testcenter.model.JiraQuery;
import com.sap.testcenter.model.JiraView;
import com.sap.testcenter.pojo.JiraIssue;

public class JiraCenter {
    private static JiraCenter instance;
    private JiraRestClient restClient;
    
    private JiraCenter() {
        String url = "https://ubtjira.smec.sap.corp/";
        String user = "i068096";
        String password = "314151Lx";
        String defaultJql = "project = TCLIENT AND \"Epic Link\" = TCLIENT-521 AND  Sprint = \"Wave 1.2 Takt 5 (1.2 - 2.2）\"";
        try {
            URI jiraServerUri = new URI(url);
            AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
            restClient = (JiraRestClient) factory.createWithBasicHttpAuthentication(jiraServerUri, user, password);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    public static synchronized JiraCenter getInstance() {
        if (instance == null) {
            instance = new JiraCenter();
        }
        return instance;
    }
    
    //  Get issues by query
    public Iterable queryNativeIssues(String jql, String fields) {
        SearchRestClient searchClient = restClient.getSearchClient();
        Set<String> fieldsSet = new HashSet<>(Arrays.asList(fields.split(",")));
        SearchResult results = searchClient.searchJql(jql, null, null, fieldsSet).claim();
        return results.getIssues();
    }
    
    // Get issues by query
    public Iterable queryNativeIssues(String jql) {
        SearchRestClient searchClient = restClient.getSearchClient();
        SearchResult results = searchClient.searchJql(jql).claim();
        return results.getIssues();
    }
    
    // Get issues by query
    public List<JiraIssue> queryIssues(String jql) {
        return simplify(queryNativeIssues(jql));
    }
    
    // Convert issue to JiraData
//    public JiraData convertJiraData(JiraIssue jiraIssue, JiraQuery query) {
//        JiraData data = new JiraData();
//        String name = query.getName();
//        List<String> fields = query.getFields();
//        
//        data.setName(name);
//        data.setDims(fields);
//        data.set
//        
//        SearchRestClient searchClient = restClient.getSearchClient();
//        SearchResult results = searchClient.searchJql(jql).claim();
//        List<JiraIssue> issues = simplify(results.getIssues());
//        List<String> dims = new ArrayList<>();
//        List<List<String>> measures = new ArrayList<>();
//        for(JiraIssue issue : issues) {
//            
//        }
//        return data;
//    }
    
    public JiraIssue simplify(Issue is) {
        JiraIssue issue = new JiraIssue();
        issue.setJiraId(is.getKey());
        issue.setSummary(is.getSummary());
        issue.setAssignee(is.getAssignee().getDisplayName());
        issue.setReporter(is.getReporter().getDisplayName());
        issue.setStatus(is.getStatus().getName());
        issue.setIssueType(is.getIssueType().getName());
        issue.setPriority(is.getPriority().getName());
        Iterator<IssueLink> itLinks = is.getIssueLinks().iterator();
        String links = "";
        while (itLinks.hasNext()) {
            IssueLink link = itLinks.next();
            if (link.getIssueLinkType().getName().equals("Blocks")) {
                links += (link.getTargetIssueKey()) + ",";
            }
        }
        if (!links.isEmpty()) {
            issue.setBlockedLinks(links.substring(0, links.length()-1));
        }
        return issue;
     }
     
    public List<JiraIssue> simplify(Iterable issues) {
        List<JiraIssue> rIssues = new ArrayList<>();
        Iterator<Issue> it = issues.iterator();
        while (it.hasNext()) {
            Issue is = it.next();
            rIssues.add(simplify(is));
        }
        return rIssues;
    }
    
    //  Print issues information
    private void printIssues(Iterable issues) {
        Iterator<Issue> it = issues.iterator();
        while (it.hasNext()) {
            Issue is = it.next();
            String key = "key: " + is.getKey();
            String summary = "summary: " + is.getSummary();
            String assignee = "assignee: " + is.getAssignee().getDisplayName();
            String reporter = "reporter: " + is.getReporter().getDisplayName();
            String status = "status: " + is.getStatus().getName();
            String info = key + "\n" + summary + "\n" + assignee + "\n" + reporter + "\n" + status + "\n";
            System.out.println(info);
        }
    }
    
    public static void main(String[] args) {
        String jql = "project = \"B1 Thin Client \"";
        List<JiraIssue> issues = getInstance().queryIssues(jql);
        System.out.println(JSONObject.toJSONString(issues));
    }
    
}
