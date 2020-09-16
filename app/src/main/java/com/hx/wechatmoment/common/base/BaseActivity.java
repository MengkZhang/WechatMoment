package com.hx.wechatmoment.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

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
        //状态栏
        initStatusBar();
        //设置布局内容
        setContentView(getLayoutId());
        doSomeThingAfterSetContentView();
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
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
     *
     * @return
     */
    protected @ColorRes
    int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 获取NavigationBar颜色，默认白色
     *
     * @return
     */
    protected @ColorRes
    int getNavigationBarColor() {
        return R.color.white;
    }

    public int getStatusHeight() {
        return ImmersionBar.getStatusBarHeight(this);
    }

    public void setStateViewHeight(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = getStatusHeight();
            view.setLayoutParams(layoutParams);
        }
    }

    protected void initStatusBar() {

    }


    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        //doSomething
    }

    protected void onStateRefresh() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}

