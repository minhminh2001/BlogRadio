package com.vnnplus.blogradio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.adapter.SameAdapter;
import com.vnnplus.blogradio.adapter.SameVlogAdapter;
import com.vnnplus.blogradio.base.BaseActivity;
import com.vnnplus.blogradio.fragment.FragmentRadio;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.presenter.SearchPresenter;
import com.vnnplus.blogradio.presenterIplm.SearchPresenterIplm;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.SearchView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Giangdv on 4/18/2017.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener,SearchView {
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.imv_Search)
    ImageView imv_Search;
    @Bind(R.id.edt_search)
    EditText edt_search;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tv_search)
    TextView tv_search;
    SearchPresenter presenter;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        imv_Search.setOnClickListener(this);
        createPresenter();
        getExtrarData();
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(run);
                handler.postDelayed(run, 2000);
            }
        });
    }

    @Override
    public void getExtrarData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void createPresenter() {
        presenter = new SearchPresenterIplm(this , this);
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.imv_Search:
                search();
                break;

        }
    }

    void search(){
        handler.removeCallbacksAndMessages(null);
        String textSearch = edt_search.getText().toString();
        InputMethodManager inputManager =
                (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if (textSearch.equals("")){
            Util.showToast(getBaseContext() , "Vui lòng nhập từ khóa");
        }
        else {
            container.removeAllViews();
            presenter.Search(textSearch , 10 , 1 , "all");
        }
    }
    Runnable run = new Runnable() {

        @Override
        public void run() {
            String textSearch = edt_search.getText().toString();
            if (textSearch.equals("")){
                Util.showToast(getBaseContext() , "Vui lòng nhập từ khóa");
            }
            else {
                container.removeAllViews();
                presenter.Search(textSearch , 10 , 1 , "all");
            }
        }
    };

    @Override
    public void showSearch(ArrayList<Radio> lRadio, ArrayList<Radio> lVlog) {
        View view1 = getCurrentFocus();
        if (view1 != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
        if (lRadio.size() >0){
            tv_search.setVisibility(View.GONE);
            showRadio(this,lRadio);
        }
        if (lVlog.size() >0){
            showVlog(this,lVlog);
            tv_search.setVisibility(View.GONE);

        }
        if (lRadio.size() == 0 && lVlog.size() == 0){
            tv_search.setVisibility(View.VISIBLE);
            tv_search.setText("Không có kết quả tìm kiếm cho từ khóa : "+ edt_search.getText().toString());
        }
    }

    private void showRadio(Activity activity , ArrayList<Radio> lRadio ){
        View element = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_search,container,false);
        TextView tittle = (TextView) element.findViewById(R.id.tv_Tittle);
        tittle.setText("RADIO");
        RecyclerView recycleview = (RecyclerView) element.findViewById(R.id.recycleview);
        SameAdapter adapter = new SameAdapter(activity , lRadio);
        recycleview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recycleview.setHasFixedSize(false);
        recycleview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recycleview.setAdapter(adapter);
        container.addView(element);
    }

    private void showVlog(Activity activity , ArrayList<Radio> lRadio ){
        View element = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_search,container,false);
        TextView tittle = (TextView) element.findViewById(R.id.tv_Tittle);
        tittle.setText("Vlog");
        RecyclerView recycleview = (RecyclerView) element.findViewById(R.id.recycleview);
        SameVlogAdapter adapter = new SameVlogAdapter(lRadio ,activity);
        recycleview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recycleview.setHasFixedSize(false);
        recycleview.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recycleview.setAdapter(adapter);
        container.addView(element);
    }
}
