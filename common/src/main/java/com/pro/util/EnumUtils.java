package com.pro.util;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 枚举常用工具类
 * 使用该枚举工具类需要指定的枚举实现
 *
 * @author zcl
 * @version 1.0
 * @date 2022-02-24
 */
@UtilityClass
public final class EnumUtils {

    @SneakyThrows
    public <E extends Enum<E>> E getByValue(Class<E> enumClass,Object value) {
        E[] constants = enumClass.getEnumConstants();
        if (ObjectUtil.isEmpty(enumClass.getFields())) {
            return null;
        }
        if (ObjectUtil.isEmpty(enumClass.getDeclaredMethods())) {
            return null;
        }
        String methodName = null;
        for (Field declaredField : enumClass.getDeclaredFields()) {
            if (StrUtil.startWith(declaredField.toString(), "private") && !StrUtil.endWith(declaredField.toString(), "$VALUES")) {
                methodName = "get" + StrUtil.upperFirst(declaredField.getName());
                break;
            }
        }
        if (StrUtil.isBlank(methodName)) {
            return null;
        }
        String val = String.valueOf(value);
        for (E constant : constants) {
            String constValue = String.valueOf(enumClass.getMethod(methodName).invoke(constant));
            if (StrUtil.equals(val, constValue)) {
                return constant;
            }
        }
        return null;
    }

    @SneakyThrows
    public <E extends Enum<E>> E getByValue(Class<E> enumClass, int value) {
        return getByValue(enumClass,String.valueOf(value));
    }

    @SneakyThrows
    public <E extends Enum<E>> E getByValue(Class<E> enumClass, long value) {
        return getByValue(enumClass,String.valueOf(value));
    }

    @SneakyThrows
    public <E extends Enum<E>> List<E> all(E[] enums) {
        List<E> typeList = Lists.newArrayListWithCapacity(enums.length);
        for (E type : enums) {
            typeList.add(type);
        }
        return typeList;
    }


    @SneakyThrows
    public <E extends Enum<E>> List<String> allValue(Class<E> enumClass) {
        return all(enumClass, false);
    }


    @SneakyThrows
    public <E extends Enum<E>> List<String> allName(Class<E> enumClass) {
        return all(enumClass, true);
    }


    @SneakyThrows
    public <E extends Enum<E>> List<String> all(Class<E> enumClass,boolean bool) {
        E[] constants = enumClass.getEnumConstants();
        List<String> values = Lists.newArrayListWithCapacity(constants.length);
        if (ObjectUtil.isEmpty(enumClass.getFields())) {
            return null;
        }
        if (ObjectUtil.isEmpty(enumClass.getDeclaredMethods())) {
            return null;
        }
        String methodName = null;
        if(bool){
            int index = 0;
            for (Field declaredField : enumClass.getDeclaredFields()) {
                if (StrUtil.startWith(declaredField.toString(), "private") && !StrUtil.endWith(declaredField.toString(), "$VALUES")) {
                    methodName = "get" + StrUtil.upperFirst(declaredField.getName());
                    index++;
                    if (index == 2) {
                        break;
                    }
                }
            }
        }else {
            for (Field declaredField : enumClass.getDeclaredFields()) {
                if (StrUtil.startWith(declaredField.toString(), "private") && !StrUtil.endWith(declaredField.toString(), "$VALUES")) {
                    methodName = "get" + StrUtil.upperFirst(declaredField.getName());
                    break;
                }
            }
        }
        if (StrUtil.isBlank(methodName)) {
            return null;
        }
        for (E constant : constants) {
            String constValue = String.valueOf(enumClass.getMethod(methodName).invoke(constant));
            values.add(constValue);
        }
        return values;
    }



}
