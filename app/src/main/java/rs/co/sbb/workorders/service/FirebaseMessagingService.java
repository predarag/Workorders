package rs.co.sbb.workorders.service;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.WorkordersActivity;
import rs.co.sbb.workorders.utils.SaveSharedPreference;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    private static final String TAG = "FCM Service";

    private int UNIQUE_INT_PER_CALL = 0;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(getApplication().POWER_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (!powerManager.isInteractive()) {

                PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyLock");
                wakeLock.acquire();
            }
        }

    /*    RemoteMessage.Notification notification = remoteMessage.getNotification();
        remoteMessage.getMessageId();*/
        sendNotification(remoteMessage.getData());
    }

    private void sendNotification(Map<String, String> data) {

        Intent intent = null;

        if (null != data) {
            String dataType = "";
            if (data.containsKey("dataType"))
                dataType = data.get("dataType");
            else
                return;


            switch (dataType) {
                case "totaltv_activation":
                    Log.i(TAG, "totaltv_activation");
                    intent = new Intent(this,HomeActivity.class);
                    final Bundle extra = new Bundle();
                    extra.putString("contract", data.get("contract"));
                    extra.putString("partnerNo", data.get("partnerNo"));
                    extra.putString("time", data.get("time"));

                    intent.putExtras(extra);
                    break;
                case "new_activation":
                    intent = new Intent(this, WorkordersActivity.class);
                    break;
                default:

                    break;


            }

        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, UNIQUE_INT_PER_CALL++, intent, PendingIntent.FLAG_UPDATE_CURRENT); //

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("text"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());


    }

}

