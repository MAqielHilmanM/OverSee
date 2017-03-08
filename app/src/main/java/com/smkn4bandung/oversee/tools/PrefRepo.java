package com.smkn4bandung.oversee.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smkn4bandung.oversee.OverSee;
import com.smkn4bandung.oversee.dao.AlarmDao;
import com.smkn4bandung.oversee.dao.ClientDao;
import com.smkn4bandung.oversee.dao.HostDao;
import com.smkn4bandung.oversee.dao.LockDao;
import com.smkn4bandung.oversee.dao.ReminderDao;

import java.util.Map;

/**
 * Created by root on 3/8/17.
 */

public class PrefRepo {
    private Context ctx = OverSee.context;
    private String prefName = "OverSeePref";
    private String prefPhoneId = "PhoneId";
    private String prefTargetId = "TargetId";
    private SharedPreferences mSharedPrefences;
    private SharedPreferences.Editor mEditor;
    private HostDao hostDao;
    private ClientDao clientDao;
    public String res = "";
    public static final String TAG = "PhoneID";

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

    public void getConnectedHost(){
        hostDao = new HostDao();
        getDatabaseReference().child("host").child(getPhoneId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hostDao = dataSnapshot.getValue(HostDao.class);
                Log.e(TAG, "onDataChange: "+dataSnapshot.toString() +" res : "+hostDao.getClient());


                mSharedPrefences = getSharedPreferences();
                mEditor = mSharedPrefences.edit();
                if(res.equals("")){
                    res = hostDao.getClient();
                    mEditor.putString(prefTargetId,res);
                }
                mSharedPrefences = getSharedPreferences();
                mEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setTargetId(){
        getConnectedHost();
    }

    public String getTargetId(){
        return getSharedPreferences().getString(prefTargetId,"");
    }
    public void delTargetId(){
        mSharedPrefences = getSharedPreferences();
        mEditor = mSharedPrefences.edit();
        mEditor.putString(prefTargetId,"");
        mEditor.commit();
    }

    public String getPhoneId(){
        return getSharedPreferences().getString(prefPhoneId,"");
    }

    public void setPhoneId(){
        mSharedPrefences = getSharedPreferences();
        mEditor = mSharedPrefences.edit();
        mEditor.putString(prefPhoneId,Helper.randId());
        mEditor.apply();

        getDatabaseReference().child("host").child(getPhoneId()).child("client").setValue("-");
        getDatabaseReference().child("client").child(getPhoneId()).child("connection").setValue("success");

        Map<String,Object> valuesAlarm = new AlarmDao("-",0,0,"OFF").toMap();
        getDatabaseReference().child("alarm").child(getPhoneId()).updateChildren(valuesAlarm);

        Map<String,Object> valuesLock = new LockDao("-","OFF").toMap();
        getDatabaseReference().child("unlock").child(getPhoneId()).updateChildren(valuesLock);

        Map<String,Object> valuesOffline = new LockDao("-","OFF").toMap();
        getDatabaseReference().child("offline").child(getPhoneId()).updateChildren(valuesOffline);

        Map<String,Object> valuesRemind = new ReminderDao("-",0,0,"OFF","-").toMap();
        getDatabaseReference().child("reminder").child(getPhoneId()).updateChildren(valuesRemind);

    }

    public void setLock(LockDao lock){
        Map<String,Object> valuesLock =lock.toMap();
        if(!getTargetId().equals(""))
        getDatabaseReference().child("unlock").child(getTargetId()).updateChildren(valuesLock);
    }


}
