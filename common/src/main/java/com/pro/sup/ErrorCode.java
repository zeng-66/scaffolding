package com.pro.sup;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    SUCCESS(0,"success"),
    NO_LOGIN_ERROR(1,"登录失败"),
    ROLE_ERROR(2,"权限错误"),
    ROLE_EXISTS(3,"角色已存在"),
    PASSWORD_ERROR(4,"密码错误"),
    ACCOUNT_EXISTS(5,"账号已存在"),
    PARAM_ERROR(6,"参数错误"),
    ROLE_NO_EXISTS(7,"角色不存在"),
    USER_FROZEN(8,"用户已禁用"),
    THE_USER_DOES_NOT_EXIST(9,"该用户不存在"),
    NO_ROLE(10,"权限不足"),
    SMS_FAIL(11,"验证码发送失败"),
    VERIFICATION_ERROR(12,"验证码不正确"),
    SMS_ONE_MINUTE(13,"短信验证码一分钟只可发送一次"),
    THE_BALANCE_PAYMENT_FUNCTION_IS_TEMPORARILY_CLOSED(14,"账户余额支付功能暂时关闭"),
    THE_POINT_PAYMENT_FUNCTION_IS_TEMPORARILY_CLOSED(15,"账户积分支付功能暂时关闭"),
    THERE_ARE_ORDERS_IN_THIS_TIME_PERIOD(16,"该时间段已有订单"),
    TASK_AUDIT_FAIL(17,"任务审批失败"),
    ACCOUNT_HAS_BEEN_DISABLED(18,"账户已禁用"),
    DEVICE_DOES_NOT_EXIST(19,"设备不存在"),
    PASSWORD_FORMAT_ERROR(20,"密码格式错误"),
    NO_FREQUENTLY_OPERATION(21, "请勿频繁操作"),
    ON_OFF_TIME_NOT_SET(22,"开关机时间未设置"),
    AMOUNT_EXCEEDS_MAXIMUM(23,"金额超过最大值"),
    INTEGRAL_EXCEEDS_MAXIMUM(24,"积分超过最大值"),
    THE_SYSTEM_IS_BUSY_PLEASE_TRY_AGAIN_LATER(25,"系统繁忙请稍后再试"),
    WECHAT_IS_NOT_AUTHORIZED(26, "微信未授权"),
    NO_LOGIN(401, "用户未登录或已过期"),



    ADMINISTRATOR_CANNOT_DELETE(1000,"管理员不能删除"),
    CLASSIFICATION_ALREADY_EXISTS(1001,"分类已存在"),
    WALLET_FUNCTION_IS_NOT_ENABLED(1002,"未开通钱包功能"),
    MEMBER_PHONE_EXISTS(1003,"手机号已存在"),
    THIS_AREA_CAN_ONLY_BE_SELECTED_ONE_A_DAY(1004,"该区域只能选择一天一台设备"),
    INVITE_NO_EXISTS(1005,"邀请人不存在"),
    INVITE_NO_ROLE(1006,"邀请人权限不足"),
    PACKAGE_HAS_BEEN_DISABLED(1007,"套餐已被禁用"),
    NO_BIND_USER(1008,"未绑定邀请人"),
    BANK_CARD_ERROR(1009,"银行卡号不正确"),
    BALANCE_NO_ENOUGH(1010,"余额不足"),
    THE_USER_ROLE_DOES_NOT_EXIST(1011,"该用户角色不存在"),
    BANK_INFO_ERROR(1012,"银行卡信息不一致"),
    DRAW_NOT_FOUND(1013,"未找到提现记录"),
    ID_CARD_FORMAT_ERROR(1014,"身份证格式不正确"),
    DRAW_STATUS_ERROR(1015,"提现记录状态不正确"),
    BANK_NAME_ERROR(1016,"银行名称不正确"),
    DRAW_FAIL(1017,"提现失败"),
    ONLY_BE_WITHDRAWN_ONCE_A_DAY(1018,"每天只能提现一次"),
    INCORRECT_MOBILE_PHONE_NUMBER(1019,"手机号不正确"),
    THIS_FEATURE_HAS_BEEN_DISABLED(1020,"该功能已被禁用"),
    THE_TEMPLATE_ALREADY_EXISTS(1021,"该模板已存在"),
    MOBILE_PHONE_NUMBER_IS_NOT_BOUND(1022,"手机号未绑定"),
    MOBILE_PHONE_NUMBER_FORMAT_ERROR(1023,"手机号格式错误"),
    THE_ROLE_HAS_BEEN_BOUND_AND_CANNOT_BE_DELETED(1024,"该角色已绑定用户不能删除"),
    REQUIRED_PARAMETERS_CANNOT_BE_MISSING(1025,"不能缺少必要参数"),
    PLEASE_APPLY_FOR_BINDING_BANK_CARD_FIRST(1026,"请先申请绑定银行卡"),
    YOU_HAVE_NOT_BOUND_YOUR_BANK_CARD_YET(1027,"您暂未绑定银行卡"),


    PRODUCT_NOT_FOUND(2000,"未找到商品"),
    THE_ORDER_AMOUNT_IS_LOWER_THAN_THE_INVOICE_RANGE(2001,"订单金额低于开具发票范围"),
    THERE_IS_NO_AMOUNT_TO_BE_REFUNDED(2002,"该订单没有待退款金额"),
    PLEASE_DO_NOT_APPLY_FOR_REFUND_AGAIN(2003,"请勿重复申请退款"),
    THERE_ARE_OTHER_PEOPLE_S_EXCLUSIVE_AREAS(2004,"所选中设备中存在他人专属区域不能下单"),




    WAIT_PAY_ORDER(3000,"您有待支付的订单"),
    THE_ORDER_AMOUNT_EXCEEDS_THE_INVOICE_RANGE(3001,"订单金额超出开具发票范围"),
    INVOICE_APPLICATION_FUNCTION_HAS_BEEN_TURNED_OFF(3002,"申请发票功能已关闭"),
    ORDER_NOT_FOUND(3003,"未找到订单"),
    ORDER_STATUS_ERROR(3004,"订单状态不正确"),
    INSUFFICIENT_INVENTORY(3005,"您在电视上选择的广告位置暂时没有空位,请您选择电视其它的位置投放"),
    ORDER_PAID(3006,"订单已支付"),


    TASK_NOT_FOUND(4000,"任务不存在"),
    TASK_OVER(4001,"任务已过期"),
    THIS_CLASSIFICATION_CANNOT_BE_MODIFIED(4002,"该分类不能修改名称或删除"),
    INTEGRAL_NOT_ENOUGH(4003,"积分不足"),
    COUNT_NOT_ENOUGH(4004,"商品数量不足"),
    CANT_BUY_SELF(4005,"不可以购买自己寄售的商品"),
    THE_PACKAGE_HAS_BEEN_ADDED(4006,"该套餐已添加"),
    THE_PACKAGE_MUST_ADD_A_TIME_PERIOD(4007,"该套餐必须添加时间段"),
    PAY_ORDER_CREATE_FAIL(5000,"创建支付订单失败"),
    PAYMENT_ASYNCHRONOUS_EXCEPTION(5001,"处理支付异步异常"),
    SYS_ERROR(-1,"系统异常"),
    PATH_IS_NOT_EXIT(404, "路径不正确"),
    ;
    private Integer  code;
    private String message;

}