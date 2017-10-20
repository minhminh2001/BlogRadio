package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.LoginPresenter;
import com.vnnplus.blogradio.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/13/2017.
 */

public class LoginPresenterIplm implements LoginPresenter {
    Activity activity;
    LoginView loginView;
    public LoginPresenterIplm(Activity activity , LoginView loginView){
        this.loginView = loginView;
        this.activity = activity;
    }

    @Override
    public void getOTP(String phone) {
        BlogRadioApi.getOTP(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jobj = new JSONObject(jsonElement.toString());
                            String ms = jobj.getString("msg");
                            if (ms.equals(""))
                                ms = jobj.getString("data");
                            int code = jobj.getInt("code");
                            loginView.showOTP(code , ms);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("otp" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void Login(String otp , String msisdn) {
        BlogRadioApi.Login(msisdn , otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("login",jsonElement.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            loginView.showLogin(code , msg);
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
    public void checkStatus(String msisdn) {
        BlogRadioApi.checkStatus(msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("status",jsonElement.toString());
                        NetParserJson.parserStatus(jsonElement.toString());
                        loginView.showStatus();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
