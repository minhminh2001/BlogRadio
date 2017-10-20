package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.PageinfoPresenter;
import com.vnnplus.blogradio.view.InfoView;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 4/18/2017.
 */

public class PageinfoPresenterIplm implements PageinfoPresenter {
    private InfoView view;
    Activity activity;
    public PageinfoPresenterIplm(Activity activity , InfoView view ){
        this.view  = view;
        this.activity = activity;
    }
    @Override
    public void getPageinfo() {
        BlogRadioApi.getPageinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String info = jsonObject.getString("data");
                            view.showPageinfo(info);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getPageFeature() {
        BlogRadioApi.getPageFeature()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String info = jsonObject.getString("data");
                            view.showPageinfo(info);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
