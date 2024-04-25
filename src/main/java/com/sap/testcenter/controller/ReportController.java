package com.sap.testcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.testcenter.service.IReportService;


@RestController
public class ReportController {
    @Autowired IReportService reportService;
    
    @GetMapping("/report/{name}")
    public String getReport(@PathVariable("name") String name) throws Exception {
       return reportService.queryReport(name);
        
    }
    
    @GetMapping("/report/initialData/{name}")
    public String initialData(@PathVariable("name") String name) throws Exception {
        reportService.initialData(name);
        return "ok";
    }
    
    @GetMapping("/report/countBy")
    public String countBy(@RequestParam("by") List<String> by, @RequestParam("filters") List<String> filters) throws Exception {
        return reportService.countBy(by, filters);
    }
    
    @GetMapping("/report/list")
    public String list(@RequestParam(value="fields", required=false) List<String> fields, @RequestParam("filters") List<String> filters) throws Exception {
        return reportService.list(fields,filters);
    }
    
    @GetMapping("/report/week")
    public String getCurrentWeek(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception {
        return reportService.calcReportWeek(startDate, endDate);
    }
    
    @GetMapping("report/status")
    public String getStatus() throws Exception {
        return reportService.calcReportStatus(null);
    }
}
