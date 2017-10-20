package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import android.widget.Toast;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.adapter.HomeAdapter;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Giangdv on 4/25/2017.
 */

public class FragmentDownload extends BaseFragment implements HomeAdapter.RemoveFavorite {

//    public FragmentDownload(Context context, FragmentManager ft) {
//        this.fmm = ft;
//        setRetainInstance(true);
//    }
    private HomeAdapter adapter;
    @Bind(R.id.recycleview)
    RecyclerView recyclerView;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    ArrayList<Radio> lDownload = new ArrayList<>();
    @Bind(R.id.ll_favotite)
    RelativeLayout favorite;
    @Bind(R.id.tvTittle)
    TextView tvTittle;
    @Bind(R.id.imv_Search)
    ImageView imv_search;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.tv_category)
    TextView category;

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {

        tvTittle.setText("Danh sách tải về trống");
        category.setText("TẢI VỀ");
        refreshLayout.setColorSchemeResources(R.color.bg_tabtrip, R.color.bg_tabtrip,
                R.color.bg_tabtrip, R.color.bg_tabtrip);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
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
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void configRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        adapter = new HomeAdapter(getActivity() , lDownload , false , true , this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {
        lDownload.clear();
        lDownload.addAll(SharePreference.getListRadio("download",getContext()));
        if (lDownload.size() == 0){
            favorite.setVisibility(View.VISIBLE);
            refreshLayout.setEnabled(false);
        }
        else {
            favorite.setVisibility(View.GONE);
            refreshLayout.setEnabled(true);
            adapter.setlRadio(lDownload);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemove(Radio radio, int pos) {

        Util.listRadio.remove(pos);
        lDownload.remove(pos);
        adapter.notifyItemRemoved(pos);
        SharePreference.setList("download",Util.listRadio, getContext());
        if (lDownload.size() == 0)
            favorite.setVisibility(View.VISIBLE);
        else
            favorite.setVisibility(View.GONE);
        Toast.makeText(getContext() , "Xóa tải về thành công" , Toast.LENGTH_SHORT).show();
    }
}
