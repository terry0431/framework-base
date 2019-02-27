package core.secret;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;


/**
 * Des
 *
 * @author gaoxy
 * @version 1.0.1
 */
public class Des {
    /**
     * 以下部分为加密算法，strDefaultKey为key
     */
    private static String strDefaultKey = "_framework0431@!";

    public Des() {
    }

    public Des(String strDefaultKey) {
        this.strDefaultKey = strDefaultKey;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public  byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public  String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    private byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }


    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        Key key = getKey(strDefaultKey.getBytes());
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) {
        try {
            return byteArr2HexStr(encrypt(strIn.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        Key key = getKey(strDefaultKey.getBytes());
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) {
        try {
            return new String(decrypt(hexStr2ByteArr(strIn)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    public static void main(String[] args) {
        Des des = new Des();
        String e = "root";
        String d = null;
        
        String codeChars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        
        String code = "";
        char codeChar ;
        for(int j = 0;j < 50;j ++){
        	strDefaultKey = "_Gframework*";
        	d = "";
        	code = "";
	        for(int i = 0;i < 5;i ++){
	        	codeChar = codeChars.charAt(r.nextInt(codeChars.length()));
	        	code += codeChar;
	        }
	        
	        strDefaultKey = code + strDefaultKey.substring(5,strDefaultKey.length());
	        
	        try {
	        	//System.out.println(code);
	            d = des.encrypt(code);
	            //System.out.println(d);
	            d = d.substring(0,2) + code.substring(0,1) + d.substring(2,4) + code.substring(1,2) + 
	            d.substring(4,6) + code.substring(2,3) + d.substring(6,7) + code.substring(3,4) +
	            d.substring(7,8) + code.substring(4,5) + d.substring(8,d.length());
	            System.out.println(d);
	            strDefaultKey = "_Gframework*";
	            code = d.substring(2,3) + d.substring(5,6) + d.substring(8,9) + d.substring(10,11) + d.substring(12,13);
	            //System.out.println(code);
	            
	            d = d.substring(0,2) + d.substring(3,5) + d.substring(6,8) + d.substring(9,10) + d.substring(11,12) + d.substring(13,d.length());
	            	
	            //System.out.println(d);
	            
	            strDefaultKey = code + strDefaultKey.substring(5,strDefaultKey.length());
	            
	            //System.out.println(des.decrypt(d));
	            //System.out.println(des.decrypt("4a93dadfce168ae136c408df422bc5ed"));
	        } catch (Exception e1) {
	            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	        }
        }
    }
}

