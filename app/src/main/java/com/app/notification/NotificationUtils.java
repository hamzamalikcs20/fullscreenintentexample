/*
 * MIT License
 *
 * Copyright (c) 2020 Giorgos Neokleous
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.app.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtils {
    private static String getChannelId(boolean isReg){
       return isReg?"regular-notification":"urgent-notification";
    }

    public static void showNotificationWithFullScreenIntent(Context context, String title, String description, boolean isRegularNotification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, getChannelId(isRegularNotification))
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(isRegularNotification?NotificationCompat.PRIORITY_DEFAULT:NotificationCompat.PRIORITY_HIGH);
        int sound=isRegularNotification? R.raw.regular_notification:R.raw.urgent_notification;
        String pkgName=context.getPackageName();
        Uri soundUri = Uri.parse("android.resource://" +  pkgName + "/" + sound);
        builder.setSound(soundUri);
             // open screen when notifications is clicked
            builder.setFullScreenIntent(getFullScreenIntent(context), true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            buildChannel(notificationManager,isRegularNotification,pkgName);
            notificationManager.notify(0, builder.build());
        }
    }

    private static void buildChannel(NotificationManager notificationManager, boolean isRegularNotification,String pkgName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Example Notification Channel";
            String description = "This is used to demonstrate the Full Screen Intent";
            int importance =isRegularNotification? NotificationManager.IMPORTANCE_DEFAULT:NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getChannelId(isRegularNotification), name, importance);
            channel.setDescription(description);
            int sound=isRegularNotification? R.raw.regular_notification:R.raw.urgent_notification;
            Uri soundUri = Uri.parse("android.resource://" +  pkgName + "/" + sound);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                .setUsage(AudioAttributes. USAGE_ALARM )
                .build() ;
            channel.setSound(soundUri, audioAttributes);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static PendingIntent getFullScreenIntent(Context context ) {
        Class<?> destination = FullScreenActivity.class;
        Intent intent = new Intent(context, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            return PendingIntent.getActivity
                    (context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        // Flags and request code are 0 for the purpose of demonstration
//        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}
