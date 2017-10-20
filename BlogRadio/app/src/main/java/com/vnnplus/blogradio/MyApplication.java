package com.vnnplus.blogradio;

import android.app.Application;

/**
 * Created by Giangdv on 3/17/2017.
 */

public class MyApplication extends Application {
    public static MyApplication singleton;

    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
