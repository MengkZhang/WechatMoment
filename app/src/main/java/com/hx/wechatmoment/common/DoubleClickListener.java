package com.hx.wechatmoment.common;

import android.view.View;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public abstract class DoubleClickListener implements View.OnClickListener {
    private static final long DOUBLE_TIME = 1000;
    private static long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime < DOUBLE_TIME) {
            onDoubleClick(v);
        }
        lastClickTime = currentTimeMillis;
    }

    public abstract void onDoubleClick(View v);
}
