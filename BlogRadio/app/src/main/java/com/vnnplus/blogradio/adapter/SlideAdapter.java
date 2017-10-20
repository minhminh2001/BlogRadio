package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.vnnplus.blogradio.DetailVlogActivity;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Slider;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/10/2017.
 */

public class SlideAdapter extends PagerAdapter {
    public int pageSelected;
    Activity activity;
    ArrayList<Slider> list = new ArrayList<>();

    public void setList(ArrayList<Slider> list) {
        this.list = list;
    }

    public SlideAdapter(ArrayList<Slider> list , Activity activity){
        this.list = list;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgv = new ImageView(activity);
        imgv.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imgv.setLayoutParams(params);
        try {
            final Slider data = list.get(position);
            Util.setImage(imgv , activity , data.getImgUrl());
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (data.getType().equals("video")){
                        boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(),activity);
                        if (isServiceRunning)
                            activity.stopService(new Intent(activity,RadioService.class));
                        Intent intent = new Intent(activity , DetailVlogActivity.class);
                        intent.putExtra("IDBLOG",data.getId());
                        activity.startActivity(intent);
                    }
                    else {
                        Radio radio = new Radio();
                        radio.setMediaurl(data.getMediaUrl());
                        radio.setId(data.getId());
                        radio.setAvatar(data.getImgUrl());
                        radio.setName(data.getName());
                        radio.setSigners(data.getSigners());
                        FragmentRadio.switchToPlayerSong(radio,activity,radio.getSigners().getName());
                    }
                }
            });
        } catch (Exception e) {
        }

        container.addView(imgv);

        return imgv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
