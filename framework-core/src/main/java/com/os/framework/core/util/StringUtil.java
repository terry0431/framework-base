package com.os.framework.core.util;

/**
 * 字符串工具类
 *
 * @author wangbo
 */
public class StringUtil {

    
    /**
     * 检查数组中是否包含元素
     *
     * @param element	元素
     * @param array	数组
     * @return
     */
    public static boolean contains(String element, String[] array) {
        boolean isMatch = false;
        for (String str : array) {
            if (array.equals(element)) {
                isMatch = true;
                break;
            }
        }
        return isMatch;
    }

    

    public static void main(String[] args) {

//        System.out.println(hexString2binaryString("4005"));  //大气压
//        HexString2int("3f00");  //温度
//        HexString2int("000003c9");  //湿度
//        HexString2int("00000000");  //雨量
//        HexString2int("0000001d");  //风向
//        HexString2int("00000007");  //风速
        

    }
}
