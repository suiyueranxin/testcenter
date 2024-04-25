package com.sap.testcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.testcenter.service.ICoverageService;

@RestController
public class CoverageController {
    @Autowired ICoverageService coverageService;
    
    @GetMapping("/coverage/{name}")
    public String getCoverage(@PathVariable("name") String name, @RequestParam(value="regen", required=false) Boolean regen) throws Exception {
        if (regen == true) {
            coverageService.genCoverage(name);
        }
        return coverageService.getCoverage(name);
    }
    
    @GetMapping("coverage/hana/schema/{name}")
    public String getHanaSchema(@PathVariable("name") String name) throws Exception {
        return coverageService.getHanaSchema(name);
    }
}
