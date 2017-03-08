package com.smkn4bandung.oversee.views.feature.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.dao.AlarmDao;
import com.smkn4bandung.oversee.dao.ReminderDao;
import com.smkn4bandung.oversee.receiver.RingtoneReceiver;
import com.smkn4bandung.oversee.tools.PrefRepo;
import com.smkn4bandung.oversee.views.feature.alarm.AlarmActivity;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    AlarmManager alarmManager;
    PendingIntent pending_intent;
    Calendar calendar;
    Intent my_intent;
    public static final String TAG = "MODUL Reminder";
    private PrefRepo prefRepo;
    EditText etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        this.context = this;
        prefRepo = new PrefRepo();

        etMessage = (EditText) findViewById(R.id.etMessage);
        alarm_timepicker = (TimePicker) findViewById(R.id.timePickerReminder);

        Button alarm_on = (Button) findViewById(R.id.btnReminderOn);
        alarm_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                int hour = 0;
                int minute = 0;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.wtf(TAG, "onClick: Hour: "+alarm_timepicker.getHour() );
                    Log.wtf(TAG, "onClick: Minute: "+alarm_timepicker.getMinute() );

                    hour = alarm_timepicker.getHour();
                    minute = alarm_timepicker.getMinute();
                }else {
                    Log.wtf(TAG, "onClick: CurHour: "+alarm_timepicker.getCurrentHour() );
                    Log.wtf(TAG, "onClick: CurMinute: "+alarm_timepicker.getCurrentMinute() );

                    hour = alarm_timepicker.getCurrentHour();
                    minute = alarm_timepicker.getCurrentMinute();
                }

                String message = etMessage.getText().toString();
                setOnlineReminder(hour,minute,message);

            }
        });

        Button alarm_off = (Button) findViewById(R.id.btnReminderOff);
        alarm_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                unsetReminder(ReminderActivity.this);
            }
        });
    }
    private void setOnlineReminder(int hour,int minute,String message) {
        ReminderDao reminderDao = new ReminderDao(prefRepo.getPhoneId(),hour,minute,"ON",message);
        prefRepo.setReminder(reminderDao);
    }


    public void unsetReminder(Context context) {


        my_intent = new Intent(ReminderActivity.this, RingtoneReceiver.class);

        alarmManager = (AlarmManager)  context.getSystemService(ALARM_SERVICE);

        pending_intent = PendingIntent.getBroadcast(context, 0 ,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pending_intent);

        Intent my_receiver = new Intent(context, RingtoneReceiver.class);

        my_receiver.putExtra("extra", "alarm off");
        my_receiver.putExtra("message", "");

        context.sendBroadcast(my_receiver);
    }

    public  void  setReminder(Context context,int hour,int minute,String message){
        alarmManager = (AlarmManager)  context.getSystemService(ALARM_SERVICE);
        my_intent = new Intent(context, RingtoneReceiver.class);
        my_intent.putExtra("extra", "alarm on");
        my_intent.putExtra("message", message);

        calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        pending_intent = PendingIntent.getBroadcast(context, 0 ,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pending_intent);
    }


}
