package com.smkn4bandung.oversee.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bandung.oversee.dao.AlarmDao;
import com.smkn4bandung.oversee.dao.ClientDao;
import com.smkn4bandung.oversee.dao.HostDao;
import com.smkn4bandung.oversee.dao.LockDao;
import com.smkn4bandung.oversee.dao.ReminderDao;
import com.smkn4bandung.oversee.dao.ShutdownDao;
import com.smkn4bandung.oversee.tools.Constant;
import com.smkn4bandung.oversee.tools.PrefRepo;
import com.smkn4bandung.oversee.views.feature.alarm.AlarmActivity;
import com.smkn4bandung.oversee.views.feature.lock.LockActivity;
import com.smkn4bandung.oversee.views.feature.lock.LockClientActivity;

import java.util.Calendar;

/**
 * Created by root on 3/8/17.
 */

public class MainService extends Service{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    HostDao hostDao;
    ClientDao clientDao;
    LockDao lockDao;
    AlarmDao alarmDao;
    ShutdownDao shutdownDao;
    ReminderDao reminderDao;
    public boolean isConnected  = false;
    public static final String TAG = "OVERSEE";
    private String key;
    PrefRepo prefRepo;
    AlarmActivity alarmActivity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        prefRepo = new PrefRepo();

        hostDao = new HostDao();
        clientDao = new ClientDao();
        lockDao = new LockDao();
        reminderDao = new ReminderDao();
        alarmDao = new AlarmDao();
        shutdownDao = new ShutdownDao();
        firebaseDatabase = FirebaseDatabase.getInstance();

        key = prefRepo.getPhoneId();
        alarmActivity = new AlarmActivity();

        Constant.IS_RUN_MAIN = true;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        databaseReference = firebaseDatabase.getReference().child("host").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf("Oversee", "onDataChange: "+dataSnapshot.toString());
                hostDao = dataSnapshot.getValue(HostDao.class);
                if(!hostDao.getClient().equals("-"))
                    sendNotifRequest(hostDao.getClient());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference().child("client").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf(TAG, "onDataChange: "+dataSnapshot.toString() );
                clientDao = dataSnapshot.getValue(ClientDao.class);
                if(clientDao.getConnection().equals("success")){
                    isConnected = true;
                }else {
                    isConnected = false;
                }
                Log.wtf(TAG, "onStartCommand: "+isConnected );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference().child("unlock").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf(TAG, "onDataChange: unlock : "+dataSnapshot.toString() );
                if(isConnected){
                    lockDao = dataSnapshot.getValue(LockDao.class);
                    if(lockDao.getStatus().equals("ON")){
                        Intent in = new Intent(getApplicationContext(), LockClientActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }else{
                        System.exit(0);
                    }
                    Toast.makeText(getApplicationContext(),"Lock "+lockDao.getStatus()+" by"+lockDao.getHost(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference().child("alarm").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.wtf(TAG, "onDataChange: "+dataSnapshot );
                alarmDao = dataSnapshot.getValue(AlarmDao.class);
                if(isConnected){
                    if(alarmDao.getStatus().equals("ON")){
                        alarmActivity.setAlarm(getApplicationContext(),alarmDao.hour,alarmDao.minute);
                    }else{
                            alarmActivity.unsetAlarm(getApplicationContext());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }

    private void sendNotifRequest(String id_user) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(this)
                .setContentTitle("Has Connected")
                .setContentText("Connect to "+id_user)
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .build();
        nm.notify(NotificationManager.IMPORTANCE_HIGH,notif);

    }
    private void sendNotifAlarm(String id_user,int hour,int minute) {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(this)
                .setContentTitle("Alarm Set")
                .setContentText("Alarm Set to "+hour+":"+minute)
                .setSmallIcon(android.R.drawable.alert_light_frame)
                .build();
        nm.notify(NotificationManager.IMPORTANCE_HIGH,notif);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.IS_RUN_MAIN = false;
    }
}
