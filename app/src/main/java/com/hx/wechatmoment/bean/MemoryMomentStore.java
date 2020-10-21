package com.hx.wechatmoment.bean;

import com.hx.wechatmoment.common.constant.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc MemoryListStore
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MemoryMomentStore {

    public static int totalPage = Constant.ONE;
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
     * @return List<MomentListBean>朋友圈列表
     */
    public synchronized List<MomentListBean> getSomeOfMomentList(int page) {
        int start = (page - Constant.ONE) * Constant.MAX_SIZE;
        int end = page * Constant.MAX_SIZE;
        int size = mList.size();
        if (size >= (end - Constant.ONE)) {
            return mList.subList(start, end);
        } else {
            return mList.subList(start,size);
        }

    }


}
