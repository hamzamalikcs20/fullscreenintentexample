package com.giorgosneokleous.fullscreenintentexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.showNotificationWithFullScreenIntent(context, false);
    }

    public static Intent build(Context context ) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        return intent;
    }
}
