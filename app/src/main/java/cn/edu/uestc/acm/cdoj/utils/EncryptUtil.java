package cn.edu.uestc.acm.cdoj.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lagranmoon on 2017/7/23.
 * 用于处理信息加密的工具类
 */

public class EncryptUtil {

    private static final String TAG = "EncryptUtil";

    /**
     * 加密字符串并返回加密后的内容
     * @param input 待加密的内容
     * @param algorithm 加密算法，支持md5,sha1和sha256加密
     * @return 加密后的内容
     */
    public static String encrypt(String input, String algorithm) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, "No Such Algorithm");
            return input;
        }
        return byteArray2Hex(digest.digest(input.getBytes()));
    }

    private String md5(String input) {
        return encrypt(input, "MD5");
    }

    private String sha1(String input) {
        return encrypt(input, "SHA-1");
    }

    private String sha256(String input) {
        return encrypt(input, "SHA-256");
    }

    /**
     * 用于将加密后的二进制数转换成需要的16进制
     * @param bytes 加密后的二进制数组
     * @return 16进制表示的加密后字符串
     */
    private static String byteArray2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte x : bytes) {
            stringBuilder.append(String.format("%x", (x & 0xF0)>>4)).append(String.format("%x", x & 0x0F));
        }
        return stringBuilder.toString();
    }
}

