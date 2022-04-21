package com.pro.helper;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.pro.exception.GlobalRuntimeException;
import com.pro.sup.RedisPrefix;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@ConfigurationProperties(prefix = "alisms")
@Data
public class SmsHelper {
    private String accessKey;
    private String secretKey;
    private String signName;
    private String smsLoginTempCode;


    @Autowired
    private RedisTemplate redisTemplate;

    //验证码过期时间 5 分钟
    public static int expireTime = 60 * 5;

    //验证码发送间隔时长 1 分钟
    public static int intervalTime = 60;

    @Value("${spring.application.name:default}")
    private String application;

    /**
     * 生成验证码
     * @param verificationCodeType
     * @param phoneNumber
     * @return
     */
    public String createAndSendCode(int verificationCodeType, String phoneNumber) {
        String redisPrefix  = "";
        switch (verificationCodeType){
            case 1:
                redisPrefix = RedisPrefix.USER_REGISTRATION_OR_LOGIN_CODE;
                break;
            case 2:
                redisPrefix = RedisPrefix.USER_CHANGE_AND_BIND_MOBILE_PHONE_CODE;
                break;
            case 3:
                redisPrefix = RedisPrefix.USER_UNBOUND_MICRO_SIGNAL;
                break;
        }
        String lockKey = application.concat(RedisPrefix.GET_VERIFY_CODE_PREFIX).concat(phoneNumber);
        Object lockObj = redisTemplate.opsForValue().get(lockKey);
        if (lockObj != null){
            throw new GlobalRuntimeException("获取验证码太过频繁，稍后再试哦");
        }
        String key = application.concat(redisPrefix).concat(phoneNumber);
        String code = getCode();
        redisTemplate.opsForValue().set(lockKey, "1", intervalTime, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(key, code, expireTime, TimeUnit.SECONDS);
        sendVerificationCode(phoneNumber ,code);
        return code;
    }

    /**
     * 校验验证码
     * @param redisPrefix
     * @param phoneNumber
     * @param verifCode
     * @return
     */
    public boolean checkCode(String redisPrefix, String phoneNumber, String verifCode) {
        String key = application.concat(redisPrefix).concat(phoneNumber);
        Object alreadyObj = redisTemplate.opsForValue().get(key);
        if (alreadyObj == null){
            return false;
        }
        if (String.valueOf(alreadyObj).equals(verifCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    /**
     * 获取随机验证码
     * @return
     */
    public String getCode() {
        return String.valueOf(RandomUtil.randomInt(100000, 999999));
    }




    /**
     * 发送验证码短信短信
     *
     * @param phone      接收手机号
     * @param params     短信模板变量
     * @return
     */
    public boolean sendVerificationCode(String phone, String params)  {
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        try {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secretKey);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(smsLoginTempCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",params);
        request.setTemplateParam(jsonObject.toJSONString());
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            return true;
        }
        }catch (Exception e){
            return false;
        }
        return false;
    }

}
