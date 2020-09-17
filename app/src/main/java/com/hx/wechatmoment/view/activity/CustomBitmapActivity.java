package com.hx.wechatmoment.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbsLifecycleActivity;
import com.hx.wechatmoment.common.listener.MultiClickListener;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.viewmodel.SplashViewModel;

import butterknife.BindView;

/**
 * Desc CustomBitmapActivity--用自定义三级缓存的图片框架加载图片
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class CustomBitmapActivity extends AbsLifecycleActivity<SplashViewModel> {


    @BindView(R.id.iv_finish_bit)
    View mViewFinish;
    @BindView(R.id.iv_bitmap)
    ImageView mImageView;

    public static void navigateToCustomBitmapActivity(Context context, String url) {
        Intent intent = new Intent(context, CustomBitmapActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        GlideUtil.loadWithSelfBitmap(this, getIntent().getStringExtra("url"), mImageView, R.mipmap.icon_default_small_head);
        mViewFinish.setOnClickListener(new MultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                finish();
            }
        });
    }


    /**
     * getLayoutId布局
     *
     * @return 布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_bitmap;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}