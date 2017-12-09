package cn.edu.uestc.acm.cdoj.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lagranmoon on 2017/7/23.
 * 用于处理信息加密的工具类
 */

public class DigestUtil {
    private static String TAG = "DigestUtil";

    public static String digest(String input, String algorithm) {
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

    public static String md5(String input) {
        return digest(input, "MD5");
    }

    public static String sha1(String input) {
        return digest(input, "SHA-1");
    }

    public static String sha256(String input) {
        return digest(input, "SHA-256");
    }

    public static String byteArray2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte x : bytes) {
            stringBuilder.append(String.format("%x", (x & 0xF0)>>4)).append(String.format("%x", x & 0x0F));
        }
        return stringBuilder.toString();
    }
}

