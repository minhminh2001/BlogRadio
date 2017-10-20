package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.fragment.FragmentInfo;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Giangdv on 3/30/2017.
 */

public class ListAvatarAdapter extends RecyclerView.Adapter<ListAvatarAdapter.LisAvatarViewHoder> {

    ArrayList<String> lAvatar = new ArrayList<>();
    Activity activity;
    int pos;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public ListAvatarAdapter(ArrayList<String> list , Activity activity){
        this.lAvatar = list;
        this.activity = activity;
    }
    @Override
    public LisAvatarViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listavatar , parent , false);
        LisAvatarViewHoder viewHoder = new LisAvatarViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(LisAvatarViewHoder holder, int position) {
        Util.setImage(holder.avatar , activity , lAvatar.get(position));
        Log.e("pos",pos+"");
        Log.e("position", position+"");
        if(pos == position){
            holder.backgrounp.setBackgroundColor(Color.BLACK);
        }
        else {
            holder.backgrounp.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return lAvatar.size();
    }

    public class LisAvatarViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.img_Avatar)
        com.vnnplus.blogradio.widget.ImageViewRatio avatar;
        @Bind(R.id.backgrounp)
        LinearLayout backgrounp;
        public LisAvatarViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            FragmentInfo.posAvatar = getPosition()+1;
            FragmentInfo.urlImage = lAvatar.get(getPosition());
            pos = getPosition();
            notifyDataSetChanged();
        }
    }
}
