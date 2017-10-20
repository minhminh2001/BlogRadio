package com.vnnplus.blogradio.audioplayer;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.vnnplus.blogradio.DetailActivity;
import com.vnnplus.blogradio.MainActivity;
import com.vnnplus.blogradio.MyApplication;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.util.Util;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class Controls {
    public static void seekTo(int time) {
        PlayerConstants.SEEKTO_HANDLER.sendMessage(PlayerConstants.SEEKTO_HANDLER.obtainMessage(0, time));
    }
    public static void pauseControl(Context context) {
        MainActivity.stopAnimation();
        if (!PlayerConstants.isPlayingVideo) {
            PlayerConstants.SONG_PAUSED = true;
            reStartService();
            sendMessage(context.getResources().getString(R.string.pause));
        }
    }

    public static void playControl(Context context) {
        MainActivity.startAnimation();
        if (!PlayerConstants.isPlayingVideo) {
            PlayerConstants.SONG_PAUSED = false;
            reStartService();
            sendMessage(context.getResources().getString(R.string.play));
        }
    }

    static void reStartService() {
        boolean isServiceRunning = Util.isServiceRunning(RadioService.class.getName(), MainActivity.activity);
        if (!isServiceRunning) {
            Intent i = new Intent(MyApplication.getInstance(), RadioService.class);
            MyApplication.getInstance().startService(i);
        }
    }
    public static void changRepeatMode(Context context, PlayerConstants.REPEAT_MODE mode) {
        PlayerConstants.reMode = mode;
        String msMode = "";

        if (mode == PlayerConstants.REPEAT_MODE.ONE) {
            msMode = "lặp một";
        } else if (mode == PlayerConstants.REPEAT_MODE.OFF) {
            msMode = "không lặp";
        }

        Toast.makeText(context, msMode, Toast.LENGTH_SHORT).show();
    }


    private static void sendMessage(String message) {
        try {
            PlayerConstants.PLAY_PAUSE_HANDLER
                    .sendMessage(PlayerConstants.PLAY_PAUSE_HANDLER.obtainMessage(0, message));
        } catch (Exception e) {

        }
    }
}
