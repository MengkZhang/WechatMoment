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
        Constants.ZERO,
        Constants.ONE,
        Constants.MAX_TIME_MILLIS,
        Constants.MAX_SIZE
})

@Retention(RetentionPolicy.SOURCE)

public @interface Constants {
    int ZERO = 0;
    int ONE = 1;
    int MAX_TIME_MILLIS = 2000;
    int MAX_SIZE = 5;

}
