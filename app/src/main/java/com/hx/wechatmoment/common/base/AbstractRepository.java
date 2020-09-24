package com.hx.wechatmoment.common.base;

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



    public AbstractRepository() {
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
