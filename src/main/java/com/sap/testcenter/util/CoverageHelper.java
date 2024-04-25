package com.sap.testcenter.util;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.sap.testcenter.model.CoverSummary;
import com.sap.testcenter.model.CoverageInfo;

import org.jsoup.nodes.Element;

public class CoverageHelper {
    public final static String rootPath = "C:/tccoverage";
    static Logger logger = Logger.getLogger(CoverageHelper.class);
    
    public static List<CoverSummary> parseCoverageInfo(CoverageInfo cInfo) {
        List<CoverSummary> results = new ArrayList<>();
        String htmlFilePath = rootPath + cInfo.getTargetPath() + cInfo.getHtmlPath(); 
        File html = new File(htmlFilePath);
        try {
            Document doc = Jsoup.parse(html, "UTF-8");
            Elements divs = doc.select(cInfo.getCssPath());
            List<CoverSummary> summaries = cInfo.getSummaries();
            for(CoverSummary s : summaries) {
                String rate = s.getRate();
                logger.info(s.getType());
                String rRate = parseRate(rate, divs);
                CoverSummary info = new CoverSummary();
                info.setRate(rRate);
                info.setType(s.getType());
                results.add(info);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(JSONObject.toJSONString(results));
        return results;
    }
    
    private static String parseRate(String rate, Elements eles) {
        String express = "";
        int begin = 0;
        int end = 0;
        for (int i=0; i<rate.length(); i++) {
            char c = rate.charAt(i);
            if (c == '[') {
                begin = i;
                end = rate.indexOf(']', begin);
                i = end;
                String sIndex = rate.substring(begin+1, end);
                Element e = eles.get(Integer.parseInt(sIndex));
                String content = e.text().replaceAll(",", "");
                express += content;
            } else {
                express += c;
            } 
        }
        logger.info("express:" + express);
        return rate.length()>3 ? runFormula(express):express;
    }
    
    private static String runFormula(String formula) {
        String result = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        try {
            double d = (double)engine.eval(formula);
            NumberFormat nf = NumberFormat.getPercentInstance();
            nf.setMaximumFractionDigits(0);
            result = nf.format(d);
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
//        String filePath = "C:/tcgit/analyticsui/target/karma/coverage_report/html/index.html";
//        String xPath = "//div/div/div/div[@class='fl pad1y space-right2']";
//        File html = new File(filePath);
//        try {
//            Document doc = Jsoup.parse(html, "UTF-8");
//            Elements divs = doc.getElementsByClass("fl");
//            for(Element div : divs) {
//               System.out.println("Amy: " + div.text());
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        CoverageInfo cInfo = new CoverageInfo();
        cInfo.setHtmlPath("/index.html");
        cInfo.setSourcePath("/test/apitest/report");
        cInfo.setTargetPath("/analytics/backend");
        cInfo.setCssPath("tfoot tr td");
        CoverSummary acover = new CoverSummary();
        acover.setType("lines");
        acover.setRate("1-[11]/[12]");
        List<CoverSummary> summaries = new ArrayList<>();
        summaries.add(acover);
        cInfo.setSummaries(summaries);
        parseCoverageInfo(cInfo);
        
//        CoverageInfo cInfo = new CoverageInfo();
//        cInfo.setHtmlPath("/index.html");
//        cInfo.setSourcePath("/target/karma/coverage_report/html");
//        cInfo.setTargetPath("/analytics/front");
//        cInfo.setCssPath("div.fl span.strong");
//        CoverSummary acover = new CoverSummary();
//        acover.setType("lines");
//        acover.setRate("[3]");
//        List<CoverSummary> summaries = new ArrayList<>();
//        summaries.add(acover);
//        cInfo.setSummaries(summaries);
//        parseCoverageInfo(cInfo);
    }
}
