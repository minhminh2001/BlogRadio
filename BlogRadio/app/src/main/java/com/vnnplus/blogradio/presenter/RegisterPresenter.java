package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 4/12/2017.
 */

public interface RegisterPresenter {
    void getPackage();
    void checkStatus(String msisdn);
    void checkPhonenumber(String msisdn  , boolean isVlog);
    void unRegister(String pks , String msisdn);
}
