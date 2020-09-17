package com.hx.wechatmoment.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.viewmodel.SplashViewModel;

/**
 * Desc SplashActivity 闪屏页
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class SplashActivity extends AbsLifecycleActivity<SplashViewModel> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        StatusBarUtil.hideNavigationBar(this);
        mViewModel.delayTime();
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        mViewModel.getDelayToTime().observe(this, delayTimeBean -> {
            if (delayTimeBean != null) {
                int state = delayTimeBean.getState();
                if (state == 1) {
                    Intent intent = new Intent(SplashActivity.this, MomentActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * getLayoutId布局
     *
     * @return 布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}