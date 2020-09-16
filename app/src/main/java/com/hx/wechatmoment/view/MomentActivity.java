package com.hx.wechatmoment.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.model.UserInfoBean;
import com.hx.wechatmoment.viewmodel.MomentViewModel;

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
    RelativeLayout rlTitleView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        //获取用户信息
        mViewModel.getUserInfo(this);
    }

    @Override
    protected void dataObserver() {
        super.dataObserver();
        mViewModel.getUserInfoData().observe(this, userInfoBean -> {
            if (userInfoBean != null) {
                setUserInfo(userInfoBean);
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
        Glide.with(MomentActivity.this).load(userInfoBean.getProfileimage()).into(mIvSelfBg);
        Glide.with(MomentActivity.this).load(userInfoBean.getAvatar()).into(mIvSelfHead);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}