package com.smkn4bandung.oversee;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.smkn4bandung.oversee.receiver.MainReceiver;
import com.smkn4bandung.oversee.tools.PrefRepo;

import java.security.PublicKey;

/**
 * Created by root on 3/8/17.
 */

public class OverSee extends Application{
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
