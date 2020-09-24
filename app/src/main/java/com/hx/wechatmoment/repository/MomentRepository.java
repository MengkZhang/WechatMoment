package com.hx.wechatmoment.repository;

import com.hx.wechatmoment.common.base.BaseRepository;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc MomentRepository
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentRepository extends BaseRepository {
    public MomentRepository() {
    }

    /**
     * 获取列表
     *
     * @return Observable
     */
    public Observable<List<MomentListBean>> getMomentList() {
        return apiService.getMomentList();
    }


    /**
     * 获取用户信息
     *
     * @return Observable
     */
    public Observable<UserInfoBean> getUserInfo() {
        return apiService.getUserInfo();
    }


}
