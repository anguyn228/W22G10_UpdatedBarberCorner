package com.example.barbercornerproj.model;

public class MessageModel {
    public static final String MESSAGE_TYPE_SEND = "send";
    public static final String MESSAGE_TYPE_RECEIVE = "receive";

    private int userID;
    private String sender;
    private String message;
    private String messageType;

    public MessageModel(int userID, String sender, String message, String messageType) {
        this.userID = userID;
        this.sender = sender;
        this.message = message;
        this.messageType = messageType;
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

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
