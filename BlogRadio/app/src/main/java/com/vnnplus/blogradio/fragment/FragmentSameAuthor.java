package com.vnnplus.blogradio.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.SameAdapter;
import com.vnnplus.blogradio.adapter.SameVlogAdapter;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.RadioPresenter;
import com.vnnplus.blogradio.presenterIplm.RadioPresenterIplm;
import com.vnnplus.blogradio.view.Radioview;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 3/20/2017.
 */

public class FragmentSameAuthor extends BaseFragment implements Radioview {
    int idBlog;
    boolean isVlog;
    String nameCate;
    SameAdapter adapter;
    SameVlogAdapter sameVlogAdapter;
    ArrayList<Radio> lRadio = new ArrayList<>();
    ArrayList<Integer> lidAuthor = new ArrayList<>();
    RadioPresenter presenter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.parent)
    LinearLayout parent;

    public FragmentSameAuthor(ArrayList<Integer> lidAuthor,int idBlog, boolean isVlog , String nameCate){
        this.lidAuthor = lidAuthor;
        this.isVlog = isVlog;
        this.idBlog = idBlog;
        this.nameCate = nameCate;
    }
    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        parent.setBackgroundColor(Color.WHITE);
        recyclerView.setBackgroundColor(Color.WHITE);
    }


    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.recycleview;
    }

    @Override
    public void createAdapter() {
        adapter = new SameAdapter(getActivity(),lRadio);
        sameVlogAdapter = new SameVlogAdapter(lRadio,getActivity());
    }

    @Override
    public void configRecyclerview() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        if (isVlog)
            recyclerView.setAdapter(sameVlogAdapter);
        else
            recyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        presenter = new RadioPresenterIplm(getActivity() , this);
    }

    @Override
    public void loadData() {
        if (lidAuthor.size()> 0){
            String id = "";
            if (lidAuthor.size() == 1)
                id = String.valueOf(lidAuthor.get(0));
            else {
                for (int i = 0 ; i< lidAuthor.size() ; i++){
                    if (i == lidAuthor.size()-1)
                        id += lidAuthor.get(i);
                    else
                        id += lidAuthor.get(i)+",";
                }
            }
            Log.e("idddddddd",id);
        if (isVlog){
            presenter.getSamebyVoice(id,"video");
        }
        else {
            presenter.getSamebyVoice(id , "audio");
            }
        }

    }

    @Override
    public void showListRadio(ArrayList<Radio> list) {
        lRadio.addAll(list);
        if (isVlog){
            sameVlogAdapter.setlRadio(lRadio);
            sameVlogAdapter.notifyDataSetChanged();
        }
        else {
            adapter.setlRadio(lRadio);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showListVlog(ArrayList<Radio> lVlog) {

    }

}
