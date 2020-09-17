package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.repository.MomentRepository;
import com.hx.wechatmoment.view.activity.MomentActivity;

import java.util.List;

/**
 * Desc MomentViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentViewModel extends AbsViewModel<MomentRepository> implements LifecycleObserver {
    /**
     * 用户信息数据
     */
    private MutableLiveData<UserInfoBean> userInfoData;
    /**
     * 列表数据
     */
    private MutableLiveData<List<MomentListBean>> momentList;
    /**
     * 是否刷新
     */
    private boolean isRefresh;

    /**
     * 构造方法
     *
     * @param application Application
     */
    public MomentViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * getMomentList
     *
     * @return MutableLiveData
     */
    public MutableLiveData<List<MomentListBean>> getMomentList() {
        if (momentList == null) {
            momentList = new MutableLiveData<>();
        }
        return momentList;
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

    /**
     * get刷新状态
     *
     * @return boolean
     */
    public boolean isRefresh() {
        return isRefresh;
    }

    /**
     * set刷新状态
     *
     * @param refresh boolean
     */
    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    /**
     * 获取用户信息
     *
     * @param context Context
     */
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

    }

    /**
     * 获取列表数据
     *
     * @param context Context
     */
    public void getMomentList(Context context) {
        mRepository.getMomentList().subscribe(new BaseResObserver<List<MomentListBean>>(context) {
            @Override
            protected void onSuccess(List<MomentListBean> momentListBeans) {
                momentList.setValue(momentListBeans);
                reSetRefreshState();
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
                reSetRefreshState();
            }
        });
    }

    /**
     * 还原刷新状态
     */
    private void reSetRefreshState() {
        if (isRefresh) {
            setRefresh(false);
        }
    }

    /**
     * 刷新
     *
     * @param context Context
     */
    public void refreshData(Context context) {
        isRefresh = true;
        getMomentList(context);
        getUserInfo(context);
    }
}
