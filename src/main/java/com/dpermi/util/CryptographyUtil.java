package com.dpermi.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by val620@126.com on 2018/7/13.
 */
public class CryptographyUtil {
    /**
     * 将输入字符串用MD5加密
     *
     * @param inputStr 输入字符串
     * @return 密文
     */
    public static String getMD5Encrypt(String inputStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            String md5 = parseByte2HexStr(md.digest());
            return md5;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将2进制转换为16进制
     *
     * @param buf 16进制字符串
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为2进制
     *
     * @param hexStr 16进制字符串
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static SecretKeySpec getKeySpec(String key) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(key.getBytes());
            kgen.init(128, sr);// 利用公钥作为随机数初始化
            SecretKey secretKey = kgen.generateKey();// 根据公钥，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回null。
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            return keySpec;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密字符串
     *
     * @param inputStr 需要被加密的字符串
     * @param key      加密需要的公钥
     * @return 密文
     */
    public static String getAESEncrypt(String inputStr, String key) {
        try {
            SecretKeySpec keySpec =getKeySpec(key);
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = inputStr.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            String encryptStr = parseByte2HexStr(result);//字节数组转化成16进制数
            return encryptStr;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAESEncrypt(String inputStr) {
        String key = FileConfig.getConfig("dpermi.AESkey");
        if (key == null || key.equals("")) {
            return "application.properties没有配置dpermi.AESkey";
        }
//        LogHelper.warn("dpermi.AESkey:" + key);
        String encryptStr = getAESEncrypt(inputStr, key);
        return encryptStr;
    }

    /**
     * AES加密字符串
     *
     * @param encryptStr 需要被解密的字符串
     * @param key        解密需要的公钥
     * @return 明文
     */
    public static String getAESDecrypt(String encryptStr, String key) {
        try {
            SecretKeySpec keySpec =getKeySpec(key);
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化为解密模式的密码器
            byte[] inputStr = parseHexStr2Byte(encryptStr);//16进制数转化成字节数组
            byte[] result = cipher.doFinal(inputStr);
            String decryptStr = new String(result);//字节数组转成字符串
            return decryptStr;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAESDecrypt(String encryptStr) {
        String key = FileConfig.getConfig("dpermi.AESkey");
        if (key == null || key.equals("")) {
            return "application.properties没有配置dpermi.AESkey";
        }
        String decryptStr = getAESDecrypt(encryptStr, key);
        return decryptStr;
    }
}
