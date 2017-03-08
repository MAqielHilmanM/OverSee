package com.smkn4bandung.oversee.views.feature.alarm;

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
import android.widget.TextView;
import android.widget.TimePicker;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.receiver.RingtoneReceiver;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    AlarmManager alarmManager;
    PendingIntent pending_intent;
    Calendar calendar;
    Intent my_intent;
    public static final String TAG = "MODUL ALARM";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.context = this;

        update_text = (TextView) findViewById(R.id.update_text);
        calendar = Calendar.getInstance();
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager)  getSystemService(ALARM_SERVICE);

        my_intent = new Intent(this.context, RingtoneReceiver.class);

        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                int hour = 0;
                int minute = 0;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.wtf(TAG, "onClick: Hour: "+alarm_timepicker.getHour() );
                    Log.wtf(TAG, "onClick: Minute: "+alarm_timepicker.getMinute() );
                    calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                    calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                    hour = alarm_timepicker.getHour();
                    minute = alarm_timepicker.getMinute();
                }else {
                    Log.wtf(TAG, "onClick: CurHour: "+alarm_timepicker.getCurrentHour() );
                    Log.wtf(TAG, "onClick: CurMinute: "+alarm_timepicker.getCurrentMinute() );

                    calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarm_timepicker.getCurrentMinute());

                    hour = alarm_timepicker.getCurrentHour();
                    minute = alarm_timepicker.getCurrentMinute();
                }
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour > 12){
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    minute_string = "0" + minute_string;
                }

                set_alarm_text("Alarm set to : " + hour_string + ":" + minute_string);

                my_intent.putExtra("extra", "alarm on");

                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0 ,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);

            }
        });

        Button alarm_off = (Button) findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){

                unsetAlarm(AlarmActivity.this);
                set_alarm_text("Alarm Off!");
            }
        });
    }

    public void unsetAlarm(Context context) {


        my_intent = new Intent(AlarmActivity.this, RingtoneReceiver.class);

        alarmManager = (AlarmManager)  context.getSystemService(ALARM_SERVICE);

        pending_intent = PendingIntent.getBroadcast(context, 0 ,
                my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pending_intent);

        Intent my_receiver = new Intent(context, RingtoneReceiver.class);

        my_receiver.putExtra("extra", "off");

        context.sendBroadcast(my_receiver);
    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

}
