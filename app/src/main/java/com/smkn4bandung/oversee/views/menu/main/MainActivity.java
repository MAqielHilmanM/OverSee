package com.smkn4bandung.oversee.views.menu.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.receiver.MainReceiver;
import com.smkn4bandung.oversee.tools.Helper;
import com.smkn4bandung.oversee.tools.PrefRepo;
import com.smkn4bandung.oversee.views.menu.client.ClientActivity;
import com.smkn4bandung.oversee.views.menu.host.HostActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrefRepo prefRepo = new PrefRepo();
        if(prefRepo.getPhoneId().equals(""))
        prefRepo.setPhoneId();


    }

    public void onClickHost(View v){
        startActivity(new Intent(this,HostActivity.class));
    }

    public void onClickClient(View v){
        startActivity(new Intent(this, ClientActivity.class));
    }

    public void onClickExit(View v){
        System.exit(0);
    }
}
