package com.hx.wechatmoment.view;

import android.os.Bundle;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.viewmodel.MomentViewModel;

/**
 * Desc MainActivity
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MainActivity extends AbsLifecycleActivity<MomentViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel.getUserInfo(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}