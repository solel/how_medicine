package com.example.how_medicine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {
    private Context context;
    private String channelId="alarm_channel";
    private int code;
    private String name;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        intent.getIntExtra("code", code);
        name = intent.getStringExtra("name");

        Intent busRouteIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(busRouteIntent);
        PendingIntent busRoutePendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_IMMUTABLE);

        final NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.ic_launcher).setDefaults(Notification.DEFAULT_VIBRATE)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setContentTitle("알람")
                .setContentText(name)
                .setContentIntent(busRoutePendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        final NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        int id=(int)System.currentTimeMillis();

        notificationManager.notify(id,notificationBuilder.build());

    }
}
