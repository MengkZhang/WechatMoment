package com.hx.wechatmoment.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.util.GlideUtil;

import java.util.List;

/**
 * Desc MomentNineGridLayout
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentNineGridLayout extends NineGridLayout {
    protected static final int MAX_W_H_RATIO = 3;

    public MomentNineGridLayout(Context context) {
        super(context);
    }

    public MomentNineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }
        });

        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        GlideUtil.load(mContext, url, imageView, R.mipmap.default_place_img);
    }

    @Override
    protected void onClickImage(int i, String url, List<String> urlList) {
        Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
    }
}
