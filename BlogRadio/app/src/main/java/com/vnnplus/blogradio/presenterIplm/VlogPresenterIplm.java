package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.Vlogpresenter;
import com.vnnplus.blogradio.view.Vlogview;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class VlogPresenterIplm implements Vlogpresenter {
    public Vlogview vlogview;
    public Activity activity;
    public VlogPresenterIplm(Vlogview vlogview , Activity activity){
        this.vlogview = vlogview;
        this.activity = activity;
    }

    @Override
    public void getListVlog(String cat_id, String singler_id, int page, int limit,String exclude) {
        BlogRadioApi.getlistVlog(cat_id , singler_id , page , limit,exclude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        vlogview.showVlog(NetParserJson.parserListRadio(jsonElement.toString(),false));
                        Log.e("vlogdata" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
