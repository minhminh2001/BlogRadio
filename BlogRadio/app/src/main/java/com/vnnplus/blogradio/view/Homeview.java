package com.vnnplus.blogradio.view;

import com.vnnplus.blogradio.model.InfoUser;
import com.vnnplus.blogradio.model.Radio;
import com.vnnplus.blogradio.model.Slider;

import java.util.ArrayList;

/**
 * Created by Giangdv on 1/3/2017.
 */

public interface Homeview {
    public void showSlide(ArrayList<Slider> sliderArrayList);
    public void showlRadio(ArrayList<Radio> lRadio);
    public void showlVlog(ArrayList<Radio> lVlog);
    void getMenuItem();
    void showmsisdn(String msisdn);
    void showStatus();
    void showInfoUser(InfoUser infoUser);
    void showError();
    void showDetail(Radio radio);

}
