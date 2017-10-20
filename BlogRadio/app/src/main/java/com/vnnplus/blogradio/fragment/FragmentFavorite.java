package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.adapter.HomeAdapter;
import com.vnnplus.blogradio.audioplayer.PlayerConstants;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.FavoritePresenter;
import com.vnnplus.blogradio.presenterIplm.FavoritePresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.FavoriteView;
import com.vnnplus.blogradio.view.RegisterView;
import com.vnnplus.blogradio.widget.OnscrollRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 4/11/2017.
 */

public class FragmentFavorite extends BaseFragment implements FavoriteView,HomeAdapter.RemoveFavorite{
    private HomeAdapter adapter;
    private FavoritePresenter presenter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    ArrayList<Radio> lRadio = new ArrayList<>();
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.ll_favotite)
    RelativeLayout favorite;
    @Bind(R.id.imv_Search)
    ImageView imv_search;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.tv_category)
    TextView category;
    int page = 1;
    int limit = 10;
    OnscrollRecyclerView onscrollRecyclerView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }

//    public FragmentFavorite(Context context, FragmentManager ft) {
//        this.fmm = ft;
//        setRetainInstance(true);
//    }
    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        showDialogLoading();
        category.setText("YÊU THÍCH");
        refreshLayout.setColorSchemeResources(R.color.bg_tabtrip, R.color.bg_tabtrip,
                R.color.bg_tabtrip, R.color.bg_tabtrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lRadio.clear();
                page = 1;
                if (!Util.isTest)
                    presenter.getListFavorite(Util.msisdn,"" , page , limit );
                else
                    presenter.getListFavorite("01658708738","" , page , limit );

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        imv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void createAdapter() {
        adapter = new HomeAdapter(getActivity() , lRadio , true , false , this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void configRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
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
        presenter = new FavoritePresenterIplm(getActivity() , this);
    }

    @Override
    public void loadData() {
        if (Util.isTest)
            presenter.getListFavorite("01658708738","" , page , limit );
        else
            presenter.getListFavorite(Util.msisdn,"" , page , limit );
    }

    @Override
    public void showListFavorite(ArrayList<Radio> lFavorite) {
        hideDialogLoading();
//        lRadio.clear();
        refreshLayout.setRefreshing(false);
        if (lFavorite.size() == 0 && lRadio.size() == 0){
            favorite.setVisibility(View.VISIBLE);
            refreshLayout.setEnabled(false);
        }
        else {
//            lRadio.clear();
            favorite.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
            lRadio.addAll(lFavorite);
            adapter.setlRadio(lRadio);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError() {
//        showToastMs("Đã xảy ra lỗi , vui lòng thử lại");
    }

    @Override
    public void successremoveFavorite(int code, String msg) {
        Util.showToast(getContext(), msg);
        if (lRadio.size() == 0)
            favorite.setVisibility(View.VISIBLE);
        else
            favorite.setVisibility(View.GONE);
    }

    @Override
    public void onRemove(Radio radio , int pos) {
        lRadio.remove(pos);
        adapter.notifyItemRemoved(pos);
        if (Util.isTest){
            presenter.removeFavorite(radio.getId(),"01658708738");
        }
        else {
            presenter.removeFavorite(radio.getId(),Util.msisdn);
        }


    }
}
