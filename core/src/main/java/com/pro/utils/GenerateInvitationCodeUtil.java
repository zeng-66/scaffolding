package com.pro.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author： zcl
 * @date 2022/3/16 11:53
 * @description：生成邀请代码
 */
@Slf4j
@UtilityClass
public class GenerateInvitationCodeUtil {


    /** 邀请码最小长度,生成邀请码小于该字段，自动补长  **/
    private static final int MIN_CODE_LENGTH = 6;
    /** 位数不足时自动补长时，充当分隔，该字段为保持唯一性，不放入下方的列表  **/
    private static final String STOP_CHAR = "Z";
    /** 考虑用户体验，此处去掉了 i o 1 0，具体列表内容自由换序  **/
    private static final String[] CHARS = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
    private static final int OFFSET = CHARS.length - 1;


    /**
     * 根据 id 生成邀请码
     * 如果是 6 位的邀请码只能支持 754137930 7亿5千万用户, 超出的id 会成为7位及以上的邀请码
     * @param id 用户id
     * @return 邀请码字符串
     */
    public  String code(int id) {
        String code = int2chars(id);
        int tailLength = MIN_CODE_LENGTH - code.length();
        if (tailLength > 1) {
            code = code + STOP_CHAR + codeTail(tailLength - 1);
        } else if (tailLength == 1) {
            code = code + STOP_CHAR;
        }
        return code;
    }

    /**
     * 根据邀请码 获取 id
     * @param code 邀请码
     * @return 用户id
     */
    public  int shareCode2id(String code) {
        int inx = code.indexOf(STOP_CHAR);
        if (inx > 0) {
            code = code.substring(0, inx);
        }
        return chars2int(code);
    }

    /**
     * 获取补长的邀请码（随机）
     * @param len 需要的长度
     * */
    private  String codeTail(int len) {
        String res = "";
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            res += CHARS[r.nextInt(OFFSET)];
        }
        return res;
    }

    private  String int2chars(int id) {
        int x = id / OFFSET;
        int remainder = id % OFFSET;
        if (x == 0) {
            return CHARS[id];
        } else if (x < OFFSET) {
            return CHARS[x] + CHARS[remainder];
        } else {
            return int2chars(x) + CHARS[remainder];
        }
    }
    /**
     * code 转 int
     * */
    private  int chars2int(String chars) {
        int res = 0;
        int codeLen = chars.length();
        List<String> totalCharsList = Arrays.asList(CHARS);

        for (int i = 0; i < codeLen; i++) {
            String a = chars.substring(i, i+1);
            if (STOP_CHAR.equals(a)) {
                break;
            }
            if (totalCharsList.contains(a)) {
                res = res * OFFSET + totalCharsList.indexOf(a);
            } else {
                res += 0;
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(code((1)));
        System.out.println(code((2)));
        System.out.println(code((3)));
        System.out.println(code((4)));
        System.out.println(code((5)));
        System.out.println(code((6)));
        System.out.println(code((7)));
        System.out.println(code((8)));
        System.out.println(code((9)));
        System.out.println(code((10)));
        System.out.println(code((11)));
        System.out.println(code((12)));
        System.out.println(code((13)));
        System.out.println(code((14)));
        System.out.println(code((15)));
        System.out.println(code((16)));
        System.out.println(code((17)));
        System.out.println(code((18)));
        System.out.println(code((19)));
        System.out.println(code((20)));
        System.out.println(code((21)));
        System.out.println(code((22)));
        System.out.println(code((23)));
        System.out.println(code((24)));
        System.out.println(code((25)));
        System.out.println(code((26)));
        System.out.println(code((27)));
        System.out.println(code((28)));
        System.out.println(code((29)));
        System.out.println(code((30)));
        System.out.println(code((31)));
        System.out.println(code((32)));
        System.out.println(code((33)));
        System.out.println(code((34)));
        System.out.println(code((35)));
        System.out.println(code((36)));
        System.out.println(code((37)));
        System.out.println(code((38)));
        System.out.println(code((39)));
        System.out.println(code((40)));
        System.out.println(code((41)));
        System.out.println(code((42)));
        System.out.println(code((43)));
        System.out.println(code((44)));
        System.out.println(code((45)));
        System.out.println(code((46)));
        System.out.println(code((47)));
        System.out.println(code((48)));
        System.out.println(code((49)));


    }

}
