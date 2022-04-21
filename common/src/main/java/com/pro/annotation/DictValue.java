package com.pro.annotation;

import com.pro.sup.SuperEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictValue
{
    String defaultValue() default "";
    int seqNo() default 0;
}