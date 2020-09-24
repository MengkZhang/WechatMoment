package com.hx.wechatmoment.view.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.statusbar.StatusBarUtil;
import com.hx.wechatmoment.view.adapter.ImagePreviewAdapter;
import com.hx.wechatmoment.view.widget.nineimg.ImageInfo;

import java.util.List;

import butterknife.BindView;

/**
 * Desc ImgDetailActivity 朋友圈图片预览页
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class ImgDetailActivity extends AppCompatActivity implements ViewTreeObserver.OnPreDrawListener {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.rootView)
    RelativeLayout mRootView;
    @BindView(R.id.tv_pager)
    TextView mTvCount;
    @BindView(R.id.iv_finish)
    View mIvFinish;

    public static final String IMAGE_INFO = "IMAGE_INFO";
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final int ANIMATE_DURATION = 200;

    private ImagePreviewAdapter imagePreviewAdapter;
    private List<ImageInfo> imageInfo;
    private int currentItem;
    private int imageHeight;
    private int imageWidth;
    private int screenWidth;
    private int screenHeight;


    @SuppressLint("StringFormatMatches")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StatusBarUtil.setImmersiveStatusBar(this, false);
        initData();
        intViews();
    }

    private void initData() {
        screenWidth = getScreenWidthHeight(true);
        screenHeight = getScreenWidthHeight(false);
        Intent intent = getIntent();
        imageInfo = (List<ImageInfo>) intent.getSerializableExtra(IMAGE_INFO);
        currentItem = intent.getIntExtra(CURRENT_ITEM, 0);
    }

    @SuppressLint("StringFormatMatches")
    private void intViews() {
        imagePreviewAdapter = new ImagePreviewAdapter(this, imageInfo);
        mViewPager.setAdapter(imagePreviewAdapter);
        mViewPager.setCurrentItem(currentItem);
        mViewPager.getViewTreeObserver().addOnPreDrawListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                mTvCount.setText(String.format(getString(R.string.select), currentItem + 1, imageInfo.size()));
            }
        });
        mTvCount.setText(String.format(getString(R.string.select), currentItem + 1, imageInfo.size()));
        mIvFinish.setOnClickListener(v -> finish());
    }



    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 绘制前开始动画
     */
    @Override
    public boolean onPreDraw() {
        mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
        final View view = imagePreviewAdapter.getPrimaryItem();
        final ImageView imageView = imagePreviewAdapter.getPrimaryImageView();
        computeImageWidthAndHeight(imageView);

        final ImageInfo imageData = imageInfo.get(currentItem);
        final float vx = imageData.imageViewWidth * 1.0f / imageWidth;
        final float vy = imageData.imageViewHeight * 1.0f / imageHeight;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.0f);
        valueAnimator.addUpdateListener(animation -> {
            long duration = animation.getDuration();
            long playTime = animation.getCurrentPlayTime();
            float fraction = duration > 0 ? (float) playTime / duration : 1f;
            if (fraction > 1) {
                fraction = 1;
            }
            view.setTranslationX(evaluateInt(fraction, imageData.imageViewX + imageData.imageViewWidth / 2 - imageView.getWidth() / 2, 0));
            view.setTranslationY(evaluateInt(fraction, imageData.imageViewY + imageData.imageViewHeight / 2 - imageView.getHeight() / 2, 0));
            view.setScaleX(evaluateFloat(fraction, vx, 1));
            view.setScaleY(evaluateFloat(fraction, vy, 1));
            view.setAlpha(fraction);
            mRootView.setBackgroundColor(evaluateArgb(fraction, Color.TRANSPARENT, Color.BLACK));
        });
        addIntoListener(valueAnimator);
        valueAnimator.setDuration(ANIMATE_DURATION);
        valueAnimator.start();
        return true;
    }


    /**
     * 计算图片的宽高
     */
    private void computeImageWidthAndHeight(ImageView imageView) {
        // 获取真实大小
        Drawable drawable = imageView.getDrawable();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        // 计算出与屏幕的比例，用于比较以宽的比例为准还是高的比例为准，因为很多时候不是高度没充满，就是宽度没充满
        float h = screenHeight * 1.0f / intrinsicHeight;
        float w = screenWidth * 1.0f / intrinsicWidth;
        if (h > w) {
            h = w;
        } else {
            w = h;
        }

        // 得出当宽高至少有一个充满的时候图片对应的宽高
        imageHeight = (int) (intrinsicHeight * h);
        imageWidth = (int) (intrinsicWidth * w);
    }

    /**
     * 进场动画过程监听
     */
    private void addIntoListener(ValueAnimator valueAnimator) {
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * Integer 估值器
     */
    public Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }


    /**
     * Float 估值器
     */
    private Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * Argb 估值器
     */
    private int evaluateArgb(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24
                | (startR + (int) (fraction * (endR - startR))) << 16
                | (startG + (int) (fraction * (endG - startG))) << 8
                | (startB + (int) (fraction * (endB - startB)));
    }

    /**
     * 获取屏幕宽高
     *
     * @param isWidth  Boolean
     * @return int
     */
    private int getScreenWidthHeight(Boolean isWidth) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        return isWidth ? metric.widthPixels : metric.heightPixels;
    }



}