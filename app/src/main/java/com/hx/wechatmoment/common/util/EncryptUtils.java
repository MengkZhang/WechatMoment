package com.hx.wechatmoment.common.util;

import java.security.MessageDigest;

/**
 * Desc 加密工具类
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public class EncryptUtils {

    private EncryptUtils() {
    }


    public static String stringToUtf(String source) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            byte[] byteArray = md.digest();
            StringBuilder rs = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    rs.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    rs.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
            return rs.toString();
        } catch (Exception e) {
            return source;
        }

    }


}
