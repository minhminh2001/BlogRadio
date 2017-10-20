package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 3/27/2017.
 */

public interface UserPresenter {
    void getUserProfile(String msisdn);
    void updateProfile(String msisdn , String nickname , String email , String avatar , String date , int gender);
    void getAllAvatar();
}
