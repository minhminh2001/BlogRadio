package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.RadioPresenter;
import com.vnnplus.blogradio.view.Radioview;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/10/2017.
 */

public class RadioPresenterIplm implements RadioPresenter {
    Activity activity;
    Radioview radioview;
    public RadioPresenterIplm(Activity activity , Radioview radioview){
        this.activity = activity;
        this.radioview = radioview;
    }
    @Override
    public void getListRadio(String cat_id, String singler_id, int page, int limit ,String exclude) {
        BlogRadioApi.getlistRadio(cat_id , singler_id , page , limit,exclude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {

                        radioview.showListRadio(NetParserJson.parserListRadio(jsonElement.toString(),false));
                        Log.e("Listradio" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getSamebyVoice(String singerId, String type) {
        BlogRadioApi.getSamevoice(singerId , type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("Voice" ,jsonElement.toString());
                        radioview.showListRadio(NetParserJson.parserListSameVoice(jsonElement.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    @Override
    public void getListVlog(String cat_id, String singler_id, int page, int limit,String exclude) {
        BlogRadioApi.getlistVlog(cat_id , singler_id , page , limit,exclude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {

                        radioview.showListVlog(NetParserJson.parserListRadio(jsonElement.toString(),false));
                        Log.e("ListVlog" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

}
