package com.hx.wechatmoment;

import android.app.Application;

import com.hx.wechatmoment.common.http.HttpHelper;
import com.hx.wechatmoment.common.http.URL;

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
