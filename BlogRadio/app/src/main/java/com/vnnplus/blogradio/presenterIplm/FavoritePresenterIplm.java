package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.FavoritePresenter;
import com.vnnplus.blogradio.view.FavoriteView;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 4/17/2017.
 */

public class FavoritePresenterIplm implements FavoritePresenter {
    FavoriteView view;
    Activity activity;
    public FavoritePresenterIplm(Activity activity , FavoriteView view){
        this.view = view ;
        this.activity = activity;
    }


    @Override
    public void getListFavorite(String msisdn, String type, int page, int limit) {
        BlogRadioApi.getListFavorite(msisdn , type , page , limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("favorite" , jsonElement.toString());
                        view.showListFavorite(NetParserJson.parserlFavorite(jsonElement.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError();
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void removeFavorite(int media_id, String msisdn) {
        BlogRadioApi.removeFavorite(media_id,msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            view.successremoveFavorite(code , msg);
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
