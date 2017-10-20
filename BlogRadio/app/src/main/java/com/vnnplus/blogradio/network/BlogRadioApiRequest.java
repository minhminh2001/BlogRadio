package com.vnnplus.blogradio.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Giangdv on 12/27/2016.
 */
public class BlogRadioApiRequest {

    public static BlogRadioApiInterface request , userRequest , msisdnRequest, packageRequest;

    private BlogRadioApiRequest(){}


    public static BlogRadioApiInterface getIstance(){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://wap.thegioinhac.vn/blogradio/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))

                .build();
        request = restAdapter.create(BlogRadioApiInterface.class);
        return request;
    }
    public static BlogRadioApiInterface getUserIstance(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://wap.thegioinhac.vn/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        userRequest = restAdapter.create(BlogRadioApiInterface.class);
        return userRequest;
    }
    public static BlogRadioApiInterface getPackage(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://wap.thegioinhac.vn")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .build();
        packageRequest = restAdapter.create(BlogRadioApiInterface.class);
        return packageRequest;
    }


}
