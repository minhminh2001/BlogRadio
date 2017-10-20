package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.RegisterPresenter;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.RegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 4/12/2017.
 */

public class RegisterPresenterIplm implements RegisterPresenter {
    Activity activity;
    RegisterView view;
    public  RegisterPresenterIplm(RegisterView view , Activity activity){
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void getPackage() {
        BlogRadioApi.getBlogPackage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        view.showInfoRegister(NetParserJson.parserInfoRegister(jsonElement.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void checkStatus(String msisdn) {
        BlogRadioApi.checkStatus(msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("status",jsonElement.toString());
                        NetParserJson.parserStatus(jsonElement.toString());
                        view.showStatus();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void checkPhonenumber(String msisdn , final boolean isVlog) {
        BlogRadioApi.checkStatus(msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("status",jsonElement.toString());
                        NetParserJson.parserStatus(jsonElement.toString());
                        view.showPhoneNumber(isVlog);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void unRegister(String pks, String msisdn) {
        BlogRadioApi.unRegister(pks , msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("unRegister", jsonElement.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            view.showUnregister(code);
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
