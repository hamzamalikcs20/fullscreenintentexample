package com.giorgosneokleous.fullscreenintentexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static java.util.concurrent.TimeUnit.SECONDS;

public class NotificationScheduler {
    private static final long SCHEDULE_TIME = 30L;

    public static void scheduleNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            long timeInMillis = System.currentTimeMillis() + SECONDS.toMillis(SCHEDULE_TIME);

            PendingIntent pendingIntent = getReceiver(context);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
            }
        }
    }

    private static PendingIntent getReceiver(Context context ) {
        Intent intent = NotificationReceiver.build(context);
        // for demo purposes no request code and no flags
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
