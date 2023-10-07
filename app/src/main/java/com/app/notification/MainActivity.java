package com.app.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }

                });

//        fyAEqxxdSLiSBnkFF57F_g:APA91bEyUG4l5kJ6bvVjZxyZ9BWJKMgdS7Kq2s9UpG9Yqoay8-72qjov_mCYyjt2R1NObVYdr5ylKkErW0nUfiFfzZHIqXfWoH_7PGmmwY1NUPigY1Ae_6Q-a43VkWKOK4BxKMi8YiCu
        Button showFullScreenIntentButton = findViewById(R.id.showFullScreenIntentButton);
        showFullScreenIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = "Regular Notification";
                TextView textView = findViewById(R.id.editTextTextMultiLine);
                // Get the text from the TextView
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
                title = "Regular Notification";
                TextView textView = findViewById(R.id.editTextTextMultiLine);
                // Get the text from the TextView
                String text = textView.getText().toString();
                if(text.length()==0) return;
                description = text;
                type = "reg";
                notifyServerToSendNotification(apiUrl, title, description, type);
            }
        });
    }

    private void notifyServerToSendNotification(String apiUrl, String title, String description, String type) {
        try {
            String jsonData = "{\n" +
                    "    \"title\": \"" + title + "\",\n" +
                    "    \"description\": \"" + description + "\",\n" +
                    "    \"type\": \"" + type + "\"\n" +
                    "}";

            // Create URL object
            URL url = new URL(apiUrl);

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send request
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


}
