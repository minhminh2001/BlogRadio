package com.vnnplus.blogradio.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vnnplus.blogradio.MainActivity;
import com.plus.blogradio.R;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.model.Package;
import com.vnnplus.blogradio.presenter.RegisterPresenter;
import com.vnnplus.blogradio.presenterIplm.RegisterPresenterIplm;
import com.vnnplus.blogradio.util.Dialog;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.RegisterView;

import java.util.ArrayList;

import butterknife.Bind;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Giangdv on 4/12/2017.
 */

public class FragmentRegister extends BaseFragment implements RegisterView {

    @Bind(R.id.tv_Dangky)
    TextView tv_infodk;
    @Bind(R.id.tv_Huygoi)
    TextView tv_infohuy;
    @Bind(R.id.tv_Radio)
    TextView tv_RA;
    @Bind(R.id.tv_Vlog)
    TextView tv_VL;
    @Bind(R.id.tv_PriceRA)
    TextView tv_PriceRA;
    @Bind(R.id.tv_PriceVL)
    TextView tv_PriceVL;
    @Bind(R.id.btn_RegisterRadio)
    TextView tv_RegisterRD;
    @Bind(R.id.btn_RegisterVlog)
    TextView tv_RegisterVL;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.ll_register)
    LinearLayout register;

    @Bind(R.id.back)
    LinearLayout back;
    String msisdn = "";
    int posDk = 0;

    private RegisterPresenter presenter;

    public FragmentRegister(Context context, FragmentManager ft) {
        this.fmm = ft;
    }

    public FragmentRegister() {

    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        showButtonLogin();
        final ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        final boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        webView.setWebViewClient(new WebViewClient() {
        });
        tv_RegisterRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("RADIO" , String.valueOf(view.getTag()));
                String tag = view.getTag()+"";
                if (tag.equals("0")){
                    if (!Util.msisdn.equals("null") && !Util.msisdn.equals("")) {
                        if (isWifi) {
                            presenter.checkPhonenumber(Util.msisdn,false);
                        } else {
                            webView.setVisibility(View.VISIBLE);
                            webView.loadUrl("http://wap.thegioinhac.vn/account/regVasgate/?pkg=RA&msisdn="+Util.msisdn);
                            register.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Dialog.dialogRegister(getActivity(), new Dialog.PopupconfirmRegister() {
                            @Override
                            public void callback(android.app.Dialog dialog, int btnId, String edt_sdt) {
                                switch (btnId){
                                    case R.id.btn1:
                                        if (!Util.validPhone(edt_sdt)){
                                            showToastMs("Số điện thoại nhập không đúng . Vui lòng thử lại");
                                        }
                                        else {
                                            boolean isMobi = Util.checkOperatorOfPhoneNumber(getContext() , edt_sdt) == Util.OPERATOR.mobi ? true : false;
                                            if (isMobi){
                                                msisdn = edt_sdt;
                                                presenter.checkPhonenumber(msisdn,false);
                                                dialog.dismiss();
                                            }
                                            else
                                                showToastMs("Bạn phải dùng thuê bao MobiFone để sử dụng dịch vụ Blogradio");
                                        }
                                        break;
                                }
                            }
                        });
                    }
                }
                else {
                    if (isWifi) {
                        startActivitySMS("HUY RA");
                    } else {
                        presenter.unRegister("RA" , Util.msisdn);
                    }
                }
            }
        });
        tv_RegisterVL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = view.getTag()+"";
                if (tag.equals("0")) {
                    if (!Util.msisdn.equals("null") && !Util.msisdn.equals("")) {
                        if (isWifi) {
                            presenter.checkPhonenumber(Util.msisdn,true);
                        } else {
                            webView.setVisibility(View.VISIBLE);
                            webView.loadUrl("http://wap.thegioinhac.vn/account/regVasgate/?pkg=VL&msisdn="+Util.msisdn);
                            register.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Dialog.dialogRegister(getActivity(), new Dialog.PopupconfirmRegister() {
                            @Override
                            public void callback(android.app.Dialog dialog, int btnId, String edt_sdt) {
                                switch (btnId){
                                    case R.id.btn1:
                                        if (!Util.validPhone(edt_sdt)){
                                            showToastMs("Số điện thoại nhập không đúng . Vui lòng thử lại");
                                        }
                                        else {
                                            boolean isMobi = Util.checkOperatorOfPhoneNumber(getContext() , edt_sdt) == Util.OPERATOR.mobi ? true : false;
                                            if (isMobi){
                                                msisdn = edt_sdt;
                                                presenter.checkPhonenumber(msisdn , true);
                                                dialog.dismiss();
                                            }
                                            else
                                                showToastMs("Bạn phải dùng thuê bao MobiFone để sử dụng dịch vụ Blogradio");
                                        }
                                        break;
                                }
                            }
                        });
                    }
                }
                else {
                    if (isWifi) {
                        startActivitySMS("HUY VL");
                    } else {
                        presenter.unRegister("VL" , Util.msisdn);
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.rlt_Mediaplayer.setVisibility(View.GONE);

        if (!Util.msisdn.equals("null") && !Util.msisdn.equals(""))
            presenter.checkStatus(Util.msisdn);
        else
            presenter.checkStatus(msisdn);
    }


    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void configRecyclerview() {

    }

    @Override
    public void initPresenter() {
        presenter = new RegisterPresenterIplm(this, getActivity());
    }

    @Override
    public void loadData() {
        presenter.getPackage();
    }

    @Override
    public void showInfoRegister(ArrayList<Package> lPakage) {
        String text = "<font color=#000000>" + "Cú pháp đăng ký : Soạn DK [MA_GOI] gửi " + "</font> <font color=#ff0000>" + lPakage.get(0).getSmsNumber() + "</font>";
        tv_infodk.setText(Html.fromHtml(text));
        String huy = "<font color=#000000>" + "Cú pháp hủy: Soạn HUY [MA_GOI] gửi " + "</font> <font color=#ff0000>" + lPakage.get(0).getSmsNumber() + "</font>";
        tv_infohuy.setText(Html.fromHtml(huy));
        tv_RA.setText(lPakage.get(0).getName());
        tv_VL.setText(lPakage.get(1).getName());
        tv_PriceRA.setText(lPakage.get(0).getPrices());
        tv_PriceVL.setText(lPakage.get(1).getPrices());
    }

    @Override
    public void showStatus() {
        showButtonLogin();
        if (Util.lPackage.size() == 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    posDk++;
                    if (posDk<4){
                        if (!msisdn.equals(""))
                            presenter.checkStatus(msisdn);
                        else
                            presenter.checkStatus(Util.msisdn);
                    }
                    else{
                        posDk = 0;
                    }
                }
            },5000);
        }
        else {
            if (!msisdn.equals(""))
                Util.msisdn = msisdn;
            SharePreference.setDefaults("msisdn",Util.msisdn , getContext());
        }

    }

    @Override
    public void showPhoneNumber(boolean isVlog) {
        if (Util.lPackage.size() ==  0){
            if (isVlog)
                startActivitySMS("DK VL");
            else
                startActivitySMS("DK RA");
        }
        else if (Util.lPackage.contains("RADIO") && !isVlog)
            showToastMs(getString(R.string.dangkyradio));
        else if (Util.lPackage.contains("VLOG") && isVlog)
            showToastMs(getString(R.string.dangkyvlog));
        else if (!Util.lPackage.contains("VLOG") && isVlog)
            startActivitySMS("DK VL");
        else if (!Util.lPackage.contains("RADIO") && !isVlog)
            startActivitySMS("DK RA");
        else if (Util.lPackage.size() == 2)
            showDialogregister(getContext(),"Bạn đã đăng ký gói cước , vui lòng đăng nhập để sử dụng dịch vụ","Thông báo");

    }

    @Override
    public void showUnregister(int code) {
        if (code == 0)
            showToastMs("Hủy gói cước thành công");
        else
            showToastMs("Lỗi , không thể hủy gói cước");
        presenter.checkStatus(Util.msisdn);
    }


    void showButtonLogin() {
        if (Util.lPackage.size() > 0) {
            if (Util.lPackage.contains("RADIO")) {
                tv_RegisterRD.setTag(1);
                tv_RegisterRD.setText("HỦY GÓI RADIO");
            }
            else {
                tv_RegisterRD.setTag(0);
                tv_RegisterRD.setText("ĐĂNG KÝ GÓI RADIO");
            }
            if (Util.lPackage.contains("VLOG")) {
                tv_RegisterVL.setTag(1);
                tv_RegisterVL.setText("HỦY GÓI VLOG");
            }
            else {
                tv_RegisterVL.setTag(0);
                tv_RegisterVL.setText("ĐĂNG KÝ GÓI VLOG");
            }
        }
        else {
            tv_RegisterRD.setTag(0);
            tv_RegisterRD.setText("ĐĂNG KÝ GÓI RADIO");
            tv_RegisterVL.setTag(0);
            tv_RegisterVL.setText("ĐĂNG KÝ GÓI VLOG");
        }
    }

    public void startActivitySMS(String arg0) {
        try {

            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "9212");
            smsIntent.putExtra("sms_body", arg0 + "");
            smsIntent.putExtra("exit_on_sent", true);
            startActivityForResult(smsIntent, 1102);


        } catch (Exception e) {
            showToastMs("Đã có lỗi xảy ra , vui lòng thử lại sau .");
        }
    }

    public void showDialogregister(Context context , String msg , String tittle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(tittle);
        builder.setCancelable(true);
        builder.setPositiveButton("Đăng ký" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                switchFragment(new FragmentLogin(getContext(),fmm));
            }
        });
        builder.setNegativeButton("Hủy" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
