package com.hx.wechatmoment.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Desc DateUtil
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public class DateUtil {

    public static String[] weekName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
        }

        return days;
    }

    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentMonthDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }


    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH) + 1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;

    }

    public static int getWeekDayFromDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDateFromString(year, month));
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return week_index;
    }


    @SuppressLint("SimpleDateFormat")
    public static Date getDateFromString(int year, int month) {
        String dateString = year + "-" + (month > 9 ? month : (0 + month)) + "-" + 01;
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static long getDateFromStringFormait(String dateString) {
        if (TextUtils.isEmpty(dateString) || dateString.split("-").length < 2) {
            return 0;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return date.getTime();
    }


    public static boolean isYesterday(long time) {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }


    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatTimeWithoutSecond(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String getCurrentTime() {
        try {
            return formatDate(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
            return "currentTime";
        }
    }


    /**
     * @param date 时间戳
     * @author Liuxiaochen
     * @time 2017/4/21 15:47
     */
    public static String formatDate(String date) {
        if (!TextUtils.isEmpty(date)) {
            return formatDate(new Date(Long.parseLong(date)));
        } else {
            return "";
        }
    }

    public static String formatDate(String date, String pattern) {
        if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(pattern)) {
            return formatDate(new Date(Long.parseLong(date)), pattern);
        } else {
            return "";
        }
    }

    /**
     * @param date 时间戳
     * @author Liuxiaochen
     * @time 2017/4/21 15:47
     */
    public static String formatTimeWithoutSecond(String date) {
        if (!TextUtils.isEmpty(date)) {
            return formatTimeWithoutSecond(new Date(Long.parseLong(date)));
        } else {
            return "";
        }
    }

    /**
     * @param date 时间戳
     * @author Liuxiaochen
     * @time 2017/4/21 15:47
     */
    public static String formatTime(String date) {
        if (!TextUtils.isEmpty(date)) {
            return formatTime(new Date(Long.parseLong(date)));
        } else {
            return "";
        }
    }

    public static Date reverseFormatDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String formatYear(int serviceDayLimit) {
        int year = serviceDayLimit / 365;
        int day = serviceDayLimit % 365;
        return (year > 0 ? year + "年" : "") + (day > 0 ? day + "天" : "");
    }


    /**
     * 计算两个日期差几天
     *
     * @param date1
     * @param date2
     * @author Liuxiaochen
     * @time 2017/11/8 14:19
     */
    public static int differentDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 < year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else if (year1 > year2) {
            int timeDistance = 0;
            for (int i = year2; i < year1; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance -= 366;
                } else    //不是闰年
                {
                    timeDistance -= 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {//同年
            //Ln.e("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
}
