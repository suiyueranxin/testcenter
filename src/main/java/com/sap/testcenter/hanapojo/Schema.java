package com.sap.testcenter.hanapojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "schemas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schema {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String schemaName;
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    
}
