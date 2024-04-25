package com.sap.testcenter.hanadao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sap.testcenter.hanapojo.Schema;


public interface ISchemaDAO extends JpaRepository<Schema, String> {
    @Query("select s from Schema s where s.schemaName like %:schemaName% order by s.schemaName desc")
    List<Schema> list(@Param("schemaName") String schemaName);
}
