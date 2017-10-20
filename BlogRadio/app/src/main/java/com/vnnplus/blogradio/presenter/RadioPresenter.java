package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 3/10/2017.
 */

public interface RadioPresenter {
    public void getListRadio(String cat_id , String singler_id , int page , int limit , String exclude);
    public void getSamebyVoice(String singerId , String type);
    void getListVlog(String cat_id , String singler_id , int page , int limit,String exclude);

}
