package com.vnnplus.blogradio.audioplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Giangdv on 3/21/2017.
 */

public class NotificationBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(RadioService.NOTIFY_PLAY))
            Controls.playControl(context);
        else if (intent.getAction().equals(RadioService.NOTIFY_PAUSE))
            Controls.pauseControl(context);
        else if (intent.getAction().equals(RadioService.NOTIFY_DELETE)){
            Controls.pauseControl(context);
            Intent i = new Intent(context, RadioService.class);
            context.stopService(i);
        }
    }
}
