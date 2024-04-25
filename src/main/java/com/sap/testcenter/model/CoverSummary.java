package com.sap.testcenter.model;

import com.alibaba.fastjson.annotation.JSONField;

public class CoverSummary {
    @JSONField(ordinal=1)
    private String type;
    @JSONField(ordinal=2)
    private String rate;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
}
