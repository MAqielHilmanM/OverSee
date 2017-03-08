package com.smkn4bandung.oversee.views.menu.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.receiver.MainReceiver;
import com.smkn4bandung.oversee.tools.Constant;
import com.smkn4bandung.oversee.tools.PrefRepo;

public class ClientActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PrefRepo prefRepo;
    EditText etKey;
    TextView txtClient;
    Button btnClient;
    public static final String TAG = "ClientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        etKey = (EditText) findViewById(R.id.etKey);
        txtClient = (TextView) findViewById(R.id.txtClient);
        btnClient = (Button) findViewById(R.id.btnClient);


        firebaseDatabase = FirebaseDatabase.getInstance();
        prefRepo = new PrefRepo();

        if(!prefRepo.getTargetId().equals("")){
            etKey.setText(prefRepo.getTargetId());
            btnClient.setText("DISCONNECT");
            etKey.setEnabled(false);
            txtClient.setText("Connected To :");
        }

    }

    public void onClickKey(View v){
        Log.e(TAG, "onClickKey: "+prefRepo.getTargetId() );
        if(!TextUtils.isEmpty(etKey.getText().toString())){
            if (prefRepo.getTargetId().equals("")) {
                databaseReference = firebaseDatabase.getReference().child("host").child(prefRepo.getPhoneId()).child("client");
                databaseReference.setValue(etKey.getText().toString());
                databaseReference = firebaseDatabase.getReference().child("host").child(etKey.getText().toString()).child("client");
                databaseReference.setValue(prefRepo.getPhoneId());

                btnClient.setText("DISCONNECT");
                etKey.setEnabled(false);
                txtClient.setText("Connected To :");

                prefRepo.setTargetId();
            }else {
                databaseReference = firebaseDatabase.getReference().child("host").child(prefRepo.getPhoneId()).child("client");
                databaseReference.setValue("-");
                databaseReference = firebaseDatabase.getReference().child("host").child(etKey.getText().toString()).child("client");
                databaseReference.setValue("-");



                btnClient.setText("CONNECT");
                etKey.setEnabled(true);
                txtClient.setText("Input Host Id :");

                prefRepo.delTargetId();
                etKey.setText("");

            }
        }
        if(!Constant.IS_RUN_MAIN){
            Intent bc = new Intent(getBaseContext(), MainReceiver.class);
            sendBroadcast(bc);
        }
    }
}
