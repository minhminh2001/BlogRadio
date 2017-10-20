package com.vnnplus.blogradio.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.fragment.FragmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giangdv on 5/11/2017.
 */

public class GenderAdapter extends BaseAdapter {

    public interface IMethodCaller{
        void yourDesiredMethod(String gender , int pos);
    }
    public GenderAdapter(Context activity , ArrayList<String> list , IMethodCaller caller){
        this.activity = activity;
        this.lGender = list;
        this.caller = caller;
    }

    ArrayList<String> lGender = new ArrayList<>();
    Context activity;
    IMethodCaller caller;
    @Override
    public int getCount() {
        return lGender.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = new TextView(activity);

        ((TextView) view).setText(lGender.get(i));
        view.setPadding(20, 25, 20, 25);
        ((TextView) view).setTextColor(activity.getResources().getColor(R.color.black));
        view.setTag(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caller.yourDesiredMethod(lGender.get(i).toString() , i+1);
            }
        });
        return view;
    }
}
