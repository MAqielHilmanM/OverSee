package com.smkn4bandung.oversee.views.menu.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smkn4bandung.oversee.R;
import com.smkn4bandung.oversee.tools.PrefRepo;

public class ClientActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PrefRepo prefRepo;
    EditText etKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

//        etKey = (EditText) findViewById(R.id.etKey);


        firebaseDatabase = FirebaseDatabase.getInstance();
        prefRepo = new PrefRepo();
    }

    public void onClickKey(View v){
        databaseReference = firebaseDatabase.getReference().child("host").child(etKey.getText().toString()).child("client");
        databaseReference.setValue(prefRepo.getPhoneId());
    }
}
