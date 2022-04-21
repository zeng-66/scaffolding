package com.pro.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class BankCardUtil {
    /*
    校验过程：
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
    */
    /**
     * 校验银行卡卡号
     */
    public  boolean checkBankCard(String bankCard) {
        if(bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeBankCard
     * @return
     */
    public  char getBankCardCheckCode(String nonCheckCodeBankCard){
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    @SneakyThrows(Exception.class)
    public boolean checkBankCardIdentity(String cardNo,String identityNo,String name,String phone) {
        boolean isSuccess = false;
        String url = "http://v.juhe.cn/verifybankcard4/query";
        String key = "14fe7e2c6b0b17d8df06607287f2658b";
        Map params = Maps.newHashMap();
        params.put("realname",name);
        params.put("idcard",identityNo);
        params.put("bankcard",cardNo);
        params.put("mobile",phone);
        params.put("key",key);
        String response = HttpUtil.get(url,params);
        JSONObject result = JSONObject.parseObject(response);
        if(StrUtil.equals(result.getString("reason"),"成功")){
            JSONObject value = JSONObject.parseObject(result.getString("result"));
            if (StrUtil.equals(value.getString("res"),"1")){
                isSuccess = true;
            }else {
                throw new RuntimeException(value.getString("message"));
            }
        }else {
            throw new RuntimeException(result.getString("reason"));
        }
        return isSuccess;
    }


    /**
     *身份证验证
     * @param idStr
     * @return
     */
    public boolean IdentityCardVerification(String idStr){
        String[] wf = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] checkCode = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
        String iDCardNo = "";
        try {
            //判断号码的长度 15位或18位
            if (idStr.length() != 15 && idStr.length() != 18) {
                return false;
            }
            if (idStr.length() == 18) {
                iDCardNo = idStr.substring(0, 17);
            } else if (idStr.length() == 15) {
                iDCardNo = idStr.substring(0, 6) + "19" + idStr.substring(6, 15);
            }
            if (isStrNum(iDCardNo) == false) {
                return false;
            }
            //判断出生年月
            String strYear = iDCardNo.substring(6, 10);// 年份
            String strMonth = iDCardNo.substring(10, 12);// 月份
            String strDay = iDCardNo.substring(12, 14);// 月份
            if (isStrDate(strYear + "-" + strMonth + "-" + strDay) == false) {
                return false;
            }
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return false;
            }
            if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
                return false;
            }
            if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
                return false;
            }
            //判断地区码
            Hashtable h = GetAreaCode();
            if (h.get(iDCardNo.substring(0, 2)) == null) {
                return false;
            }
            //判断最后一位
            int theLastOne = 0;
            for (int i = 0; i < 17; i++) {
                theLastOne = theLastOne + Integer.parseInt(String.valueOf(iDCardNo.charAt(i))) * Integer.parseInt(checkCode[i]);
            }
            int modValue = theLastOne % 11;
            String strVerifyCode = wf[modValue];
            iDCardNo = iDCardNo + strVerifyCode;
            if (idStr.length() == 18 &&!iDCardNo.equals(idStr)) {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 地区代码
     * @return Hashtable
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isStrNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断字符串是否为日期格式
     * @param strDate
     * @return
     */
    public static boolean isStrDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }







    /**
     * 请求
     * @return

     */
    public static Map<String, String> send(String cardNo) throws Exception {
        String body = "";
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";
        Map<String, String> map = new HashMap<String, String>();
        map.put("cardNo", cardNo);
        map.put("cardBinCheck", "true");
        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();

        JSONObject json_obj= JSONObject.parseObject(body);
        System.out.println("--------------->>"+json_obj.get("bank")+"");

        Map<String, String> info = new HashMap<>();
        info.put("bankType",json_obj.get("cardType").toString());
        info.put("bankName",getBankName(json_obj.get("bank").toString()));
        return info;

    }


    public static String getBankName(String bankCode){
        String bankJsonString ="{\n" +
                "  \"SRCB\": \"深圳农村商业银行\",\n" +
                "  \"BGB\": \"广西北部湾银行\",\n" +
                "  \"SHRCB\": \"上海农村商业银行\",\n" +
                "  \"BJBANK\": \"北京银行\",\n" +
                "  \"WHCCB\": \"威海市商业银行\",\n" +
                "  \"BOZK\": \"周口银行\",\n" +
                "  \"KORLABANK\": \"库尔勒市商业银行\",\n" +
                "  \"SPABANK\": \"平安银行\",\n" +
                "  \"SDEB\": \"顺德农商银行\",\n" +
                "  \"HURCB\": \"湖北省农村信用社\",\n" +
                "  \"WRCB\": \"无锡农村商业银行\",\n" +
                "  \"BOCY\": \"朝阳银行\",\n" +
                "  \"CZBANK\": \"浙商银行\",\n" +
                "  \"HDBANK\": \"邯郸银行\",\n" +
                "  \"BOC\": \"中国银行\",\n" +
                "  \"BOD\": \"东莞银行\",\n" +
                "  \"CCB\": \"中国建设银行\",\n" +
                "  \"ZYCBANK\": \"遵义市商业银行\",\n" +
                "  \"SXCB\": \"绍兴银行\",\n" +
                "  \"GZRCU\": \"贵州省农村信用社\",\n" +
                "  \"ZJKCCB\": \"张家口市商业银行\",\n" +
                "  \"BOJZ\": \"锦州银行\",\n" +
                "  \"BOP\": \"平顶山银行\",\n" +
                "  \"HKB\": \"汉口银行\",\n" +
                "  \"SPDB\": \"上海浦东发展银行\",\n" +
                "  \"NXRCU\": \"宁夏黄河农村商业银行\",\n" +
                "  \"NYNB\": \"广东南粤银行\",\n" +
                "  \"GRCB\": \"广州农商银行\",\n" +
                "  \"BOSZ\": \"苏州银行\",\n" +
                "  \"HZCB\": \"杭州银行\",\n" +
                "  \"HSBK\": \"衡水银行\",\n" +
                "  \"HBC\": \"湖北银行\",\n" +
                "  \"JXBANK\": \"嘉兴银行\",\n" +
                "  \"HRXJB\": \"华融湘江银行\",\n" +
                "  \"BODD\": \"丹东银行\",\n" +
                "  \"AYCB\": \"安阳银行\",\n" +
                "  \"EGBANK\": \"恒丰银行\",\n" +
                "  \"CDB\": \"国家开发银行\",\n" +
                "  \"TCRCB\": \"江苏太仓农村商业银行\",\n" +
                "  \"NJCB\": \"南京银行\",\n" +
                "  \"ZZBANK\": \"郑州银行\",\n" +
                "  \"DYCB\": \"德阳商业银行\",\n" +
                "  \"YBCCB\": \"宜宾市商业银行\",\n" +
                "  \"SCRCU\": \"四川省农村信用\",\n" +
                "  \"KLB\": \"昆仑银行\",\n" +
                "  \"LSBANK\": \"莱商银行\",\n" +
                "  \"YDRCB\": \"尧都农商行\",\n" +
                "  \"CCQTGB\": \"重庆三峡银行\",\n" +
                "  \"FDB\": \"富滇银行\",\n" +
                "  \"JSRCU\": \"江苏省农村信用联合社\",\n" +
                "  \"JNBANK\": \"济宁银行\",\n" +
                "  \"CMB\": \"招商银行\",\n" +
                "  \"JINCHB\": \"晋城银行JCBANK\",\n" +
                "  \"FXCB\": \"阜新银行\",\n" +
                "  \"WHRCB\": \"武汉农村商业银行\",\n" +
                "  \"HBYCBANK\": \"湖北银行宜昌分行\",\n" +
                "  \"TZCB\": \"台州银行\",\n" +
                "  \"TACCB\": \"泰安市商业银行\",\n" +
                "  \"XCYH\": \"许昌银行\",\n" +
                "  \"CEB\": \"中国光大银行\",\n" +
                "  \"NXBANK\": \"宁夏银行\",\n" +
                "  \"HSBANK\": \"徽商银行\",\n" +
                "  \"JJBANK\": \"九江银行\",\n" +
                "  \"NHQS\": \"农信银清算中心\",\n" +
                "  \"MTBANK\": \"浙江民泰商业银行\",\n" +
                "  \"LANGFB\": \"廊坊银行\",\n" +
                "  \"ASCB\": \"鞍山银行\",\n" +
                "  \"KSRB\": \"昆山农村商业银行\",\n" +
                "  \"YXCCB\": \"玉溪市商业银行\",\n" +
                "  \"DLB\": \"大连银行\",\n" +
                "  \"DRCBCL\": \"东莞农村商业银行\",\n" +
                "  \"GCB\": \"广州银行\",\n" +
                "  \"NBBANK\": \"宁波银行\",\n" +
                "  \"BOYK\": \"营口银行\",\n" +
                "  \"SXRCCU\": \"陕西信合\",\n" +
                "  \"GLBANK\": \"桂林银行\",\n" +
                "  \"BOQH\": \"青海银行\",\n" +
                "  \"CDRCB\": \"成都农商银行\",\n" +
                "  \"QDCCB\": \"青岛银行\",\n" +
                "  \"HKBEA\": \"东亚银行\",\n" +
                "  \"HBHSBANK\": \"湖北银行黄石分行\",\n" +
                "  \"WZCB\": \"温州银行\",\n" +
                "  \"TRCB\": \"天津农商银行\",\n" +
                "  \"QLBANK\": \"齐鲁银行\",\n" +
                "  \"GDRCC\": \"广东省农村信用社联合社\",\n" +
                "  \"ZJTLCB\": \"浙江泰隆商业银行\",\n" +
                "  \"GZB\": \"赣州银行\",\n" +
                "  \"GYCB\": \"贵阳市商业银行\",\n" +
                "  \"CQBANK\": \"重庆银行\",\n" +
                "  \"DAQINGB\": \"龙江银行\",\n" +
                "  \"CGNB\": \"南充市商业银行\",\n" +
                "  \"SCCB\": \"三门峡银行\",\n" +
                "  \"CSRCB\": \"常熟农村商业银行\",\n" +
                "  \"SHBANK\": \"上海银行\",\n" +
                "  \"JLBANK\": \"吉林银行\",\n" +
                "  \"CZRCB\": \"常州农村信用联社\",\n" +
                "  \"BANKWF\": \"潍坊银行\",\n" +
                "  \"ZRCBANK\": \"张家港农村商业银行\",\n" +
                "  \"FJHXBC\": \"福建海峡银行\",\n" +
                "  \"ZJNX\": \"浙江省农村信用社联合社\",\n" +
                "  \"LZYH\": \"兰州银行\",\n" +
                "  \"JSB\": \"晋商银行\",\n" +
                "  \"BOHAIB\": \"渤海银行\",\n" +
                "  \"CZCB\": \"浙江稠州商业银行\",\n" +
                "  \"YQCCB\": \"阳泉银行\",\n" +
                "  \"SJBANK\": \"盛京银行\",\n" +
                "  \"XABANK\": \"西安银行\",\n" +
                "  \"BSB\": \"包商银行\",\n" +
                "  \"JSBANK\": \"江苏银行\",\n" +
                "  \"FSCB\": \"抚顺银行\",\n" +
                "  \"HNRCU\": \"河南省农村信用\",\n" +
                "  \"COMM\": \"交通银行\",\n" +
                "  \"XTB\": \"邢台银行\",\n" +
                "  \"CITIC\": \"中信银行\",\n" +
                "  \"HXBANK\": \"华夏银行\",\n" +
                "  \"HNRCC\": \"湖南省农村信用社\",\n" +
                "  \"DYCCB\": \"东营市商业银行\",\n" +
                "  \"ORBANK\": \"鄂尔多斯银行\",\n" +
                "  \"BJRCB\": \"北京农村商业银行\",\n" +
                "  \"XYBANK\": \"信阳银行\",\n" +
                "  \"ZGCCB\": \"自贡市商业银行\",\n" +
                "  \"CDCB\": \"成都银行\",\n" +
                "  \"HANABANK\": \"韩亚银行\",\n" +
                "  \"CMBC\": \"中国民生银行\",\n" +
                "  \"LYBANK\": \"洛阳银行\",\n" +
                "  \"GDB\": \"广东发展银行\",\n" +
                "  \"ZBCB\": \"齐商银行\",\n" +
                "  \"CBKF\": \"开封市商业银行\",\n" +
                "  \"H3CB\": \"内蒙古银行\",\n" +
                "  \"CIB\": \"兴业银行\",\n" +
                "  \"CRCBANK\": \"重庆农村商业银行\",\n" +
                "  \"SZSBK\": \"石嘴山银行\",\n" +
                "  \"DZBANK\": \"德州银行\",\n" +
                "  \"SRBANK\": \"上饶银行\",\n" +
                "  \"LSCCB\": \"乐山市商业银行\",\n" +
                "  \"JXRCU\": \"江西省农村信用\",\n" +
                "  \"ICBC\": \"中国工商银行\",\n" +
                "  \"JZBANK\": \"晋中市商业银行\",\n" +
                "  \"HZCCB\": \"湖州市商业银行\",\n" +
                "  \"NHB\": \"南海农村信用联社\",\n" +
                "  \"XXBANK\": \"新乡银行\",\n" +
                "  \"JRCB\": \"江苏江阴农村商业银行\",\n" +
                "  \"YNRCC\": \"云南省农村信用社\",\n" +
                "  \"ABC\": \"中国农业银行\",\n" +
                "  \"GXRCU\": \"广西省农村信用\",\n" +
                "  \"PSBC\": \"中国邮政储蓄银行\",\n" +
                "  \"BZMD\": \"驻马店银行\",\n" +
                "  \"ARCU\": \"安徽省农村信用社\",\n" +
                "  \"GSRCU\": \"甘肃省农村信用\",\n" +
                "  \"LYCB\": \"辽阳市商业银行\",\n" +
                "  \"JLRCU\": \"吉林农信\",\n" +
                "  \"URMQCCB\": \"乌鲁木齐市商业银行\",\n" +
                "  \"XLBANK\": \"中山小榄村镇银行\",\n" +
                "  \"CSCB\": \"长沙银行\",\n" +
                "  \"JHBANK\": \"金华银行\",\n" +
                "  \"BHB\": \"河北银行\",\n" +
                "  \"NBYZ\": \"鄞州银行\",\n" +
                "  \"LSBC\": \"临商银行\",\n" +
                "  \"BOCD\": \"承德银行\",\n" +
                "  \"SDRCU\": \"山东农信\",\n" +
                "  \"NCB\": \"南昌银行\",\n" +
                "  \"TCCB\": \"天津银行\",\n" +
                "  \"WJRCB\": \"吴江农商银行\",\n" +
                "  \"CBBQS\": \"城市商业银行资金清算中心\",\n" +
                "  \"HBRCU\": \"河北省农村信用社\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(bankJsonString);
        Map<String, String> map = (Map)jsonObject;
        return map.get(bankCode);
    }



}
