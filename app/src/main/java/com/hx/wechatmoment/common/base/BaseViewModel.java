package com.hx.wechatmoment.common.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hx.wechatmoment.common.util.ObjectClassUtil;

/**
 * Desc AbsViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class BaseViewModel<T extends AbstractRepository> extends AndroidViewModel {


    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = ObjectClassUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }

}
