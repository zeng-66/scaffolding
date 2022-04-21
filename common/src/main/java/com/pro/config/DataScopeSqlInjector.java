package com.pro.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.github.yulichang.injector.MPJSqlInjector;
import com.github.yulichang.method.*;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author： zcl
 * @date 2022/3/2 10:07
 * @description：mybatis-plus-join配置类
 */
@Configuration
public class DataScopeSqlInjector extends MPJSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        final List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new SelectList());
        methodList.add(new SelectCount());
        methodList.add(new SelectJoinCount());
        methodList.add(new SelectJoinList());
        methodList.add(new SelectJoinMap());
        methodList.add(new SelectJoinMaps());
        methodList.add(new SelectJoinMapsPage());
        methodList.add(new SelectJoinOne());
        methodList.add(new SelectJoinPage());
        methodList.add(new SelectMaps());
        methodList.add(new SelectMapsPage());
        methodList.add(new SelectObjs());
        methodList.add(new SelectOne());
        methodList.add(new SelectPage());
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}
