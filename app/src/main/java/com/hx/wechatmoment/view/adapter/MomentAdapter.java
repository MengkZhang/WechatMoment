package com.hx.wechatmoment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.model.CommentsBean;
import com.hx.wechatmoment.model.ImagesBean;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.SenderBean;
import com.hx.wechatmoment.view.widget.comment.CommentsView;
import com.hx.wechatmoment.view.widget.nineimg.ImageInfo;
import com.hx.wechatmoment.view.widget.nineimg.NineGridView;
import com.hx.wechatmoment.view.widget.nineimg.NineGridViewClickAdapter;

import java.util.ArrayList;
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
        holder.viewLine.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        MomentListBean momentListBean = mList.get(position);
        if (momentListBean == null) {
            return;
        }

        SenderBean sender = momentListBean.getSender();
        //sender在数据结构中已经判空
        GlideUtil.loadRoundedCorner(mContext, sender.getAvatar(), holder.ivHead, R.mipmap.icon_default_small_head);
        holder.tvName.setText(sender.getUsername());

        holder.tvDesc.setText(momentListBean.getContent());

        //设置九宫格图片
        List<ImagesBean> images = momentListBean.getImages();
        List<ImageInfo> imageInfo = new ArrayList<>();
        if (images != null && images.size() != 0) {
            holder.mNineGridView.setVisibility(View.VISIBLE);
            for (ImagesBean image : images) {
                imageInfo.add(getImageInfo(image));
            }
            NineGridViewClickAdapter adapter = new NineGridViewClickAdapter(mContext, imageInfo);
            holder.mNineGridView.setAdapter(adapter);

        } else {
            holder.mNineGridView.setVisibility(View.GONE);
        }

        //适配评论
        List<CommentsBean> comments = momentListBean.getComments();
        if (comments != null && comments.size() != 0) {
            holder.commentRoot.setVisibility(View.VISIBLE);
            holder.mCommentsView.setList(comments);
            holder.mCommentsView.notifyDataSetChanged();
        } else {
            holder.commentRoot.setVisibility(View.GONE);

        }


    }

    private ImageInfo getImageInfo(ImagesBean bean) {
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setBigImageUrl(bean.getUrl());
        imageInfo.setThumbnailUrl(bean.getUrl());
        return imageInfo;
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
        @BindView(R.id.ngv)
        NineGridView mNineGridView;
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.rl_comment)
        View commentRoot;
        @BindView(R.id.cv)
        CommentsView mCommentsView;


        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
