package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.CategoryPresenter;
import com.vnnplus.blogradio.view.CategoryView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class CategoryPresenterIplm implements CategoryPresenter {
    CategoryView categoryView;
    Activity activity;
    public CategoryPresenterIplm(CategoryView view , Activity activity){
        this.categoryView = view;
        this.activity = activity;
    }

    @Override
    public void getListCategory(String type, String parentId) {
        BlogRadioApi.getListCategory(type , parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("category",jsonElement.toString());
                        categoryView.showListCategory(NetParserJson.parserListCategory(jsonElement.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
