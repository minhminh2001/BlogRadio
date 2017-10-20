package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vnnplus.blogradio.DetailVlogActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/8/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHoder> {
    Activity activity;
    private int widthItem;
    ArrayList<Radio> lRadio = new ArrayList<>();
    boolean isFavorite , isDownload;
    RemoveFavorite callBack;

    public void setlRadio(ArrayList<Radio> lRadio) {
        this.lRadio = lRadio;
    }

    public HomeAdapter(Activity activity, ArrayList<Radio> list , boolean isFavorite , boolean isDownload , RemoveFavorite callBack){
        this.activity = activity;
        this.lRadio = list;
        this.callBack = callBack;
        this.isFavorite = isFavorite;
        this.isDownload = isDownload;
        widthItem = Util.getWidthScreen(activity);
    }
    public interface RemoveFavorite{
        void onRemove(Radio radio , int pos);
    }
    @Override
    public HomeViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_home , parent , false);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
//        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;;
//        layoutParams.height = widthItem*3/4;
//        view.setLayoutParams(layoutParams);
        HomeViewHoder viewHoder = new HomeViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(HomeViewHoder holder, int position) {
        Radio radio = lRadio.get(position);
        if (!isFavorite && !isDownload){
            holder.vlog.setVisibility(View.GONE);
            if (radio.isVlog()){
                holder.tv_cate.setText("VLOG");
            }
            else {
                holder.tv_cate.setText("RADIO");
            }
        }
        else if (isDownload && !isFavorite){
            holder.img_download.setVisibility(View.VISIBLE);
            if (radio.isVlog())
                holder.vlog.setVisibility(View.VISIBLE);
            else
                holder.vlog.setVisibility(View.GONE);
        }
        else {
            holder.img_favorite.setVisibility(View.VISIBLE);
            if (radio.isVlog())
                holder.vlog.setVisibility(View.VISIBLE);
            else
                holder.vlog.setVisibility(View.GONE);
        }
        Util.setImage(holder.home,activity,radio.getAvatar());
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
        if (singler.equals(""))
            holder.tv_Singler.setText("Chưa cập nhật");
        else
            holder.tv_Singler.setText(singler);
        holder.tv_Tittle.setText(radio.getName());
        holder.tv_time.setText(radio.getPublistdate());
    }


    @Override
    public int getItemCount() {
        return lRadio.size();
    }

    public class HomeViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView home , vlog , img_favorite , img_download;
        TextView tv_time, tv_cate,tv_Singler , tv_Tittle;
        RelativeLayout  rlt_bottom;
        public HomeViewHoder(View itemView) {
            super(itemView);
            home = (ImageView) itemView.findViewById(R.id.imv_home);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_cate = (TextView) itemView.findViewById(R.id.tittle);
            tv_Singler = (TextView) itemView.findViewById(R.id.tv_Singler);
            rlt_bottom = (RelativeLayout) itemView.findViewById(R.id.rlt_bottom);
            tv_Tittle = (TextView) itemView.findViewById(R.id.Tv_tittle);
            img_download = (ImageView) itemView.findViewById(R.id.img_download);
            vlog = (ImageView) itemView.findViewById(R.id.vlog);
            img_favorite = (ImageView) itemView.findViewById(R.id.img_favorite);
            itemView.setOnClickListener(this);
            img_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onRemove(lRadio.get(getPosition()) , getPosition());
                }
            });
            img_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callBack.onRemove(lRadio.get(getPosition()),getPosition());

                }
            });
        }

        @Override
        public void onClick(View view) {
            if (lRadio.get(getPosition()).isVlog()){
                boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(),activity);
                if (isServiceRunning)
                    activity.stopService(new Intent(activity,RadioService.class));
                Intent intent = new Intent(activity , DetailVlogActivity.class);
                intent.putExtra("IDBLOG",lRadio.get(getPosition()).getId());
                activity.startActivity(intent);
            }
            else {
                FragmentRadio.switchToPlayerSong(lRadio.get(getPosition()),activity,lRadio.get(getPosition()).getSigners().getName());
            }
        }
    }
}
