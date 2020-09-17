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

    /**
     * 获取5条
     *
     * page = 1  -----    0 - 5     01234
     * page = 2  -----    5 - 10    56789
     * page = 3  -----    10 -15    10 11 12 13 14
     *
     * start = (page - 1) * 5
     * end   = page * 5
     *
     * @param page page
     * @return List<MomentListBean>
     */
    public synchronized List<MomentListBean> getSomeOfMomentList(int page) {
        int start = (page - 1) * 5;
        int end = page * 5;
        int size = mList.size();
        if (size >= (end - 1)) {
            return mList.subList(start, end);
        } else {
            return mList.subList(start,size);
        }

//        if (page == 2) {
//            return mList.subList(5, 10);
//        } else if (page == 3) {
//            return mList.subList(10, 15);
//        } else if (page == 4) {
//            return mList.subList(15, 20);
//        } else {
//            return mList.subList(20, mList.size());
//        }
    }

    public synchronized void removeCookie() {
        mList.clear();
    }

}
