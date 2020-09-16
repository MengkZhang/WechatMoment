package com.hx.wechatmoment.view;

import android.os.Bundle;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.viewmodel.MomentViewModel;

/**
 * Desc MomentActivity 朋友圈
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentActivity extends AbsLifecycleActivity<MomentViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        mViewModel.getUserInfo(this);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}