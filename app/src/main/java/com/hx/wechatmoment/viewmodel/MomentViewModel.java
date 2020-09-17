package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.model.LoadMoreBean;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.repository.MemoryMomentStore;
import com.hx.wechatmoment.repository.MomentRepository;

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
     * loadMore
     */
    private MutableLiveData<LoadMoreBean> loadMore;
    /**
     * 是否刷新
     */
    private boolean isRefresh;
    /**
     * 是否是加载更多
     */
    private boolean isLoadMore;
    /**
     * 当前页数
     */
    private int page = 1;


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
     * getLoadMore
     *
     * @return MutableLiveData
     */
    public MutableLiveData<LoadMoreBean> getLoadMore() {
        if (loadMore == null) {
            loadMore = new MutableLiveData<>();
        }
        return loadMore;
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
                if (momentListBeans != null && momentListBeans.size() != 0) {
                    int size = momentListBeans.size();
                    int length = size / 5;
                    int other = size % 5;
                    MemoryMomentStore.totalPage = other == 0 ? length : (length + 1);
                    momentList.setValue(getLocalMaxSize(momentListBeans));
                    MemoryMomentStore.getInstance().saveMomentList(momentListBeans);
                }
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
        page = 1;
        getMomentList(context);
        getUserInfo(context);
    }

    /**
     * 加载更多
     */
    public void loadMoreData() {
        isLoadMore = true;

        if (page < MemoryMomentStore.totalPage) {
            //加载更多
            page++;
            Log.e("====z", "page = " + page);
            momentList.setValue(MemoryMomentStore.getInstance().getSomeOfMomentList(page));

            setLoadMoreState(true);

        } else {
            Log.e("===z", "没有更多数据了");
            setLoadMoreState(false);
        }

    }

    /**
     * 设置加载更多
     *
     * @param hasMore 是否还有更多
     */
    private void setLoadMoreState(boolean hasMore) {
        LoadMoreBean loadMoreBean = new LoadMoreBean();
        loadMoreBean.setLoadMoreSuccess(true);
        loadMoreBean.setHasMoreData(hasMore);
        loadMore.setValue(loadMoreBean);
        isLoadMore = false;
    }

    /**
     * 本地获取数组的最大值
     *
     * @param list List<MomentListBean>
     * @return List<MomentListBean>
     */
    private List<MomentListBean> getLocalMaxSize(List<MomentListBean> list) {
        //最多只取前两条数据
        int maxSize = 5;

        if (list.size() <= maxSize) {
            return list;
        }

        return list.subList(0, maxSize);
    }

}
