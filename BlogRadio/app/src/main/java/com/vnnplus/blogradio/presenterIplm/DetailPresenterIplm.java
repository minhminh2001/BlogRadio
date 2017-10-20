package com.vnnplus.blogradio.presenterIplm;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.vnnplus.blogradio.network.BlogRadioApi;
import com.vnnplus.blogradio.network.NetParserJson;
import com.vnnplus.blogradio.presenter.DetailPresenter;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.Detailview;

import org.json.JSONException;
import org.json.JSONObject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Giangdv on 3/15/2017.
 */

public class DetailPresenterIplm implements DetailPresenter {
    Detailview detailview;
    Activity activity;
    public DetailPresenterIplm(Detailview detailview , Activity activity){
        this.detailview = detailview;
        this.activity = activity;
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
                        detailview.showDetail(NetParserJson.parserListRadio(jsonElement.toString(),true).get(0));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void getComment(int blog_id) {
        BlogRadioApi.getComment(blog_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            if (code == -1){
                                String msg = jsonObject.getString("msg");
                                Util.showToast(activity , msg);
                            }
                            else {
                                detailview.showComment(NetParserJson.parserListComment(jsonElement.toString()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("getComment" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void addComment(String msisdn, int blog_id, String comment) {
        BlogRadioApi.addComment(msisdn , blog_id , comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            String sms = jsonObject.getString("msg");
                            detailview.addCmtSuccess();
//                            Util.showToast(activity.getBaseContext(),sms);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("addComment" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void addFavorite(int media_id, String media_type, String msisdn) {
        BlogRadioApi.addFavorite(media_id,media_type,msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            detailview.successaddFavorite(code , msg);
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
                            detailview.successremoveFavorite(code , msg);
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
    public void Tracking(int id, String msisdn,String mediatype) {
        BlogRadioApi.Tracking(id,msisdn,mediatype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            detailview.trackking(code , msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("tracking" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void saveListent(String msisdn, String time, int blogId, String type) {
        BlogRadioApi.saveListent(msisdn,time , blogId , type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        Log.e("savelistent" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void checkFavorite(int blogId, String msisdn) {
        BlogRadioApi.checkFavorite(blogId , msisdn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JsonElement>() {
                    @Override
                    public void call(JsonElement jsonElement) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonElement.toString());
                            int code = jsonObject.getInt("code");
                            detailview.checkFavorite(code);
                        } catch (JSONException e) {

                        }
                        Log.e("checkfavorite" , jsonElement.toString());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
}
