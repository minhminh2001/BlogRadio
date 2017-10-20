package com.vnnplus.blogradio.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.model.DownloadObject;
import com.vnnplus.blogradio.model.ItemMenu;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.network.DownloadTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Giangdv on 3/8/2017.
 */

public class Util {
    public static Radio radioService = new Radio();
    public static Radio radio = new Radio();
    public static ArrayList<ItemMenu> lRadio = new ArrayList<>();
    public static ArrayList<ItemMenu> lVlog = new ArrayList<>();
    public static String msisdn = "";
    public static ArrayList<Radio> listRadio = new ArrayList<>();
    public static ArrayList<String> lPackage = new ArrayList<>();
    public  static ArrayList<String> lAllReg = new ArrayList<>();
    public static boolean isTest = false;
    public static String AvatarUser = "";
    public static boolean tracking = false;
    public static boolean isFirst = true;
    public static boolean isShow = true;

    public static String offPayment;


    public static void addToTrackDownload(Radio data, final Context context, boolean isVlog) {

            DownloadObject obj = new DownloadObject();
            obj.data = data;
            obj.isVlog = isVlog;
            obj.task = new DownloadTask(context);
            obj.task.execute(obj);
    }


    public static int getWidthScreen(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            display.getRealSize(point);

            return point.x;
        }
        return display.getWidth();
    }
    public static int getHeightScreen(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            display.getRealSize(point);

            return point.y;
        }
        return display.getHeight();
    }

    public static void changeHeightView2(View view, float ratioPerScreenWidth, float ratioPerWidth, Activity activity) {
        // change heigth image
        float width = Util.getWidthScreen(activity);
        width = width * ratioPerScreenWidth;
        float height = (width * ratioPerWidth) / 16;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) height;
        params.width = (int) width;
        view.setLayoutParams(params);
        view.invalidate();
    }
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public static void setImage(ImageView view , Activity activity , String url){
        Glide.with(activity)
                .load(url)
                .asBitmap()
                .placeholder(R.drawable.placehoder)
                .error(R.drawable.placehoder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }
    public static boolean validPhone(String phoneInput) {
        //
        String tempPhone = phoneInput;
        boolean phoneValidStart = false;
        int startPhoneLeght = 0;
        //
        String GPC = "8491,8494,84123,84124,84125,84127,84129,8488";
        String VMS = "8490,8493,84120,84121,84122,84126,84128";
        String VIETTEL = "8496,8497,8498,8416";
        String SPHONE = "8495";
        String VNM = "8492,84186,84188";
        String VSAT = "992";


        if (tempPhone == null || tempPhone.equals(""))
            return false;
        if (tempPhone.startsWith("0")) {
            tempPhone = tempPhone.substring(1, tempPhone.length());
            tempPhone = "84" + tempPhone;
        }
        //check valide tempphone


        //vina
        String[] strPlit = GPC.split(",");
        for (String item : strPlit) {
            if (tempPhone.startsWith(item)) {
                phoneValidStart = true;
                startPhoneLeght = item.length() - 1;
                break;
            }
        }

        //mobi
        if (!phoneValidStart) {
            strPlit = VMS.split(",");
            for (String item : strPlit) {
                if (tempPhone.startsWith(item)) {
                    phoneValidStart = true;
                    startPhoneLeght = item.length() - 1;
                    break;
                }
            }
        }
        //viettel
        if (!phoneValidStart) {
            strPlit = VIETTEL.split(",");
            for (String item : strPlit) {
                if (tempPhone.startsWith(item)) {
                    phoneValidStart = true;
                    if (item.equals("8416"))
                        startPhoneLeght = item.length();
                    else
                        startPhoneLeght = item.length() - 1;
                    break;
                }
            }
        }
        //sphone
        if (!phoneValidStart) {
            strPlit = VNM.split(",");
            for (String item : strPlit) {
                if (tempPhone.startsWith(item)) {
                    phoneValidStart = true;
                    startPhoneLeght = item.length() - 1;
                    break;
                }
            }
        }
        //VNM
        if (!phoneValidStart) {
            strPlit = SPHONE.split(",");
            for (String item : strPlit) {
                if (tempPhone.startsWith(item)) {
                    phoneValidStart = true;
                    startPhoneLeght = item.length() - 1;
                    break;
                }
            }
        }
        //VSAT
        if (!phoneValidStart) {
            strPlit = VSAT.split(",");
            for (String item : strPlit) {
                if (tempPhone.startsWith(item)) {
                    phoneValidStart = true;
                    startPhoneLeght = item.length();
                    break;
                }
            }
        }

        if (!phoneValidStart)
            return false;

        //3 || 4
        //3 097,098...
        if (startPhoneLeght == 3) {
            if (phoneInput.startsWith("84")) {
                if (phoneInput.length() != 11)
                    return false;
            } else {
                if (phoneInput.length() != 10)
                    return false;
            }
        }
        //4 0166 ...
        else {
            if (phoneInput.startsWith("84")) {
                if (phoneInput.length() != 12)
                    return false;
            } else {
                if (phoneInput.length() != 11)
                    return false;
            }
        }

        return true;
    }
    public static enum OPERATOR {mobi, vina, viettel, sphone, vnm, vsat, other}

    public static OPERATOR checkOperatorOfPhoneNumber(Context context, String phoneInput) {
        String GPC = "8491,8494,84123,84124,84125,84127,84129,8488";
        String VMS = "8490,8493,84120,84121,84122,84126,84128";
        String VIETTEL = "8496,8497,8498,8416";
        String SPHONE = "8495";
        String VNM = "8492,84186,84188";
        String VSAT = "992";


        if (phoneInput == null || phoneInput.equals(""))
            return OPERATOR.mobi;
        if (phoneInput.startsWith("0")) {
            phoneInput = phoneInput.substring(1, phoneInput.length());
            phoneInput = "84" + phoneInput;
        }

        //vina
        String[] strPlit = GPC.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.vina;

        }
        //mobi
        strPlit = VMS.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.mobi;

        }
        //viettel
        strPlit = VIETTEL.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.viettel;

        }
        //sphone
        strPlit = VNM.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.sphone;

        }
        //VNM
        strPlit = SPHONE.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.vnm;

        }
        //VSAT
        strPlit = VSAT.split(",");
        for (String item : strPlit) {
            if (phoneInput.startsWith(item))
                return OPERATOR.vsat;

        }
        return OPERATOR.other;
    }
    public static void showToast(Context context, String ms) {
        try {
            Toast.makeText(context, ms, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
        }
    }
    public static String getDuration(long milliseconds) {
        if (milliseconds == 0)
            return "--:--";
        long sec = (milliseconds / 1000) % 60;
        long min = (milliseconds / (60 * 1000)) % 60;
        long hour = milliseconds / (60 * 60 * 1000);

        String s = (sec < 10) ? "0" + sec : "" + sec;
        String m = (min < 10) ? "0" + min : "" + min;
        String h = "" + hour;

        String time = "";
        if (hour > 0) {
            time = h + ":" + m + ":" + s;
        } else {
            time = m + ":" + s;
        }
        return time;
    }
    public static boolean isServiceRunning(String serviceName, Activity activity) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkOnline(Context context) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;

    }
    public static String formatTime(String time,Context context){
        Date date = new Date(time);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return dateFormat.format(date);
    }

    public static void shareFacebook(Radio radio ,Activity activity){
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(activity);
        String linkShare = "http://wap.thegioinhac.vn/blogradio/home/deepLink/?id="+radio.getId();
        shareDialog = new ShareDialog(activity);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(radio.getName())
                .setContentDescription(radio.getDescription())
                .setImageUrl(Uri.parse(radio.getAvatar()))
                .setContentUrl(Uri.parse(linkShare)).build();
        shareDialog.show(linkContent,ShareDialog.Mode.AUTOMATIC);
    }
}
