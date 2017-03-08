package com.smkn4bandung.oversee.views.feature.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.views.menu.main.MainActivity;

public class AlarmClientActivity extends AppCompatActivity {

    Button btnUnsetAlarm;
    AlarmActivity alarmActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);
        alarmActivity = new AlarmActivity();
        btnUnsetAlarm = (Button) findViewById(R.id.btnUnsetAlarm);
    }
    public void OnClick (View v){
//        alarmInterface.unSet(SlideAlarmActivity.this);
        alarmActivity.unsetAlarm(this);
        this.finish();
    }
}
