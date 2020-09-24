package com.hx.wechatmoment.view.activity;

import android.os.Bundle;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbstractLifecycleActivity;
import com.hx.wechatmoment.common.constant.Constant;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.viewmodel.SplashViewModel;

/**
 * Desc SplashActivity 闪屏页
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class SplashActivity extends AbstractLifecycleActivity<SplashViewModel> {


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
                if (Constant.NEED_FINISH_SPLASH == delayTimeBean.getState()) {
                    MomentActivity.navigateToMomentActivity(SplashActivity.this);
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