package com.hx.wechatmoment.common.util;

import java.security.MessageDigest;

/**
 * Desc 加密工具类
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public class EncryptUtil {

    public static String stringToUtf(String source) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            byte[] byteArray = md.digest();
            StringBuilder rs = new StringBuilder();
            for (byte b : byteArray) {
                if (Integer.toHexString(0xFF & b).length() == 1) {
                    rs.append("0").append(
                            Integer.toHexString(0xFF & b));
                } else {
                    rs.append(Integer.toHexString(0xFF & b));
                }
            }
            return rs.toString();
        } catch (Exception e) {
            return source;
        }

    }


}
