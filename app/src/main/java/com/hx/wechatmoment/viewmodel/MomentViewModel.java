package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.repository.MomentRepository;

/**
 * Desc MomentViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentViewModel extends AbsViewModel<MomentRepository> {
    public MomentViewModel(@NonNull Application application) {
        super(application);
    }

    public void getUserInfo(Context context) {
        mRepository.getUserInfo().subscribe(new BaseResObserver<UserInfoBean>(context) {
            @Override
            protected void onSuccess(UserInfoBean s) {
                Log.e("===z","onNext hiUserInfoVo" + s.getProfileimage() + "-- " + s.getAvatar() + "---" + s.getUsername());
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
                Log.e("===z","e=" + e.getMessage());
            }
        });
    }
}
