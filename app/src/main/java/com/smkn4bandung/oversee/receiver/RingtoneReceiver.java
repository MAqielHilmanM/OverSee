package com.smkn4bandung.oversee.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.smkn4bandung.oversee.service.RingtoneService;

/**
 * Created by root on 3/8/17.
 */

public class RingtoneReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We Are In Receiver.", "Yay!");

        String get_your_string = intent.getExtras().getString("extra");

        Log.e("What is the key ?", get_your_string);

        Intent service_intent = new Intent(context, RingtoneService.class);

        service_intent.putExtra("extra", get_your_string);

        context.startService(service_intent);
    }
}
