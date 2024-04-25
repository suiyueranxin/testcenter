package com.sap.testcenter.dao.spec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sap.testcenter.pojo.JiraIssue;

public class JiraIssueSpec {
    public static Specification<JiraIssue> firstMethod() {
        return new Specification<JiraIssue>() {
            @Override
            public Predicate toPredicate(Root<JiraIssue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
    }
    
    public static Specification<JiraIssue> createCountBySpec(String by, HashMap<String, String> filters) {
        return new Specification<JiraIssue>() {
            @Override
            public Predicate toPredicate(Root<JiraIssue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // part: select
                query.select(root.get(by));
                
                // part: where
                Iterator<Entry<String, String>> it = filters.entrySet().iterator();
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
                Predicate[] whereArr = new Predicate[whereList.size()];
                query.where(cb.and(whereList.toArray(whereArr)));
                
                // part: group by
                query.groupBy(root.get(by));
                
                return query.getRestriction();
            }
        };
    }
}
