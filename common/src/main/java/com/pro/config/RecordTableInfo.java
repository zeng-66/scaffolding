package com.pro.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;

@Data
@AllArgsConstructor
public class RecordTableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 实体
     */
    private Class entityName;

    /**
     * 记录字段
     */
    private List<Field> recordFields;

}