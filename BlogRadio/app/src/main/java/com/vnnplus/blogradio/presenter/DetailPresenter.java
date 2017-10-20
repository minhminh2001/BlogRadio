package com.vnnplus.blogradio.presenter;

/**
 * Created by Giangdv on 3/15/2017.
 */

public interface DetailPresenter {
    void getDetail(int id);
    void getComment(int blog_id);
    void addComment(String msisdn , int blog_id , String comment);
    void addFavorite(int media_id , String media_type , String msisdn);
    void removeFavorite(int media_id , String msisdn);
    void Tracking(int id , String msisdn,String mediatype);
    void saveListent(String msisdn , String time , int blogId , String type);
    void checkFavorite(int blogId , String msisdn);
}
