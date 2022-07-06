package com.texasimaginology.ticms.Notification.PushNotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by deepbhai on 11/29/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String REG_TOKEN = "REG_TOKEN";
    @Override
    public void onTokenRefresh() {
        String device_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPref = getSharedPreferences("DeviceToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", device_token);
        editor.apply();
        Log.d(REG_TOKEN, device_token);
    }
}
