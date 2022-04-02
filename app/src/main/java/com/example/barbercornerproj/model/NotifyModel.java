package com.example.barbercornerproj.model;

public class NotifyModel {
    private int id;
    private String title;
    private String description;
    private int receiveUserId;

    public NotifyModel(String title, String description, int receiveUserId) {
        this.title = title;
        this.description = description;
        this.receiveUserId = receiveUserId;
    }

    public NotifyModel(int id, String title, String description, int receiveUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.receiveUserId = receiveUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(int receiveUserId) {
        this.receiveUserId = receiveUserId;
    }
}
