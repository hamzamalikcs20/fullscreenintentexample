package com.giorgosneokleous.fullscreenintentexample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showFullScreenIntentButton = findViewById(R.id.showFullScreenIntentButton);
        showFullScreenIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils.showNotificationWithFullScreenIntent(getApplicationContext(),false);
            }
        });

        Button showFullScreenIntentWithDelayButton = findViewById(R.id.showFullScreenIntentWithDelayButton);
        showFullScreenIntentWithDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationScheduler. scheduleNotification(getApplicationContext(),false);
            }
        });

        Button showFullScreenIntentLockScreenWithDelayButton = findViewById(R.id.showFullScreenIntentLockScreenWithDelayButton);
        showFullScreenIntentLockScreenWithDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationScheduler.scheduleNotification(getApplicationContext(),true);
            }
        });
    }

}
