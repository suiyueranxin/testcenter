package com.sap.testcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sap.testcenter.dao.ITestLabDAO;
import com.sap.testcenter.hanadao.ISchemaDAO;
import com.sap.testcenter.hanapojo.Schema;
import com.sap.testcenter.model.CoverSummary;
import com.sap.testcenter.model.CoverageInfo;
import com.sap.testcenter.model.GitInfo;
import com.sap.testcenter.model.RunInfo;
import com.sap.testcenter.pojo.TestLab;
import com.sap.testcenter.service.ICoverageService;
import com.sap.testcenter.util.CmdUtil;
import com.sap.testcenter.util.CoverageHelper;
import com.sap.testcenter.util.GitClient;

@Service
public class CoverageServiceImpl implements ICoverageService {
    @Autowired ISchemaDAO schemaDAO;
    @Autowired ITestLabDAO testLabDAO;
    
    public String genCoverage(String name) {
        // fetch build coverage information from db
        TestLab testlab = testLabDAO.getOne(name);
        GitInfo gitInfo = testlab.getGitInfo();
        RunInfo runInfo = testlab.getRunInfo();
        
        // git fetch latest code
        GitClient gc = new GitClient(gitInfo);
        gc.fetchRepo();
        
        // build env and run coverage
        String curPath = gc.getLocalRepoPath();
        List<String> cmds = runInfo.getCmds();
        CmdUtil.run(cmds, curPath);
        return curPath;
    }
    
    public String getHanaSchema(String userId) {
        List<Schema> list = schemaDAO.list(userId);
        return list.size() > 0 ? list.get(0).getSchemaName() : null;
    }
    
    public String getCoverage(String name) {
        TestLab testlab = testLabDAO.getOne(name);
        List<CoverSummary> info = CoverageHelper.parseCoverageInfo(testlab.getCoverageInfo());
        return JSONObject.toJSONString(info);
    }
}
