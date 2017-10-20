package com.vnnplus.blogradio.widget;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.util.Util;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Giangdv on 3/9/2017.
 */

public class ViewMenuLeft extends RelativeLayout implements View.OnClickListener {
    View.OnClickListener onClickListener;
    public View tv_doctruyen , tv_giaitri , tv_tamsu , tv_tintuc , tv_bongmat , tv_cafe , tv_chiemtinh , tv_vlogtintuc , tv_favorite , tv_download;
    public View tv_taikhoan , tv_Thegioinhac , tv_Radio , tv_Vlog , tv_Saolive , tv_Thongtin , tv_signout;
    public RelativeLayout rlt_header , actionbar;
    TextView tv_nhandien , tv_nameuser;
    Activity activity;
    LinearLayout signIn , signUp , ll_info , signUp1;
    CircleImageView avatar;
    ImageView cover , line;
    ScrollView scrollview;

    public ViewMenuLeft(Activity activity, OnClickListener onClickListener) {
        super(activity);

        this.onClickListener = onClickListener;
        this.activity = activity;
        init();
    }

    private void init() {
        LayoutInflater li = LayoutInflater.from(this.getContext());
        RelativeLayout view = (RelativeLayout) li.inflate(R.layout.view_menu_left, this, true);
        tv_bongmat = view.findViewById(R.id.vlog_bongmat);
        tv_cafe =  view.findViewById(R.id.vlog_cafe);
        tv_chiemtinh =  view.findViewById(R.id.vlog_chiemtinh);
        tv_doctruyen =  view.findViewById(R.id.rdo_doctruyen);
        tv_giaitri =  view.findViewById(R.id.rdo_Giaitri);
        tv_tamsu =  view.findViewById(R.id.rdo_tamsu);
        tv_vlogtintuc =  view.findViewById(R.id.vlog_tintuc);
        tv_tintuc =  view.findViewById(R.id.rdo_tintuc);
        rlt_header = (RelativeLayout) view.findViewById(R.id.rlt_top);
        tv_taikhoan =  view.findViewById(R.id.tv_Taikhoan);
        scrollview = (ScrollView) view.findViewById(R.id.scrollview);
        tv_Thegioinhac =  view.findViewById(R.id.tv_tgn);
        tv_Radio =  view.findViewById(R.id.tv_Radio);
        tv_Vlog =  view.findViewById(R.id.tv_Vlog);
        tv_Thongtin =  view.findViewById(R.id.tv_info);
        tv_favorite = view.findViewById(R.id.favorite);
        tv_download = view.findViewById(R.id.download);
        tv_nhandien = (TextView) view.findViewById(R.id.tv_nhandien);
        actionbar = (RelativeLayout) view.findViewById(R.id.actionbar);
        signIn = (LinearLayout) view.findViewById(R.id.image_signin);
        signUp = (LinearLayout) view.findViewById(R.id.image_signup);
        ll_info = (LinearLayout) view.findViewById(R.id.ll_info);
        avatar = (CircleImageView) view.findViewById(R.id.image);
        tv_nameuser = (TextView) view.findViewById(R.id.tv_nameuser);
        cover = (ImageView) view.findViewById(R.id.avatar);
        signUp1 = (LinearLayout) view.findViewById(R.id.image_signup1);
        tv_signout = view.findViewById(R.id.tv_signout);
        line = (ImageView) view.findViewById(R.id.line);
        if (!Util.msisdn.equals("null") && !Util.msisdn.equals("")){
          tv_signout.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
        }
        else {
           tv_signout.setVisibility(GONE);
            line.setVisibility(GONE);
        }
        fillData(tv_taikhoan,"TÀI KHOẢN",true);
        fillData(tv_Thegioinhac,"TIỆN ÍCH KHÁC",true);
        fillData(tv_Thongtin,"THÔNG TIN",true);
        fillData(tv_Vlog,"VLOG",true);
        fillData(tv_Radio,"RADIO",true);
        fillData(tv_signout , "ĐĂNG XUẤT" , true);
        fillData(tv_favorite , "Yêu Thích" , false);
        fillData(tv_download , "Tải Về" ,false);
    }
    void fillData(View view,String text,boolean isBig){
        TextView textView;
        if (isBig)
            textView =(TextView) view.findViewById(R.id.menu);
        else
            textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);
    }
    public void UpdateAvatar(String name , String url , boolean isregister){
        if (!Util.msisdn.equals("null") && !Util.msisdn.equals("")){
            tv_nhandien.setText("Xin chào : " + Util.msisdn);
        }
        else {
            tv_nhandien.setText("Không nhận diện được số điện thoại");
        }
        if (isregister){
            signIn.setVisibility(GONE);
            signUp.setVisibility(GONE);
            cover.setVisibility(VISIBLE);
            ll_info.setVisibility(VISIBLE);
            tv_nameuser.setText(name);
            Util.setImage(avatar,activity , url);
            Util.setImage(cover,activity , url);
        }
        else {
            if (Util.lPackage.size() == 0){
                cover.setVisibility(GONE);
                signIn.setVisibility(VISIBLE);
                signUp.setVisibility(VISIBLE);
                ll_info.setVisibility(GONE);
            }
            if (!Util.msisdn.equals("null") && !Util.msisdn.equals("") )
                signUp1.setVisibility(VISIBLE);
            else
                signUp1.setVisibility(GONE);
        }
    }
    public void updateView(){
        scrollview.scrollTo(0,0);
        if (!Util.msisdn.equals("null") && !Util.msisdn.equals("")){
            tv_nhandien.setText("Xin chào : " + Util.msisdn);
            signIn.setVisibility(GONE);
        }
        else {
            tv_nhandien.setText("Không nhận diện được số điện thoại");
            signUp1.setVisibility(GONE);
            signIn.setVisibility(VISIBLE);
            signUp.setVisibility(VISIBLE);
        }
        final ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        final boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        if (!Util.msisdn.equals("null") && !Util.msisdn.equals("") && isWifi){
            tv_signout.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
        }
        else {
            tv_signout.setVisibility(GONE);
            line.setVisibility(GONE);
        }
        if (Util.lRadio.size()>0 && Util.lVlog.size() > 0){
            fillData(tv_tamsu , capitalizeAllWords(Util.lRadio.get(0).getName()),false);
            fillData(tv_giaitri , capitalizeAllWords(Util.lRadio.get(1).getName()),false);
            fillData(tv_tintuc , capitalizeAllWords(Util.lRadio.get(2).getName()),false);
            fillData(tv_doctruyen , capitalizeAllWords(Util.lRadio.get(3).getName()),false);
            fillData(tv_cafe , capitalizeAllWords(Util.lVlog.get(0).getName()),false);
            fillData(tv_vlogtintuc , capitalizeAllWords(Util.lVlog.get(1).getName()),false);
            fillData(tv_chiemtinh , capitalizeAllWords(Util.lVlog.get(2).getName()),false);
            fillData(tv_bongmat , capitalizeAllWords(Util.lVlog.get(3).getName()),false);
        }


//
    }
    public static String capitalizeAllWords(String str) {
        String phrase = "";
        boolean capitalize = true;
        for (char c : str.toLowerCase().toCharArray()) {
            if (Character.isLetter(c) && capitalize) {
                phrase += Character.toUpperCase(c);
                capitalize = false;
                continue;
            } else if (c == ' ') {
                capitalize = true;
            }
            phrase += c;
        }
        return phrase;
    }

    @Override
    public void onClick(View view) {

    }
}
