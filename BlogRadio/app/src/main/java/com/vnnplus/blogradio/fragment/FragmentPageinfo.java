package com.vnnplus.blogradio.fragment;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.SearchActivity;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.presenter.PageinfoPresenter;
import com.vnnplus.blogradio.presenterIplm.PageinfoPresenterIplm;
import com.vnnplus.blogradio.view.InfoView;

import butterknife.Bind;

/**
 * Created by Giangdv on 4/18/2017.
 */

public class FragmentPageinfo extends BaseFragment implements InfoView{
    @Bind(R.id.wv_content)
    WebView wv_info;
    PageinfoPresenter presenter;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.back)
    RelativeLayout back;
    @Bind(R.id.imv_Search)
    ImageView img_search;
    boolean isPageinfo;

    public FragmentPageinfo(boolean isPageinfo){
        this.isPageinfo = isPageinfo;
    }
    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        showDialogLoading();
        if (isPageinfo)
            tvCategory.setText("THÔNG TIN");
        else
            tvCategory.setText("TIỆN ÍCH KHÁC");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , SearchActivity.class);
                getActivity().startActivity(intent);
            }
        });
        wv_info.getSettings().setJavaScriptEnabled(true);
//        wv_info.setWebViewClient(new WebViewClient());

    }

    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pageinfo;
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void configRecyclerview() {

    }

    @Override
    public void initPresenter() {
        presenter = new PageinfoPresenterIplm(getActivity() , this);
    }

    @Override
    public void loadData() {
        if (isPageinfo)
            presenter.getPageinfo();
        else
            presenter.getPageFeature();
    }

    @Override
    public void showPageinfo(String info) {
//        tv_info.setText(Html.fromHtml(info));
        wv_info.loadDataWithBaseURL("", info, "text/html", "UTF-8", "");
        hideDialogLoading();

    }
}
