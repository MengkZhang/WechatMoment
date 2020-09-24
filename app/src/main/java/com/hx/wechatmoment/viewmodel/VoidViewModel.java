package com.hx.wechatmoment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;

import com.hx.wechatmoment.common.base.BaseViewModel;
import com.hx.wechatmoment.repository.VoidRepository;

/**
 * Desc VoidViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class VoidViewModel extends BaseViewModel<VoidRepository> implements LifecycleObserver {

    public VoidViewModel(@NonNull Application application) {
        super(application);
    }
}
