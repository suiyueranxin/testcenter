package com.sap.testcenter.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.testcenter.dao.IJiraIssueExtDAO;
import com.sap.testcenter.pojo.JiraIssue;

@Repository
@Transactional
public class IJiraIssueDAOImpl implements IJiraIssueExtDAO {
    @PersistenceContext
    protected EntityManager entityManager;
    
    public List<Object[]> countBy(List<String> groupBy, List<String> filters) {
        //1. initial query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<JiraIssue> root = query.from(JiraIssue.class);
        
        HashMap<String, String> filterMap = new HashMap<>();
        for(String filter : filters) {
            String[] pair = filter.split("=");
            filterMap.put(pair[0], pair[1]);
        }
        
        //2. part: select and group by
        List<Selection<?>> selections = new ArrayList<>(groupBy.size());
        List<Expression<?>> exps = new ArrayList<>(groupBy.size());
        for(String group : groupBy) {
            selections.add(root.get(group));
            exps.add(root.get(group));
        }
        selections.add(cb.count(root));
        query.multiselect(selections);
        query.groupBy(exps);
        
        //3. part: where
        Iterator<Entry<String, String>> it = filterMap.entrySet().iterator();
        List<Predicate> whereList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, String> filter = ( Map.Entry<String, String>)it.next();
            String property = filter.getKey();
            String value = filter.getValue();
            if (value.indexOf(",") != -1) {
                whereList.add(cb.in(root.get(property)).value(value));
            } else {
                whereList.add(cb.equal(root.get(property),value));
            }
        }
        query.where(cb.and(whereList.toArray(new Predicate[whereList.size()])));
        
        return entityManager.createQuery(query).getResultList();
    }
    
    public List<Object[]> list(List<String> filters) {
        //1. initial query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<JiraIssue> root = query.from(JiraIssue.class);
        
        HashMap<String, String> filterMap = new HashMap<>();
        for(String filter : filters) {
            String[] pair = filter.split("=");
            filterMap.put(pair[0], pair[1]);
        }
        
        //2. part: select
        query.multiselect(root);
        
        //3. part: where
        Iterator<Entry<String, String>> it = filterMap.entrySet().iterator();
        List<Predicate> whereList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, String> filter = ( Map.Entry<String, String>)it.next();
            String property = filter.getKey();
            String value = filter.getValue();
            if (value.indexOf(",") != -1) {
                whereList.add(cb.in(root.get(property)).value(value));
            } else {
                whereList.add(cb.equal(root.get(property),value));
            }
        }
        query.where(cb.and(whereList.toArray(new Predicate[whereList.size()])));
        
        return entityManager.createQuery(query).getResultList();
    }
    
    public List<Object[]> list(List<String> fields, List<String> filters) {
        //1. initial query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<JiraIssue> root = query.from(JiraIssue.class);
        
        HashMap<String, String> filterMap = new HashMap<>();
        for(String filter : filters) {
            String[] pair = filter.split("=");
            filterMap.put(pair[0], pair[1]);
        }
        
        //2. part: select
        if (fields != null && fields.size() > 0) {
            List<Selection<?>> selections = new ArrayList<>(fields.size());
            for(String field : fields) {
                selections.add(root.get(field));
            }
            query.multiselect(selections);
        } else {
            query.multiselect(root);
        }
        
        //3. part: where
        Iterator<Entry<String, String>> it = filterMap.entrySet().iterator();
        List<Predicate> whereList = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, String> filter = ( Map.Entry<String, String>)it.next();
            String property = filter.getKey();
            String value = filter.getValue();
            if (value.indexOf(",") != -1) {
                whereList.add(cb.in(root.get(property)).value(value));
            } else {
                whereList.add(cb.equal(root.get(property),value));
            }
        }
        query.where(cb.and(whereList.toArray(new Predicate[whereList.size()])));
        
        return entityManager.createQuery(query).getResultList();
    }
    
    public void testSucc() {
        System.out.println("Amy success!");
    }
}
