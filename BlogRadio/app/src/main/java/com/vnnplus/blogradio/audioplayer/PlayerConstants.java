package com.vnnplus.blogradio.audioplayer;

import android.os.Handler;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class PlayerConstants {
    public static enum REPEAT_MODE {
        ONE, OFF
    }
    public static REPEAT_MODE reMode = REPEAT_MODE.OFF;
    public static long DURATION = 0;

    public static boolean isPlayingVideo = false;
    public static boolean SONG_PAUSED = false;

    public static Handler SEEKTO_HANDLER;

    public static Handler PROGRESSBAR_HANDLER;

    public static Handler SONG_CHANGE_HANDLER;

    public static Handler PLAY_PAUSE_HANDLER;

    public static Handler HANDLER_SAVE_LISTEN;


    public static Handler CHANGE_CONNECTION;

    public static Handler NETWORK_CONNECTION;




}
