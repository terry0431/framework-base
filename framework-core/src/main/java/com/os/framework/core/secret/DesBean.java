package com.os.framework.core.secret;

/**
 * des加密bean
 *
 * @author gaoxy
 * @version 1.0.1
 */
public class DesBean {

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String strIn){
        Des des = new Des();
        return des.encrypt(strIn);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String strIn) {
        Des des = new Des();
        return des.decrypt(strIn);
    }
    
    public static void main(String[] args) {
        String e = "root";
        String d = "";
        try {
            d = encrypt(e);
            System.out.println(d);
            System.out.println(decrypt(d));
        } catch (Exception e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
