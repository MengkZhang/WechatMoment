package com.hx.wechatmoment.common.util;

import java.text.DecimalFormat;

/**
 * Desc NumberUtils
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class NumberUtils {

    private NumberUtils() {
    }

    private final static float EPSILONF = 0.000001f;
    private final static double EPSILOND = 0.000001d;

    public static String removeDecimal(float netPayMoney) {
        return removeDecimal(netPayMoney, true);
    }

    public static String removeDecimal(float netPayMoney, boolean keep2zero) {
        double temp = netPayMoney % 1.0;
        if (isDoubleZero(temp) && !keep2zero) {
            return String.valueOf((long) netPayMoney);
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(netPayMoney);
    }

    public static boolean isFloatZero(float param) {
        return Math.abs(param) < EPSILONF;
    }

    public static boolean isDoubleZero(double param) {
        return Math.abs(param) < EPSILOND;
    }

    public static boolean equalsFloat(float param1, float param2) {
        return Math.abs(param1 - param2) < EPSILONF;
    }

    public static boolean equalsDouble(double param1, double param2) {
        return Math.abs(param1 - param2) < EPSILOND;
    }
}
