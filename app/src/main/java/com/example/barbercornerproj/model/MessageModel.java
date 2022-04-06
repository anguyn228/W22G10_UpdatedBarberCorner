package com.example.barbercornerproj.model;

public class MessageModel {
    private int messageId;
    private int senderId;
    private int receiveId;
    private String message;

    public MessageModel(int senderId, int receiveId, String message) {
        this.senderId = senderId;
        this.receiveId = receiveId;
        this.message = message;
    }

    public MessageModel(int messageId, int senderId, int receiveId, String message) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiveId = receiveId;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }
}
