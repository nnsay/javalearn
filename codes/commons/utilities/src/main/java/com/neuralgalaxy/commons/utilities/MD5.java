package com.neuralgalaxy.commons.utilities;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5 工具类
 */
public class MD5 {

    /**
     * 将数据进行 MD5 加密，并以16进制字符串格式输出
     *
     * @param data 数据内容
     */
    @SneakyThrows
    public static String md5(String data) {
        byte[] md5 = md5(data.getBytes(StandardCharsets.UTF_8));
        return toHexString(md5);
    }

    /**
     * 将字节数组进行 MD5 加密
     *
     * @param data 数据字节内容
     * @return hex
     */
    @SneakyThrows
    public static byte[] md5(byte[] data) {
        MessageDigest md = MessageDigest.getInstance("md5");
        return md.digest(data);
    }

    /**
     * 将加密后的字节数组，转换成16进制的字符串
     *
     * @param md5 数据内容
     * @return hex
     */
    private static String toHexString(byte[] md5) {
        StringBuilder sb = new StringBuilder();
        for (byte b : md5) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
}
