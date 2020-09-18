package com.hx.wechatmoment.view.activity;

import android.app.Instrumentation;
import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/18
 */
@RunWith(AndroidJUnit4.class)
public class MomentActivityTest {

    private Context mContext;

    @Rule
    public ActivityScenarioRule<MomentActivity> rule = new ActivityScenarioRule<>(MomentActivity.class);

    @Before
    public void setUp() throws Exception {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() {
        ActivityScenario<MomentActivity> scenario = rule.getScenario();
        Instrumentation.ActivityResult result = scenario.getResult();
    }

    @Test
    public void initEvent() {
    }

    @Test
    public void dataObserver() {
    }
}