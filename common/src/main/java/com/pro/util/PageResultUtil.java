package com.pro.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;

@UtilityClass
@Slf4j
public class PageResultUtil {


    public Page copyListPage(Page  source, Class listType){
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
                targetList.add(obj);
            }  catch (Exception e) {
                log.error("newInstance listType error {}:",e.getMessage());
            }

        });
        page.setRecords(targetList);

        return page;
    }
}
