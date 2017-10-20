package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vnnplus.blogradio.DetailVlogActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/17/2017.
 */

public class SameVlogAdapter extends RecyclerView.Adapter<SameVlogAdapter.SameVlogViewHoder> {
    ArrayList<Radio> lRadio = new ArrayList<>();
    Activity activity;
    int widthItem; 

    public void setlRadio(ArrayList<Radio> lRadio) {
        this.lRadio = lRadio;
    }
    public interface ChangeVideo{
        void changeVideo(Radio radio);
    }

    public SameVlogAdapter(ArrayList<Radio> list , Activity activity){
        this.lRadio = list;
        this.activity = activity;
        widthItem = Util.getWidthScreen(activity)*3/7;
    }
    @Override
    public SameVlogViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_samevlog , parent , false);
        SameVlogViewHoder viewHoder = new SameVlogViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(SameVlogViewHoder holder, int position) {
        Radio radio = lRadio.get(position);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthItem,widthItem*2/3);
        holder.img_sameVlog.setLayoutParams(layoutParams);
        Util.setImage(holder.img_sameVlog , activity , radio.getAvatar());
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
            holder.imgGiongdoc.setVisibility(View.GONE);
        holder.tv_Author.setText(singler);
        holder.tv_Tittle.setText(radio.getName());
    }

    @Override
    public int getItemCount() {
        return lRadio.size();
    }

    public class SameVlogViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_sameVlog,imgGiongdoc;
        TextView tv_Tittle , tv_Author;
        public SameVlogViewHoder(View itemView) {
            super(itemView);
            img_sameVlog = (ImageView) itemView.findViewById(R.id.img_sameVlog);
            tv_Tittle = (TextView) itemView.findViewById(R.id.tv_Tittle);
            tv_Author = (TextView) itemView.findViewById(R.id.tv_Author);
            imgGiongdoc = (ImageView) itemView.findViewById(R.id.imgGiongdoc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Radio radio = lRadio.get(getPosition());
            if (radio == null)
                return;
//            if (activity instanceof DetailVlogActivity){
//                PlayerConstants.VIDEO_CHANGE_HANDLER.sendMessage(PlayerConstants.VIDEO_CHANGE_HANDLER.obtainMessage(0 , lRadio.get(getPosition())));
//            }
//            else {
                boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(),activity);
                if (isServiceRunning)
                    activity.stopService(new Intent(activity,RadioService.class));
            if (activity instanceof SearchActivity){

            }
            else
                activity.finish();
            Intent intent = new Intent(activity , DetailVlogActivity.class);
            intent.putExtra("IDBLOG",lRadio.get(getPosition()).getId());
            activity.startActivity(intent);
//            }
        }
    }
}
