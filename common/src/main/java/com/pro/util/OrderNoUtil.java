package com.pro.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderNoUtil {


    /**
     * 订单编号
     *
     * @param
     * @param userId
     * @return
     */
    public String getOrderNo(Long userId) {
        StringBuffer sb = new StringBuffer();
        sb.append(System.currentTimeMillis());
        sb.append(StrUtil.fillBefore(userId.toString(), '0', 6));
        sb.append(RandomUtil.randomInt(100, 999));
        return sb.toString();
    }


    /**
     * 交易流水编号
     *
     * @param
     * @param userId
     * @return
     */
    public String getRecordNo(Long userId) {
        StringBuffer sb = new StringBuffer();
        sb.append("T");
        sb.append(System.currentTimeMillis());
        sb.append(StrUtil.fillBefore(userId.toString(), '0', 6));
        sb.append(RandomUtil.randomInt(100, 999));
        return sb.toString();
    }


}
