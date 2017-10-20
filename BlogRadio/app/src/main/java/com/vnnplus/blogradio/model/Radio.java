package com.vnnplus.blogradio.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Giangdv on 3/13/2017.
 */

public class Radio implements Serializable {
    int id;
    String name ;
    String description;
    String sort;
    int view;
    String avatar;
    String mediaurl;
    String content;
    boolean isVlog;
    String publistdate;

    public String getPublistdate() {
        return publistdate;
    }

    public void setPublistdate(String publistdate) {
        this.publistdate = publistdate;
    }

    Signers signers;

    ArrayList<Signers> lSingler  = new ArrayList<>();
    ArrayList<Signers> lMember = new ArrayList<>();

    public Signers getSigners() {
        return signers;
    }

    public void setSigners(Signers signers) {
        this.signers = signers;
    }

    public boolean isVlog() {
        return isVlog;
    }

    public void setVlog(boolean vlog) {
        isVlog = vlog;
    }

    public ArrayList<Signers> getlMember() {
        return lMember;
    }

    public void setlMember(ArrayList<Signers> lMember) {
        this.lMember = lMember;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMediaurl() {
        return mediaurl;
    }

    public void setMediaurl(String mediaurl) {
        this.mediaurl = mediaurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Signers> getlSingler() {
        return lSingler;
    }

    public void setlSingler(ArrayList<Signers> lSingler) {
        this.lSingler = lSingler;
    }
}
