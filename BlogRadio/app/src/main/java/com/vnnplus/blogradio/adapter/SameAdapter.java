package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/15/2017.
 */

public class SameAdapter extends RecyclerView.Adapter<SameAdapter.SameViewHoder> {
    ArrayList<Radio> lRadio = new ArrayList<>();
    Activity activity;

    public void setlRadio(ArrayList<Radio> lRadio) {
        this.lRadio = lRadio;
    }

    public SameAdapter(Activity activity , ArrayList<Radio> list ){
        this.activity = activity;
        this.lRadio = list;
    }
    @Override
    public SameViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_same , parent , false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = Util.getWidthScreen(activity)/6;
        view.setLayoutParams(layoutParams);
        SameViewHoder viewHoder = new SameViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(SameViewHoder holder, int position) {
        Radio radio = lRadio.get(position);
        holder.tv_Tittle.setText(radio.getName());
        if (radio.getlSingler().size()>0){
            String text = "<font color=#000000>"+radio.getName()+ "</font> <font color=#8a9aac>"+"   -"+radio.getlSingler().get(0).getName() +"</font>";
            holder.tv_Tittle.setText(Html.fromHtml(text));
        }
//            holder.tv_Author.setText("- "+ radio.getlSingler().get(0).getName());
    }

    @Override
    public int getItemCount() {
        return lRadio.size();
    }

    public class SameViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_Tittle , tv_Author;
        public SameViewHoder(View itemView) {
            super(itemView);
//            tv_Author = (TextView) itemView.findViewById(R.id.tv_Author);
            tv_Tittle = (TextView) itemView.findViewById(R.id.tv_Tittle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (activity instanceof SearchActivity){

            }
            else {
                activity.finish();
            }
            FragmentRadio.switchToPlayerSong(lRadio.get(getPosition()),activity,lRadio.get(getPosition()).getSigners().getName());
        }
    }
}
