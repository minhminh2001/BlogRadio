//package com.vnnplus.blogradio.fragment;
//
//import android.app.Activity;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.vnnplus.blogradio.ActivityTest;
//import com.plus.blogradio.R;
//import com.vnnplus.blogradio.adapter.SameAdapter;
//import com.vnnplus.blogradio.adapter.SameVlogAdapter;
//import com.vnnplus.blogradio.base.BaseFragment;
//import com.vnnplus.blogradio.model.Radio;
//import com.vnnplus.blogradio.presenter.SearchPresenter;
//import com.vnnplus.blogradio.presenterIplm.SearchPresenterIplm;
//import com.vnnplus.blogradio.view.RegisterView;
//import com.vnnplus.blogradio.view.SearchView;
//import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//
///**
// * Created by Giangdv on 4/3/2017.
// */
//
//public class FragmentSearch extends BaseFragment implements SearchView {
//
//    @Bind(R.id.container)
//    LinearLayout container;
//
//    private String textSearch = "";
//    SearchPresenter presenter;
//
//
//    public FragmentSearch(String textSearch , FragmentManager ft){
//        this.textSearch = textSearch;
//    }
//    @Override
//    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
//
//    }
//
//    @Override
//    public void getExtraData() {
//
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_search;
//    }
//
//    @Override
//    public void createAdapter() {
//
//    }
//
//    @Override
//    public void configRecyclerview() {
//
//    }
//
//    @Override
//    public void initPresenter() {
//        presenter = new SearchPresenterIplm(getActivity() , this);
//    }
//
//    @Override
//    public void loadData() {
//        presenter.Search(textSearch , 10 , 1 , "all");
//    }
//
//
//    private void showRadio(Activity activity , ArrayList<Radio> lRadio ){
//        View element = LayoutInflater.from(getContext()).inflate(R.layout.item_search,container,false);
//        TextView tittle = (TextView) element.findViewById(R.id.tv_Tittle);
//        tittle.setText("RADIO");
//        RecyclerView recycleview = (RecyclerView) element.findViewById(R.id.recycleview);
//        SameAdapter adapter = new SameAdapter(activity , lRadio , "");
//        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recycleview.setHasFixedSize(false);
//        recycleview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
//        recycleview.setAdapter(adapter);
//        container.addView(element);
//    }
//    private void showVlog(Activity activity , ArrayList<Radio> lRadio ){
//        View element = LayoutInflater.from(getContext()).inflate(R.layout.item_search,container,false);
//        TextView tittle = (TextView) element.findViewById(R.id.tv_Tittle);
//        tittle.setText("Vlog");
//        RecyclerView recycleview = (RecyclerView) element.findViewById(R.id.recycleview);
//        SameVlogAdapter adapter = new SameVlogAdapter(lRadio ,activity , ActivityTest.istance);
//        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recycleview.setHasFixedSize(false);
//        recycleview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
//        recycleview.setAdapter(adapter);
//        container.addView(element);
//    }
//
//    @Override
//    public void showSearch(ArrayList<Radio> lRadio, ArrayList<Radio> lVlog) {
//        if (lRadio.size() >0)
//            showRadio(getActivity(),lRadio);
//        if (lVlog.size() >0)
//            showVlog(getActivity() , lVlog);
//    }
//}
