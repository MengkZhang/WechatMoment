package com.hx.wechatmoment.common.statusbar;

import android.os.Build;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Desc PhoneModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class PhoneModel {

    private PhoneModel() {

    }

    /**
     * 判断手机是不是flyme
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            String brand = Build.BRAND;
            return brand != null && brand.toLowerCase().contains("meizu");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMIUISimple() {
        try {
            String brand = Build.MANUFACTURER;
            return brand != null && brand.toLowerCase().contains("xiaomi");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断系统是不是miui
     *
     * @return
     */
    public static boolean isMIUI() {
        try {
            String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
            String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
            String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
            return getProperty(KEY_MIUI_VERSION_CODE, null) != null || getProperty(KEY_MIUI_VERSION_NAME, null) != null || getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (Exception e) {
            return false;
        }
    }


    private static Properties properties;


    private static String getProperty(final String name, final String defaultValue) {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream  fileInputStream = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException e) {
                return null;
            }
        }
        return properties.getProperty(name, defaultValue);
    }
}
