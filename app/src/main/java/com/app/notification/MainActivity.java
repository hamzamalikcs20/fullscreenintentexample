package com.app.notification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String apiUrl = "https://demo-app-server-production.up.railway.app/sendNotification";
    String title = "";
    String description = "";
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { }
                });

        // Check Android version and request notification access if it's Android 13 or greater
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            if (Build.VERSION.SDK_INT >= 33) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        Button showFullScreenIntentButton = findViewById(R.id.showFullScreenIntentButton);
        showFullScreenIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = "Regular Notification";
                TextView textView = findViewById(R.id.editTextTextMultiLine);
                String text = textView.getText().toString();
                if(text.length()==0) return;
                description = text;
                type = "reg";
                notifyServerToSendNotification(apiUrl, title, description, type);
            }
        });

        Button showFullScreenIntentWithDelayButton = findViewById(R.id.showFullScreenIntentWithDelayButton);
        showFullScreenIntentWithDelayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = "Urgent Notification";
                TextView textView = findViewById(R.id.editTextTextMultiLine);
                String text = textView.getText().toString();
                if(text.length()==0) return;
                description = text;
                type = "urg";
                notifyServerToSendNotification(apiUrl, title, description, type);
            }
        });
    }

    private void notifyServerToSendNotification(final String apiUrl, final String title, final String description, final String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonData = "{\n" +
                            "    \"title\": \"" + title + "\",\n" +
                            "    \"description\": \"" + description + "\",\n" +
                            "    \"type\": \"" + true + "\"\n" +
                            "}";

                    // Create URL object
                    URL url = new URL(apiUrl);

                    // Create connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Write JSON data to output stream
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = jsonData.getBytes("utf-8");
                        os.write(input, 0, input.length);
                    }

                    // Get response from the server
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Request sent successfully. HTTP Response Code: " + responseCode);
                    } else {
                        System.out.println("Request failed. HTTP Response Code: " + responseCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}
