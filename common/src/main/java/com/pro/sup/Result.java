 package com.pro.sup;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.pro.annotation.Dict;
import com.pro.annotation.DictMapping;
import com.pro.annotation.DictValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
@ApiModel
@NoArgsConstructor
@Slf4j
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 4920883416481893223L;
    @Getter@Setter
    @ApiModelProperty("请求流水号")
    protected String requestNo;
    @Getter@Setter
    @ApiModelProperty("状态文本")
    protected String message;
    private ErrorCode errorCode ;
    @Getter@Setter
    @ApiModelProperty("状态码")
    protected Integer code ;
    @Getter@Setter
    @ApiModelProperty("结果集")
    protected T data ;

    public Result(ErrorCode errorCode,T data) {
        this.requestNo = StrUtil.removeAll(UUID.randomUUID().toString(), "-");
        this.message =  errorCode.getMessage();
        this.code = errorCode.getCode();
        this.data = data;
    }

    public static  <T> Result<T> success() {
        return new Result<>(ErrorCode.SUCCESS,null);
    }
    public static  <T> Result<T> success(T o) {
        return new Result<>(ErrorCode.SUCCESS,o);
    }


    public static  Result success(Object o,Class clazz) {
        Object o1 =null;
        try {
            o1 = clazz.newInstance();
            BeanUtil.copyProperties(o,o1);
            replaceValue(o1);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Result(ErrorCode.SUCCESS,o1);
    }

    public static  Result successColl(Collection collection,Class clazz) {
        List list = Lists.newArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()){
            Object o = it.next();
            Object o1 =null;
            try {
                o1 = clazz.newInstance();
                BeanUtil.copyProperties(o,o1);
                replaceValue(o1);
                list.add(o1);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return new Result(ErrorCode.SUCCESS,list);
    }

    public static  <T> Result<T> fail(String errorMessage) {
        Result result = new Result();
        result.setCode(-1);
        result.setMessage(errorMessage);
        result.setRequestNo(StrUtil.removeAll(UUID.randomUUID().toString(), "-"));
        return result;
    }
    public static  <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode,null);
    }


    public static void replaceValue(Object data){
        try {
            Field[] fields =  data.getClass().getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(Dict.class)){
                    Dict dict = field.getAnnotation(Dict.class);
                    int seqNo = dict.seqNo();
                    List<Field> valueFileds=Lists.newArrayList();
                    for (Field field1: fields) {
                        if(field1.isAnnotationPresent(DictValue.class)){
                            DictValue dictValue = field1.getAnnotation(DictValue.class);
                            if(dictValue.seqNo()==seqNo){
                                valueFileds.add(field1);
                                field1.setAccessible(true);
                            }
                        }

                    }
                    if(CollUtil.isEmpty(valueFileds)){
                        continue;
                    }
                    field.setAccessible(true);
                    if(dict.enumType()!=Enum.class){
                        Method getCode = dict.enumType().getDeclaredMethod("getCode");
                        Enum[] enumConstants = dict.enumType().getEnumConstants();
                        for (Enum enumConstant : enumConstants) {
                            if(StrUtil.equals(String.valueOf(getCode.invoke(enumConstant)),String.valueOf(field.get(data)))){
                                Field[] enumsFields = dict.enumType().getDeclaredFields();
                                for (Field enumsFiled : enumsFields) {
                                    if(enumsFiled.isAnnotationPresent(DictMapping.class)){
                                        enumsFiled.setAccessible(true);
                                        Object desc = enumsFiled.get(enumConstant);
                                        if(desc!=null){
                                            valueFileds.forEach(valueFiled->{
                                                try {
                                                    valueFiled.set(data,desc);
                                                } catch (IllegalAccessException e) {
                                                    log.error("字典错误");
                                                }
                                            });
                                        }
                                    }
                                }

                            }
                        }
                    }else if (StrUtil.equals(dict.tableType().getSuperclass().getName(), SuperEntity.class.getName())){
                        Object obj = dict.tableType().newInstance();
                        Method setId = obj.getClass().getSuperclass().getDeclaredMethod("setId",Long.class);
                        setId.invoke(obj,field.get(data));
                        Method method = dict.tableType().getMethod("selectById");
                        for (Field field1 : dict.tableType().getDeclaredFields()) {
                            if(field1.isAnnotationPresent(DictMapping.class)){
                                field1.setAccessible(true);
                                Object o = field1.get(method.invoke(obj));
                                valueFileds.forEach(valueFiled->{
                                    if(StrUtil.equals(valueFiled.getName(),field1.getName())){
                                        try {
                                            valueFiled.set(data,o);
                                        } catch (IllegalAccessException e) {
                                            log.error("字典错误");
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            log.warn("field to dict error");
        }

    }

}
