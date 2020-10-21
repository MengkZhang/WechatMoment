package com.hx.wechatmoment.bean;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/5/15
 */
public class LoadMoreBean {
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
