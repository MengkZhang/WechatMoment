package com.hx.wechatmoment.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.hx.wechatmoment.common.base.BaseViewModel;
import com.hx.wechatmoment.common.base.BaseResObserver;
import com.hx.wechatmoment.common.constant.Constant;
import com.hx.wechatmoment.model.LoadMoreBean;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.model.MemoryMomentStore;
import com.hx.wechatmoment.repository.MomentRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc MomentViewModel
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentViewModel extends BaseViewModel<MomentRepository> implements LifecycleObserver {

    private MutableLiveData<UserInfoBean> userInfoData;

    private MutableLiveData<List<MomentListBean>> momentList;

    private MutableLiveData<LoadMoreBean> loadMore;

    private int page = Constant.ONE;

    public MomentViewModel(@NonNull Application application) {
        super(application);
        userInfoData = new MutableLiveData<>();
        momentList = new MutableLiveData<>();
        loadMore = new MutableLiveData<>();
    }

    public MutableLiveData<List<MomentListBean>> getMomentList() {
        if (momentList == null) {
            momentList = new MutableLiveData<>();
        }
        return momentList;
    }

    public MutableLiveData<UserInfoBean> getUserInfoData() {
        if (userInfoData == null) {
            userInfoData = new MutableLiveData<>();
        }
        return userInfoData;
    }

    public MutableLiveData<LoadMoreBean> getLoadMore() {
        if (loadMore == null) {
            loadMore = new MutableLiveData<>();
        }
        return loadMore;
    }


    public boolean isRefresh() {
        return page == Constant.ONE;
    }

    /**
     * 获取用户信息
     *
     * @param context Context
     */
    public void getUserInfo(Context context) {
        mRepository.getUserInfo().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseResObserver<UserInfoBean>(context) {
            @Override
            protected void onSuccess(UserInfoBean s) {
                userInfoData.setValue(s);
            }

            @Override
            protected void onFailure(Throwable e) {
                super.onFailure(e);
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
        mRepository.getMomentList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseResObserver<List<MomentListBean>>(context) {
            @Override
            protected void onSuccess(List<MomentListBean> momentListBeans) {
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

    private List<MomentListBean> getLocalMaxSize(List<MomentListBean> list) {
        //最多只取前两条数据
        int maxSize = Constant.MAX_SIZE;

        if (list.size() <= maxSize) {
            return list;
        }

        return list.subList(0, maxSize);
    }

}
