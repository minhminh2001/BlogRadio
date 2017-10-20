package com.vnnplus.blogradio.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vnnplus.blogradio.DetailActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.adapter.RadioAdapter;
import com.vnnplus.blogradio.audioplayer.PlayerConstants;
import com.vnnplus.blogradio.audioplayer.RadioService;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Signers;
import com.vnnplus.blogradio.presenter.RadioPresenter;
import com.vnnplus.blogradio.presenterIplm.RadioPresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Radioview;
import com.vnnplus.blogradio.widget.OnscrollRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 5/5/2017.
 */

public class FragmentRadio extends BaseFragment implements Radioview {


    RadioAdapter adapter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.tv_category)
    TextView category;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.rlt_header)
    RelativeLayout header;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.imv_Search)
    ImageView search;
    OnscrollRecyclerView onscrollRecyclerView;
    public static ViewGroup container;
    int idCategory;
    public static FragmentManager fragmentManager;
    String nameCategory;
    boolean isVlog;
    ArrayList<Radio> lRadio = new ArrayList<>();
    RadioPresenter presenter;
    int limit = 10;
    int page = 1;

    public FragmentRadio(int idCate , String nameCate , boolean isVlog){
        this.idCategory = idCate;
        this.nameCategory = nameCate;
        this.isVlog = isVlog;
    }


    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        initSwiper();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , SearchActivity.class);
                startActivity(intent);
            }
        });
        category.setText(nameCategory.toUpperCase());
    }

    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.radio_recycleview;
    }

    @Override
    public void createAdapter() {
        adapter = new RadioAdapter(getActivity(),lRadio,isVlog,nameCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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
    public void configRecyclerview() {

    }

    @Override
    public void initPresenter() {
        presenter = new RadioPresenterIplm(getActivity() ,this);
    }

    @Override
    public void loadData() {
        if (isVlog)
            presenter.getListVlog(String.valueOf(idCategory),"",page ,limit,"");
        else
            presenter.getListRadio(String.valueOf(idCategory),"",page ,limit,"");
    }
    void initSwiper(){
        refreshLayout.setColorSchemeResources(R.color.bg_tabtrip, R.color.bg_tabtrip,
                R.color.bg_tabtrip, R.color.bg_tabtrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onscrollRecyclerView.refresh();
                lRadio.clear();
                page = 1;
                loadData();
            }
        });
    }

    @Override
    public void showListRadio(ArrayList<Radio> list) {
        refreshLayout.setRefreshing(false);
        for (int i =0;i<list.size();i++){
            Signers signers = new Signers();
            signers.setName(nameCategory);
            list.get(i).setSigners(signers);
        }
        lRadio.addAll(list);
        adapter.setlRadio(lRadio);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showListVlog(ArrayList<Radio> lVlog) {
        refreshLayout.setRefreshing(false);
        lRadio.addAll(lVlog);
        adapter.setlRadio(lRadio);
        adapter.notifyDataSetChanged();
    }


    public static void switchToPlayerSong(Radio radio , Activity activity, String nameCategory) {
        Util.radio = Util.radioService;
        Util.radioService = radio;
        boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(), activity);
        if (!isServiceRunning || PlayerConstants.SONG_CHANGE_HANDLER == null) {
            Intent i = new Intent(activity, RadioService.class);
            activity.startService(i);
            new CountDownTimer(1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onFinish() {
                    if (PlayerConstants.SONG_CHANGE_HANDLER != null)
                        PlayerConstants.SONG_CHANGE_HANDLER
                                .sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage(0, null));
                }
            }.start();
//            Intent intent = new Intent(activity , RadioService.class);
//        activity.startService(intent);
        }else {
            if (PlayerConstants.SONG_CHANGE_HANDLER != null)
                PlayerConstants.SONG_CHANGE_HANDLER
                        .sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage(0, null));
        }
//        if (activity instanceof DetailActivity){
//            PlayerConstants.SONG_PAUSED = false;
//            activity.finish();
////            PlayerConstants.HANDLER_UPDATE_DETAIL.sendMessage(PlayerConstants.HANDLER_UPDATE_DETAIL.obtainMessage(0,radio));
//        }
//        else {
            Intent intent = new Intent(activity,DetailActivity.class);
            intent.putExtra("NAMEBLOG",nameCategory);
            intent.putExtra("IDBLOG",radio.getId());
            activity.startActivity(intent);
//        }
    }
}
