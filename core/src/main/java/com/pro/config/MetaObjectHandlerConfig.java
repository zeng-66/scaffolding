package com.pro.config;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pro.interceptor.UserPool;
import com.pro.sup.BackUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MetaObjectHandlerConfig extends BackUserInfo implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        DateTime now = DateTime.now();
        setFieldValByName("createTime", now, metaObject);
        setFieldValByName("modifyTime", now, metaObject);
        setFieldValByName("isDelete", Boolean.FALSE, metaObject);
        setFieldValByName("createId", getId(), metaObject);
        setFieldValByName("modifyId", getId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("modifyTime", DateTime.now(), metaObject);
        setFieldValByName("modifyId", getId(), metaObject);
    }

    /**
     * 获取ID
     *
     * @return
     */
    private Long getId() {
        Long id = 0L;
        try {
            if (ObjectUtil.isNotNull(getLoginUser())) {
                id = getLoginUser().getId();
            } else if (ObjectUtil.isNotNull(UserPool.getUser())) {
                id = UserPool.getUser().getId();
            }
        } catch (Exception e) {
            log.info("用户信息为空");
        }
        return id;
    }
}
