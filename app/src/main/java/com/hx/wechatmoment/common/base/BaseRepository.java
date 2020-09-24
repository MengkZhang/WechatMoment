package com.hx.wechatmoment.common.base;


import com.hx.wechatmoment.api.ApiService;
import com.hx.wechatmoment.common.http.HttpHelper;

/**
 * Desc BaseRepository
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class BaseRepository extends AbstractRepository {

    protected ApiService apiService;


    public BaseRepository() {
        apiService = HttpHelper.getInstance().create(ApiService.class);
    }


}
