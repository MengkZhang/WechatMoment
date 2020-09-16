package com.hx.wechatmoment.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.view.adapter.MomentAdapter;
import com.hx.wechatmoment.view.widget.StatusView;
import com.hx.wechatmoment.viewmodel.MomentViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc MomentActivity 朋友圈
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentActivity extends AbsLifecycleActivity<MomentViewModel> {
    /**
     * 列表RecyclerView
     */
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    /**
     * StatusView
     */
    @BindView(R.id.sv_status)
    StatusView mStatusView;
    /**
     * SwipeRefreshLayout
     */
    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    /**
     * AppBarLayout
     */
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    /**
     * titleBar
     */
    @BindView(R.id.rl_bar_title)
    RelativeLayout mRlTitleView;
    /**
     * 自身背景
     */
    @BindView(R.id.iv_user_bg)
    ImageView mIvSelfBg;
    /**
     * 自身头像
     */
    @BindView(R.id.iv_self_head)
    ImageView mIvSelfHead;
    /**
     * 自身昵称
     */
    @BindView(R.id.tv_self_name)
    TextView mTvSelfName;
    /**
     * LinearLayoutManager
     */
    private LinearLayoutManager mLayoutManager;
    /**
     * 数据
     */
    private List<MomentListBean> mList;
    /**
     * MomentAdapter
     */
    private MomentAdapter mAdapter;
    /**
     * appBar高度
     */
    private int mAppBarLayoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        //获取用户信息
        mViewModel.getUserInfo(this);
        initRecyclerView();
    }


    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MomentAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mAppBarLayout.post(() -> mAppBarLayoutHeight = mAppBarLayout.getHeight());
    }

    /**
     * 初始化事件
     */
    @Override
    protected void initEvent() {
        super.initEvent();
        //appBarEvent事件
        appBarEvent();
    }

    /**
     * appBarEvent事件
     */
    private void appBarEvent() {
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                mSwipeRefreshLayout.setEnabled(true);
                //将标题栏的颜色设置为完全不透明状态
                mRlTitleView.setAlpha(0f);
                mStatusView.setAlpha(0f);
                StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, false);
            } else {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                int abs = Math.abs(verticalOffset);
                if (abs <= mAppBarLayoutHeight - 100) {
                    float alpha = (float) abs / mAppBarLayoutHeight;
                    mRlTitleView.setAlpha(alpha);
                    mStatusView.setAlpha(alpha);
                    StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, false);
                } else {
                    //将标题栏的颜色设置为完全不透明状态
                    mRlTitleView.setAlpha(1.0f);
                    mStatusView.setAlpha(1.0f);
                    StatusBarUtil.setImmersiveStatusBar(MomentActivity.this, true, ContextCompat.getColor(MomentActivity.this, R.color.home_status_bar_color));
                }
            }
        });
    }

    /**
     * 数据回调
     */
    @Override
    protected void dataObserver() {
        super.dataObserver();
        //用户信息回调
        observerUserInfo();
        //列表回调
        observerMomentList();
    }

    /**
     * ，列表数据回调
     */
    private void observerMomentList() {
        mViewModel.getMomentList().observe(this, momentListBeans -> {
            if (momentListBeans != null && momentListBeans.size() != 0) {
                mList.addAll(momentListBeans);
                mAdapter.notifyDataSetChanged();
            } else {
                //列表为空
            }
        });
    }

    /**
     * 用户信息回调
     */
    private void observerUserInfo() {
        mViewModel.getUserInfoData().observe(this, userInfoBean -> {
            if (userInfoBean != null) {
                setUserInfo(userInfoBean);
                mViewModel.getMomentList(MomentActivity.this);
            } else {
                //用户信息为空 展示缺省页
            }
        });
    }

    /**
     * 设置用户信息
     *
     * @param userInfoBean UserInfoBean
     */
    private void setUserInfo(UserInfoBean userInfoBean) {
        mTvSelfName.setText(userInfoBean.getUsername());
        GlideUtil.load(this,userInfoBean.getProfileimage(),mIvSelfBg,R.mipmap.default_place_img);
        GlideUtil.load(this,userInfoBean.getAvatar(),mIvSelfHead,R.mipmap.icon_default_small_head);
    }

    /**
     * getLayoutId布局
     *
     * @return 布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}