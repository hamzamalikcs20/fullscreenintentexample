package com.app.notification;



import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService

{


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String description = data.get("description");
        boolean isReg = data.get("type").equals("reg");
        NotificationUtils.showNotificationWithFullScreenIntent(getApplicationContext(),title,description ,isReg);
    }
}




