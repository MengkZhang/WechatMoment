package com.hx.wechatmoment.api;


import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Desc ApiService
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public interface ApiService {
    /**
     * 请求用户信息
     *
     * @return Observable
     */
    @GET("user/jsmith")
    Observable<UserInfoBean> getUserInfo();

    /**
     * 请求列表
     *
     * @return Observable
     */
    @GET("user/jsmith/tweets")
    Observable<List<MomentListBean>> getMomentList();

}
