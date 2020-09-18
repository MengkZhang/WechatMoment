package com.hx.wechatmoment.view.activity;

import android.content.Context;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/18
 */
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {


    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void dataObserver() {
        assertEquals("com.example.wechatmoment", mContext.getPackageName());
    }
}