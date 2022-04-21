package com.pro.annotation;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author： zcl
 * @date 2022/1/22 10:08
 * @description： 验证手机号
 */

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {Phone.PhoneFormatCheckUtils.class})
public @interface Phone {

    String message() default "手机格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     * 手机号格式验证
     */
    class PhoneFormatCheckUtils implements ConstraintValidator<Phone, String> {

        @Override
        public void initialize(Phone constraintAnnotation) {
        }

        @Override
        public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
            if (null == phone || "".equals(phone.trim())) {
                return false;
            }
            return isPhoneLegal(phone);
        }

        /**
         * 大陆号码或香港号码均可
         */
        public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
            return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
        }

        /**
         * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
         * 此方法中前三位格式有：
         * 13+任意数
         * 145,147,149
         * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
         * 166
         * 17+3,5,6,7,8
         * 18+任意数
         * 198,199
         */
        public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
            // ^ 匹配输入字符串开始的位置
            // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
            // $ 匹配输入字符串结尾的位置
            String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                    "|(18[0-9])|(19[8,9]))\\d{8}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(str);
            return m.matches();
        }

        /**
         * 香港手机号码8位数，5|6|8|9开头+7位任意数
         */
        public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
            // ^ 匹配输入字符串开始的位置
            // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
            // $ 匹配输入字符串结尾的位置
            String regExp = "^(5|6|8|9)\\d{7}$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(str);
            return m.matches();
        }
    }
}

