package com.vnnplus.blogradio.view;

import com.vnnplus.blogradio.model.Package;

import java.util.ArrayList;

/**
 * Created by Giangdv on 4/12/2017.
 */

public interface RegisterView {
    void showInfoRegister(ArrayList<Package> lPakage);
    void showStatus();
    void showPhoneNumber(boolean isVlog);
    void showUnregister(int code);


}