package com.smkn4bandung.oversee.dao;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/8/17.
 */

public class ReminderDao {
    public String host;
    public int hour;
    public int minute;
    public String status;
    public String message;

    public ReminderDao(String host, int hour, int minute, String status, String message) {
        this.host = host;
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.message = message;
    }

    public ReminderDao() {

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("host",host);
        result.put("hour",hour);
        result.put("minute",minute);
        result.put("status",status);
        result.put("message",message);
        return result;
    }
}


