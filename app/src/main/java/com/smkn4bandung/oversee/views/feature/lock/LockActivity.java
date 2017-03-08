package com.smkn4bandung.oversee.views.feature.lock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.dao.LockDao;
import com.smkn4bandung.oversee.tools.PrefRepo;

public class LockActivity extends AppCompatActivity {
    LockDao lockDao ;
    PrefRepo prefRepo;
    TextView txtKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        prefRepo = new PrefRepo();
        txtKey = (TextView) findViewById(R.id.txtLockTargetKey);
        txtKey.setText(prefRepo.getTargetId());
    }
    public void onClickUnlock(View v){
        prefRepo.setLock(new LockDao(prefRepo.getPhoneId(),"OFF"));
    }
    public void onClickLock(View v){
        prefRepo.setLock(new LockDao(prefRepo.getPhoneId(),"ON"));
    }
}
