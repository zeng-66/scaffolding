package com.pro.config;

import com.google.common.collect.Lists;
import com.pro.filter.ParamsFilter;
import com.pro.interceptor.UserAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        // registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }
    //@Autowired
    //private SysAuthInterceptor sysAuthInterceptor;
    @Autowired
    private UserAuthInterceptor userAuthInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(sysAuthInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(userAuthInterceptor).addPathPatterns("/api/**");
    }

    @Bean
    public FilterRegistrationBean repeatedlyReadFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ParamsFilter());
        registrationBean.setUrlPatterns(Lists.newArrayList("/*"));
        return registrationBean;
    }
}