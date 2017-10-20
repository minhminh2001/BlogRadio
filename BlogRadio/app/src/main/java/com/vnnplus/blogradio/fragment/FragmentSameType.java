package com.vnnplus.blogradio.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.vnnplus.blogradio.widget.OnscrollRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class FragmentSameType extends BaseFragment implements Radioview {
    int idBlog;
    SameAdapter adapter;
    SameVlogAdapter sameVlogAdapter;
    ArrayList<Radio> lRadio = new ArrayList<>();
    ArrayList<Integer> lidAuthor = new ArrayList<>();
    RadioPresenter presenter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.parent)
    LinearLayout parent;
    boolean isVlog;
    String nameCate;
    int page = 1;
    int limit = 10;
    int idCate;
    OnscrollRecyclerView onscrollRecyclerView;


    public FragmentSameType(int id,int idCate, boolean isVlog , String nameCate ){
        this.idBlog = id;
        this.isVlog = isVlog;
        this.nameCate = nameCate;
        this.idCate = idCate;
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
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (isVlog)
            recyclerView.setAdapter(sameVlogAdapter);
        else
            recyclerView.setAdapter(adapter);
        onscrollRecyclerView = new OnscrollRecyclerView((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int limit) {
                page ++;
                loadData();
            }
        };
        recyclerView.addOnScrollListener(onscrollRecyclerView);
    }

    @Override
    public void initPresenter() {
        presenter = new RadioPresenterIplm(getActivity() , this);
    }

    @Override
    public void loadData() {
        if (isVlog){
            presenter.getListVlog(String.valueOf(idCate),"",page,limit, String.valueOf(idBlog));
        }
        else {
            presenter.getListRadio(String.valueOf(idCate),"",page,limit, String.valueOf(idBlog));
        }
    }

    @Override
    public void showListRadio(ArrayList<Radio> list) {
        lRadio.addAll(list);
        adapter.setlRadio(lRadio);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showListVlog(ArrayList<Radio> lVlog) {
        lRadio.addAll(lVlog);
        sameVlogAdapter.setlRadio(lRadio);
        sameVlogAdapter.notifyDataSetChanged();
    }


//    @Override
//    public void changeVideo(Radio radio) {
//
//    }
}
