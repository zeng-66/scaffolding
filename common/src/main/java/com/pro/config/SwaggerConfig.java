package com.pro.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {


    private String appletPackage = "com.pro.controller";// 小程序接口所在的包

    private String adminPackage = "com.pro.admin.controller";// 管理后台接口所在的包

    @Value("${spring.application.descp}")
    private String title ;   // 当前文档的标题

    private String description = "接口文档";//当前文档的详细描述

    private String version = "1.0.0";

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("后台管理系统")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(adminPackage))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Lists.newArrayList(new ParameterBuilder().name("token").description("token")
                        .modelRef(new ModelRef("String")).parameterType("header")
                        .required(false).build()));
    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("小程序")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(appletPackage))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(Lists.newArrayList(new ParameterBuilder().name("token").description("token")
                        .modelRef(new ModelRef("String")).parameterType("header")
                        .required(false).build()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }


}