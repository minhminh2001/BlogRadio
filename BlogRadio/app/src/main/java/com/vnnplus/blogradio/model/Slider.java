package com.vnnplus.blogradio.model;

/**
 * Created by Giangdv on 3/10/2017.
 */

public class Slider {
    private String name;
    private String slugname;
    private String imgUrl;
    private String type;
    private int id;
    private String mediaUrl;
    private Signers signers;

    public Signers getSigners() {
        return signers;
    }

    public void setSigners(Signers signers) {
        this.signers = signers;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
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

    public String getSlugname() {
        return slugname;
    }

    public void setSlugname(String slugname) {
        this.slugname = slugname;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
