package com.hx.wechatmoment.common.base;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Desc 基类返回值处理
 *
 * @author zhangxiaolin
 * Date 2020/8/27
 */
public abstract class BaseResObserver<T> implements Observer<T> {

    private Lifecycle mLifecycle;

    public BaseResObserver(Context ctx) {
        if (ctx instanceof LifecycleOwner) {
            mLifecycle = ((LifecycleOwner) ctx).getLifecycle();
        }
    }

    public BaseResObserver() {
    }

    public BaseResObserver(Lifecycle lifecycle) {
        mLifecycle = lifecycle;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    /**
     * 当前上下文生命周期判断
     */
    private boolean shouldPostResult() {
        if (mLifecycle != null) {
            return mLifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED);
        }
        return true;
    }

    @Override
    public void onNext(T baseInfo) {
        if (baseInfo == null) {
            return;
        }


        if (!shouldPostResult()) {
            return;
        }
        onRequestEnd();

        try {
            onSuccess(baseInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onError(Throwable e) {
        if (!shouldPostResult()) {
            return;
        }
        onRequestEnd();

        onFailure(e);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功
     */
    protected abstract void onSuccess(T t);

    /**
     * 返回失败
     */
    protected void onFailure(Throwable e) {

    }

    protected void onRequestStart() {

    }

    protected void onRequestEnd() {

    }
}
