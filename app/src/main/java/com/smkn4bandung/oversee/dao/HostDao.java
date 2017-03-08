package com.smkn4bandung.oversee.dao;

/**
 * Created by maqie on 08/03/2017.
 */

public class HostDao {
    public String client;

    public HostDao(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public HostDao() {

    }
}
