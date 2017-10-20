package com.vnnplus.blogradio.view;

import com.vnnplus.blogradio.model.Radio;

import java.util.ArrayList;

/**
 * Created by Giangdv on 4/17/2017.
 */

public interface FavoriteView {
    void showListFavorite(ArrayList<Radio> lFavorite);
    void showError();
    void successremoveFavorite(int code , String msg);
}
