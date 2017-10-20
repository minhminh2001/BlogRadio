package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.CategoryAdapter;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Category;
import com.vnnplus.blogradio.presenter.CategoryPresenter;
import com.vnnplus.blogradio.presenterIplm.CategoryPresenterIplm;
import com.vnnplus.blogradio.view.CategoryView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 12/27/2016.
 */
public class FragmentCateRadio extends BaseFragment implements CategoryView {

    CategoryAdapter adapter;
    CategoryPresenter presenter;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    ArrayList<Category> lRadio = new ArrayList<>();

    public FragmentCateRadio(Context context , FragmentManager fragmentManager){
        this.fmm = fragmentManager;
        setRetainInstance(true);
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        refreshLayout.setColorSchemeResources(R.color.bg_tabtrip, R.color.bg_tabtrip,
                R.color.bg_tabtrip, R.color.bg_tabtrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListCategory("audio","687");
            }
        });
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
        adapter = new CategoryAdapter(getActivity(),lRadio,false);
    }

    @Override
    public void configRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initPresenter() {
        presenter = new CategoryPresenterIplm(this , getActivity());
    }

    @Override
    public void loadData() {
        presenter.getListCategory("audio","687");
    }


    @Override
    public void showListCategory(ArrayList<Category> list) {
//        lRadio.addAll(list);
        refreshLayout.setRefreshing(false);
        adapter.setlRadio(list);
        adapter.notifyDataSetChanged();
    }
}
