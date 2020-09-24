package com.hx.wechatmoment.common.base;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Desc AbsRepository
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public abstract class AbstractRepository {

    private CompositeDisposable mCompositeDisposable;

    public MutableLiveData<String> loadState;


    public AbstractRepository() {
        loadState = new MutableLiveData<>();
    }

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
