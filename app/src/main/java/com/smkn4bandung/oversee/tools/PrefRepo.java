package com.smkn4bandung.oversee.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smkn4bandung.oversee.OverSee;

/**
 * Created by root on 3/8/17.
 */

public class PrefRepo {
    private Context ctx = OverSee.context;
    private String prefName = "OverSeePref";
    private String prefPhoneId = "PhoneId";

    public FirebaseDatabase getFirebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }

    public DatabaseReference getDatabaseReference(){
        return getFirebaseDatabase().getReference();
    }

    public SharedPreferences getSharedPreferences(){
        return ctx.getSharedPreferences(prefName,0);
    }
    public SharedPreferences.Editor getEditor(){
        return getSharedPreferences().edit();
    }

    public String getPhoneId(){
        return getSharedPreferences().getString(prefPhoneId,"");
    }

    public void setPhoneId(){
        String oldId = getPhoneId();
        if(!oldId.equals("")){
            getEditor().putString(prefPhoneId,Helper.randId());
            getEditor().apply();
            getDatabaseReference().child("host").child(getPhoneId()).child("client").setValue("-");
            getDatabaseReference().child("client").child(getPhoneId()).child("connection").setValue("success");
        }
    }


}
