package com.hx.wechatmoment.repository;

import com.hx.wechatmoment.common.base.BaseRepository;
import com.hx.wechatmoment.bean.MomentListBean;
import com.hx.wechatmoment.bean.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;

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
     */
    public Observable<List<MomentListBean>> getMomentList() {
        return apiService.getMomentList();
    }


    /**
     * 获取用户信息
     *
     */
    public Observable<UserInfoBean> getUserInfo() {
        return apiService.getUserInfo();
    }


}
