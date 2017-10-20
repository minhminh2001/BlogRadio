package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.SearchPresenter;
import com.vnnplus.blogradio.view.SearchView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 4/14/2017.
 */

public class SearchPresenterIplm implements SearchPresenter {
    SearchView view;
    Activity activity;
    public SearchPresenterIplm(Activity activity,SearchView view ){
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void Search(String textSearch, int limit, int page, String type) {
        BlogRadioApi.Search(textSearch , limit , page , type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        view.showSearch(NetParserJson.parserlSearch(jsonElement.toString(),true),NetParserJson.parserlSearch(jsonElement.toString(),false));
                        Log.e("search" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
