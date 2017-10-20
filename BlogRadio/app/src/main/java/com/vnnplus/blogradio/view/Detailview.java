package com.vnnplus.blogradio.view;

import com.vnnplus.blogradio.model.Comment;
import com.vnnplus.blogradio.model.Radio;

import java.util.ArrayList;

/**
 * Created by Giangdv on 3/15/2017.
 */

public interface Detailview {
    void showDetail(Radio radio);
    void showComment(ArrayList<Comment> lComment);
    void trackking(int code , String msg);
    void successaddFavorite(int code , String msg);
    void successremoveFavorite(int code , String msg);
    void addCmtSuccess();
    void checkFavorite(int code);
//    void showComment();

}
