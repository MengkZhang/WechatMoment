package com.hx.wechatmoment.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 */
public class ScreenUtils {

    private ScreenUtils() {
    }

    public static int getPixelSize(Context context, int dimenId) {
        return context.getResources().getDimensionPixelSize(dimenId);
    }

    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.widthPixels;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        return display.heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static boolean isScreenPortrait(Context context) {
        if (context == null)
            return true;

        int orientation = context.getResources().getConfiguration().orientation;
        return orientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                || orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    public static void setScreenPortrait(Activity activity, boolean landScreen) {

        int orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (landScreen)
            orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

        activity.setRequestedOrientation(orientation);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context Context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取px单位的宽度
     *
     * @param width 宽度dp
     * @return px宽度
     */
    public static int getRealWidthPx(Context context,int width) {
        //设置图分辨率宽度375dp  1080px * 2506px  对应===>>> 375dp * 870dp 蓝湖上的分辨率
        //屏幕宽度
        int screenWidth = ScreenUtils.getDisplayWidth(context);
        return ((screenWidth * width) / 250);
    }

}