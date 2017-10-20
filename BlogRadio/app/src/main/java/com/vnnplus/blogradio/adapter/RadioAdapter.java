package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vnnplus.blogradio.DetailVlogActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHoder> {
    Activity activity;
    private int widthItem;
    ArrayList<Radio> lRadio = new ArrayList<>();
    boolean isVlog;
    String nameCate;

    public void setlRadio(ArrayList<Radio> lRadio) {
        this.lRadio = lRadio;
    }

    public RadioAdapter(Activity activity , ArrayList<Radio> list,boolean isVlog,String nameCate){
        this.lRadio = list;
        this.activity = activity;
        this.isVlog = isVlog;
        this.nameCate = nameCate;
        widthItem = Util.getWidthScreen(activity) - 40;
    }
    @Override
    public RadioViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_radio , parent , false);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
//        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
//        view.setLayoutParams(layoutParams);
        RadioViewHoder viewHoder = new RadioViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(RadioViewHoder holder, int position) {
        Radio radio = lRadio.get(position);
        if(isVlog)
            holder.vlog.setVisibility(View.VISIBLE);
        else
            holder.vlog.setVisibility(View.GONE);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.home.getLayoutParams();
//        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.height = widthItem*2/3;
//        holder.home.setLayoutParams(layoutParams);
        Util.setImage(holder.home , activity , radio.getAvatar());
        holder.tittle.setText(radio.getName());
        String singler = "";
        if (radio.getlSingler().size() == 1)
            singler = radio.getlSingler().get(0).getName();
        else {
            for (int i =0 ; i<radio.getlSingler().size() ; i++){
                if (i==radio.getlSingler().size()-1)
                    singler+=radio.getlSingler().get(i).getName();
                else
                    singler+= radio.getlSingler().get(i).getName()+",";
            }
        }
        if (radio.getlSingler().size()>0)
            holder.author.setText(singler);
    }

    @Override
    public int getItemCount() {
        return lRadio.size();
    }

    public class RadioViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView  vlog;
        ImageView home;
        TextView tittle , author;
        public RadioViewHoder(View itemView) {
            super(itemView);
            home = (ImageView) itemView.findViewById(R.id.imv_home);
            tittle = (TextView) itemView.findViewById(R.id.Tv_tittle);
            author = (TextView) itemView.findViewById(R.id.tv_author);
            vlog = (ImageView) itemView.findViewById(R.id.vlog);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isVlog){
                boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(),activity);
                if (isServiceRunning)
                    activity.stopService(new Intent(activity,RadioService.class));
                Intent intent = new Intent(activity , DetailVlogActivity.class);
                intent.putExtra("IDBLOG",lRadio.get(getPosition()).getId());
                activity.startActivity(intent);
            }
            else {
                FragmentRadio.switchToPlayerSong(lRadio.get(getPosition()),activity,nameCate);
            }
        }
    }
}
