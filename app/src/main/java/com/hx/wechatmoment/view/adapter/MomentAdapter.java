package com.hx.wechatmoment.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hx.wechatmoment.R;
import com.hx.wechatmoment.common.constant.LoadingState;
import com.hx.wechatmoment.common.listener.MultiClickListener;
import com.hx.wechatmoment.common.util.DateUtil;
import com.hx.wechatmoment.common.util.GlideUtil;
import com.hx.wechatmoment.model.CommentsBean;
import com.hx.wechatmoment.model.ImagesBean;
import com.hx.wechatmoment.model.MomentListBean;
import com.hx.wechatmoment.model.SenderBean;
import com.hx.wechatmoment.view.activity.CustomBitmapActivity;
import com.hx.wechatmoment.view.widget.ToastView;
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
public class MomentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MomentListBean> mList;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOT = 2;

    public MomentAdapter(Context context, List<MomentListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //条目
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.item_moment, parent, false);
            return new MomentViewHolder(view);
        } else {
            //底部
            View view = inflater.inflate(R.layout.item_recyclerview_footer, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            MomentViewHolder holder = (MomentViewHolder) viewHolder;
            adaptData(holder, position);
        } else {
            FootViewHolder holder = (FootViewHolder) viewHolder;
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }


    private void adaptData(@NonNull MomentViewHolder holder, int position) {
        holder.viewLine.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
        MomentListBean momentListBean = mList.get(position);
        if (momentListBean == null) {
            return;
        }

        //设置时间
        holder.tvTime.setText(DateUtil.getCurrentTime());

        SenderBean sender = momentListBean.getSender();
        String avatar = sender.getAvatar();
        //sender在数据结构中已经判空
        GlideUtil.loadRoundedCorner(mContext, avatar, holder.ivHead, R.mipmap.icon_default_small_head);
        if (!TextUtils.isEmpty(avatar)) {
            holder.ivHead.setOnClickListener(new MultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    CustomBitmapActivity.navigateToCustomBitmapActivity(mContext, avatar);
                }
            });
        } else {
            holder.ivHead.setOnClickListener(new MultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    ToastView.showToast("数据异常");
                }
            });
        }
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
            return mList.size() + 1;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == position + 1) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
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
        @BindView(R.id.tv_time)
        TextView tvTime;


        public MomentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setFootView(int loadingState) {
        if (loadingState == LoadingState.LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("正在加载...");
        } else if (loadingState == LoadingState.LOADING_ERROR) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("加载出错~");
        } else if (loadingState == LoadingState.LOADING_COMPLETE) {
            mProgressBar.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        } else if (loadingState == LoadingState.LOADING_NO_MORE) {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("更多精彩内容,敬请期待~");
        }
    }


    /**
     * 底部内容
     */
    TextView mTextView;
    /**
     * 底部进度条
     */
    ProgressBar mProgressBar;

    private class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.we_media_loading);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.we_media_progress);
        }
    }
}
