package com.hx.wechatmoment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.SenderBean;
import com.hx.wechatmoment.view.activity.MomentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Desc MomentAdapter
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
public class MomentAdapter extends RecyclerView.Adapter<MomentAdapter.MomentViewHolder> {

    private Context mContext;
    private List<MomentListBean> mList;

    public MomentAdapter(Context context, List<MomentListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MomentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_moment, parent, false);
        return new MomentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MomentViewHolder holder, int position) {
        MomentListBean momentListBean = mList.get(position);
        if (momentListBean == null) {
            return;
        }

        SenderBean sender = momentListBean.getSender();
        if (sender != null) {
            GlideUtil.load(mContext,sender.getAvatar(),holder.ivHead,R.mipmap.icon_default_small_head);
            holder.tvName.setText(sender.getUsername());
        }
        holder.tvDesc.setText(momentListBean.getContent());


    }


    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size();
        }
        return 0;
    }

    static class MomentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_head)
        ImageView ivHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;

        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
