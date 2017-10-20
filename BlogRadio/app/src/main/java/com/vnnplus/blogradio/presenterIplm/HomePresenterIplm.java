package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.HomePresenter;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Homeview;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 1/3/2017.
 */

public class HomePresenterIplm implements HomePresenter {
    private Activity activity;
    private Homeview homeview;

    public HomePresenterIplm(Homeview homeview , Activity activity){
        this.homeview = homeview;
        this.activity = activity;
    }

    @Override
    public void getSlide() {
        BlogRadioApi.getSlider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("slider",jsonElement.toString());
                        homeview.showSlide(NetParserJson.parserSlider(jsonElement.toString()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getMenuItem() {
        BlogRadioApi.getMenuItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        NetParserJson.parserMenuItem(jsonElement.toString());
                        homeview.getMenuItem();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getListRadio(String cat_id, String singler_id, int page, int limit,String exclude) {
        BlogRadioApi.getlistRadio(cat_id , singler_id , page , limit,exclude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("listRadio",jsonElement.toString());
                        homeview.showlRadio(NetParserJson.parserListRadio(jsonElement.toString(),false));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        homeview.showError();
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getListVlog(String cat_id, String singler_id, int page, int limit,String exclude) {
        BlogRadioApi.getlistVlog(cat_id , singler_id , page , limit , exclude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("listVlog",jsonElement.toString());

                        homeview.showlVlog(NetParserJson.parserListRadio(jsonElement.toString(),false));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getMsisdn() {
        BlogRadioApi.getMsisdn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            Util.msisdn = jsonObject.getString("data");
                            Log.e("msisdn",jsonElement.toString());
                            homeview.showmsisdn(Util.msisdn);
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
                        homeview.showStatus();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getUserProfile(String msisdn) {
        BlogRadioApi.getUserProfile(msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        homeview.showInfoUser(NetParserJson.parserInfoUser(jsonElement.toString()));
                        Log.e("userprofile" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getDetail(int id) {
        BlogRadioApi.getDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("getDetail" , jsonElement.toString());
                        homeview.showDetail(NetParserJson.parserListRadio(jsonElement.toString(),true).get(0));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onOffpayment() {
        BlogRadioApi.onOffpayment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            JSONObject data = jsonObject.getJSONObject("data");
                            Util.offPayment = data.getString("payment_status");
                            
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
