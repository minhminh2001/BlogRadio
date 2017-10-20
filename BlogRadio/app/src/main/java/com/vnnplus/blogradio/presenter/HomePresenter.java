package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 1/3/2017.
 */

public interface HomePresenter {
    void getSlide();
    void getMenuItem();
    void getListRadio(String cat_id , String singler_id , int page , int limit , String exclude);
    void getListVlog(String cat_id , String singler_id , int page , int limit,String exclude);
    void getMsisdn();
    void checkStatus(String msisdn);
    void getUserProfile(String msisdn);
    void getDetail(int id);
    void onOffpayment();
}
