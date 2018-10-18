package com.example.android.chattous.Model;

public class Chat {

    private String sender;
    private String reciver;
    private String message;
    private String bm;


    public Chat(String sender, String reciver, String message, String bm) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.bm = bm;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String receiver) {
        this.reciver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }
}
