package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 4/17/2017.
 */

public interface FavoritePresenter {
    void getListFavorite( String msisdn , String type , int page , int limit);
    void removeFavorite(int media_id , String msisdn);

}
