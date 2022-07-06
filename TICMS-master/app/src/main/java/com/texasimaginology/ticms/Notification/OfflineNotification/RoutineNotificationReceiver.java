package com.texasimaginology.ticms.Notification.OfflineNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaActionSound;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.texasimaginology.ticms.MainActivity;
import com.texasimaginology.ticms.Notification.PushNotification.AllNotification;
import com.texasimaginology.ticms.R;
import com.texasimaginology.ticms.Settings;

import java.util.Calendar;

public class RoutineNotificationReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 100;
    public static final String NOTIFICATION_CHANNEL="routineChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MINUTE,10);
        Intent jumpIntent = new Intent(context, MainActivity.class);

        Bundle bundle= intent.getExtras();
        StringBuilder stringBuilder = new StringBuilder();
        if(bundle != null){
            stringBuilder.append("Hello Mr. ");
            stringBuilder.append(bundle.getString("Teacher",""));
            stringBuilder.append(", ");
            stringBuilder.append("Your class for ");
            stringBuilder.append(bundle.getString("Subject",""));
            stringBuilder.append(" is going to start after 10 minutes in ");
            stringBuilder.append(bundle.getString("Course",""));
            stringBuilder.append(" ");
            stringBuilder.append(bundle.getString("Semester",""));
            stringBuilder.append(" semester");
            stringBuilder.append(" at ");
            stringBuilder.append(calendar.get(Calendar.HOUR));
            stringBuilder.append(":");
            stringBuilder.append(calendar.get(Calendar.MINUTE));
        }

        jumpIntent.putExtra("FromOfflineNotification", "TeacherDashboard");
        jumpIntent.putExtra("NotificationBody",stringBuilder.toString());
        jumpIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, jumpIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d("OnReceive::","Called ");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(),NOTIFICATION_CHANNEL);
            builder.setContentTitle("Texas Class Alert!!!");
            builder.setContentText(stringBuilder)
                    .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(stringBuilder));
            builder.setSmallIcon(R.mipmap.ic_launcher);
            Bitmap texasLogo =BitmapFactory.decodeResource(context.getResources(),R.drawable.logo);
            builder.setLargeIcon(texasLogo);
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setContentIntent(pIntent);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            builder.setAutoCancel(true);
            builder.setChannelId(NOTIFICATION_CHANNEL);

            Notification notification = builder.build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(99, notification);
        }



    }


}
