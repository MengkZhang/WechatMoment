package com.hx.wechatmoment.repository;

import com.hx.wechatmoment.model.MomentListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc MemoryListStore
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MemoryMomentStore {


    private List<MomentListBean> mList = new ArrayList<>();

    private MemoryMomentStore() {
    }

    private static MemoryMomentStore instance;

    public static MemoryMomentStore getInstance() {
        if (instance == null) {
            synchronized (MemoryMomentStore.class) {
                if (instance == null) {
                    instance = new MemoryMomentStore();
                }
            }
        }
        return instance;
    }

    public synchronized void saveMomentList(List<MomentListBean> list) {
        mList.clear();
        mList.addAll(list);
    }


    public synchronized List<MomentListBean> loadAllMomentList() {
        return mList;
    }


    public synchronized List<MomentListBean> getSomeOfMomentList(int start, int end) {
        if (mList.size() >= (end - 1)) {
            return mList.subList(start, end);
        }
        return mList;
    }

    public synchronized void removeCookie() {
        mList.clear();
    }

}
