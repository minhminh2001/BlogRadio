package com.vnnplus.blogradio.model;

import com.vnnplus.blogradio.network.DownloadTask;

/**
 * Created by Giangdv on 3/23/2017.
 */

public class DownloadObject {
    public DownloadObject() {
    }

    public long progess;
    public int status; // 0 - done , 1 - false , 2 - cancel
    public Radio data;
    public DownloadTask task;
    public boolean isVlog;
}
