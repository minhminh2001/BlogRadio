package com.vnnplus.blogradio.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plus.blogradio.R;
import com.vnnplus.blogradio.base.BaseFragment;
import com.vnnplus.blogradio.presenter.LoginPresenter;
import com.vnnplus.blogradio.presenterIplm.LoginPresenterIplm;
import com.vnnplus.blogradio.util.SharePreference;
import com.vnnplus.blogradio.util.Util;
import com.vnnplus.blogradio.view.LoginView;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Giangdv on 3/13/2017.
 */

public class FragmentLogin extends BaseFragment implements View.OnClickListener,LoginView {
    LoginPresenter presenter;
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    @Bind(R.id.btn_Login)
    TextView btn_login;
    @Bind(R.id.edt_phone)
    EditText edt_phone;
    @Bind(R.id.edt_Otp)
    EditText edt_Otp;
    @Bind(R.id.back)
    LinearLayout back;

    public FragmentLogin(Context context, FragmentManager ft) {
        this.fmm = ft;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup viewGroup) {
        rootView.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void getExtraData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void createAdapter() {

    }

    @Override
    public void configRecyclerview() {

    }

    @Override
    public void initPresenter() {
        presenter = new LoginPresenterIplm(getActivity() , this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rootView:
                break;
            case R.id.btn_Login:
                String tag = btn_login.getTag() + "";
                if (tag.equals("0")){
                    String phoneInput = edt_phone.getText() + "";
                    if (!Util.validPhone(phoneInput)){
                        showToastMs("Số điện thoại nhập không đúng . Vui lòng thử lại");
                        return;
                    }
                    else {
                        boolean isMobi = Util.checkOperatorOfPhoneNumber(getContext() , phoneInput) == Util.OPERATOR.mobi ? true : false;
                        if (isMobi){

                            if (Util.isFirst){

                                presenter.getOTP(phoneInput);
                                Util.isFirst = false;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Util.isFirst = true;
                                    }
                                }, 180000);
                            }
                            else {
                                showToastMs("Bạn cần chờ 3 phút để đăng nhập lại");
                            }
                        }
                        else
                            showToastMs("Bạn phải dùng thuê bao MobiFone để sử dụng dịch vụ Blogradio");
                    }
                }
                else {
                    presenter.Login(edt_Otp.getText().toString() , edt_phone.getText().toString());
                    }
                }


        }

    @Override
    public void showOTP(int code, String ms) {
        if (code == 0){
            Util.showToast(getContext(),"Mã OTP sẽ gửi về sau ít phút");
            edt_Otp.setVisibility(View.VISIBLE);
            btn_login.setTag(1);
            btn_login.setText("Đăng Nhập");
        }
        else if (code == -3){
            Util.showToast(getContext(),ms);
        }
        else {
            Util.showToast(getContext() , "Số điện thoại của bạn chưa được đăng ký dịch vụ");
        }
    }

    @Override
    public void showLogin(int code, String ms) {
        if (code == 0){
            Util.msisdn = edt_phone.getText().toString();
            SharePreference.setDefaults("msisdn",Util.msisdn , getContext());
            showToastMs(ms);
            getActivity().onBackPressed();
        }
        else {
            showToastMs("Sai mã OTP , vui lòng nhập lại");
        }
    }

    @Override
    public void showStatus() {

    }
}
