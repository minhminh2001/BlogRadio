package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.UserPresenter;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.UserInfoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/27/2017.
 */

public class UserPresenterIplm implements UserPresenter {
    UserInfoView view;
    Activity activity;
    public UserPresenterIplm(UserInfoView view , Activity activity){
        this.view = view;
        this.activity = activity;
    }
    @Override
    public void getUserProfile(String msisdn) {
        BlogRadioApi.getUserProfile(msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        view.showInfoUser(NetParserJson.parserInfoUser(jsonElement.toString()));
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
    public void updateProfile(String msisdn, String nickname, String email, String avatar, String date, int gender) {
        BlogRadioApi.editUserProfile(msisdn,nickname ,email , avatar , date , gender)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String msg = jsonObject.getString("msg");
                            Util.showToast(activity.getBaseContext() , msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("edituser" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getAllAvatar() {
        BlogRadioApi.getListAvatar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        ArrayList<String> lAvatar = new ArrayList<String>();
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i =0 ; i < jsonArray.length() ; i++){
                                lAvatar.add(String.valueOf(jsonArray.get(i)));
                                view.ListAvatar(lAvatar);
                            }
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
