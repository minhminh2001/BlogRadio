package com.vnnplus.blogradio.network;

import com.google.gson.JsonElement;

import java.util.HashMap;
import rx.Observable;

/**
 * Created by Giangdv on 12/27/2016.
 */
public class BlogRadioApi {

    public static HashMap getBaseParams() {
        HashMap<String, String> fields = new HashMap<>();
        return fields;
    }

    public static Observable<JsonElement> getSlider(){
        return BlogRadioApiRequest.getIstance().getSlider();
    }
    public static Observable<JsonElement> getlistRadio(String catid , String singler_id , int page , int limit , String exclude){
        return BlogRadioApiRequest.getIstance().getListRadio(catid , singler_id , page , limit,exclude);
    }
    public static Observable<JsonElement> getOTP(String phone){
        return BlogRadioApiRequest.getUserIstance().getOTP(phone);
    }
    public static Observable<JsonElement> getlistVlog(String catid , String singler_id , int page , int limit,String exclude){
        return BlogRadioApiRequest.getIstance().getListVlog(catid,singler_id,page,limit,exclude);
    }
    public static Observable<JsonElement> getListCategory(String type,String parentId){
        return BlogRadioApiRequest.getIstance().getCategory(type , parentId);
    }
    public static Observable<JsonElement> getComment(int blog_id){
        return BlogRadioApiRequest.getIstance().getComment(blog_id);
    }
    public static Observable<JsonElement> addComment(String mssisdn , int blog_id , String comment){
        return BlogRadioApiRequest.getIstance().addComment(mssisdn , blog_id , comment);
    }
    public static Observable<JsonElement> getDetail(int id){
        return BlogRadioApiRequest.getIstance().getDetail(id);
    }
    public static Observable<JsonElement> getSamebyAuthor(String indAuthor , String type , int limit , int exclude){
        return BlogRadioApiRequest.getIstance().getSameAuthor(indAuthor , type , limit , exclude);
    }

    public static Observable<JsonElement> getMenuItem(){
        return BlogRadioApiRequest.getIstance().getMenuItem();
    }

    public static Observable<JsonElement> getMsisdn(){
        return BlogRadioApiRequest.getUserIstance().getMsisdn();
    }

    public static Observable<JsonElement> addFavorite(int media_id , String media_type , String msisdn){
        return BlogRadioApiRequest.getIstance().addFavorite(media_id , media_type , "app" , msisdn);
    }

    public static Observable<JsonElement> removeFavorite(int media_id , String msisdn){
        return BlogRadioApiRequest.getIstance().removeFavorite(media_id , msisdn);
    }

    public static Observable<JsonElement> Tracking(int id , String msisdn,String mediatype){
        return BlogRadioApiRequest.getIstance().Tracking(id , msisdn , "app",mediatype);
    }

    public static Observable<JsonElement> saveListent(String msisdn , String time , int blogId , String type){
        return BlogRadioApiRequest.getIstance().saveListent(msisdn , time , blogId , type , "app");
    }

    public static Observable<JsonElement> getUserProfile(String msisdn){
        return BlogRadioApiRequest.getIstance().getUserProfile(msisdn);
    }

    public static Observable<JsonElement> checkStatus(String msisdn){
        return BlogRadioApiRequest.getUserIstance().checkStatus(msisdn);
    }

    public static Observable<JsonElement> editUserProfile(String msisdn , String nickname , String email , String avatar , String date , int gender){
        return BlogRadioApiRequest.getIstance().updateUserProfile(msisdn , nickname , email , avatar , gender , date);
    }

    public static Observable<JsonElement> getListAvatar(){
        return BlogRadioApiRequest.getIstance().getListAvatar();
    }

    public static Observable<JsonElement> getBlogPackage(){
        return BlogRadioApiRequest.getPackage().getBlogPackage();
    }

    public static Observable<JsonElement> unRegister(String pkg , String msisdn){
        return BlogRadioApiRequest.getPackage().unRegister(pkg , msisdn);
    }

    public static Observable<JsonElement> Login(String msisdn , String otp){
        return BlogRadioApiRequest.getUserIstance().Login(msisdn , otp);
    }

    public static Observable<JsonElement> Search(String textSearch , int limit , int page , String type){
        return BlogRadioApiRequest.getIstance().Search(textSearch , limit , page , type);
    }

    public static Observable<JsonElement> getListFavorite(String msisdn , String type , int page , int limit){
        return BlogRadioApiRequest.getIstance().getListFavorite(msisdn , type , page , limit);
    }

    public static Observable<JsonElement> getPageinfo(){
        return BlogRadioApiRequest.getIstance().getPageinfo();
    }

    public static Observable<JsonElement> getPageFeature(){
        return BlogRadioApiRequest.getIstance().getPageFeature();
    }

    public static Observable<JsonElement> getSamevoice(String singerId,String type){
        return BlogRadioApiRequest.getIstance().getSamevoice(singerId , type);
    }

    public static Observable<JsonElement> checkFavorite(int blogId , String msisdn){
        return BlogRadioApiRequest.getIstance().checkFavorite(blogId , msisdn);
    }

    public static Observable<JsonElement> onOffpayment(){
        return BlogRadioApiRequest.getIstance().onOffpayment();
    }

}
