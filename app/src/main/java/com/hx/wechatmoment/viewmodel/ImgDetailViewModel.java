package com.hx.wechatmoment.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.repository.MomentRepository;

import java.util.List;

/**
 * Desc ImgDetailViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class ImgDetailViewModel extends AbsViewModel implements LifecycleObserver {

    /**
     * 构造方法
     *
     * @param application Application
     */
    public ImgDetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Integer 估值器
     */
    public Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }


    /**
     * Float 估值器
     */
    public Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * Argb 估值器
     */
    public int evaluateArgb(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24
                | (startR + (int) (fraction * (endR - startR))) << 16
                | (startG + (int) (fraction * (endG - startG))) << 8
                | (startB + (int) (fraction * (endB - startB)));
    }

    /**
     * 获取屏幕宽高
     *
     * @param activity Activity
     * @param isWidth  Boolean
     * @return int
     */
    public int getScreenWidthHeight(Activity activity, Boolean isWidth) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return isWidth ? metric.widthPixels : metric.heightPixels;
    }


}
