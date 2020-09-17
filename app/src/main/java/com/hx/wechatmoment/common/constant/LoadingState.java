package com.hx.wechatmoment.common.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */

@IntDef({
        LoadingState.LOADING,
        LoadingState.LOADING_ERROR,
        LoadingState.LOADING_COMPLETE,
        LoadingState.LOADING_NO_MORE
})

@Retention(RetentionPolicy.SOURCE)

public @interface LoadingState {
    int LOADING = 0;
    int LOADING_ERROR = 2;
    int LOADING_COMPLETE = 3;
    int LOADING_NO_MORE = 4;

}
