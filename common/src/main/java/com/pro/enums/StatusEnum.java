package com.pro.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author： zcl
 * @date 2022/1/22 11:24
 * @description：状态枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusEnum {

    //0-禁用 1-启用
    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    ;

    private int index;

    private String note;

    /**根据index获取枚举值**/
    public static StatusEnum getType(Integer index) {
        for (StatusEnum enums : StatusEnum.values()) {
            if (enums.getIndex() == index) {
                return enums;
            }
        }
        return null;
    }

}
