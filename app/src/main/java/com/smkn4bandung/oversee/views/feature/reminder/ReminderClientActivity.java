package com.smkn4bandung.oversee.views.feature.reminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smkn4bandung.oversee.R;

public class ReminderClientActivity extends AppCompatActivity {
    TextView txtMessage;
    ReminderActivity reminderActivity;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_client);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        reminderActivity = new ReminderActivity();

        String message = getIntent().getStringExtra("message");
        txtMessage.setText(message);
    }

    public void onClickDismiss(View v){
        reminderActivity.unsetReminder(this);
        this.finish();
    }
}
