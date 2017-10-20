package com.vnnplus.blogradio.model;

/**
 * Created by Giangdv on 3/28/2017.
 */

public class InfoUser {
    String msisdn;
    String nickName;
    String avatar;
    String email;
    int genDer;
    String Date;

    public int getGenDer() {
        return genDer;
    }

    public void setGenDer(int genDer) {
        this.genDer = genDer;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
