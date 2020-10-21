package com.hx.wechatmoment.view.widget.comment;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hx.wechatmoment.bean.CommentsBean;
import com.hx.wechatmoment.bean.SenderBean;
import com.hx.wechatmoment.view.widget.ToastView;

import java.util.List;

/**
 * Desc CommentsView自定义评论view
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
public class CommentsView extends LinearLayout {

    private Context mContext;
    private List<CommentsBean> mDatas;
    private onItemClickListener listener;

    public CommentsView(Context context) {
        this(context, null);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        this.mContext = context;
    }

    /**
     * 设置评论列表信息
     *
     * @param list
     */
    public void setList(List<CommentsBean> list) {
        mDatas = list;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void notifyDataSetChanged() {
        removeAllViews();
        if (mDatas == null || mDatas.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        for (int i = 0; i < mDatas.size(); i++) {
            View view = getView(i);
            if (view != null) {
                addView(view, i, layoutParams);
            }
        }
    }

    private View getView(final int position) {
        final CommentsBean item = mDatas.get(position);
        SenderBean replyUser = item.getSender();
        // 是否有回复
        boolean hasReply = replyUser.isHasReply();

        TextView textView = new TextView(mContext);
        textView.setTextSize(15);
        textView.setTextColor(0xff686868);

        SpannableStringBuilder builder = new SpannableStringBuilder();
        SenderBean comUser = item.getSender();
        String name = comUser.getUsername();
        if (hasReply) {
            builder.append(setClickableSpan(name, item.getSender()));
            builder.append(" 回复 ");
            builder.append(setClickableSpan(replyUser.getUsername(), item.getSender()));

        } else {
            builder.append(setClickableSpan(name, item.getSender()));
        }
        builder.append(" : ");
        builder.append(setClickableSpanContent(item.getContent(), position));
        textView.setText(builder);
        // 设置点击背景色
        textView.setHighlightColor(getResources().getColor(android.R.color.transparent));
        //  textView.setHighlightColor(0xff000000);

        textView.setMovementMethod(new CircleMovementMethod(0xffcccccc, 0xffcccccc));

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position, item);
                }
            }
        });

        return textView;
    }

    /**
     * 设置评论内容点击事件
     *
     * @param item
     * @param position
     * @return
     */
    public SpannableString setClickableSpanContent(final String item, final int position) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论内容点击事件
                ToastView.showToast("position: " + position + " , content: " + item);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的内容文本颜色
                ds.setColor(0xff686868);
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置评论用户名字点击事件
     *
     * @param item
     * @param bean
     * @return
     */
    public SpannableString setClickableSpan(final String item, final SenderBean bean) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论用户名字点击事件
                ToastView.showToast(bean.getUsername());
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的用户名文本颜色
                ds.setColor(0xff387dcc);
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 定义一个用于回调的接口
     */
    public interface onItemClickListener {
        void onItemClick(int position, CommentsBean bean);
    }
}
