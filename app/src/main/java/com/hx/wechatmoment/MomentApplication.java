package com.hx.wechatmoment;

import android.app.Application;

import com.hx.wechatmoment.common.http.HttpHelper;
import com.hx.wechatmoment.common.http.URL;
import com.hx.wechatmoment.common.util.GlideImageLoader;
import com.hx.wechatmoment.view.widget.ToastView;
import com.hx.wechatmoment.view.widget.nineimg.NineGridView;

/**
 * Desc MomentApplication
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
        initNineImage();
        initToast();
    }

    /**
     * 初始化toast
     */
    private void initToast() {
        ToastView.init(this);
    }

    /**
     * 初始化九宫格
     */
    private void initNineImage() {
        NineGridView.setImageLoader(new GlideImageLoader());
    }

    /**
     * 初始化http
     */
    private void initOkHttp() {
        new HttpHelper.Builder(this)
                .initOkHttp()
                .createRetrofit(URL.BASE_URL)
                .build();
    }
}
