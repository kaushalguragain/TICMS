package com.texasimaginology.ticms.Notification.PushNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.texasimaginology.ticms.R;

import java.util.Map;

/**
 * Created by deepbhai on 11/29/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private boolean isEnable;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        SharedPreferences sharedPreferences = getSharedPreferences("NotificationCheck" , Context.MODE_PRIVATE);
        Boolean checkNotification = sharedPreferences.getBoolean("isNotificationOn",true);

        Intent intent = new Intent(this, AllNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        Log.d("Message::::: {}", remoteMessage.getData().toString());
        Map<String,String> msg = remoteMessage.getData();
        intent.putExtra("title",msg.get("title"));
        intent.putExtra("body",msg.get("body"));
        intent.putExtra("faculty",msg.get("notificationType"));
        Log.d("setting::::",checkNotification.toString());
        if (checkNotification) {
            //intent.putExtra("semester",msg.get())
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            notificationBuilder.setContentTitle(msg.get("title"));
            notificationBuilder.setContentText(msg.get("body"));
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setSound(notification);
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());

        }
    }

}
