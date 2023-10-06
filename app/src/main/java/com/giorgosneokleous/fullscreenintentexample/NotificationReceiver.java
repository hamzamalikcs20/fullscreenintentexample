package com.giorgosneokleous.fullscreenintentexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String LOCK_SCREEN_KEY = "lockScreenKey";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isLockScreen = intent.getBooleanExtra(LOCK_SCREEN_KEY, true);
        if (isLockScreen) {
            NotificationUtils.showNotificationWithFullScreenIntent(context,true);
        } else {
            NotificationUtils.showNotificationWithFullScreenIntent(context,false);
        }
    }

    public static Intent build(Context context, boolean isLockScreen) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra(LOCK_SCREEN_KEY, isLockScreen);
        return intent;
    }
}
