package rs.co.sbb.workorders.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.HomeActivity;
import rs.co.sbb.workorders.activity.WorkordersActivity;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{


    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.i(TAG, "From: " + remoteMessage.getFrom());
        Log.i(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        remoteMessage.getMessageId();
        sendNotification(notification.getBody(),notification.getTitle());
    }

    private void sendNotification(String messageBody, String title){
        Intent intent = new Intent(this, WorkordersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,notificationBuilder.build());


    }

}
