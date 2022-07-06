package com.texasimaginology.ticms;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {
    private ToggleButton notificationOnOrOff;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        notificationOnOrOff=findViewById(R.id.toggleButton);
        notificationOnOrOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationOnOrOff.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences("NotificationCheck" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean("isNotificationOn",true);
                    editor.apply();
                }
                else{
                    SharedPreferences sharedPreferences = getSharedPreferences("NotificationCheck" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putBoolean("isNotificationOn",false);
                    editor.apply();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationCheck" , Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isNotificationOn",true)){
            notificationOnOrOff.setChecked(true);
        }
        else{
            notificationOnOrOff.setChecked(false);
        }
        super.onStart();
    }
}
