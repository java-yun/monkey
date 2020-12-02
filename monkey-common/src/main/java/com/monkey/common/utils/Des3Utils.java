package com.monkey.common.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * DES3 加解密工具
 * @author jiangyun
 * @version 0.0.1
 * @date 2020/12/2 14:41
 */
public class Des3Utils {

    /**
     * 定义加密算法，DESede即3DES
     */
    private static final String ALGORITHM = "DESede";

    private static final String DEFAULT_KEY = "";



    public static String encrypt(String src) {
        return encrypt(src, DEFAULT_KEY);
    }

    public static String decrypt(String src){
        return decrypt(src, DEFAULT_KEY);
    }

    /**
     * 加密方法
     * @param src 被加密字符串
     * @param key key
     * @return String
     */
    public static String encrypt(String src, String key) {
        byte[] targetSrc = src.getBytes(StandardCharsets.UTF_8);
        try {
            // 生成密钥
            SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM);
            // 实例化Cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] ciphers = cipher.doFinal(targetSrc);
            byte[] base64 = Base64.getEncoder().encode(ciphers);
            return new String(base64, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密方法
     * @param src 需要解密的串
     * @param key key
     * @return String
     */
    public static String decrypt(String src, String key){
            byte[] targetSrc = Base64.getDecoder().decode(src.getBytes());
        byte[] ciphers;
        try {
            SecretKey secretKey = new SecretKeySpec(build3DesKey(key), ALGORITHM);
            Cipher c1 = Cipher.getInstance(ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, secretKey);
            ciphers = c1.doFinal(targetSrc);
            return new String(ciphers, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 根据字符串生成密钥24位的字节数组
     *
     * @param keyStr keyStr
     * @return byte[]
     */
    private static byte[] build3DesKey(String keyStr) {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(temp, 0, key, 0, Math.min(key.length, temp.length));
        return key;
    }
}
