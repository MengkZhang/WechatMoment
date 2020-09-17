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

    //默认嗨学课堂加密KEY
    public static final String http_security_key = "tu6bol3u";

    public static String encryptParams(String str) {
        return string2MD5UTF8(str + http_security_key);
    }

    /**
     * sig加密
     *
     * @param source
     * @param salt
     * @return
     */
    public static String sigEncrypt(String source, String salt) {
        //检查加密源是否存在
        if (source == null) {
            return null;
        }
        //添加盐值
        if (salt != null) {
            source += salt;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            byte[] byteArray = md.digest();
            StringBuilder rs = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    rs.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    rs.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
            return rs.toString();
        } catch (Exception e) {
            return source;
        }
    }

    public static String string2MD5UTF8(String source) {

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
