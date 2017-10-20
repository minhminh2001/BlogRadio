package com.vnnplus.blogradio.network;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.vnnplus.blogradio.MyApplication;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.model.DownloadObject;
import com.vnnplus.blogradio.util.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Giangdv on 3/23/2017.
 */

public class DownloadTask extends AsyncTask<DownloadObject , Integer , String> {
    private Context context;
    private PowerManager.WakeLock mWakeLock;
    String fileOutputUri;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    int id = 0;
    Timer timer;
    DownloadObject data;
    private boolean isPaused = false;
    int value, curProgess;


    public DownloadTask(Context context){
        this.context = context;
        timer = new Timer();
    }
    public void remove() {
        if (timer != null)
            timer.cancel();
        cancel(false);
        if (mNotifyManager != null)
            if (mNotifyManager != null) {
                mNotifyManager.cancel(id);
                Log.d("download - remove", id + "");
            }
    }
    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();
    }

    @Override
    protected String doInBackground(DownloadObject... downloadObjects) {
        this.data = downloadObjects[0];
        this.id = this.data.data.getId();
        int count;
        Bitmap bitmap = null;
        try {
            URL url1 = new URL(data.data.getAvatar());
            bitmap = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }
        mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentText("Đang tải...").setContentTitle(downloadObjects[0].data.getName()).setProgress(100, 0, false)
                .setSmallIcon(R.drawable.ic_playall_song)
                .setAutoCancel(true)
                .setLargeIcon(bitmap);
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() +"/" + "Blogradio");
//        Log.e("uri", String.valueOf(uri));
//        intent.setDataAndType(uri, "text/csv");
//        Intent.createChooser(intent, "Open folder");
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        mBuilder.setContentIntent(pendingIntent);
        try{
            URL url = null;
            String fileUrl = downloadObjects[0].data.getMediaurl();
            File dir = checkExistFolder("Blogradio");
            if (this.data.isVlog)
                fileOutputUri = dir + "/" + downloadObjects[0].data.getName() + ".mp4";
            else
                fileOutputUri = dir + "/" + downloadObjects[0].data.getName() + ".mp3";
            if ( fileUrl != null){
                url = new URL(fileUrl);
            }
            if (url == null)
                return "lỗi";
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(fileOutputUri);

            byte data[] = new byte[1024];

            long total = 0;




            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    if (curProgess != value && !isPaused) {
                        mBuilder.setContentInfo(value + "%").setProgress(100, value, false);
                        mNotifyManager.notify(id, mBuilder.build());
                        Log.d("download - push ", id + "");
                        curProgess = value;
                        DownloadTask.this.data.progess = value;
//                        PlayerConstants.handlerDownload.sendMessage(PlayerConstants.handlerDownload.obtainMessage());
                    }
                }
            }, 0, 2000);
            while ((count = input.read(data)) != -1) {
                if (!isPaused) {
                    total += count;
                    publishProgress((int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }
            }
            output.flush();

            // closing streams
            output.close();
            input.close();
        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            mWakeLock.release();
            String title;
            if (s != null) {
                // Toast.makeText(context, "Download error: " + result,
                // Toast.LENGTH_LONG).show();
                title = "Tải xuống lỗi .";
            } else {
                // Toast.makeText(context, "File downloaded",
                // Toast.LENGTH_SHORT).show();
//                Util.lDownloading.remove(data);
//                PlayerConstants.handlerDownload.sendMessage(PlayerConstants.handlerDownload.obtainMessage());
                title = "Tải xuống thành công .";
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");
                MediaScannerConnection.scanFile(MyApplication.getInstance(), new String[] { fileOutputUri },
                        new String[] { mimeType }, null);
                mBuilder.setContentText(title);
                if (s == null) {
                    mBuilder.setProgress(0, 0, false);
                    mBuilder.setContentInfo(100 + "%");
                }

                mNotifyManager.notify(id, mBuilder.build());
            }
        }catch (Exception e){

        }
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        value = values[0];
    }
    public File checkExistFolder(String folderName) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
        Log.e("uri1",Environment.getExternalStorageDirectory() + "/" + folderName);
        if (!file.exists())
            file.mkdir();
//        File storageDir = null;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            //RUNTIME PERMISSION Android M
//            if(PackageManager.PERMISSION_GRANTED== ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                storageDir = new File(Environment.getExternalStorageDirectory() + "/" + folderName);
//            }else{
//                requestPermission(context);
//            }
//
//        }

        return file;
    }
    int REQUEST_WRITE_EXTERNAL_STORAGE=1;

}
