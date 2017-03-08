package com.smkn4bandung.oversee.dao;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/8/17.
 */

public class ShutdownDao {
    public String host;
    public String status;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ShutdownDao() {
    }

    public ShutdownDao(String host, String status) {
        this.host = host;
        this.status = status;
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("host",host);
        result.put("status",status);
        return result;
    }
}
