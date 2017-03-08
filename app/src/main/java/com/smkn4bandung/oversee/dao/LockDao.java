package com.smkn4bandung.oversee.dao;

/**
 * Created by root on 3/8/17.
 */

public class LockDao {
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

    public LockDao(String host, String status) {

        this.host = host;
        this.status = status;
    }

    public LockDao() {

    }
}
