 package com.pro.sup;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

 @ApiModel
 @NoArgsConstructor
 @Slf4j
 public class PageResult<T> extends Result {
     private static final long serialVersionUID = 4920883416482893223L;
     @Getter@Setter
     @ApiModelProperty("当前页")
     protected Long currentPage;
     @Getter@Setter
     @ApiModelProperty("总数")
     protected Long totalCount;
     @Getter@Setter
     @ApiModelProperty("页码")
     protected Long pageSize;

     public PageResult(ErrorCode errorCode, Page<T> data) {
         this.requestNo = StrUtil.removeAll(UUID.randomUUID().toString(), "-");
         this.message =  errorCode.getMessage();
         this.code = errorCode.getCode();
         if(ObjectUtil.isNotNull(data)){
             this.data = data.getRecords();
             this.currentPage = data.getCurrent();
             this.pageSize=data.getSize();
             this.totalCount = data.getTotal();
         }

     }

     public static  PageResult success() {
         return new PageResult(ErrorCode.SUCCESS,null);
     }
     public static  <T> PageResult success(Page<T> o) {
         return new PageResult(ErrorCode.SUCCESS,o);
     }
     public static  <T> PageResult success(Page<T> o,Class clazz) {
         return new PageResult(ErrorCode.SUCCESS, copyListPage(o,clazz));
     }
     public static  PageResult fail(ErrorCode errorCode) {
         return new PageResult(errorCode,null);
     }


     public static Page copyListPage(Page  source, Class listType){
         Assert.notNull(source, "Source must not be null");
         Assert.notNull(listType, "listType must not be null");
         Page page = new Page();
         BeanUtil.copyProperties(source,page,"records");
         List targetList = Lists.newArrayList();
         source.getRecords().forEach(list->{
             Object obj = null;
             try {
                 obj = listType.newInstance();
                 BeanUtil.copyProperties(list,obj);
                 replaceValue(obj);
                 targetList.add(obj);
             }  catch (Exception e) {
                 log.error("newInstance listType error {}:",e.getMessage());
             }

         });
         page.setRecords(targetList);

         return page;
     }

 }
