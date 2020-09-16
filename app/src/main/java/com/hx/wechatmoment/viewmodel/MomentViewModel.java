package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.repository.MomentRepository;

import java.util.List;

/**
 * Desc MomentViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentViewModel extends AbsViewModel<MomentRepository> {
    /**
     * 用户信息数据
     */
    private MutableLiveData<UserInfoBean> userInfoData;

    public MomentViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * getUserInfoData
     *
     * @return MutableLiveData
     */
    public MutableLiveData<UserInfoBean> getUserInfoData() {
        if (userInfoData == null) {
            userInfoData = new MutableLiveData<>();
        }
        return userInfoData;
    }

    public void getUserInfo(Context context) {
        mRepository.getUserInfo().subscribe(new BaseResObserver<UserInfoBean>(context) {
            @Override
            protected void onSuccess(UserInfoBean s) {
                Log.e("===z", "onNext hiUserInfoVo" + s.getProfileimage() + "-- " + s.getAvatar() + "---" + s.getUsername());
                userInfoData.setValue(s);
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
                Log.e("===z", "e=" + e.getMessage());
            }
        });
//        mRepository.getMomentList().subscribe(new BaseResObserver<List<MomentListBean>>(context) {
//            @Override
//            protected void onSuccess(List<MomentListBean> momentListBeans) {
//                Log.e("===z", "onNext size = " + momentListBeans.size());
//            }
//
//            @Override
//            protected void onFailure(Throwable e) {
//                super.onFailure(e);
//            }
//        });
    }
}
