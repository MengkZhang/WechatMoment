package com.hx.wechatmoment.view.widget;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hx.wechatmoment.R;


/**
 * Desc ToastUtil
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public class ToastView {

    private static Application sApplication = null;

    public static void init(Application application) {
        sApplication = application;
    }

    public static void showToast(String normal) {
        showToast(sApplication, normal, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String normal, int duration) {
        if (!TextUtils.isEmpty(normal)) {
            Toast currentToast = Toast.makeText(context, "", duration);
            View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
            TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
            toastTextView.setText("   " + normal + "   ");
            currentToast.setView(toastLayout);
            currentToast.setGravity(Gravity.CENTER, 0, 0);
            currentToast.show();
        }
    }

}
