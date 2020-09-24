package com.hx.wechatmoment.common.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hx.wechatmoment.common.util.ObjectClassUtil;

/**
 * Desc AbsRepository
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public abstract class AbstractLifecycleActivity<T extends BaseViewModel> extends BaseActivity {

    protected T mViewModel;

    public AbstractLifecycleActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = viewModelProviders(this, ObjectClassUtil.getInstance(this, 0));
        dataObserver();
        initEvent();
    }


    protected <VM extends ViewModel> VM viewModelProviders(AppCompatActivity fragment, @NonNull Class<VM> modelClass) {
        return (VM) new ViewModelProvider(fragment).get(modelClass);

    }

    protected void initEvent() {

    }

    protected void dataObserver() {

    }


}
