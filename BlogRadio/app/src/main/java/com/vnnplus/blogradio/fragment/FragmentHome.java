package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.HomeAdapter;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.InfoUser;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Slider;
import com.vnnplus.blogradio.presenter.HomePresenter;
import com.vnnplus.blogradio.presenterIplm.HomePresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Homeview;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 12/27/2016.
 */
public class FragmentHome extends BaseFragment implements Homeview {
    HomeAdapter adapter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    HomePresenter presenter;
    boolean isFirst = false;



    ArrayList<Radio> lRadio = new ArrayList<>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    public FragmentHome(Context context , FragmentManager fragmentManager){
        this.fmm = fragmentManager;
        setRetainInstance(true);
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
//        showDialogLoading();
        refreshLayout.setColorSchemeResources(R.color.bg_tabtrip, R.color.bg_tabtrip,
                R.color.bg_tabtrip, R.color.bg_tabtrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lRadio.clear();
                presenter.getListRadio("","",1,4,"");
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
        adapter = new HomeAdapter(getActivity() , lRadio,false , false , null);
    }

    @Override
    public void configRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        presenter = new HomePresenterIplm(this,getActivity());
    }

    @Override
    public void loadData() {
        if (!isFirst)
            presenter.getListRadio("","",1,4,"");
    }

    @Override
    public void showSlide(ArrayList<Slider> sliderArrayList) {
    }

    @Override
    public void showlRadio(ArrayList<Radio> lRadio) {
        refreshLayout.setRefreshing(false);
        for (int i =0 ; i < lRadio.size() ; i++)
            lRadio.get(i).setVlog(false);
        this.lRadio.addAll(lRadio);
        presenter.getListVlog("","",1,4,"");
        isFirst = true;

    }

    @Override
    public void showlVlog(ArrayList<Radio> lVlog) {
        for (int i =0 ; i < lVlog.size() ; i++)
            lVlog.get(i).setVlog(true);
        this.lRadio.addAll(lVlog);
        adapter.setlRadio(this.lRadio);
        adapter.notifyDataSetChanged();
        hideDialogLoading();
    }

    @Override
    public void getMenuItem() {

    }

    @Override
    public void showmsisdn(String msisdn) {

    }

    @Override
    public void showStatus() {

    }

    @Override
    public void showInfoUser(InfoUser infoUser) {

    }

    @Override
    public void showError() {
        Util.showToast(getContext() , "Đã xảy ra lỗi , vui lòng kiểm tra kết nối mạng");
    }

    @Override
    public void showDetail(Radio radio) {

    }
}
