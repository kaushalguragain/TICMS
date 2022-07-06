package com.texasimaginology.ticms.Notification.PushNotification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.texasimaginology.ticms.R;

public class NotificationDetail extends AppCompatActivity{
private TextView notificationTitle,notificationMessage,notificationSemester;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    notificationTitle=findViewById(R.id.notification_title);
    notificationMessage=findViewById(R.id.notification_messages);
    notificationSemester=findViewById(R.id.notification_semesters);

    notificationTitle.setText(getIntent().getStringExtra("title"));
    notificationSemester.setText(getIntent().getStringExtra("semester"));
    notificationMessage.setText(getIntent().getStringExtra("message"));
    }


}
