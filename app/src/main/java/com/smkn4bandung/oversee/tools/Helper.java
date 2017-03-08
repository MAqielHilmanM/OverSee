package com.smkn4bandung.oversee.tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 3/8/17.
 */

public class Helper {

    public static final String TAG = "HELPER";

    public static String randId(){
        String header = "OSee-";
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();

        list.add(rand.nextInt(9));
        list.add(rand.nextInt(9));
        list.add(rand.nextInt(9));
        list.add(rand.nextInt(9));
        list.add(rand.nextInt(9));

        for (int i = 0; i <list.size() ; i++) {
            header = header.concat(String.valueOf(list.get(i)));
        }

        Log.e(TAG, "randId: "+header );

        return header;
    }

}
