package com.vnnplus.blogradio.network;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by Giangdv on 12/27/2016.
 */
public interface BlogRadioApiInterface {

    @GET("/get_slider")
    Observable<JsonElement> getSlider();

    @GET("/get_list_radio/")
    Observable<JsonElement> getListRadio(@Query("cat_id") String catid , @Query("singer_id") String singerid , @Query("page") int page , @Query("limit") int limit , @Query("exclude") String exclude);

    @GET("/User/getOtp/")
    Observable<JsonElement> getOTP(@Query("msisdn") String msisdn);

    @GET("/get_list_vlog/")
    Observable<JsonElement> getListVlog(@Query("cat_id") String catid , @Query("singer_id") String singerid , @Query("page") int page , @Query("limit") int limit, @Query("exclude") String exclude);

    @GET("/get_list_category/")
    Observable<JsonElement> getCategory(@Query("type") String type , @Query("parentId") String patentId);

    @GET("/getCommentByBlogId/")
    Observable<JsonElement> getComment(@Query("blogId") int blog_id);

    @GET("/add_comment/")
    Observable<JsonElement> addComment(@Query("msisdn") String msisdn , @Query("blog_id") int blog_id , @Query("comment") String comment);

    @GET("/get_detail_news/")
    Observable<JsonElement> getDetail(@Query("id") int id);

    @GET("/getDetailByAuthorIds/")
    Observable<JsonElement> getSameAuthor(@Query("authorIds") String idAuthor , @Query("type") String type , @Query("limit") int limit , @Query("exclude") int exclude);

    @GET("/getMenuItem")
    Observable<JsonElement> getMenuItem();

    @GET("/user/getMsisdn")
    Observable<JsonElement> getMsisdn();

    @GET("/add_favourite/")
    Observable<JsonElement> addFavorite(@Query("media_id") int media_id , @Query("media_type") String media_type,
                                        @Query("source") String source , @Query("msisdn") String msisdn);

    @GET("/remove_favourite/")
    Observable<JsonElement> removeFavorite(@Query("media_id") int media_id ,@Query("msisdn") String msisdn);

    @GET("/tracking/")
    Observable<JsonElement> Tracking(@Query("mediaid") int id , @Query("msisdn") String msisdn , @Query("source") String source ,@Query("mediatype") String mediatype);

    @GET("/addPlayTime/")
    Observable<JsonElement> saveListent(@Query("msisdn") String msisdn , @Query("time") String time , @Query("blogId") int blogId ,@Query("type") String type,
                                        @Query("source") String source);

    @GET("/getUserProfile/")
    Observable<JsonElement> getUserProfile(@Query("msisdn") String msisdn);

    @GET("/editUser/")
    Observable<JsonElement> updateUserProfile(@Query("msisdn") String msisdn , @Query("nickname") String nickname , @Query("email") String email ,
                                              @Query("avatar") String avatar, @Query("gender") int gender , @Query("birthday") String date );

    @GET("/user/checkMsisdnStatusV2/")
    Observable<JsonElement> checkStatus(@Query("msisdn") String msisdn);

    @GET("/getListAvatar")
    Observable<JsonElement> getListAvatar();

    @GET("/blogradio/api/getBlogPackage")
    Observable<JsonElement> getBlogPackage();


    @GET("/account/unRegVasgate")
    Observable<JsonElement> unRegister(@Query("pkg") String pkg , @Query("msisdn") String msisdn);

    @GET("/User/login/")
    Observable<JsonElement> Login(@Query("msisdn") String msisdn , @Query("otp") String otp);

    @GET("/search/")
    Observable<JsonElement> Search(@Query("q") String textSearch , @Query("limit") int limit , @Query("page") int page , @Query("type") String type);

    @GET("/getFavourite/")
    Observable<JsonElement> getListFavorite(@Query("msisdn") String msisdn , @Query("type") String type ,@Query("page") int page ,
                                            @Query("limit") int limit);

    @GET("/pageInfo")
    Observable<JsonElement> getPageinfo();

    @GET("/pageFeature")
    Observable<JsonElement> getPageFeature();

    @GET("/getBlogBySinger/")
    Observable<JsonElement> getSamevoice(@Query("singerId") String singerId , @Query("type") String type);

    @GET("/checkFavourite/")
    Observable<JsonElement> checkFavorite(@Query("blogid") int blogId , @Query("msisdn") String msisdn);

    @GET("/init")
    Observable<JsonElement> onOffpayment();

}
