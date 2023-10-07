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



//        super.onMessageReceived(remoteMessage);
//        NotificationUtils.showNotificationWithFullScreenIntent(getApplicationContext(),isReg);

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String description = data.get("description");
        boolean isReg= data.get("type").equals("reg");
        NotificationUtils.showNotificationWithFullScreenIntent(getApplicationContext(),isReg);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,isReg?"reg":"high")
//                .setSmallIcon(android.R.drawable.arrow_up_float)
//                .setContentTitle(title)
//                .setContentText(description)
//                .setPriority(isReg?NotificationCompat.PRIORITY_DEFAULT:NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true);
//        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
//        manager.notify(101,builder.build());
    }

    public void getFirebaseMessage(String title, Map<String, String> msg)

    {


    }
}




