package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 3/13/2017.
 */

public interface LoginPresenter {
    public void getOTP(String phone);
    void Login(String otp , String msisdn);
    void checkStatus(String msisdn);
}
