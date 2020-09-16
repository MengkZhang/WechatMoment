package com.hx.wechatmoment.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.view.widget.nineimg.NineGridView;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class GlideImageLoader implements NineGridView.ImageLoader{
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        GlideUtil.load(context,url,imageView, R.mipmap.default_place_img);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
