package com.hx.wechatmoment.common.base;

import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.hx.wechatmoment.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Desc BaseActivity
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView(getLayoutId());
        doSomeThingAfterSetContentView();
        bind = ButterKnife.bind(this);
        initViews(savedInstanceState);
        initToolBar();

    }

    protected void doSomeThingAfterSetContentView() {
        if (hasImmersionBar()) {
            initImmersionBar();
        }
    }

    protected boolean hasImmersionBar() {
        return true;
    }


    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .statusBarColor(getStatusBarColor())
                .navigationBarColor(getNavigationBarColor())
                .init();
    }

    /**
     * 获取StatusBar颜色，默认白色
     */
    protected @ColorRes
    int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 获取NavigationBar颜色，默认白色
     */
    protected @ColorRes
    int getNavigationBarColor() {
        return R.color.white;
    }


    protected void initStatusBar() {

    }

    /**
     * 获取布局文件
     *
     * @return 布局
     */
    public abstract int getLayoutId();

    /**
     * 初始化布局
     *
     * @param savedInstanceState 保存页面状态的Bundle
     */
    public abstract void initViews(Bundle savedInstanceState);


    protected void initToolBar() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}

