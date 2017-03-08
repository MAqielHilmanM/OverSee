package com.smkn4bandung.oversee.views.menu.host;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.tools.PrefRepo;
import com.smkn4bandung.oversee.views.feature.alarm.AlarmActivity;
import com.smkn4bandung.oversee.views.feature.lock.LockActivity;
import com.smkn4bandung.oversee.views.feature.reminder.ReminderActivity;
import com.smkn4bandung.oversee.views.feature.shutdown.ShutdownActivity;

public class HostActivity extends AppCompatActivity {
    TextView txtIdKey;
    PrefRepo prefRepo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        prefRepo = new PrefRepo();

        txtIdKey = (TextView) findViewById(R.id.txtIdKey);
        txtIdKey.setText(prefRepo.getPhoneId());

        context = this;

    }

    public void onClickAlarm(View v){
        startActivity( new Intent(context, AlarmActivity.class));
    }

    public void onClickMainLock(View v){
        startActivity( new Intent(context, LockActivity.class));
    }

    public void onClickReminder(View v){
        startActivity( new Intent(context, ReminderActivity.class));
    }

    public void onClickShutdown(View v){
        startActivity( new Intent(context, ShutdownActivity.class));
    }
}
