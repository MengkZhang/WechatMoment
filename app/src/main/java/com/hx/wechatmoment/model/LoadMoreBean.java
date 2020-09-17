package com.hx.wechatmoment.model;

import java.io.Serializable;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/5/15
 */
public class LoadMoreBean implements Serializable {
    private boolean isLoadMoreSuccess;
    private boolean hasMoreData;

    public boolean isLoadMoreSuccess() {
        return isLoadMoreSuccess;
    }

    public void setLoadMoreSuccess(boolean loadMoreSuccess) {
        isLoadMoreSuccess = loadMoreSuccess;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }
}
