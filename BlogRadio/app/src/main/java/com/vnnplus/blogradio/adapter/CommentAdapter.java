package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/15/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHoder> {
    Activity activity;
    ArrayList<Comment> lComment = new ArrayList<>();

    public void setlComment(ArrayList<Comment> lComment) {
        this.lComment = lComment;
    }

    public CommentAdapter(ArrayList<Comment> list , Activity activity){
        this.activity = activity;
        this.lComment = list;
    }
    @Override
    public CommentViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cmt , parent , false);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
//        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.height = Util.getWidthScreen(activity)/6;
//        view.setLayoutParams(layoutParams);
        CommentViewHoder viewHoder = new CommentViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(CommentViewHoder holder, int position) {
        Comment comment = lComment.get(position);
        holder.tv_comment.setText(comment.getComment());
        holder.tv_Username.setText(comment.getNickname());
        holder.tv_Date.setText(comment.getTime());
        Util.setImage(holder.imv_avatar , activity , comment.getAvatar());
    }

    @Override
    public int getItemCount() {
        return lComment.size()>0 ? lComment.size():0;
    }

    public class CommentViewHoder extends RecyclerView.ViewHolder {
        ImageView imv_avatar;
        TextView tv_Username , tv_Date , tv_comment;
        public CommentViewHoder(View itemView) {
            super(itemView);
            imv_avatar = (ImageView) itemView.findViewById(R.id.imv_User);
            tv_Username = (TextView) itemView.findViewById(R.id.tv_User);
            tv_Date = (TextView) itemView.findViewById(R.id.tv_Date);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_Comment);
        }
    }
}
