package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vnnplus.blogradio.MainActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Category;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.widget.ViewMenuLeft;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/8/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RadioViewHoder> {
    Activity activity;
    ArrayList<Category> lRadio = new ArrayList<>();
    boolean isVlog;

    public void setlRadio(ArrayList<Category> lRadio) {
        this.lRadio = lRadio;
    }

    private int widthItem;
    public CategoryAdapter(Activity activity , ArrayList<Category> list,boolean isVlog){
        this.activity = activity;
        this.lRadio = list;
        this.isVlog = isVlog;
        widthItem = Util.getWidthScreen(activity);
    }
    @Override
    public RadioViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.it_category, parent , false);
//        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
//        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;;
//        layoutParams.height = (int) (widthItem*2.5/4);
//        view.setLayoutParams(layoutParams);
        RadioViewHoder viewHoder = new RadioViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(RadioViewHoder holder, int position) {
        Category radio = lRadio.get(position);
        holder.tv_cate.setText(ViewMenuLeft.capitalizeAllWords(radio.getName()));
        Util.setImage(holder.imv_cateRadio , activity , radio.getAvatar());
    }

    @Override
    public int getItemCount() {
        return lRadio.size();
    }

    public class RadioViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imv_cateRadio;
        TextView tv_cate;
        public RadioViewHoder(View itemView) {
            super(itemView);
            imv_cateRadio = (ImageView) itemView.findViewById(R.id.imv_radio);
            tv_cate = (TextView) itemView.findViewById(R.id.tv_cateradio);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MainActivity.switchFragment(MainActivity.container , MainActivity.fmm ,new FragmentRadio(lRadio.get(getPosition()).getId(),
                    lRadio.get(getPosition()).getName(),isVlog),false);
//            Intent intent = new Intent(activity , RadioActivity.class);
//            intent.putExtra("IDCATEGORY" , lRadio.get(getPosition()).getId());
//            intent.putExtra("NAMECATEGORY" , lRadio.get(getPosition()).getName());
//            intent.putExtra("ISVLOG",isVlog);
//            activity.startActivity(intent);
        }
    }
}
