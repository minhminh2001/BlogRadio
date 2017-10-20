package com.vnnplus.blogradio.audioplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.vnnplus.blogradio.DetailActivity;
import com.vnnplus.blogradio.MainActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Giangdv on 3/14/2017.
 */

public class RadioService extends Service {
    private MediaPlayer mp;
    public static Bitmap albumArt = null;
    AudioManager audioManager;
    public static int loadError = 0;
    private static Timer timer;
    public static final String NOTIFY_DELETE = "com.audioplayer.delete";
    public static final String NOTIFY_PAUSE = "com.audioplayer.pause";
    public static final String NOTIFY_PLAY = "com.audioplayer.play";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mp = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (PlayerConstants.reMode == PlayerConstants.REPEAT_MODE.ONE){
                    playSong();
                    PlayerConstants.reMode = PlayerConstants.REPEAT_MODE.OFF;
                }
                else {
                    Controls.pauseControl(getApplicationContext());
                }

            }
        });
        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                loadError++;
                if (loadError<3){
                    mp.reset();
                    mp.start();
                }
                else {
                    loadError = 0;
                    Util.showToast(getBaseContext() , "Không thể nghe Radio này");
                    mp.reset();
                }

                return false;
            }
        });

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer arg0) {
                // TODO Auto-generated method stub
                try {
                    if (!PlayerConstants.SONG_PAUSED)
                        arg0.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        playSong();
        NewNotification();
        try {
            MainActivity. UpdateMediaPlayer();

        } catch (Exception e) {
            // TODO: handle exception
        }
        PlayerConstants.SEEKTO_HANDLER = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                // TODO Auto-generated method stub
                int time = (Integer) msg.obj;
                if (!mp.isPlaying())
                    mp.start();
                mp.seekTo(time);
                return false;
            }
        });
        PlayerConstants.SONG_CHANGE_HANDLER = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                loadError = 0;
                if (Util.radio.getId() == Util.radioService.getId()){

                }
                else {
                    PlayerConstants.SONG_PAUSED = false;
                    playSong();
                    NewNotification();
                }
                try {
                    MainActivity. UpdateMediaPlayer();
                    DetailActivity.UpdateUI();

                }catch (Exception e){

                }
            }
        };

        PlayerConstants.PLAY_PAUSE_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String) msg.obj;
                if (mp == null)
                    return false;
                if (message.equalsIgnoreCase(getResources().getString(R.string.play))) {
                    mp.start();
                } else if (message.equalsIgnoreCase(getResources().getString(R.string.pause))) {
                    mp.pause();
                }
                Integer i[] = new Integer[2];
                i[0] = mp.getCurrentPosition();
                i[1] = mp.getDuration();
                PlayerConstants.HANDLER_SAVE_LISTEN.sendMessage(PlayerConstants.HANDLER_SAVE_LISTEN.obtainMessage(0,i));
                NewNotification();
                try {
                    MainActivity. UpdateMediaPlayer();
                    DetailActivity.UpdateUI();

                }catch (Exception e){

                }
                return false;
            }
        });
        return START_STICKY;
    }
    private void playSong() {
        try{
            if (mp.isPlaying())
                mp.stop();
            mp.reset();
            mp.setDataSource(Util.radioService.getMediaurl());
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new MainTask(), 0, 1000);

        } catch (Exception e)
        {
        }

    }
    private class MainTask extends TimerTask {
        public void run() {
            try {
                if (mp.isPlaying())
                    handler.sendMessage(handler.obtainMessage(0, mp.getDuration()));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (mp != null){
                int duration = (Integer) msg.obj;
                int progress = (mp.getCurrentPosition() * 100) / duration;
                Integer i[] = new Integer[3];
                i[0] = mp.getCurrentPosition();
                i[1] = mp.getDuration();
                i[2] = progress;

                PlayerConstants.DURATION = i[1];
                PlayerConstants.PROGRESSBAR_HANDLER.sendMessage(PlayerConstants.PROGRESSBAR_HANDLER.obtainMessage(0, i));
            }
        }
    };

    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.stop();
            mp = null;
        }
        super.onDestroy();
    }
    private void NewNotification(){
        if (Util.radioService == null)
            return;
        RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(),
                R.layout.new_notification);
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_playall_song).setContentTitle(Util.radioService.getName()).build();
        setListeners(simpleContentView);
        notification.contentView = simpleContentView;
        new downloadImage().execute(Util.radioService.getAvatar());
        if (albumArt != null) {
            notification.contentView.setImageViewBitmap(R.id.imageViewAlbumArt, albumArt);
        } else {
            notification.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.drawable.face);
        }

        if (PlayerConstants.SONG_PAUSED){
            notification.contentView.setViewVisibility(R.id.btnPause, View.GONE);
            notification.contentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);
        }
        else {
            notification.contentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
            notification.contentView.setViewVisibility(R.id.btnPlay, View.GONE);
        }
        notification.contentView.setTextViewText(R.id.textSongName, Util.radioService.getName());
        if (Util.radioService.getlSingler().size()>0)
            notification.contentView.setTextViewText(R.id.textAlbumName, Util.radioService.getlSingler().get(0).getName());
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
//        notification.contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
//                new Intent(getApplicationContext(), DetailActivity.class), 0);

        Intent resultIntent = new Intent(this, DetailActivity.class);
        String target = String.valueOf(Util.radioService.getId());
        resultIntent.setAction(target);
        resultIntent.putExtra("target", target);
        if(Util.radioService.getSigners() != null)
            resultIntent.putExtra("nameCate",Util.radioService.getSigners().getName());
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,resultIntent,0);
        notification.contentIntent = pendingIntent;

        startForeground(1111, notification);
    }
    public void setListeners(RemoteViews view) {
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent play = new Intent(NOTIFY_PLAY);

        PendingIntent pDelete = PendingIntent.getBroadcast(getApplicationContext(), 0, delete,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);

        PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPause, pPause);

        PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.btnPlay, pPlay);
    }
    class downloadImage extends AsyncTask<String , Void , Void>{

        @Override
        protected Void doInBackground(String... strings) {
            URL url1 = null;
            try {
                url1 = new URL(strings[0]);
                albumArt = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
