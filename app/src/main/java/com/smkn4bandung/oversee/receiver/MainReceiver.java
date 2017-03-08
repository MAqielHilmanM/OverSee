package com.smkn4bandung.oversee.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smkn4bandung.oversee.service.MainService;

/**
 * Created by root on 3/8/17.
 */

public class MainReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context, MainService.class);
        context.startService(in);
    }
}
