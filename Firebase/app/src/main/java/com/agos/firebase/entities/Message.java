package com.agos.firebase.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by arielortuno on 6/11/16.
 */
public class Message implements Serializable {

    private String userName;
    private String message;
    private Date date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
