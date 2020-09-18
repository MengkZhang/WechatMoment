package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.AbsViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.common.constant.Constant;
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
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;
    /**
     * 当前页数
     */
    private int page = Constant.ONE;


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
        return page == Constant.ONE;
    }

    /**
     * isFirstLoad
     *
     * @return boolean
     */
    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    /**
     * setFirstLoad
     *
     * @param firstLoad boolean
     */
    public void setFirstLoad(boolean firstLoad) {
        isFirstLoad = firstLoad;
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
                userInfoData.setValue(s);
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
                Log.e("===z", "e=" + e.getMessage());
                userInfoData.setValue(null);
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
                setFirstLoad(false);
                if (momentListBeans != null && momentListBeans.size() != 0) {
                    int size = momentListBeans.size();
                    int length = size / Constant.MAX_SIZE;
                    int other = size % Constant.MAX_SIZE;
                    MemoryMomentStore.totalPage = other == 0 ? length : (length + 1);
                    momentList.setValue(getLocalMaxSize(momentListBeans));
                    MemoryMomentStore.getInstance().saveMomentList(momentListBeans);
                }
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
                setFirstLoad(false);
                momentList.setValue(null);
            }
        });
    }


    /**
     * 刷新
     *
     * @param context Context
     */
    public void refreshData(Context context) {
        page = Constant.ONE;
        getMomentList(context);
        getUserInfo(context);
    }

    /**
     * 加载更多
     */
    public void loadMoreData() {

        if (page < MemoryMomentStore.totalPage) {
            //加载更多
            page++;
            momentList.setValue(MemoryMomentStore.getInstance().getSomeOfMomentList(page));

            setLoadMoreState(true);

        } else {
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
    }

    /**
     * 本地获取数组的最大值
     *
     * @param list List<MomentListBean>
     * @return List<MomentListBean>
     */
    private List<MomentListBean> getLocalMaxSize(List<MomentListBean> list) {
        //最多只取前两条数据
        int maxSize = Constant.MAX_SIZE;

        if (list.size() <= maxSize) {
            return list;
        }

        return list.subList(0, maxSize);
    }

}
