package com.vnnplus.blogradio.audioplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Giangdv on 4/18/2017.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")){
            try {
                PlayerConstants.CHANGE_CONNECTION.sendEmptyMessage(0);
            }catch (Exception e){}
        }
        if (intent.getExtras() != null){
            NetworkInfo ni=(NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(ni!=null && ni.getState()==NetworkInfo.State.CONNECTED) {

            } else if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {
                try{
                    PlayerConstants.NETWORK_CONNECTION.sendEmptyMessage(0);

                }
                catch (Exception e){}
            }
        }
//        else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)){
//            try {
//                PlayerConstants.NETWORK_CONNECTION.sendEmptyMessage(0);
//            }catch (Exception e){}
//        }
    }
}
