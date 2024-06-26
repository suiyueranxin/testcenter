package com.sap.testcenter.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.joda.time.DateTime;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.BasicUser;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Field;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.Lists;

public class JiraUtil {
    public static String BaseURL = "https://ubtjira.smec.sap.corp/";
    public static String User = "i068096";
    public static String Password = "314151Lx";
    private static URI jiraServerUri;
    private static boolean quiet = false;
    private static final long BUG_TYPE_ID = 1L; // JIRA magic value
    private static final long TASK_TYPE_ID = 3L; // JIRA magic value
    private static final DateTime DUE_DATE = new DateTime();
    private static final String PRIORITY = "Trivial";
    private static final String DESCRIPTION = "description";

    

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {

        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        try {
            jiraServerUri = new URI(BaseURL);
            final JiraRestClient restClient = (JiraRestClient) factory
                    .createWithBasicHttpAuthentication(jiraServerUri, User,
                            Password);
//            getAllProjects(restClient);
//            getProject(restClient, "TCLIENT");
//            getIssue(restClient, "TCLIENT-2456");
//            getIssueFields(restClient, "TCLIENT-2456");
            String bugsJql = "project = TCLIENT AND issuetype = Bug AND priority in (\"Very High\", High) AND \"Epic Link\" = TCLIENT-521 order by created DESC";
            Iterable<Issue> bugs = queryIssues(restClient, bugsJql);
            printIssues(bugs);
            
            String featuresJql = "project = TCLIENT AND issuetype = Feature AND \"Epic Link\" = TCLIENT-521 order by created DESC";
            Iterable<Issue> features = queryIssues(restClient, featuresJql);
            printIssues(features);

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
    }
    
    private static void println(Object o) {
        if (!quiet) {
            System.out.println(o);
        }
    }
    
//    Print issues information
    private static void printIssues(Iterable issues) {
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

    private static void parseArgs(String[] argsArray) throws URISyntaxException {
        final List<String> args = Lists.newArrayList(argsArray);
        if (args.contains("-q")) {
            quiet = true;
            args.remove(args.indexOf("-q"));
        }

        if (!args.isEmpty()) {
            jiraServerUri = new URI(args.get(0));
        }
    }

    private static Transition getTransitionByName(
            Iterable<Transition> transitions, String transitionName) {
        for (Transition transition : transitions) {
            if (transition.getName().equals(transitionName)) {
                return transition;
            }
        }
        return null;
    }
    
//    Get issues by query
    private static Iterable queryIssues(final JiraRestClient jiraRestClient, String jql)  {

	    	SearchRestClient searchClient = jiraRestClient.getSearchClient();
	        
	        SearchResult results = searchClient.searchJql(jql).claim();
	        return results.getIssues();
    }
    
    // 得到所有项目信息
    private static void getAllProjects(final JiraRestClient restClient)
            throws InterruptedException, ExecutionException {
        try {

            Promise<Iterable<BasicProject>> list = restClient
                    .getProjectClient().getAllProjects();
            Iterable<BasicProject> a = list.get();
            Iterator<BasicProject> it = a.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

        } finally {
        }
    }

    // 得到单一项目信息
    private static void getProject(final JiraRestClient restClient,
            String porjectKEY) throws InterruptedException, ExecutionException {
        try {

            Project project = restClient.getProjectClient()
                    .getProject(porjectKEY).get();
            System.out.println(project);

        } finally {
        }
    }

    // 得到单一问题信息
    private static void getIssue(final JiraRestClient restClient,
            String issueKEY) throws InterruptedException, ExecutionException {
        try {

            Promise<Issue> list = restClient.getIssueClient()
                    .getIssue(issueKEY);
            Issue issue = list.get();
            System.out.println(issue);

        } finally {
        }
    }
    
    // 创建问题
    public static BasicIssue createIssue(final JiraRestClient jiraRestClient,
            IssueInput newIssue) {
        BasicIssue basicIssue = jiraRestClient.getIssueClient()
                .createIssue(newIssue).claim();
        return basicIssue;
    }
    
    //添加备注到问题
    public static void addCommentToIssue(final JiraRestClient jiraRestClient,Issue issue, String comment) {
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        issueClient.addComment(issue.getCommentsUri(), Comment.valueOf(comment)).claim();
    }
    
    
    //删除问题
    public static void deleteIssue(final JiraRestClient jiraRestClient, Issue issue) {
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        issueClient.deleteIssue(issue.getKey(), false).claim();
    }

    //通过标题获取问题
    public static Iterable findIssuesByLabel(final JiraRestClient jiraRestClient, String label) {
        SearchRestClient searchClient = jiraRestClient.getSearchClient();
        String jql = "labels%3D"+label;
        SearchResult results = ((SearchRestClient) jiraRestClient).searchJql(jql).claim();
        return results.getIssues();
    }

    //通过KEY获取问题
    public static Issue findIssueByIssueKey(final JiraRestClient jiraRestClient, String issueKey) {
        SearchRestClient searchClient = jiraRestClient.getSearchClient();
        String jql = "issuekey = \"" + issueKey + "\"";
        SearchResult results = searchClient.searchJql(jql).claim();
        return (Issue) results.getIssues().iterator().next();
    }
    
    

    // 创建问题 ：仅有简单问题名称
    private static void addIssue(final JiraRestClient restClient,
            String porjectKEY, String issueName) throws InterruptedException,
            ExecutionException {
        try {
            IssueInputBuilder builder = new IssueInputBuilder(porjectKEY,
                    TASK_TYPE_ID, issueName);
            builder.setDescription("issue description");
            final IssueInput input = builder.build();

            try {
                // create issue
                final IssueRestClient client = restClient.getIssueClient();
                final BasicIssue issue = client.createIssue(input).claim();
                final Issue actual = client.getIssue(issue.getKey()).claim();
                System.out.println(actual);
            } finally {
                if (restClient != null) {
                    // restClient.close();
                }
            }

        } finally {
        }
    }

    // 创建问题 ：包含自定义字段
    private static void addIssueComplex(final JiraRestClient restClient,
            String porjectKEY, String issueName) throws InterruptedException,
            ExecutionException {
        try {
            IssueInputBuilder builder = new IssueInputBuilder(porjectKEY,
                    TASK_TYPE_ID, issueName);
            builder.setDescription("issue description");
            // builder.setFieldValue("priority", ComplexIssueInputFieldValue.with("name", PRIORITY));
            //单行文本
            builder.setFieldValue("customfield_10042", "单行文本测试");
            
            //单选字段
            builder.setFieldValue("customfield_10043", ComplexIssueInputFieldValue.with("value", "一般"));
            
            //数值自定义字段
            builder.setFieldValue("customfield_10044", 100.08);
            
            //用户选择自定义字段
            builder.setFieldValue("customfield_10045", ComplexIssueInputFieldValue.with("name", "admin"));
            //用户选择自定义字段(多选)
            Map<String, Object> user1 = new HashMap<String, Object>();
            user1.put("name", "admin");
            Map<String, Object> user2 = new HashMap<String, Object>();
            user2.put("name", "wangxn");            
            ArrayList peoples = new ArrayList();
            peoples.add(user1);
            peoples.add(user2);
            builder.setFieldValue("customfield_10047", peoples); 
            
            //设定父问题
            Map<String, Object> parent = new HashMap<String, Object>();
            parent.put("key", "FEEDBACK-25");
            FieldInput parentField = new FieldInput("parent", new ComplexIssueInputFieldValue(parent));
            builder.setFieldInput(parentField);

            final IssueInput input = builder.build();
            try {
                final IssueRestClient client = restClient.getIssueClient();
                final BasicIssue issue = client.createIssue(input).claim();
                final Issue actual = client.getIssue(issue.getKey()).claim();
                System.out.println(actual);
            } finally {
                if (restClient != null) {
                    // restClient.close();
                }
            }

        } finally {
        }
    }

    
    //获取问题的所有字段
    private static void getIssueFields(final JiraRestClient restClient,
            String issueKEY) throws InterruptedException, ExecutionException {
        try {

            Promise<Issue> list = restClient.getIssueClient()
                    .getIssue(issueKEY);
            Issue issue = list.get();
            Iterable<IssueField> fields = issue.getFields();
            Iterator<IssueField> it = fields.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }

        } finally {
        }
    }

}