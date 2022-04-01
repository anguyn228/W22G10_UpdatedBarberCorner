package com.example.barbercornerproj.model;

public class MessageModel {
    private int userID;
    private String sender;
    private String message;

    public MessageModel(int userID, String sender, String message) {
        this.userID = userID;
        this.sender = sender;
        this.message = message;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
