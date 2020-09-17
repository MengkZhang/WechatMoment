package com.hx.wechatmoment.common.listener;

import android.view.View;

/**
 * Desc 防止连续点击
 *
 * @author zhangxiaolin
 * Date 2020/8/7
 */
public abstract class MultiClickListener implements View.OnClickListener {
    private static final int min_click_delay_time = 500;
    private static long lastClickTime;

    public abstract void onMultiClick(View view);

    @Override
    public void onClick(View view) {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= min_click_delay_time) {
            lastClickTime = curClickTime;
            onMultiClick(view);
        }
    }
}
