package com.hx.wechatmoment.common.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Desc GlideUtil
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class GlideUtil {
    public static void load(Context context, String imageUrl, ImageView imageView, int placeId) {
        Glide.with(context).load(imageUrl).apply(getPlaceErrorCenter(placeId).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
    }

    private static RequestOptions getPlaceErrorCenter(int errorResId) {
        return new RequestOptions().placeholder(errorResId).error(errorResId).centerCrop();
    }
}
