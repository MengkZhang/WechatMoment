package com.hx.wechatmoment.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.base.AbstractLifecycleActivity;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.common.base.VoidViewModel;

import butterknife.BindView;

/**
 * Desc CustomBitmapActivity--用自定义三级缓存的图片框架加载图片
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class CustomBitmapActivity extends AbstractLifecycleActivity<VoidViewModel> {

    private static final String URL = "url";
    private static final String AVATAR = "avatar";
    @BindView(R.id.iv_finish_bit)
    View mViewFinish;
    @BindView(R.id.iv_bitmap)
    ImageView mImageView;

    public static void navigateToCustomBitmapActivity(Context context, String url, boolean isAvatar) {
        Intent intent = new Intent(context, CustomBitmapActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(AVATAR, isAvatar);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        GlideUtil.loadWithSelfBitmap(this, getIntent().getStringExtra(URL), mImageView,
                getIntent().getBooleanExtra(URL, false) ? R.mipmap.icon_default_small_head : R.mipmap.default_place_img);
        mViewFinish.setOnClickListener(view -> finish());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).navigationBarColor(R.color.black).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bitmap;
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}