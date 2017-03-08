package com.smkn4bandung.oversee.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.tools.Constant;
import com.smkn4bandung.oversee.views.feature.alarm.AlarmActivity;
import com.smkn4bandung.oversee.views.feature.alarm.AlarmClientActivity;
import com.smkn4bandung.oversee.views.feature.reminder.ReminderClientActivity;

/**
 * Created by root on 3/8/17.
 */

public class RingtoneService extends Service{

    MediaPlayer media_song;
    int startId;
    Boolean isRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Constant.IS_RUN_RINGTONE = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");
        String message = intent.getExtras().getString("message");

        Log.e("Ringtone extra is ", state);

        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), AlarmActivity.class);

        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click Me")
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentIntent(pending_intent_main_activity)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();


        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "off":
                startId = 0;
                Log.e("Start ID is", state);
                break;
            default:
                startId = 0;
                break;
        }


        if (!this.isRunning && startId == 1){

            Log.e("there is no music","and you want start");

            media_song = MediaPlayer.create(this, R.raw.allthetime);
            media_song.start();

            this.isRunning = true;
            this.startId = 0;
            notify_manager.notify(NotificationManager.INTERRUPTION_FILTER_PRIORITY,notification_popup);
            Intent in ;
            if(message.equals(""))
            in = new Intent(getApplicationContext(),AlarmClientActivity.class);
            else
            in = new Intent(getApplicationContext(), ReminderClientActivity.class);
            in.putExtra("message",message);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in);

        }else if (this.isRunning && startId == 0){
            Log.e("there is music","and you want start");

            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;

        }else if (!this.isRunning && startId == 0){
            Log.e("there is no music","and you want start");

            this.isRunning = false;
            this.startId = 0;

        }else if (this.isRunning && startId == 1){
            Log.e("there is music","and you want start");

            this.isRunning = true;
            this.startId = 1;

        }else {
            Log.e("else","somehow you reached this");
        }


        return START_NOT_STICKY;
    }



    @Override
    public void onDestroy() {
        // Tell the user we stopped.

        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
        Constant.IS_RUN_RINGTONE = false;
    }
}
