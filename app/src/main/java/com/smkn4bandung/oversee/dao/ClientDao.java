package com.smkn4bandung.oversee.dao;

/**
 * Created by maqie on 08/03/2017.
 */

public class ClientDao {
    public String connection;

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public ClientDao(String connection) {

        this.connection = connection;
    }

    public ClientDao() {

    }
}
